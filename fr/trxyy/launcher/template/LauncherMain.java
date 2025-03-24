package fr.trxyy.launcher.template;

import java.text.DecimalFormat;

import fr.trxyy.alternative.alternative_api.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.util.Duration;

import fr.trxyy.alternative.alternative_api.updater.GameUpdater;
import fr.trxyy.alternative.alternative_api.utils.Mover;
import fr.trxyy.alternative.alternative_api_ui.LauncherBackground;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.AlternativeBase;
import fr.trxyy.alternative.alternative_api_ui.base.LauncherBase;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherMain extends AlternativeBase {
	public static final ConnectionHandler connectionHandler = new ConnectionHandler();
	public static Pane pane;
	public static MediaPlayer mediaPlayer;
	public DecimalFormat decimalFormat = new DecimalFormat("#.#");
	private GameFolder gameFolder = new GameFolder("krosmocraft");
	private LauncherPreferences launcherPreferences = new LauncherPreferences("Launcher Krosmocraft", 1067, 600, Mover.MOVE);
	private GameLinks gameLinks = new GameLinks("https://krosmocraft.fr/downloads/", "1.20.4.json");
	private GameEngine gameEngine = new GameEngine(this.gameFolder, this.gameLinks, this.launcherPreferences, GameStyle.VANILLA_PLUS);
	private GameConnect autoConnect = new GameConnect(Infos.serverIp, "3000");
	private GameUpdater updater = new GameUpdater();

	//First screen init
	public void start(Stage primaryStage) throws Exception {
		Scene scene = createContent(false);
		this.gameEngine.reg(autoConnect);
		LauncherBase launcher = new LauncherBase(primaryStage, scene, StageStyle.TRANSPARENT, this.gameEngine);
		centerStage(primaryStage);
		launcher.setIconImage(primaryStage, "favicon.png");
		this.updater.reg(gameEngine);
		this.gameEngine.reg(this.updater);
		Thread updateThread = new Thread(() -> updater.run());
		this.updater.downloadAvailableServers();
		updateThread.start();
		/** ===================== REFAICHIR LE NOM DU FICHIER, PROGRESSBAR, POURCENTAGE  ===================== **/
		Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.0D), event -> timelineUpdate()),
                new KeyFrame(Duration.seconds(0.1D)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	private void centerStage(Stage primaryStage){
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) * 0.5d);
		primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) * 0.5d);
	}

	//On launcher expand / retract
	public void resize(Stage primaryStage, int x, int y, boolean fullScreen) {
		Mover m = fullScreen ? Mover.DONT_MOVE : Mover.MOVE;
		this.launcherPreferences = new LauncherPreferences("Launcher Krosmocraft", x, y, m);
		this.gameEngine = new GameEngine(this.gameFolder, this.gameLinks, this.launcherPreferences, GameStyle.VANILLA_PLUS);
		Scene scene = createContent(fullScreen);
		this.gameEngine.reg(primaryStage);
		this.gameEngine.reg(autoConnect);
		LauncherBase launcher = new LauncherBase(primaryStage, scene, StageStyle.TRANSPARENT, this.gameEngine);
		if (!fullScreen){
			centerStage(primaryStage);
		}
		launcher.setIconImage(primaryStage, "favicon.png");
	}

	private Scene createContent(boolean fullScreen) {
		pane = new LauncherPane(this.gameEngine);
		Rectangle rect = new Rectangle(this.gameEngine.getLauncherPreferences().getWidth(), this.gameEngine.getLauncherPreferences().getHeight());
		if (!fullScreen) {
			rect.setArcHeight(15.0);
			rect.setArcWidth(15.0);
		}
		pane.setClip(rect);
		pane.setStyle("-fx-background-color: transparent;");
		new LauncherBackground(this.gameEngine, "background.mp4", pane);
		LauncherPanel p = new LauncherPanel(this.gameEngine, this, fullScreen, Infos.connected); //CONNECTED
		Scene scene = new Scene(pane);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode() == KeyCode.ENTER && !Infos.connected) {
                p.connect();
                ke.consume();
            }
        });
		return scene;
	}

	private double currentPercent = 0;
	public void timelineUpdate() {
		double percent = (updater.downloadedSize * 100.0D / updater.sizeToDownload);
		if (percent > currentPercent) {
			currentPercent = percent;
		}
		if (updater.downloadedSize > 0) {
			LauncherPanel.percentageLabel.setText(decimalFormat.format(
					currentPercent) + "%");
		}
		//this.currentFileLabel.setText(updater.getCurrentFile());
		LauncherPanel.currentStep.setText(updater.getCurrentInfo());
		LauncherPanel.bar.setProgress(currentPercent / 100.0D);
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}