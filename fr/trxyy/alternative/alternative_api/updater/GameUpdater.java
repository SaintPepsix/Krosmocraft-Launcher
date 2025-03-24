package fr.trxyy.alternative.alternative_api.updater;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import fr.trxyy.alternative.alternative_api.Infos;
import fr.trxyy.launcher.template.LauncherPanel;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.GameVerifier;
import fr.trxyy.alternative.alternative_api.assets.AssetIndex;
import fr.trxyy.alternative.alternative_api.assets.AssetObject;
import fr.trxyy.alternative.alternative_api.minecraft.java.JVMFile;
import fr.trxyy.alternative.alternative_api.minecraft.java.JVMManifest;
import fr.trxyy.alternative.alternative_api.minecraft.json.MinecraftLibrary;
import fr.trxyy.alternative.alternative_api.minecraft.json.MinecraftVersion;
import fr.trxyy.alternative.alternative_api.minecraft.utils.Arch;
import fr.trxyy.alternative.alternative_api.minecraft.utils.CompatibilityRule;
import fr.trxyy.alternative.alternative_api.minecraft.utils.EnumJavaOS;
import fr.trxyy.alternative.alternative_api.minecraft.utils.EnumJavaVersion;
import fr.trxyy.alternative.alternative_api.minecraft.utils.EnumJavaFileType;
import fr.trxyy.alternative.alternative_api.utils.Logger;
import fr.trxyy.alternative.alternative_api.utils.OperatingSystem;
import fr.trxyy.alternative.alternative_api.utils.file.FileUtil;
import fr.trxyy.alternative.alternative_api.utils.file.GameUtils;
import fr.trxyy.alternative.alternative_api.utils.file.JsonUtil;
import fr.trxyy.alternative.alternative_api.utils.file.LauncherFile;
import fr.trxyy.launcher.template.CAnimation;

/**
 * @author Trxyy
 */
public class GameUpdater extends Thread {

	/**
	 * The custom files to download in a HashMap
	 */
	public HashMap<String, LauncherFile> files = new HashMap<String, LauncherFile>();
	/**
	 * The Assets Url
	 */
	private static final String ASSETS_URL = "https://resources.download.minecraft.net/";
	/**
	 * The Host to check for offline
	 */
	private String HOST = "http://www.google.com";
	/**
	 * The Minecraft Version from Json
	 */
	public static MinecraftVersion minecraftVersion;
	/**
	 * The Minecraft Java manifest
	 */
	public JVMManifest javaManifest;
	/**
	 * The Java style
	 */
	public String javaStyle;
	/**
	 * The Minecraft Local Version from Json
	 */
	public static MinecraftVersion minecraftLocalVersion;
	/**
	 * Custom files has a custom jar ?
	 */
	public boolean hasCustomJar = false;
	/**
	 * The AssetIndex
	 */
	public AssetIndex assetsList;
	/**
	 * The GameEngine instance
	 */
	public GameEngine engine;
	/**
	 * The GameVerifier instance
	 */
	private GameVerifier verifier;
	/**
	 * The assets Executor
	 */
	private final ExecutorService assetsExecutor = Executors.newFixedThreadPool(5);
	/**
	 * The custom files Executor
	 */
	private final ExecutorService customJarsExecutor = Executors.newFixedThreadPool(5);
	/**
	 * The libraries Executor
	 */
	private final ExecutorService jarsExecutor = Executors.newFixedThreadPool(5);
	/**
	 * The java Executor
	 */
	private final ExecutorService javaExecutor = Executors.newFixedThreadPool(5);
	/**
	 * The current Info text of the progressbar
	 */
	private String currentInfoText = "";
	/**
	 * The current file of the progressbar
	 */
	private String currentFile = "";
	/**
	 * The downloaded custom files
	 */
	public int downloadedFiles = 0;
	public long downloadedSize = 0;
	/**
	 * The custom files to download
	 */
	public int filesToDownload = 0;
	public long sizeToDownload = 0;
	public long sizeToBuffer = 0;
	private boolean isOnline = true;
	
	/**
	 * Register some things...
	 * @param gameEngine The GameEngine instance
	 */
	public void reg(GameEngine gameEngine) {
		this.engine = gameEngine;
	}

	/**
	 * Run the update (Thread)
	 */
	@Override
	public void run() {
		/** -------------------------------------- */
		this.HOST = engine.getGameLinks().getBaseUrl();
		this.isOnline = this.isOnline();
		this.engine.setOnline(this.isOnline);
		if (this.isOnline) {
			Logger.log("=============UPDATING GAME==============");

			this.setCurrentInfoText("Préparation de la mise a jour...");

			Logger.log("Updating Local Minecraft Version.");
			Logger.log("========================================");
			this.downloadVersion();

			this.verifier = new GameVerifier(this.engine);
			Logger.log("Getting ignore/delete list   [Step 1/6]");
			Logger.log("========================================");

			this.setCurrentInfoText("Récupération de la ignoreList/deleteList...");

			this.verifier.getIgnoreList();
			this.verifier.getDeleteList();
			Logger.log("Indexing version              [Step 2/6]");
			Logger.log("========================================");

			this.setCurrentInfoText("Indexion de la version Minecraft...");

			this.indexVersion();
			Logger.log("Indexing assets               [Step 3/6]");
			Logger.log("========================================");

			this.setCurrentInfoText("Indexion des fichiers de ressources...");

			this.indexAssets();
			if (!this.engine.getGameStyle().equals(GameStyle.VANILLA)) {
				Logger.log("Indexing custom jars         [3-bonus/6]");
				Logger.log("========================================");

				this.setCurrentInfoText("Indexion des fichiers Krosmocraft...");

				GameParser.getFilesToDownload(engine);
			}
			Logger.log("Updating assets               [Step 4/6]");
			Logger.log("========================================");

			this.setCurrentInfoText("Téléchargement des ressources...");

			this.updateAssets();
			Logger.log("Updating jars/libraries       [Step 5/6]");
			Logger.log("========================================");
			
			this.setCurrentInfoText("Téléchargement des librairies...");
			
			Logger.log("Downloading required java version");
			this.downloadJavaVersion(OperatingSystem.getJavaBit(), OperatingSystem.getCurrentPlatform());
			this.updateJava();

			this.updateJars();
			if (!engine.getGameStyle().equals(GameStyle.VANILLA)) {
				Logger.log("Updating custom jars         [5-bonus/6]");
				Logger.log("========================================");
				this.setCurrentInfoText("Téléchargement du jeu...");
				this.updateCustomJars();
			}
			this.customJarsExecutor.shutdown();
			try {
				this.customJarsExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Logger.log("Cleaning installation         [Step 6/6]");
			Logger.log("========================================");

			this.setCurrentInfoText("Vérification de l'installation...");

			this.verifier.verify();

			while(sizeToBuffer > 0) {
				sizeToDownload -= 4000;
				sizeToBuffer -= 4000;
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Logger.log("========================================");
			Logger.log("|      Update Finished. Launching.     |");
			Logger.log("|            Version " + minecraftVersion.getId() + "            |");
			Logger.log("|          Runtime: " + System.getProperty("java.version") + "          |");
			Logger.log("========================================");
			Logger.log("\n\n");
			Logger.log("==============GAME OUTPUT===============");
			ready();
		}
		else {
			Logger.log("\n\n");
			Logger.log("=========UPDATING GAME OFFLINE==========");

			this.setCurrentInfoText("Preparation de la mise a jour.");
			
			Logger.log("Indexing local version         [Step 1/1]");
			Logger.log("========================================");
			this.indexLocalVersion();
			
			if (!this.engine.getGameStyle().equals(GameStyle.VANILLA)) {
				Logger.log("Indexing custom local jars   [Extra Step]");
				Logger.log("========================================");
				this.setCurrentInfoText("Acquisition des fichiers perso en local.");
				GameParser.getFilesToDownloadOffline(engine);
			}

			while(sizeToBuffer > 0) {
				sizeToBuffer--;
			}
			Logger.log("========================================");
			Logger.log("|       Can't Update. Launching.       |");
			Logger.log("|            Version " + minecraftLocalVersion.getId() + "            |");
			Logger.log("|          Runtime: " + System.getProperty("java.version") + "          |");
			Logger.log("========================================");
			Logger.log("\n\n");
			Logger.log("==============GAME OUTPUT===============");
			ready();
		}
	}
	
	private void downloadJavaVersion(Arch currentArch, OperatingSystem currentOs) {
		if (this.engine.getMinecraftVersion().getJavaVersion() != null) {
			String code = this.engine.getMinecraftVersion().getJavaVersion().getComponent();
			if (code.equals(EnumJavaVersion.JAVA_RUNTIME_GAMMA.getCode())) {
				this.javaStyle = EnumJavaVersion.JAVA_RUNTIME_GAMMA.getCode();
				if (currentOs.equals(OperatingSystem.WINDOWS)) {
					if (currentArch.equals(Arch.x64)) {
						this.indexJava(EnumJavaOS.WIN_64_GAMMA);
					}
					else{
						this.indexJava(EnumJavaOS.WIN_32_GAMMA);
					}
				}
				if (currentOs.equals(OperatingSystem.LINUX)) {
					this.indexJava(EnumJavaOS.LINUX_GAMMA);
				}
				if (currentOs.equals(OperatingSystem.OSX) || currentOs.equals(OperatingSystem.SOLARIS)) {
					this.indexJava(EnumJavaOS.MACOS_GAMMA);
				}
			}
		}
	}

	/**
	 * Download Minecraft Json version at Every Launch to be up to date.
	 */
	public void downloadVersion() {
		File theFile = new File(engine.getGameFolder().getCacheDir(), engine.getGameLinks().getJsonName());
		GameVerifier.addToFileList(theFile.getAbsolutePath().replace(engine.getGameFolder().getCacheDir().getAbsolutePath(), "").replace('/', File.separatorChar));
		try {
			URL url = new URL(this.engine.getGameLinks().getJsonUrl());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			float totalDataRead = 0;
			BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
			FileOutputStream fos = new FileOutputStream(theFile);
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte[] data = new byte[1024];
			int i = 0;
			while ((i = in.read(data, 0, 1024)) >= 0) {
				totalDataRead = totalDataRead + i;
				bout.write(data, 0, i);
			}
			bout.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void downloadAvailableServers() {
		URL url = null;
		BufferedReader read = null;
		try {
			url = new URL(this.engine.getGameLinks().getServerListUrl());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			read = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String i;
		try {
			while ((i = read.readLine()) != null) {
				String[] parts = i.split("/");

				if (parts.length == 3) {
					String serverName = parts[0].trim();
					String address = parts[1].trim();
					String port = parts[2].trim();
					Infos.servers.put(serverName, address);
					Infos.ports.put(serverName, port);
				} else {
					System.out.println("Invalid line format: " + i);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LauncherPanel.serverChoice.getItems().addAll("Live");
		LauncherPanel.serverChoice.getItems().addAll(Infos.servers.keySet());

		// Default Server
		Infos.servers.put("Live", Infos.serverIp);
		Infos.ports.put("Live", "3000");
	}

	/**
	 * @return A generated Lot Number
	 */
	public static String generateLot() {
		String lot = "";
		SimpleDateFormat year = new SimpleDateFormat("YY");
		SimpleDateFormat hour = new SimpleDateFormat("HHmmss");
		Date date = new Date();
		int julianDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		lot = "L" + year.format(date) + julianDay + "/" + hour.format(date);
		return lot;
	}

	/**
	 * Construct the classpath
	 * @param engine The GameEngine instance
	 * @return The result of the classpath
	 */
	public static String constructClasspath(GameEngine engine) {
		Logger.log("Constructing classpath (new, only in version)");
		String result = "";
		String separator = System.getProperty("path.separator");
		for (MinecraftLibrary lib : minecraftVersion.getLibraries()) {
			File libPath = new File(engine.getGameFolder().getLibsDir(), lib.getArtifactPath());
			result += libPath + separator;
		}
		result += engine.getGameFolder().getGameJar().getAbsolutePath();
		return result;
	}

	/**
	 * Update minecraft libraries
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void updateJars() {
		for (MinecraftLibrary lib : minecraftVersion.getLibraries()) {
			File libPath = new File(engine.getGameFolder().getLibsDir(), lib.getArtifactPath());

			GameVerifier.addToFileList(
					libPath.getAbsolutePath().replace(engine.getGameFolder().getGameDir().getAbsolutePath(), "")
							.replace('/', File.separatorChar));

			if (lib.getCompatibilityRules() != null) {
				for (final CompatibilityRule rule : lib.getCompatibilityRules()) {
					if (rule.getOs() != null && rule.getAction() != null) {
						for (final String os : rule.getOs().getName().getAliases()) {
							if (lib.appliesToCurrentEnvironment()) {
                                lib.setSkipped(rule.getAction().equals("disallow"));
							} else {
                                lib.setSkipped(!rule.getAction().equals("allow"));
							}
						}
					}
				}
			}

			if (!lib.isSkipped()) {
				if (lib.getDownloads().getArtifact() != null) {
					final Downloader downloadTask = new Downloader(libPath,
							lib.getDownloads().getArtifact().getUrl().toString(),
							lib.getDownloads().getArtifact().getSha1(), engine);
					if (downloadTask.requireUpdate()) {
						if (!verifier.existInDeleteList(libPath.getAbsolutePath()
								.replace(engine.getGameFolder().getGameDir().getAbsolutePath(), ""))) {
							this.filesToDownload++;
						    try {
						    	URLConnection conn = new URL(downloadTask.getUrl()).openConnection();
						        sizeToDownload += conn.getContentLengthLong();
						    } catch (IOException e) {
						        throw new RuntimeException(e);
						    }
							this.jarsExecutor.submit(downloadTask);
						} else {
						}
					}
				}

				if (lib.hasNatives()) {
					for (final String osName : lib.getNatives().values()) {
						String realOsName = osName.replace("${arch}", Arch.CURRENT.getBit());
						if (lib.getDownloads().getClassifiers().get(realOsName) != null) {
							final File nativePath = new File(engine.getGameFolder().getNativesCacheDir(),
									lib.getArtifactNatives(realOsName));
							GameVerifier.addToFileList(nativePath.getAbsolutePath()
									.replace(engine.getGameFolder().getGameDir().getAbsolutePath(), "")
									.replace('/', File.separatorChar));
							final Downloader downloadTask8 = new Downloader(nativePath,
									lib.getDownloads().getClassifiers().get(realOsName).getUrl().toString(),
									lib.getDownloads().getClassifiers().get(realOsName).getSha1(), engine);
							if (downloadTask8.requireUpdate()) {
								if (!verifier.existInDeleteList(nativePath.getAbsolutePath()
										.replace(engine.getGameFolder().getGameDir().getAbsolutePath(), ""))) {
									this.filesToDownload++;
								    try {
								    	URLConnection conn = new URL(downloadTask8.getUrl()).openConnection();
								        sizeToDownload += conn.getContentLengthLong();
								    } catch (IOException e) {
								        throw new RuntimeException(e);
								    }
									this.jarsExecutor.submit(downloadTask8);
								} else {
								}
							}
						}
					}
				}
			}
		}

		this.jarsExecutor.shutdown();

		try {
			this.jarsExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void updateJava() {
		Map<String, JVMFile> objects = this.javaManifest.getFiles();

		for (String javaFile : objects.keySet()) {
			JVMFile jvmFile = objects.get(javaFile);
			File localFolder = new File(engine.getGameFolder().getBinDir(), this.javaStyle);
			localFolder.mkdirs();
			File local = new File(localFolder, javaFile);
			
			if (!jvmFile.getType().equals(EnumJavaFileType.DIRECTORY.getName())) {
				Downloader downloadTask = new Downloader(local, jvmFile.getDownloads().getRaw().getUrl().toString(), jvmFile.getDownloads().getRaw().getSha1(), engine);
				GameVerifier.addToFileList(local.getAbsolutePath().replace(engine.getGameFolder().getCacheDir().getAbsolutePath(), "").replace('/', File.separatorChar));
				if (downloadTask.requireUpdate()) {
				    try {
				    	URLConnection conn = new URL(downloadTask.getUrl()).openConnection();
				        sizeToDownload += conn.getContentLengthLong();
				    } catch (IOException e) {
				        throw new RuntimeException(e);
				    } 
					this.javaExecutor.submit(downloadTask);
					this.filesToDownload++;
				}
			}
			else {
				local.mkdirs();
			}
		}
		
		this.javaExecutor.shutdown();
		try {
			this.javaExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Logger.log("Jre Update finished.");
	}

	/**
	 * Update minecraft assets
	 */
	public void updateAssets() {
		String json = null;
		String assetUrl = minecraftVersion.getAssetIndex().getUrl().toString();
		AssetIndex assetsList;
		try {
			json = JsonUtil.loadJSON(assetUrl);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			assetsList = JsonUtil.getGson().fromJson(json, AssetIndex.class);
		}
		Map<String, AssetObject> objects = assetsList.getObjects();
		for (String assetKey : objects.keySet()) {
			AssetObject asset = objects.get(assetKey);
			File mc = getAssetInMcFolder(asset.getHash());
			File local = getAsset(asset.getHash());

			GameVerifier.addToFileList(
					local.getAbsolutePath().replace(engine.getGameFolder().getGameDir().getAbsolutePath(), "")
							.replace('/', File.separatorChar));

			local.getParentFile().mkdirs();
			if ((!local.exists()) || (!FileUtil.matchSHA1(local, asset.getHash()))) {
				if ((!local.exists()) && (mc.exists()) && (FileUtil.matchSHA1(mc, asset.getHash()))) {
					this.assetsExecutor.submit(new Duplicator(mc, local, engine));
					Logger.log("Copying asset " + local.getName());
					this.setCurrentInfoText("Copie d'une ressource.");
				} else {
					Downloader downloadTask = new Downloader(local, toURL(asset.getHash()), asset.getHash(), engine);
					if (downloadTask.requireUpdate()) {
					    try {
					    	URLConnection conn = new URL(downloadTask.getUrl()).openConnection();
					        sizeToDownload += conn.getContentLengthLong();
					    } catch (IOException e) {
					        throw new RuntimeException(e);
					    } 
						this.assetsExecutor.submit(downloadTask);
						this.filesToDownload++;
						Logger.log("Downloading asset " + local.getName());
					}
				}
			}
		}
		this.assetsExecutor.shutdown();
		File indexes = new File(engine.getGameFolder().getAssetsDir(), "indexes");
		indexes.mkdirs();
		File index = new File(indexes, minecraftVersion.getAssets() + ".json");

		GameVerifier.addToFileList(index.getAbsolutePath()
				.replace(engine.getGameFolder().getGameDir().getAbsolutePath(), "").replace('/', File.separatorChar));

		if (!index.exists()) {
			try {
				index.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(index));
				writer.write(JsonUtil.getGson().toJson(assetsList));
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			this.assetsExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	private void ready() {
		Infos.updating = false;
		CAnimation c = new CAnimation(LauncherPanel.percentageLabel.opacityProperty(), 0, 500);
		c.run();
		c = new CAnimation(LauncherPanel.currentStep.opacityProperty(), 0, 500);
		c.run();
		c = new CAnimation(LauncherPanel.bar.opacityProperty(), 0, 500);
		c.run();
		if (Infos.connected && LauncherPanel.fakeButton != null) {
			LauncherPanel.fakeButton.setVisible(false);
			LauncherPanel.fakeButton.setDisable(true);
			LauncherPanel.playButton.setDisable(false);
			LauncherPanel.playButton.setOpacity(1);
		}
	}

	/**
	 * Index Minecraft version json
	 */
	public void indexVersion() {
		String json = null;
		try {
			json = JsonUtil.loadJSON(engine.getGameLinks().getJsonUrl());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			minecraftVersion = JsonUtil.getGson().fromJson(json, MinecraftVersion.class);
			engine.reg(minecraftVersion);
		}
	}
	
	/**
	 * Index Minecraft local version json
	 */
	public void indexLocalVersion() {
		File f = new File(engine.getGameFolder().getCacheDir(), engine.getGameLinks().getJsonName());
		String json = null;
		try {
			json = JsonUtil.loadJSON(f.toURL().toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			minecraftLocalVersion = JsonUtil.getGson().fromJson(json, MinecraftVersion.class);
			engine.reg(minecraftLocalVersion);
		}
	}
	
	/**
	 * Index Minecraft java version
	 */
	public void indexJava(EnumJavaOS java) {
		String javaManifestJson = null;
		try {
			javaManifestJson = JsonUtil.loadJSON(java.getUrl());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			javaManifest = JsonUtil.getGson().fromJson(javaManifestJson, JVMManifest.class);
		}
	}

	/**
	 * Index minecraft assets json
	 */
	public void indexAssets() {
		String json = null;
		String assetUrl = minecraftVersion.getAssetIndex().getUrl().toString();
		try {
			json = JsonUtil.loadJSON(assetUrl);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			assetsList = JsonUtil.getGson().fromJson(json, AssetIndex.class);
		}
	}

	/**
	 * @return The assetsList
	 */
	public AssetIndex getAssetsList() {
		return assetsList;
	}

	/**
	 * @param hash The hash
	 * @return The hash url of the assets
	 */
	private String toURL(String hash) {
		return ASSETS_URL + hash.substring(0, 2) + "/" + hash;
	}

	/**
	 * Update custom jars
	 */
	private void updateCustomJars() {
		for (String name : this.files.keySet()) {
			String fileDest = name.replace(engine.getGameLinks().getCustomFilesUrl(), "");
			String fileName = fileDest;
			int index = fileName.lastIndexOf("\\");
			String dirLocation = fileName.substring(index + 1);

			File libPath = new File(engine.getGameFolder().getGameDir() + File.separator + dirLocation);
			String url = engine.getGameLinks().getCustomFilesUrl() + name;

			String hash = this.files.get(name).hash;
			final Downloader customDownloadTask = new Downloader(libPath, url, hash, engine); // GameParser
			if (customDownloadTask.requireUpdate()) {
				if (!verifier.existInDeleteList(
						libPath.getAbsolutePath().replace(engine.getGameFolder().getGameDir().getAbsolutePath(), ""))) {
					this.customJarsExecutor.submit(customDownloadTask);
				}
			}
		}
	}

	/**
	 * @param hash The hash
	 * @return The asset File
	 */
	private File getAsset(String hash) {
		File assetsDir = this.engine.getGameFolder().getAssetsDir();
		File mcObjectsDir = new File(assetsDir, "objects");
		File hex = new File(mcObjectsDir, hash.substring(0, 2));
		return new File(hex, hash);
	}

	/**
	 * @param hash The hash
	 * @return The asset file in minecraft folder
	 */
	private File getAssetInMcFolder(String hash) {
		File minecraftAssetsDir = new File(GameUtils.getWorkingDirectory("minecraft"), "assets");
		File minecraftObjectsDir = new File(minecraftAssetsDir, "objects");
		File hex = new File(minecraftObjectsDir, hash.substring(0, 2));
		return new File(hex, hash);
	}

	/**
	 * @return The GameEngine instance
	 */
	public GameEngine getEngine() {
		return engine;
	}

	/**
	 * @return Get current Info text
	 */
	public String getCurrentInfo() {
		return this.currentInfoText;
	}

	/**
	 * Set current info text
	 * @param name The text of the info
	 */
	public void setCurrentInfoText(String name) {
		this.currentInfoText = name;
	}

	/**
	 * @return The current File name
	 */
	public String getCurrentFile() {
		return this.currentFile;
	}

	/**
	 * Set current File name
	 * @param name The name
	 */
	public void setCurrentFile(String name) {
		this.currentFile = name;
	}
	
	public MinecraftVersion getLocalVersion() {
		return minecraftLocalVersion;
	}

	/**
	 * @return If the host is reachable
	 */
	public boolean isOnline() {
		try {
			URL url = new URL(HOST);
			URLConnection connection = url.openConnection();
			connection.connect();
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
}
