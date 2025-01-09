package fr.trxyy.alternative.alternative_api.updater;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameVerifier;
import fr.trxyy.alternative.alternative_api.utils.Logger;
import fr.trxyy.alternative.alternative_api.utils.file.FileUtil;

/**
 * @author Trxyy
 */
public class Downloader extends Thread {
	/**
	 * The download url
	 */
	private final String url;
	/**
	 * The Sha1
	 */
	private final String sha1;
	/**
	 * The file location
	 */
	private final File file;
	/**
	 * The gameEngine instance
	 */
	private GameEngine engine;
	
	/**
	 * Run the Thread
	 */
	public void run() {
		try {
			download();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The Constructor
	 * @param file The file
	 * @param url The Url
	 * @param sha1 The Sha1
	 * @param engine_ The gameEngine instance
	 */
	public Downloader(File file, String url, String sha1, GameEngine engine_) {
		this.file = file;
		this.url = url;
		this.sha1 = sha1;
		this.engine = engine_;
		GameVerifier.addToFileList(file.getAbsolutePath().replace(engine.getGameFolder().getGameDir().getAbsolutePath(), "").replace("\\", "/"));
		file.getParentFile().mkdirs();
	}

	/**
	 * Download the file ion question
	 * @throws IOException
	 */
	public void download() throws IOException {
		Logger.log("Acquiring file '" + this.file.getName() + "'");
		engine.getGameUpdater().setCurrentFile(this.file.getName());
		if (this.file.getAbsolutePath().contains("assets")) {
			engine.getGameUpdater().setCurrentInfoText("Téléchargement d'une ressource...");
		}
		else if (this.file.getAbsolutePath().contains("java-runtime-gamma")) {
			engine.getGameUpdater().setCurrentInfoText("Téléchargement de java...");
		}
		else {
			engine.getGameUpdater().setCurrentInfoText("Téléchargement du jeu...");
		}
		BufferedInputStream bufferedInputStream = null;
		FileOutputStream fileOutputStream = null;

		int retries = 5;
		int retryCount = 0;
		boolean success = false;

		while (retryCount < retries && !success) {
			try {
				URL downloadUrl = new URL(this.url.replace(" ", "%20"));
				URLConnection urlConnection = downloadUrl.openConnection();
				urlConnection.setConnectTimeout(10000);
				urlConnection.setReadTimeout(10000);
				urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");

				bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
				fileOutputStream = new FileOutputStream(this.file);

				byte[] data = new byte[1024];
				int read;

				while ((read = bufferedInputStream.read(data, 0, 1024)) != -1) {
					fileOutputStream.write(data, 0, read);
					this.engine.getGameUpdater().downloadedSize += read;
				}
				engine.getGameUpdater().downloadedFiles++;
				success = true;
			} catch (IOException e) {
				// Handle the error
				if (retryCount < retries) {
					Logger.log("Download failed (attempt " + (retryCount + 1) + "). Retrying...");
					retryCount++;
					try {
						Thread.sleep(2000);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
					}
				} else {
					Logger.log("Download failed after " + retries + " attempts.");
					throw e;
				}
			} finally {
				if (bufferedInputStream != null) {
					try {
						bufferedInputStream.close();
					} catch (IOException e) {
						Logger.log("Error closing input stream.");
					}
				}
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						Logger.log("Error closing output stream.");
					}
				}
			}
		}
	}

	public String getUrl() {
		return this.url;
	}
	
	/**
	 * @return If the file require a update
	 */
	public boolean requireUpdate() {
		if ((this.file.exists()) && (FileUtil.matchSHA1(this.file, this.sha1))) {
			return false;
		}
		return true;
	}
}