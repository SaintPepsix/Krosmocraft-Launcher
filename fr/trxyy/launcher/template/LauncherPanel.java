package fr.trxyy.launcher.template;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import fr.trxyy.alternative.alternative_api.*;
import fr.trxyy.alternative.alternative_api.build.GameRunner;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.ResourceLocation;
import fr.trxyy.alternative.alternative_api.utils.config.EnumConfig;
import fr.trxyy.alternative.alternative_api.utils.config.LauncherConfig;
import fr.trxyy.alternative.alternative_api_ui.base.IScreen;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherImage;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherPasswordField;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherProgressBar;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherTextField;
import fr.trxyy.alternative.alternative_auth.account.AccountType;
import fr.trxyy.alternative.alternative_auth.base.GameAuth;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class LauncherPanel extends IScreen {
	/** TOP */
	public static boolean phase2 = false;
	public static boolean phase3 = false;
	private static LauncherImage topRectangle;
	private static LauncherImage titleImage;
	private static LauncherLabel titleLabel;
	private static LauncherButton closeButton;
	private static LauncherButton reduceButton;
	public static LauncherButton maxiButton;
	private static int fontSize = 12;
	private static int fontSize2 = 14;
	/** LOGIN */
	private static LauncherRectangle blurRectangle;
	private static LauncherLabel forgotPassword;
	private static LauncherLabel needAccount;
	private static LauncherTextField usernameField;
	private static LauncherPasswordField passwordField;
	private static LauncherButton loginButton;
	private static LauncherImage loadingSpin;
	private static LauncherImage loadingSpin2;
	/** LINKS FOR BUTTONS IMAGES */
	private static final String SKIN_URL = "https://krosmocraft.fr/skin";
	private static final String INSCRIPTION_URL = "https://krosmocraft.fr/register/";
	private static final String FORGOT_URL = "https://krosmocraft.fr/resetting/request";
	private static final String WEB_URL = "https://krosmocraft.fr/";
	private static final String YOUTUBE_URL = "https://www.youtube.com/@krosmocraft/videos";
	private static final String DISCORD_URL = "https://discord.gg/dD7XtDjuK6";
	/** GAMEENGINE REQUIRED */
	private static GameEngine theGameEngine;
	/** CONFIGURATION **/
	public static LauncherConfig config;
	
	
	
	/** PHASE 2 **/
	private static LauncherImage bottomRec;
	private static LauncherLabel playerCount;
	private static LauncherLabel playerCountValue;
	private static LauncherLabel serverVersion;
	private static LauncherLabel name;
	private static LauncherImage mute;
	private static LauncherImage unmute;
	private static LauncherImage discord;
	private static LauncherImage youtube;
	private static LauncherImage link;
	private static LauncherImage settings;
	private static LauncherImage line;
	private static LauncherImage imgBg;
	private static LauncherImage imgOverlay;
	private static LauncherLabel imgOverlayText;
	private static LauncherImage img;
	private static LauncherLabel countHitbox;
	private static LauncherLabel settingsHitbox;
	private static LauncherLabel linkHitbox;
	private static LauncherLabel discordHitbox;
	private static LauncherLabel youtubeHitbox;
	private static LauncherLabel muteHitbox;
	public static LauncherLabel percentageLabel;
	public static LauncherLabel currentStep;
	public static LauncherProgressBar bar;
	public static LauncherLabel fakeButton;
	public static LauncherLabel playButton;
	
	public LauncherPanel(GameEngine engine, LauncherMain egg, boolean fullScreen, boolean phasing) {
		phase2 = false;
		phase3 = false;

		fontSize = fullScreen ? 22 : 12;
		fontSize2 = fullScreen ? 26 : 14;
		theGameEngine = engine;
		config = new LauncherConfig(engine);
		config.loadConfiguration();
		config.prevQuality = (String) config.getValue(EnumConfig.QUALITY);

		/** ===================== ECRAN CONNEXION ===================== */
		topRectangle = new LauncherImage(loadImage(theGameEngine, "topfade.png"));
		topRectangle.setBounds(0, 0, theGameEngine.getWidth(), theGameEngine.getHeight() / 8);
		topRectangle.setOpacity(0);
		topRectangle.setVisible(true);
		blurRectangle = new LauncherRectangle(0, 0, theGameEngine.getWidth(), theGameEngine.getHeight());
		blurRectangle.setFill(Color.rgb(10, 10, 10, 0.9));
		titleImage = new LauncherImage();
		titleImage.setImage(getResourceLocation().loadImage(theGameEngine, "logo.png"));
		titleImage.setBounds(theGameEngine.getWidth() / 2 - theGameEngine.getWidth() / 14, theGameEngine.getHeight() / 12 + theGameEngine.getHeight() / 21, theGameEngine.getWidth() / 8 + theGameEngine.getWidth() / 50, theGameEngine.getHeight() / 5 + theGameEngine.getHeight() / 12);
		titleLabel = new LauncherLabel();
		titleLabel.setText("Krosmocraft");
		titleLabel.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2));
		titleLabel.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		titleLabel.setPosition(8, -4);
		titleLabel.setOpacity(1);
		titleLabel.setSize(500, 40);
		closeButton = new LauncherButton();
		closeButton.setInvisible();
		closeButton.setPosition(theGameEngine.getWidth() - theGameEngine.getWidth() / 30, 2);
		closeButton.setSize(theGameEngine.getHeight() / 20, theGameEngine.getHeight() / 40);
		closeButton.setBackground(null);
		LauncherImage closeImage = new LauncherImage(getResourceLocation().loadImage(theGameEngine, "close.png"));
		closeImage.setSize(theGameEngine.getHeight() / 40, theGameEngine.getHeight() / 40);
		closeButton.setGraphic(closeImage);
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		reduceButton = new LauncherButton();
		reduceButton.setInvisible();
		reduceButton.setPosition(theGameEngine.getWidth() - theGameEngine.getWidth() / 10, 2);
		reduceButton.setSize(theGameEngine.getHeight() / 20, theGameEngine.getHeight() / 40);
		reduceButton.setBackground(null);
		LauncherImage reduceImage = new LauncherImage(getResourceLocation().loadImage(theGameEngine, "reduce.png"));
		reduceImage.setSize(theGameEngine.getHeight() / 40, theGameEngine.getHeight() / 40);
		reduceButton.setGraphic(reduceImage);
		reduceButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Stage stage = (Stage) ((LauncherButton) event.getSource()).getScene().getWindow();
				stage.setIconified(true);
			}
		});
		maxiButton = new LauncherButton();
		maxiButton.setInvisible();
		maxiButton.setPosition(theGameEngine.getWidth() - theGameEngine.getWidth() / 15, 2);
		maxiButton.setSize(theGameEngine.getHeight() / 20, theGameEngine.getHeight() / 40);
		maxiButton.setBackground(null);
		LauncherImage maxiImage = new LauncherImage(getResourceLocation().loadImage(theGameEngine, "maxi.png"));
		maxiImage.setSize(theGameEngine.getHeight() / 40, theGameEngine.getHeight() / 40);
		maxiButton.setGraphic(maxiImage);
		maxiButton.setOnAction(event -> {
            Stage stage = (Stage) maxiButton.getScene().getWindow();
            stage.hide();
			Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
            if (s.getWidth() != stage.getWidth()) {
                try {
					stage.close();
					Stage s2 = new Stage();
					s2.setX(0);
					s2.setY(0);
                    egg.resize(s2, (int) s.getWidth(), (int) s.getHeight(), true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    egg.resize(new Stage(), 1067, 600, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
		usernameField = new LauncherTextField();
        // Set the maximum limit to 16 characters
		usernameField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 16 ? change : null));
		if (Infos.usernamet.isEmpty()) {
			if (!Infos.userSet) {
				usernameField.setText((String) config.getValue(EnumConfig.USERNAME));
				Infos.userSet = true;
			}
			Infos.usernamet = usernameField.getText();
		}
		else {
			usernameField.setText(Infos.usernamet);
		}
		if (!usernameField.getText().isEmpty() && usernameField.getText() != null) {
			Infos.username = true;
		}
		usernameField.setBounds(theGameEngine.getWidth() / 2 - theGameEngine.getWidth() / 7, theGameEngine.getHeight() / 2 - theGameEngine.getHeight() / 10 + theGameEngine.getHeight() / 21, theGameEngine.getWidth() / 4 + theGameEngine.getWidth() / 28, theGameEngine.getHeight() / 12);
		usernameField.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", fontSize2));
		usernameField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		usernameField.setVoidText("Nom de compte");
		usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
			Infos.usernamet = usernameField.getText();
		    if (newValue.isEmpty() || newValue == null || newValue.length() < 3) {
		    	loginButton.setOpacity(0.2);
				Infos.username = false;
		    }
		    else {
				Infos.username = true;
		    	if(Infos.password) {
			    	loginButton.setOpacity(0.9);
		    	}
		    }
		});
		/** ===================== CASE MOT DE PASSE ===================== */
		passwordField = new LauncherPasswordField();
        // Set the maximum limit to 20 characters
        passwordField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 20 ? change : null));
        
		if (!Infos.passwordt.isEmpty()) {
			passwordField.setText(Infos.passwordt);
		}
		passwordField.setBounds(theGameEngine.getWidth() / 2 - theGameEngine.getWidth() / 7, theGameEngine.getHeight() / 2 + theGameEngine.getHeight() / 21, theGameEngine.getWidth() / 4 + theGameEngine.getWidth() / 28, theGameEngine.getHeight() / 12);
		passwordField.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", fontSize2));
		passwordField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		passwordField.setVoidText("Mot de passe");
		passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
			Infos.passwordt = passwordField.getText();
		    if (newValue.isEmpty() || newValue == null || newValue.length() < 6) {
		    	loginButton.setOpacity(0.2);
				Infos.password = false;
		    }
		    else {
				Infos.password = true;
		    	if(Infos.username) {
			    	loginButton.setOpacity(0.9);
		    	}
		    }
		});
		

		/** ===================== CUSTOM LOGIN STUFF ===================== */
		forgotPassword = new LauncherLabel();
		forgotPassword.setText("Mot de passe oublié");
		forgotPassword.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", fontSize));
		forgotPassword.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		forgotPassword.setPosition(theGameEngine.getWidth() / 2 + theGameEngine.getWidth() / 45, theGameEngine.getHeight() / 2 + theGameEngine.getHeight() / 9 + theGameEngine.getHeight() / 21);
		forgotPassword.setOpacity(0.4);
		forgotPassword.setSize(theGameEngine.getWidth() / 8, theGameEngine.getHeight() / 20);
		forgotPassword.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	CAnimation c = new CAnimation(forgotPassword.opacityProperty(), 0.9, 200);
		    	c.run();
		    }
		});
		forgotPassword.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	CAnimation c = new CAnimation(forgotPassword.opacityProperty(), 0.4, 200);
		    	c.run();
		    }
		});
		forgotPassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				openLink(FORGOT_URL);
			}
		});
		
		needAccount = new LauncherLabel();
		needAccount.setText("Inscription");
		needAccount.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", fontSize));
		needAccount.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		needAccount.setPosition(theGameEngine.getWidth() / 3 + theGameEngine.getWidth() / 39, theGameEngine.getHeight() / 2 + theGameEngine.getHeight() / 9 + theGameEngine.getHeight() / 21);
		needAccount.setOpacity(0.4);
		needAccount.setSize(theGameEngine.getWidth() / 15, theGameEngine.getHeight() / 20);
		needAccount.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	CAnimation c = new CAnimation(needAccount.opacityProperty(), 0.9, 200);
		    	c.run();
		    }
		});
		needAccount.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	CAnimation c = new CAnimation(needAccount.opacityProperty(), 0.4, 200);
		    	c.run();
		    }
		});
		needAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				openLink(INSCRIPTION_URL);
			}
		});

		loadingSpin = new LauncherImage();
		loadingSpin.setImage(getResourceLocation().loadImage(theGameEngine, "loading.png"));
		loadingSpin.setBounds(theGameEngine.getWidth() / 2 - theGameEngine.getHeight() / 44, theGameEngine.getWidth() / 2 - theGameEngine.getHeight() / 15, theGameEngine.getHeight() / 24, theGameEngine.getHeight() / 24);
		loadingSpin.setOpacity(0);

		loginButton = new LauncherButton();
		loginButton.setText("Se connecter >");
		loginButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", fontSize * 2));
		loginButton.setBounds(theGameEngine.getWidth() / 2 - theGameEngine.getWidth() / 7, theGameEngine.getHeight() / 2 + theGameEngine.getHeight() / 6 + theGameEngine.getHeight() / 21,  theGameEngine.getWidth() / 4 + theGameEngine.getWidth() / 28, theGameEngine.getHeight() / 10);
		loginButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		loginButton.setOpacity(0.2);
    	if(Infos.username && Infos.password) {
	    	loginButton.setOpacity(0.9);
    	}
		loginButton.setOnMouseEntered(t -> {
            if (Infos.username && Infos.password) {
                Glow g = new Glow(0.0);
                loginButton.setEffect(g);
                CAnimation c = new CAnimation(loginButton.opacityProperty(), 0.9, 200);
                CAnimation c2 = new CAnimation(g.levelProperty(), 0.9, 200);
                c.run();
                c2.run();
            }
        });
		loginButton.setOnMouseExited(t -> {
            if (Infos.username && Infos.password) {
                Glow g = new Glow(0.9);
                loginButton.setEffect(g);
                CAnimation c = new CAnimation(g.levelProperty(), 0.0, 200);
                CAnimation c2 = new CAnimation(loginButton.opacityProperty(), 0.9, 200);
                c.run();
                c2.run();
            }
        });
		loginButton.setOnAction(event -> connect());
		if (LauncherMain.connectionHandler.isRunning() && !Infos.connected) {
			connect();
		}
		
		currentStep = new LauncherLabel();
		currentStep.setText("Préparation de la mise à jour...");
		currentStep.setFont(Font.font("Verdana", FontPosture.ITALIC, fontSize2 + getResponsive(4))); // FontPosture.ITALIC
		currentStep.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		currentStep.setAlignment(Pos.CENTER_LEFT);
		currentStep.setTextAlignment(TextAlignment.CENTER);
		currentStep.setBounds(getResponsive(10), getResponsive(10) * 5 + getResponsive(9), theGameEngine.getWidth(), getResponsive(10) * 4);
		currentStep.setOpacity(0);
		currentStep.setVisible(false);
		percentageLabel = new LauncherLabel();
		percentageLabel.setText("0%");
		percentageLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", fontSize2 + getResponsive(3)));
		percentageLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		percentageLabel.setAlignment(Pos.CENTER_LEFT);
		percentageLabel.setBounds(getResponsive(10) * 22 + getResponsive(2), getResponsive(10) * 8 + 1, getResponsive(10) * 32, getResponsive(10) * 4);
		percentageLabel.setOpacity(0);
		percentageLabel.setVisible(false);
		bar = new LauncherProgressBar(theGameEngine);
		bar.setPosition(getResponsive(10) + getResponsive(3), getResponsive(10) * 9 + getResponsive(6));
		bar.setSize(getResponsive(10) * 20, getResponsive(10));
		bar.setOpacity(0);
		bar.setVisible(false);
		
		if (phasing) {
			phase2(false);
		}
	}
	
	public int getResponsive(int x) {
        return switch (x) {
            case 2 -> theGameEngine.getHeight() / 300;
            case 3 -> theGameEngine.getHeight() / 200;
            case 4 -> theGameEngine.getHeight() / 135;
            case 5 -> theGameEngine.getWidth() / 200;
            case 6 -> theGameEngine.getWidth() / 170;
            case 7 -> theGameEngine.getWidth() / 135;
            case 8 -> theGameEngine.getWidth() / 130;
            case 9 -> theGameEngine.getWidth() / 110;
            case 10 -> theGameEngine.getWidth() / 100;
            default -> 0;
        };
	}

	// After login done
	public void phase2(boolean animate) {
		if (phase2){
			return;
		}
		phase2 = true;
    	blurRectangle.setDisable(true);
    	titleImage.setDisable(true);
    	usernameField.setDisable(true);
    	passwordField.setDisable(true);
    	needAccount.setDisable(true);
    	forgotPassword.setDisable(true);
    	loginButton.setDisable(true);
    	needAccount.setDisable(true);
    	if (loadingSpin != null) {
        	loadingSpin.setDisable(true);
    	}
		if (animate) {
			CAnimation c = new CAnimation(blurRectangle.opacityProperty(), 0, 700);
		    c.run();
			c = new CAnimation(titleImage.opacityProperty(), 0, 700);
		    c.run();
			c = new CAnimation(usernameField.opacityProperty(), 0, 700);
		    c.run();
			c = new CAnimation(passwordField.opacityProperty(), 0, 700);
		    c.run();
			c = new CAnimation(needAccount.opacityProperty(), 0, 700);
		    c.run();
			c = new CAnimation(forgotPassword.opacityProperty(), 0, 700);
		    c.run();
			c = new CAnimation(loginButton.opacityProperty(), 0, 700);
		    c.run();
			c = new CAnimation(loadingSpin.opacityProperty(), 0, 700);
		    c.run();
			c = new CAnimation(loadingSpin2.opacityProperty(), 0, 700);
		    c.run();
	    	Task<Void> sleeper = new Task<Void>() {
	            @Override
	            protected Void call() {
	                try {
	                    Thread.sleep(Infos.pseudo == null ? 500 : (config.getValue(EnumConfig.QUALITY) != null && !config.getValue(EnumConfig.QUALITY).equals("")) ? 800 : 600);
	                } catch (InterruptedException e) {
	                }
	                return null;
	            }
	        };
			LauncherPanel inst = this;
	        sleeper.setOnSucceeded(event -> {
				if (phase3){
					return;
				}
                if (Infos.pseudo == null) {
                    PseudoAlert a = new PseudoAlert(usernameField.getText(), passwordField.getText(), theGameEngine, fontSize2, inst);
                    a.draw();
                    return;
                }
                if (config.getValue(EnumConfig.QUALITY) == null || config.getValue(EnumConfig.QUALITY).equals("")) {
                    SettingsAlert a = new SettingsAlert(theGameEngine, config, getResourceLocation(), fontSize, inst);
                    a.draw();
                    return;
                }
                setupMainScreen(true);
            });
	        new Thread(sleeper).start();
		}
		else {
	    	blurRectangle.setOpacity(0);
	    	titleImage.setOpacity(0);
	    	usernameField.setOpacity(0);
	    	passwordField.setOpacity(0);
	    	needAccount.setOpacity(0);
	    	forgotPassword.setOpacity(0);
	    	needAccount.setOpacity(0);
	    	loginButton.setOpacity(0);
			phase3 = true;
		    if (Infos.pseudo == null) {
		    	PseudoAlert a = new PseudoAlert(usernameField.getText(), passwordField.getText(), theGameEngine, fontSize2, this);
				a.draw();
				return;
		    }
			if (config.getValue(EnumConfig.QUALITY).equals("")) {
				SettingsAlert a = new SettingsAlert(theGameEngine, config, getResourceLocation(), fontSize, this);
				a.draw();
				return;
			}
			setupMainScreen(false);
		}
	}

	public void setupMainScreen(boolean animate) {
		if (LauncherMain.mediaPlayer == null) {
			LauncherMain.mediaPlayer = new MediaPlayer(new Media(ResourceLocation.class.getResource(theGameEngine.getLauncherPreferences().getResourceLocation() + "mainmenu.mp3").toExternalForm()));
			LauncherMain.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			LauncherMain.mediaPlayer.setVolume(0.5);
			LauncherMain.mediaPlayer.play();
		}
		if (animate) {
			bottomRec = new LauncherImage(loadImage(theGameEngine, "fade.png"));
			bottomRec.setBounds(0, theGameEngine.getHeight() - theGameEngine.getHeight() / 4, theGameEngine.getWidth(), theGameEngine.getHeight() / 4);
			bottomRec.setOpacity(0);
			bottomRec.setVisible(true);
			playButton = new LauncherLabel();
			playButton.setText("JOUER >");
			playButton.setAlignment(Pos.CENTER);
			playButton.setTextAlignment(TextAlignment.CENTER);
			playButton.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2 * 2));
			playButton.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
			playButton.setBounds(0, theGameEngine.getHeight() - (theGameEngine.getHeight() / 11 + theGameEngine.getHeight() / 23), theGameEngine.getWidth(), theGameEngine.getHeight() / 6);
			playButton.setOpacity(0);
			playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent t) {
			    	if(!Infos.updating && !Infos.launching) {
						Infos.launching = true;
						theGameEngine.reg(GameMemory.getMemory(Double.parseDouble((String) config.getValue(EnumConfig.RAM))));
						theGameEngine.reg(GameSize.getWindowSize(Integer.parseInt((String) config.getValue(EnumConfig.GAME_SIZE))));
						String vmArgs = (String) config.getValue(EnumConfig.VM_ARGUMENTS);
						String[] s = null;
						if (vmArgs != null && !vmArgs.equals("")) {
							s = vmArgs.split(" ");
						}
						String ss = "";
						if (s != null) {
							for (String str : s) {
								if (!str.contains("CMSIncrementalMode")) {
									ss = ss + str + " ";
								}
							}
							s = null;
							if (ss != null && !ss.equals("")) {
								s = ss.split(" ");
							}
							if (s != null) {
								JVMArguments arguments = new JVMArguments(s);
								theGameEngine.reg(arguments);
							}
						}
						else {
							s = null;
							JVMArguments arguments = new JVMArguments(s);
							theGameEngine.reg(arguments);
						}
						GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(), AccountType.OFFLINE);
						GameRunner gameRunner = new GameRunner(theGameEngine, auth.getSession(), fontSize2);
						try {
							playButton.setText("Lancement du jeu...");
							gameRunner.launch();
						} catch (Exception e) {
							e.printStackTrace();
						}
			    	}
			    }
			});
			fakeButton = new LauncherLabel();
			fakeButton.setText("Chargement...");
			fakeButton.setAlignment(Pos.CENTER);
			fakeButton.setTextAlignment(TextAlignment.CENTER);
			fakeButton.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2 * 2));
			fakeButton.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
			fakeButton.setBounds(0, theGameEngine.getHeight() - (theGameEngine.getHeight() / 11 + theGameEngine.getHeight() / 23), theGameEngine.getWidth(), theGameEngine.getHeight() / 6);
			fakeButton.setOpacity(0);
			playerCount = new LauncherLabel();
			playerCount.setText("JOUEURS");
			playerCount.setAlignment(Pos.CENTER);
			playerCount.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2));
			playerCount.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
			playerCount.setBounds(0, theGameEngine.getHeight() - theGameEngine.getWidth() / 17, theGameEngine.getHeight() / 7, theGameEngine.getHeight() / 6);
			playerCount.setOpacity(0);
			playerCountValue = new LauncherLabel();
			playerCountValue.setText(Infos.playerCount + "/300");
			playerCountValue.setAlignment(Pos.CENTER_LEFT);
			playerCountValue.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2 - 4));
			playerCountValue.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: gray;");
			playerCountValue.setBounds(theGameEngine.getHeight() / 7 + getResponsive(3), theGameEngine.getHeight() - (theGameEngine.getWidth() / 34 + theGameEngine.getWidth() / 35), theGameEngine.getHeight() / 11, theGameEngine.getHeight() / 6);
			playerCountValue.setOpacity(0);
			serverVersion = new LauncherLabel();
			serverVersion.setText("Krosmocraft v" + Infos.version);
			serverVersion.setAlignment(Pos.CENTER_RIGHT);
			serverVersion.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2));
			serverVersion.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
			serverVersion.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 4 + theGameEngine.getHeight() / 10), theGameEngine.getHeight() - theGameEngine.getWidth() / 17, theGameEngine.getHeight() / 6 * 2, theGameEngine.getHeight() / 6);
			serverVersion.setOpacity(0);
			settings = new LauncherImage(loadImage(theGameEngine, "settings.png"));
			settings.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - (theGameEngine.getWidth() / 9 + theGameEngine.getWidth() / 47), theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 32);
			settings.setOpacity(0);
			settings.setVisible(true);
			line = new LauncherImage(loadImage(theGameEngine, "line.png"));
			line.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - (theGameEngine.getHeight() / 7 + theGameEngine.getHeight() / 20), theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 24);
			line.setOpacity(0);
			line.setVisible(true);
			link = new LauncherImage(loadImage(theGameEngine, "link.png"));
			link.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - theGameEngine.getHeight() / 7, theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 32);
			link.setOpacity(0);
			link.setVisible(true);
			discord = new LauncherImage(loadImage(theGameEngine, "discord.png"));
			discord.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - (theGameEngine.getWidth() / 30 + theGameEngine.getHeight() / 30), theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 32);
			discord.setOpacity(0);
			discord.setVisible(true);
			youtube = new LauncherImage(loadImage(theGameEngine, "youtube.png"));
			youtube.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - (theGameEngine.getHeight() / 49 + theGameEngine.getHeight() / 46), theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 34);
			youtube.setOpacity(0);
			youtube.setVisible(true);
			mute = new LauncherImage(loadImage(theGameEngine, "mute.png"));
			unmute = new LauncherImage(loadImage(theGameEngine, "unmute.png"));
			if ((boolean) config.getValue(EnumConfig.MUTED)) {
				LauncherMain.mediaPlayer.setVolume(0);
			}
			mute.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 + theGameEngine.getHeight() / 135, theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 34);
			mute.setOpacity(0);
			mute.setVisible(true);
			unmute.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 + theGameEngine.getHeight() / 135, theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 34);
			unmute.setOpacity(0);
			unmute.setVisible(true);
			name = new LauncherLabel();
			name.setText(Infos.pseudo);
			name.setAlignment(Pos.CENTER_RIGHT);
			name.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2));
			name.setStyle("-fx-font-weight: bolder; -fx-background-color: transparent; -fx-text-fill: white;");
			name.setBounds(theGameEngine.getWidth() - (theGameEngine.getWidth() / 4 + theGameEngine.getWidth() / 33), theGameEngine.getHeight() / 15 * 2, theGameEngine.getHeight() / 4, theGameEngine.getWidth() / 35);
			name.setOpacity(0);
		    DropShadow shadow = new DropShadow();
		    shadow.setSpread(0.7);
		    shadow.setRadius(15);
			Glow gg = new Glow(0.7);
			gg.setInput(shadow);
			name.setEffect(gg);
			imgBg = new LauncherImage(loadImage(theGameEngine, "picBg.png"));
			imgBg.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 8 + theGameEngine.getHeight() / 12), theGameEngine.getHeight() / 12, (theGameEngine.getHeight() / 20 * 3), (theGameEngine.getHeight() / 20 * 3));
			imgBg.setOpacity(0);
			imgBg.setVisible(true);
			img = new LauncherImage(Infos.webImg);
			img.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 12 + theGameEngine.getWidth() / 22), theGameEngine.getHeight() / 11 + 1, theGameEngine.getWidth() / 29, (theGameEngine.getHeight() / 15 * 2));
			img.setOpacity(0);
			img.setVisible(true);
			CAnimation c = new CAnimation(topRectangle.opacityProperty(), 1, 700);
		    c.run();
		    c = new CAnimation(bottomRec.opacityProperty(), 1, 700);
		    c.run();
		    c = new CAnimation(playerCount.opacityProperty(), 1, 700);
		    c.run();
		    c = new CAnimation(playerCountValue.opacityProperty(), 1, 700);
		    c.run();
		    c = new CAnimation(serverVersion.opacityProperty(), 1, 700);
		    c.run();
		    c = new CAnimation(settings.opacityProperty(), 1, 700);
		    c.run();
		    c = new CAnimation(line.opacityProperty(), 1, 700);
		    c.run();
		    c = new CAnimation(link.opacityProperty(), 1, 700);
		    c.run();
		    c = new CAnimation(discord.opacityProperty(), 1, 700);
		    c.run();
		    c = new CAnimation(youtube.opacityProperty(), 1, 700);
		    c.run();
		    if ((boolean) config.getValue(EnumConfig.MUTED)) {
    		    c = new CAnimation(unmute.opacityProperty(), 1, 700);
    		    c.run();
		    }
		    else {
    		    c = new CAnimation(mute.opacityProperty(), 1, 700);
    		    c.run();
		    }
		    c = new CAnimation(name.opacityProperty(), 1, 700);
		    c.run();
		    c = new CAnimation(imgBg.opacityProperty(), 1, 700);
		    c.run();
		    c = new CAnimation(img.opacityProperty(), 1, 700);
		    c.run();
		    if (Infos.updating) {
		    	percentageLabel.setVisible(true);
		    	currentStep.setVisible(true);
		    	bar.setVisible(true);
		    	bar.animate();
		    	if (getResponsive(10) == 10) {
		    		bar.small();
		    	}
		    	else {
		    		bar.big();
		    	}
				c = new CAnimation(percentageLabel.opacityProperty(), 0.4, 700);
			    c.run();
				c = new CAnimation(currentStep.opacityProperty(), 0.4, 700);
			    c.run();
				c = new CAnimation(bar.opacityProperty(), 0.8, 700);
			    c.run();
    		    c = new CAnimation(fakeButton.opacityProperty(), 1, 700);
    		    c.run();
		    }
		    else {
				fakeButton.setVisible(false);
				fakeButton.setDisable(true);
				playButton.setDisable(false);
    		    c = new CAnimation(playButton.opacityProperty(), 1, 700);
    		    c.run();
		    }
		}
		else {
			bottomRec = new LauncherImage(loadImage(theGameEngine, "fade.png"));
			bottomRec.setBounds(0, theGameEngine.getHeight() - theGameEngine.getHeight() / 4, theGameEngine.getWidth(), theGameEngine.getHeight() / 4);
			bottomRec.setOpacity(0);
			bottomRec.setVisible(true);
			playButton = new LauncherLabel();
			playButton.setText("JOUER >");
			playButton.setAlignment(Pos.CENTER);
			playButton.setTextAlignment(TextAlignment.CENTER);
			playButton.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2 * 2));
			playButton.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
			playButton.setBounds(0, theGameEngine.getHeight() - (theGameEngine.getHeight() / 11 + theGameEngine.getHeight() / 23), theGameEngine.getWidth(), theGameEngine.getHeight() / 6);
			playButton.setOpacity(0);
			playButton.setOnMouseClicked(t -> {
                if(!Infos.updating && !Infos.launching) {
                    Infos.launching = true;
                    theGameEngine.reg(GameMemory.getMemory(Double.parseDouble((String) config.getValue(EnumConfig.RAM))));
                    theGameEngine.reg(GameSize.getWindowSize(Integer.parseInt((String) config.getValue(EnumConfig.GAME_SIZE))));
                    String vmArgs = (String) config.getValue(EnumConfig.VM_ARGUMENTS);
                    String[] s = null;
                    if (vmArgs != null && !vmArgs.equals("")) {
                        s = vmArgs.split(" ");
                    }
                    String ss = "";
                    if (s != null) {
                        for (String str : s) {
                            if (!str.contains("CMSIncrementalMode")) {
                                ss = ss + str + " ";
                            }
                        }
                        s = null;
                        if (ss != null && !ss.equals("")) {
                            s = ss.split(" ");
                        }
                        if (s != null) {
                            JVMArguments arguments = new JVMArguments(s);
                            theGameEngine.reg(arguments);
                        }
                    }
                    else {
                        s = null;
                        JVMArguments arguments = new JVMArguments(s);
                        theGameEngine.reg(arguments);
                    }
                    GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(), AccountType.OFFLINE);
                    GameRunner gameRunner = new GameRunner(theGameEngine, auth.getSession(), fontSize2);
                    try {
                        playButton.setText("Lancement du jeu...");
                        gameRunner.launch();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
			fakeButton = new LauncherLabel();
			fakeButton.setText("Chargement...");
			fakeButton.setAlignment(Pos.CENTER);
			fakeButton.setTextAlignment(TextAlignment.CENTER);
			fakeButton.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2 * 2));
			fakeButton.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
			fakeButton.setBounds(0, theGameEngine.getHeight() - (theGameEngine.getHeight() / 11 + theGameEngine.getHeight() / 23), theGameEngine.getWidth(), theGameEngine.getHeight() / 6);
			fakeButton.setOpacity(0);
			playerCount = new LauncherLabel();
			playerCount.setText("JOUEURS");
			playerCount.setAlignment(Pos.CENTER);
			playerCount.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2));
			playerCount.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
			playerCount.setBounds(0, theGameEngine.getHeight() - theGameEngine.getWidth() / 17, theGameEngine.getHeight() / 7, theGameEngine.getHeight() / 6);
			playerCount.setOpacity(0);
			playerCountValue = new LauncherLabel();
			playerCountValue.setText(Infos.playerCount + "/300");
			playerCountValue.setAlignment(Pos.CENTER_LEFT);
			playerCountValue.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2 - 4));
			playerCountValue.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: gray;");
			playerCountValue.setBounds(theGameEngine.getHeight() / 7 + getResponsive(3), theGameEngine.getHeight() - (theGameEngine.getWidth() / 34 + theGameEngine.getWidth() / 35), theGameEngine.getHeight() / 11, theGameEngine.getHeight() / 6);
			playerCountValue.setOpacity(0);
			serverVersion = new LauncherLabel();
			serverVersion.setText("Krosmocraft v" + Infos.version);
			serverVersion.setAlignment(Pos.CENTER_RIGHT);
			serverVersion.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2));
			serverVersion.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
			serverVersion.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 4 + theGameEngine.getHeight() / 10), theGameEngine.getHeight() - theGameEngine.getWidth() / 17, theGameEngine.getHeight() / 6 * 2, theGameEngine.getHeight() / 6);
			serverVersion.setOpacity(0);
			settings = new LauncherImage(loadImage(theGameEngine, "settings.png"));
			settings.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - (theGameEngine.getWidth() / 9 + theGameEngine.getWidth() / 47), theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 32);
			settings.setOpacity(0);
			settings.setVisible(true);
			line = new LauncherImage(loadImage(theGameEngine, "line.png"));
			line.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - (theGameEngine.getHeight() / 7 + theGameEngine.getHeight() / 20), theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 24);
			line.setOpacity(0);
			line.setVisible(true);
			link = new LauncherImage(loadImage(theGameEngine, "link.png"));
			link.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - theGameEngine.getHeight() / 7, theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 32);
			link.setOpacity(0);
			link.setVisible(true);
			discord = new LauncherImage(loadImage(theGameEngine, "discord.png"));
			discord.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - (theGameEngine.getWidth() / 30 + theGameEngine.getHeight() / 30), theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 32);
			discord.setOpacity(0);
			discord.setVisible(true);
			youtube = new LauncherImage(loadImage(theGameEngine, "youtube.png"));
			youtube.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - (theGameEngine.getHeight() / 49 + theGameEngine.getHeight() / 46), theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 34);
			youtube.setOpacity(0);
			youtube.setVisible(true);
			mute = new LauncherImage(loadImage(theGameEngine, "mute.png"));
			unmute = new LauncherImage(loadImage(theGameEngine, "unmute.png"));
			if ((boolean) config.getValue(EnumConfig.MUTED)) {
				LauncherMain.mediaPlayer.setVolume(0);
			}
			mute.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 + theGameEngine.getHeight() / 135, theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 34);
			mute.setOpacity(0);
			mute.setVisible(true);
			unmute.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 + theGameEngine.getHeight() / 135, theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 34);
			unmute.setOpacity(0);
			unmute.setVisible(true);
			name = new LauncherLabel();
			name.setText(Infos.pseudo);
			name.setAlignment(Pos.CENTER_RIGHT);
			name.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2));
			name.setStyle("-fx-font-weight: bolder; -fx-background-color: transparent; -fx-text-fill: white;");
			name.setBounds(theGameEngine.getWidth() - (theGameEngine.getWidth() / 4 + theGameEngine.getWidth() / 33), theGameEngine.getHeight() / 15 * 2, theGameEngine.getHeight() / 4, theGameEngine.getWidth() / 35);
			name.setOpacity(0);
			DropShadow shadow = new DropShadow();
			shadow.setSpread(0.7);
			shadow.setRadius(15);
			Glow gg = new Glow(0.7);
			gg.setInput(shadow);
			name.setEffect(gg);
			imgBg = new LauncherImage(loadImage(theGameEngine, "picBg.png"));
			imgBg.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 8 + theGameEngine.getHeight() / 12), theGameEngine.getHeight() / 12, (theGameEngine.getHeight() / 20 * 3), (theGameEngine.getHeight() / 20 * 3));
			imgBg.setOpacity(0);
			imgBg.setVisible(true);
			img = new LauncherImage(Infos.webImg);
			img.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 12 + theGameEngine.getWidth() / 22), theGameEngine.getHeight() / 11 + 1, theGameEngine.getWidth() / 29, (theGameEngine.getHeight() / 15 * 2));
			img.setOpacity(0);
			img.setVisible(true);
			topRectangle.setOpacity(1);
			bottomRec.setOpacity(1);
			playerCount.setOpacity(1);
			playerCountValue.setOpacity(1);
			serverVersion.setOpacity(1);
			settings.setOpacity(1);
			line.setOpacity(1);
			link.setOpacity(1);
			discord.setOpacity(1);
			youtube.setOpacity(1);
			if ((boolean) config.getValue(EnumConfig.MUTED)) {
				unmute.setOpacity(1);
			}
			else {
				mute.setOpacity(1);
			}
			name.setOpacity(1);
			imgBg.setOpacity(1);
			img.setOpacity(1);
			if (Infos.updating) {
				percentageLabel.setVisible(true);
				currentStep.setVisible(true);
				bar.setVisible(true);
				percentageLabel.setOpacity(0.4);
				currentStep.setOpacity(0.4);
				bar.setOpacity(0.8);
				bar.animate();
				if (getResponsive(10) == 10) {
					bar.small();
				}
				else {
					bar.big();
				}
				fakeButton.setOpacity(1);
			}
			else {
				fakeButton.setVisible(false);
				fakeButton.setDisable(true);
				playButton.setDisable(false);
				playButton.setOpacity(1);
			}
		}
		countHitbox = new LauncherLabel();
		countHitbox.setStyle("-fx-background-color: transparent;");
		countHitbox.setBounds(0, theGameEngine.getHeight() - theGameEngine.getHeight() / 20, theGameEngine.getHeight() / 5 + theGameEngine.getHeight() / 17, theGameEngine.getHeight() / 20);
		settingsHitbox = new LauncherLabel();
		settingsHitbox.setStyle("-fx-background-color: transparent;");
		settingsHitbox.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - (theGameEngine.getWidth() / 9 + theGameEngine.getWidth() / 47), theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 32);
		linkHitbox = new LauncherLabel();
		linkHitbox.setStyle("-fx-background-color: transparent;");
		linkHitbox.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - theGameEngine.getHeight() / 7, theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 32);
		discordHitbox = new LauncherLabel();
		discordHitbox.setStyle("-fx-background-color: transparent;");
		discordHitbox.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - (theGameEngine.getWidth() / 30 + theGameEngine.getHeight() / 30), theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 32);
		youtubeHitbox = new LauncherLabel();
		youtubeHitbox.setStyle("-fx-background-color: transparent;");
		youtubeHitbox.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 - (theGameEngine.getHeight() / 49 + theGameEngine.getHeight() / 46), theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 32);
		muteHitbox = new LauncherLabel();
		muteHitbox.setStyle("-fx-background-color: transparent;");
		muteHitbox.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 11 + theGameEngine.getWidth() / 30), theGameEngine.getHeight() / 2 + theGameEngine.getHeight() / 135, theGameEngine.getHeight() / 32, theGameEngine.getHeight() / 32);
		imgOverlay = new LauncherImage(loadImage(theGameEngine, "picOverlay.png"));
		imgOverlay.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 8 + theGameEngine.getHeight() / 12),  theGameEngine.getHeight() / 12, (theGameEngine.getHeight() / 20 * 3), (theGameEngine.getHeight() / 20 * 3));
		imgOverlay.setOpacity(0);
		imgOverlay.setVisible(true);
		imgOverlayText = new LauncherLabel();
		imgOverlayText.setAlignment(Pos.CENTER);
		imgOverlayText.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", fontSize2 + 2));
		imgOverlayText.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		imgOverlayText.setBounds(theGameEngine.getWidth() - (theGameEngine.getHeight() / 8 + theGameEngine.getHeight() / 12),  theGameEngine.getHeight() / 12, (theGameEngine.getHeight() / 20 * 3), (theGameEngine.getHeight() / 20 * 3));
		imgOverlayText.setOpacity(0);
		imgOverlayText.setText("Modifier");
		imgOverlayText.setOnMouseEntered(t -> {
            CAnimation c = new CAnimation(imgOverlay.opacityProperty(), 1, 200);
            c.run();
            c = new CAnimation(imgOverlayText.opacityProperty(), 1, 200);
            c.run();
        });
		imgOverlayText.setOnMouseClicked(t -> openLink(SKIN_URL));
		imgOverlayText.setOnMouseExited(t -> {
            CAnimation c = new CAnimation(imgOverlay.opacityProperty(), 0.0, 200);
            c.run();
            c = new CAnimation(imgOverlayText.opacityProperty(), 0, 200);
            c.run();
        });
		playButton.setOnMouseEntered(t -> {
            Glow g = new Glow(0.0);
            playButton.setEffect(g);
            CAnimation c = new CAnimation(g.levelProperty(), 1, 200);
            c.run();
        });
		playButton.setOnMouseExited(t -> {
            Glow g = new Glow(1);
            playButton.setEffect(g);
            CAnimation c = new CAnimation(g.levelProperty(), 0.0, 200);
            c.run();
        });
		fakeButton.setOnMouseEntered(t -> {
            Glow g = new Glow(0.0);
            fakeButton.setEffect(g);
            CAnimation c = new CAnimation(g.levelProperty(), 1, 200);
            c.run();
        });
		fakeButton.setOnMouseExited(t -> {
            Glow g = new Glow(1);
            fakeButton.setEffect(g);
            CAnimation c = new CAnimation(g.levelProperty(), 0.0, 200);
            c.run();
        });
		serverVersion.setOnMouseEntered(t -> {
            Glow g = new Glow(0.0);
            serverVersion.setEffect(g);
            CAnimation c = new CAnimation(g.levelProperty(), 1, 200);
            c.run();
        });
		serverVersion.setOnMouseExited(t -> {
            Glow g = new Glow(1);
            serverVersion.setEffect(g);
            CAnimation c = new CAnimation(g.levelProperty(), 0.0, 200);
            c.run();
        });
		countHitbox.setOnMouseEntered(t -> {
            Glow g = new Glow(0.0);
            playerCount.setEffect(g);
            playerCountValue.setEffect(g);
            CAnimation c = new CAnimation(g.levelProperty(), 1, 200);
            c.run();
        });
		countHitbox.setOnMouseExited(t -> {
            Glow g = new Glow(1);
            playerCount.setEffect(g);
            playerCountValue.setEffect(g);
            CAnimation c = new CAnimation(g.levelProperty(), 0.0, 200);
            c.run();
        });
		settingsHitbox.setOnMouseEntered(t -> {
            CAnimation cc = new CAnimation(settings.scaleXProperty(), 1.4, 100);
            cc.run();
            cc = new CAnimation(settings.scaleYProperty(), 1.4, 100);
            cc.run();
        });
		settingsHitbox.setOnMouseClicked(t -> {
            Settings s = new Settings(config, theGameEngine, getResourceLocation(), Infos.pseudo, fontSize);
            s.draw();
        });
		settingsHitbox.setOnMouseExited(t -> {
            CAnimation cc = new CAnimation(settings.scaleXProperty(), 1, 100);
            cc.run();
            cc = new CAnimation(settings.scaleYProperty(), 1, 100);
            cc.run();
        });
		linkHitbox.setOnMouseEntered(t -> {
            CAnimation cc = new CAnimation(link.scaleXProperty(), 1.4, 100);
            cc.run();
            cc = new CAnimation(link.scaleYProperty(), 1.4, 100);
            cc.run();
        });
		linkHitbox.setOnMouseClicked(t -> openLink(WEB_URL));
		linkHitbox.setOnMouseExited(t -> {
            CAnimation cc = new CAnimation(link.scaleXProperty(), 1, 100);
            cc.run();
            cc = new CAnimation(link.scaleYProperty(), 1, 100);
            cc.run();
        });
		discordHitbox.setOnMouseEntered(t -> {
            CAnimation cc = new CAnimation(discord.scaleXProperty(), 1.4, 100);
            cc.run();
            cc = new CAnimation(discord.scaleYProperty(), 1.4, 100);
            cc.run();
        });
		discordHitbox.setOnMouseClicked(t -> openLink(DISCORD_URL));
		discordHitbox.setOnMouseExited(t -> {
            CAnimation cc = new CAnimation(discord.scaleXProperty(), 1, 100);
            cc.run();
            cc = new CAnimation(discord.scaleYProperty(), 1, 100);
            cc.run();
        });
		youtubeHitbox.setOnMouseEntered(t -> {
            CAnimation cc = new CAnimation(youtube.scaleXProperty(), 1.4, 100);
            cc.run();
            cc = new CAnimation(youtube.scaleYProperty(), 1.4, 100);
            cc.run();
        });
		youtubeHitbox.setOnMouseClicked(t -> openLink(YOUTUBE_URL));
		youtubeHitbox.setOnMouseExited(t -> {
            CAnimation cc = new CAnimation(youtube.scaleXProperty(), 1, 100);
            cc.run();
            cc = new CAnimation(youtube.scaleYProperty(), 1, 100);
            cc.run();
        });
		muteHitbox.setOnMouseEntered(t -> {
            CAnimation cc = new CAnimation(mute.scaleXProperty(), 1.4, 100);
            cc.run();
            cc = new CAnimation(mute.scaleYProperty(), 1.4, 100);
            cc.run();
            cc = new CAnimation(unmute.scaleXProperty(), 1.4, 100);
            cc.run();
            cc = new CAnimation(unmute.scaleYProperty(), 1.4, 100);
            cc.run();
        });
		muteHitbox.setOnMouseClicked(t -> {
            config.loadConfiguration();
            if (!(boolean) config.getValue(EnumConfig.MUTED)) {
                config.updateValue("muted", true);
                unmute.setOpacity(1);
                mute.setOpacity(0);
				LauncherMain.mediaPlayer.setVolume(0);
            }
            else {
                config.updateValue("muted", false);
				LauncherMain.mediaPlayer.setVolume(0.5);
                mute.setOpacity(1);
                unmute.setOpacity(0);
            }
        });
		muteHitbox.setOnMouseExited(t -> {
            CAnimation cc = new CAnimation(mute.scaleXProperty(), 1, 100);
            cc.run();
            cc = new CAnimation(mute.scaleYProperty(), 1, 100);
            cc.run();
            cc = new CAnimation(unmute.scaleXProperty(), 1, 100);
            cc.run();
            cc = new CAnimation(unmute.scaleYProperty(), 1, 100);
            cc.run();
        });
	}

	private boolean firstTimeUwU = true;
	
	public void connect() {
		if(Infos.username && Infos.password) {
			if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
				config.updateValue("username", usernameField.getText());
				loginButton.setText("Connexion...");
				usernameField.setDisable(true);
				passwordField.setDisable(true);
				usernameField.setOpacity(0.7);
				passwordField.setOpacity(0.7);
				loadingSpin.setOpacity(1);
				if (firstTimeUwU){
					CAnimation c = new CAnimation(loadingSpin.rotateProperty(), 360, 1200);
					c.loop();
					firstTimeUwU = false;
				}
				LauncherMain.connectionHandler.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
					@Override
					public void handle(WorkerStateEvent event) {
					  // La tâche s'est correctement terminée.
						 switch (LauncherMain.connectionHandler.getValue()) {
							 case Success: {
									loadingSpin.setOpacity(0);
									loadingSpin2 = new LauncherImage();
									loadingSpin2.setImage(new Image(ResourceLocation.class.getResource(theGameEngine.getLauncherPreferences().getResourceLocation() + "checked.gif").toExternalForm(), theGameEngine.getHeight() / 20, theGameEngine.getHeight() / 20, true, true));
									loadingSpin2.setX(theGameEngine.getWidth() / 2 - theGameEngine.getHeight() / 37);
									loadingSpin2.setY(theGameEngine.getHeight() * 0.817);
									loginButton.setText("Succès !");
									String username = usernameField.getText();
								 	Infos.connected = true;
							    	Task<Void> loader = new Task<Void>() {
							            @Override
							            protected Void call() throws Exception {
											try {
												if (Infos.webImg == null) {
													URL url = new URL("https://krosmocraft.fr/skin/3d?user=" + username);
										            URLConnection conn = url.openConnection();
										            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
										            conn.connect();
										            InputStream urlStream = conn.getInputStream();
										            BufferedImage i = ImageIO.read(urlStream);
													Infos.webImg = SwingFXUtils.toFXImage(i, null);
													Thread.sleep(1000);
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
							                return null;
							            }
							        };
							        loader.setOnSucceeded(e -> phase2(true));
							        new Thread(loader).start();
							        return;
							 }
							 case FillPseudo: {
									loadingSpin.setOpacity(0);
									loadingSpin2 = new LauncherImage();
									loadingSpin2.setImage(new Image(ResourceLocation.class.getResource(theGameEngine.getLauncherPreferences().getResourceLocation() + "checked.gif").toExternalForm(), theGameEngine.getHeight() / 20, theGameEngine.getHeight() / 20, true, true));
									loadingSpin2.setX(theGameEngine.getWidth() / 2 - theGameEngine.getHeight() / 37);
									loadingSpin2.setY(theGameEngine.getHeight() * 0.817);
									loginButton.setText("Succès !");
								 	String username = usernameField.getText();
								 	Infos.connected = true;
							    	Task<Void> loader = new Task<Void>() {
							            @Override
							            protected Void call() throws Exception {
											try {
												if (Infos.webImg == null) {
													URL url = new URL("https://krosmocraft.fr/skin/3d?user=" + username);
										            URLConnection conn = url.openConnection();
										            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
										            conn.connect();
										            InputStream urlStream = conn.getInputStream();
										            BufferedImage i = ImageIO.read(urlStream);
													Infos.webImg = SwingFXUtils.toFXImage(i, null);
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
							                return null;
							            }
							        };
							        loader.setOnSucceeded(e -> phase2(true));
							        new Thread(loader).start();
							        return;
							 }
							 case NoInternet: {
								 new ErrorAlert(theGameEngine, "Authentification échouée !", "Veuillez vérifier votre connexion Internet puis réessayer", fontSize2).draw();
								 break;
							 }
							 case WrongCredentials: {
								 new ErrorAlert(theGameEngine, "Authentification échouée !", "Veuillez vérifier le nom de compte et le mot de passe saisi", fontSize2).draw();
								 break;
							 }
							 case ServerDown: {
								 new ErrorAlert(theGameEngine, "Authentification échouée !", "Le serveur est hors ligne ou en maintenance, réessayer plus tard", fontSize2).draw();
								 break;
							 }
							 default: {
								break;
							 }
						 }
						 loadingSpin.setOpacity(0);
						 loginButton.setText("Se connecter >");
						 usernameField.setDisable(false);
						 passwordField.setDisable(false);
						 usernameField.setOpacity(1);
						 passwordField.setOpacity(1);
					}
				});
				LauncherMain.connectionHandler.start(usernameField.getText(), passwordField.getText());
			}
		}
	}
}