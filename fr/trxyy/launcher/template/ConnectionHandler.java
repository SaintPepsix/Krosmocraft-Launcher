package fr.trxyy.launcher.template;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;

import fr.trxyy.alternative.alternative_api.Infos;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ConnectionHandler extends Service<ConnectionStatus> {
	private KeyPair clientKeyPair;
	private String clientPublicKeyJson;
	private RSAPublicKey serverPublicKey;

	public ConnectionHandler() {
		CookieHandler.setDefault(new CookieManager());
	}
	  
	private ConnectionStatus connect(String username, String password) {
	  if (!hasInternet()) {
		  return ConnectionStatus.NoInternet;
	  }
	  if (!handshake(username)) {
		  return ConnectionStatus.ServerDown;
	  }
	  return sendLoginPost(username, password);
	}
	  
	private boolean hasInternet() {
		try {
			URL url = new URL("http://google.com");
	        final URLConnection conn = url.openConnection();
	        conn.connect();
	        conn.getInputStream().close();
	        return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Get server public key for connecting user
	private boolean handshake(String username) {
		try {
			if (clientKeyPair == null) {
	            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
	            generator.initialize(2048, new SecureRandom());
	            clientKeyPair = generator.generateKeyPair();
	            clientPublicKeyJson = String.format("{\"algorithm\":\"%s\",\"encoded\":\"%s\"}", clientKeyPair.getPublic().getAlgorithm(),
						java.util.Base64.getEncoder().encodeToString(clientKeyPair.getPublic().getEncoded()));
			}
	            
			URL obj = new URL("http://" + Infos.serverIp + ":6969/handshake");
				
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

			conn.setRequestMethod("POST");
			conn.setUseCaches(false);

			actLikeABrowser(conn);

			conn.setDoOutput(true);
			conn.setDoInput(true);
				
			// Send post request
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.write((clientPublicKeyJson + "_NAME_" + username).getBytes());
			wr.flush();
			wr.close();
				
			BufferedReader in =
		            new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine + '\n');
			}
			in.close();
				
		    // Parse the JSON string and create a JSONObject instance
		    JSONObject publicKeyJsonObj = (JSONObject) new JSONParser().parse(response.toString());
		    String algorithm = (String) publicKeyJsonObj.get("algorithm");
		    byte[] encodedKey = Base64.getDecoder().decode((String) publicKeyJsonObj.get("encoded"));

		    if (algorithm.equals("RSA")) {
			    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
			    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
			    serverPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		    }
		    else {
		    	throw new Exception();
		    }
		    return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	  
	public void actLikeABrowser(URLConnection conn) {
		conn.setRequestProperty("User-Agent", "Mozilla/5.0");
		conn.setRequestProperty("Accept",
		"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
		conn.setRequestProperty("Accept-Language", "fr-FR,fr;q=0.9");
	}


	// Send encrypted username and password to login server for authentification and retrieve token
	private ConnectionStatus sendLoginPost(String username, String password) {
		try {
			URL obj = new URL("http://" + Infos.serverIp + ":6969/login");
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
				
			actLikeABrowser(conn);
				
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, serverPublicKey);
			// Encrypt
		    byte[] encryptedLoginInfo = cipher.doFinal((username + "_PS_" + password).getBytes(StandardCharsets.UTF_8));

			conn.setRequestProperty("Referer", "https://krosmocraft.fr/login");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", Integer.toString(encryptedLoginInfo.length));

			conn.setDoOutput(true);
			conn.setDoInput(true);

			// Send post request
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.write(encryptedLoginInfo);
			wr.flush();
			wr.close();

			int responseCode = conn.getResponseCode();
				
			if (responseCode == 200) {
				// Decrypt response
				String response = decrypt(conn.getInputStream().readAllBytes());
				conn.getInputStream().close();
				if (response.contains("_RES_") && response.split("_RES_").length == 4) {
					Infos.pseudo = response.split("_RES_")[0];
					Infos.token = response.split("_RES_")[1];
					Infos.version = response.split("_RES_")[2];
					Infos.playerCount = response.split("_RES_")[3];
					fillInfo(Infos.pseudo);
					return ConnectionStatus.Success;
				}
				else {
					return ConnectionStatus.WrongCredentials;
				}
			}
			else if (responseCode == 201) {
				conn.getInputStream().close();
				return ConnectionStatus.FillPseudo;
			}
			else {
				conn.getInputStream().close();
				return ConnectionStatus.WrongCredentials;
			}
	  }
	  catch(Exception e) {
		  e.printStackTrace();
		  return ConnectionStatus.WrongCredentials;
	  }
	}
	  
	private String decrypt(byte[] encrypted) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
	    cipher.init(Cipher.DECRYPT_MODE, clientKeyPair.getPrivate());
	    byte[] decryptedPassword = cipher.doFinal(encrypted);
	    return new String(decryptedPassword, StandardCharsets.UTF_8);
	}

	private void fillInfo(String pseudo) throws ClientProtocolException, IOException {
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    HttpGet httpget = new HttpGet("https://krosmocraft.fr/profile/" + pseudo);

		// Send get request
	    HttpResponse httpresponse = httpclient.execute(httpget);
	    Scanner sc = new Scanner(httpresponse.getEntity().getContent());
	    StringBuffer sb = new StringBuffer();
	    while(sc.hasNext()) {
	       sb.append(sc.next());
	    }
	    //Retrieving the String from the String Buffer object
	    String result = sb.toString();
	    //Removing the HTML tags
	    result = result.replaceAll("<[^>]*>", "");
	    int x = result.indexOf("Grade");
	    if (x != -1) {
			Infos.grade = result.substring(x).substring(5).split("NombredeJetonde")[0];
		}
	    x = result.indexOf("NombredeJetondevotes");
	    if (x != -1) {
			Infos.jVotes = result.substring(x).substring(20).split("JetondevotesNomb")[0];
		}
	    x = result.indexOf("Nombred'Ogrines");
	    if (x != -1) {
			Infos.ogrines = result.substring(x).substring(15).split("OgrinesNombre")[0];
		}
	    sc.close();
	}

	// Send encrypted pseudo, username, password to login server for authentification
	// If pseudo is valid and set, retrieve token
	private ConnectionStatus sendPseudoRequest(String username, String password, String pseudo) {
		try {
			URL obj = new URL("http://" + Infos.serverIp + ":6969/pseudo");
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

			conn.setRequestMethod("POST");
			conn.setUseCaches(false);

			actLikeABrowser(conn);

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, serverPublicKey);
			// Encrypt
			byte[] encryptedLoginInfo = cipher.doFinal((username + "_.=._" + password + "_.#._" + pseudo).getBytes(StandardCharsets.UTF_8));

			conn.setRequestProperty("Referer", "https://krosmocraft.fr/login");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", Integer.toString(encryptedLoginInfo.length));

			conn.setDoOutput(true);
			conn.setDoInput(true);

			// Send post request
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.write(encryptedLoginInfo);
			wr.flush();
			wr.close();

			int responseCode = conn.getResponseCode();

			if (responseCode == 200) {
				String response = decrypt(conn.getInputStream().readAllBytes());
				conn.getInputStream().close();
				if (response.contains("_RES_") && response.split("_RES_").length == 4) {
					Infos.pseudo = response.split("_RES_")[0];
					Infos.token = response.split("_RES_")[1];
					Infos.version = response.split("_RES_")[2];
					Infos.playerCount = response.split("_RES_")[3];
					fillInfo(Infos.pseudo);
					return ConnectionStatus.Success;
				}
				else {
					return ConnectionStatus.Relog;
				}
			}
			else if (responseCode == 201) {
				int res = Integer.parseInt(new String(conn.getInputStream().readAllBytes()));
				conn.getInputStream().close();
				switch (res) {
					case 1:
						return ConnectionStatus.AlreadyTaken;
					case 2:
						return ConnectionStatus.Invalid;
					case 3:
						return ConnectionStatus.BadSize;
					default:
						return ConnectionStatus.Relog;
				}
			}
			else {
				conn.getInputStream().close();
				return ConnectionStatus.Relog;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return ConnectionStatus.Relog;
		}
	}

	private String username;
	private String password;
	private String pseudo;

	public void start(String username, String password){
		if (this.isRunning()){
			return;
		}
		this.username = username;
		this.password = password;
		this.pseudo = null;
		super.restart();
	}

	public void start(String username, String password, String pseudo){
		if (this.isRunning()){
			return;
		}
		this.username = username;
		this.password = password;
		this.pseudo = pseudo;
		super.restart();
	}

	@Override
	protected Task<ConnectionStatus> createTask() {
		return new Task<ConnectionStatus>(){
			  @Override
			  protected ConnectionStatus call() throws Exception {
			if (pseudo == null){
				return connect(username, password);
			}
			else{
				return sendPseudoRequest(username, password, pseudo);
			}
			  }
		};
	}
}
