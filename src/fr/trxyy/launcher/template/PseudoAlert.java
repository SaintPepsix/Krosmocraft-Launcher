package fr.trxyy.launcher.template;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.ResourceLocation;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherImage;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherTextField;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PseudoAlert {
	private LauncherRectangle blurRectangle;
	private LauncherLabel title;
	private LauncherLabel message;
	private LauncherLabel message2;
	private LauncherTextField pseudoField;
	private LauncherButton sendButton;
	private final GameEngine theGameEngine;
	private final LauncherPanel panel;
	private final Pane root;
	private final String username;
	private final String password;
	private final int size;
	private boolean listening = true;
	private LauncherImage loadingSpin;
	private final ResourceLocation r = new ResourceLocation();
	
	public PseudoAlert(String username, String password, GameEngine engine, int size, LauncherPanel panel){
		this.username = username;
		this.password = password;
		this.root = LauncherMain.pane;
		this.theGameEngine = engine;
		this.size = size;
		this.panel = panel;
	}
	
	public void draw() {
		this.blurRectangle = new LauncherRectangle(0, 0, theGameEngine.getWidth(), theGameEngine.getHeight());
		this.blurRectangle.setOpacity(0);
		this.blurRectangle.setFill(Color.rgb(10, 10, 10, 0.9));
    	CAnimation c = new CAnimation(blurRectangle.opacityProperty(), 1, 500);
    	c.run();
    	
		this.title = new LauncherLabel();
		this.title.setText("Veuillez choisir un pseudo de compte");
		this.title.setAlignment(Pos.CENTER);
		this.title.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size * 2));
		this.title.setStyle("-fx-font-weight: bold; -fx-font-family: \"Helvetica\"; -fx-background-color: transparent; -fx-text-fill: white;");
		this.title.setBounds(0, 0, theGameEngine.getWidth(), theGameEngine.getHeight() - getResponsive(10) * 16);
		this.title.setOpacity(0);
    	c = new CAnimation(title.opacityProperty(), 1, 500);
    	c.run();
    	
		this.message = new LauncherLabel();
		this.message.setText("Ce pseudo permettra aux autres joueurs de vous reconnaître peu importe votre personnage");
		this.message.setAlignment(Pos.CENTER);
		this.message.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.message.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.message.setBounds(0, 0, theGameEngine.getWidth(), theGameEngine.getHeight() - getResponsive(10) * 6);
		this.message.setOpacity(0);
    	c = new CAnimation(message.opacityProperty(), 1, 500);
    	c.run();
    	
		this.message2 = new LauncherLabel();
		this.message2.setText("Attention, celui-ci est définitif");
		this.message2.setAlignment(Pos.CENTER);
		this.message2.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.message2.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.message2.setBounds(0, 0, theGameEngine.getWidth(), theGameEngine.getHeight() + getResponsive(10) * 2);
		this.message2.setOpacity(0);
    	c = new CAnimation(message2.opacityProperty(), 1, 500);
    	c.run();
    	
    	this.pseudoField = new LauncherTextField();
        // Set the maximum limit to 13 characters
    	this.pseudoField.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 13 ? change : null));
		this.pseudoField.setBounds(theGameEngine.getWidth() / 2 - getResponsive(10) * 13 + getResponsive(6), theGameEngine.getHeight() / 2 + getResponsive(10) * 6, getResponsive(10) * 25, getResponsive(10) * 4);
		this.pseudoField.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.pseudoField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.pseudoField.setVoidText("Pseudo");
		this.pseudoField.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));
		this.pseudoField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.contains(" ") || containsAccents(newValue)) {
				this.pseudoField.setText(oldValue);
				return;
			}
			else {
				int digitCount = 0;
				int specialCount = 0;
				HashMap<Character, Integer> limits = new HashMap<Character, Integer>();
		        for (int i = 0; i < newValue.length(); i++) {
		            char character = newValue.charAt(i);
		            if (!Character.isLetterOrDigit(character)) {
		            	if (character != '-') {
							this.pseudoField.setText(oldValue);
							return;
		            	}
		            	else {
		            		specialCount++;
		            	}
		            }
		            else if (Character.isDigit(character)) {
		                digitCount++;
		            }
		            else {
		            	limits.put(Character.toLowerCase(character), limits.getOrDefault(Character.toLowerCase(character), 0) + 1);
		            }
		            
		            if (digitCount > 3 || specialCount > 2 || limits.getOrDefault(Character.toLowerCase(character), 0) > 5) {
						this.pseudoField.setText(oldValue);
						return;
		            }
		        }
			}
			formatText(newValue);
		    if (newValue.isEmpty() || newValue == null || newValue.length() < 3) {
		    	this.sendButton.setOpacity(0.2);
		    }
		    else {
		    	this.sendButton.setOpacity(0.9);
		    }
		});
    	
		this.sendButton = new LauncherButton();
		this.sendButton.setText("Confirmer >");
		this.sendButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size * 2));
		this.sendButton.setBounds(theGameEngine.getWidth() / 2 - theGameEngine.getWidth() / 7, theGameEngine.getHeight() / 2 + theGameEngine.getHeight() / 6 + theGameEngine.getHeight() / 21,  theGameEngine.getWidth() / 4 + theGameEngine.getWidth() / 28, theGameEngine.getHeight() / 10);
		this.sendButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.sendButton.setOpacity(0.2);
		
		Glow g = new Glow(0.5);
		sendButton.setEffect(g);
		
		this.sendButton.setOnMouseEntered(t -> {
            if (pseudoField.getText() != null && !pseudoField.getText().isEmpty() && pseudoField.getText().length() >= 3) {
                Glow g12 = new Glow(0.5);
                sendButton.setEffect(g12);
                CAnimation c12 = new CAnimation(sendButton.opacityProperty(), 1, 200);
                CAnimation c2 = new CAnimation(g12.levelProperty(), 1, 200);
                c12.run();
                c2.run();
            }
        });
		this.sendButton.setOnMouseExited(t -> {
            if (pseudoField.getText() != null && !pseudoField.getText().isEmpty() && pseudoField.getText().length() >= 3) {
                Glow g1 = new Glow(1);
                sendButton.setEffect(g1);
                CAnimation c1 = new CAnimation(sendButton.opacityProperty(), 0.8, 200);
                CAnimation c2 = new CAnimation(g1.levelProperty(), 0.5, 200);
                c1.run();
                c2.run();
            }
        });

		Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(e -> {
            root.getScene().addEventHandler(KeyEvent.KEY_PRESSED, ke -> {
                if (ke.getCode() == KeyCode.ENTER && listening) {
                    if (pseudoField.getText() != null && !pseudoField.getText().isEmpty() && pseudoField.getText().length() >= 3) {
                        sendPseudo();
                    }
                }
            });
            sendButton.setOnMouseClicked(me -> {
                if (pseudoField.getText() != null && !pseudoField.getText().isEmpty() && pseudoField.getText().length() >= 3) {
                    sendPseudo();
                }
            });
        });
        new Thread(sleeper).start();
	}
	
	private void sendPseudo() {
		this.sendButton.setText("Chargement...");
		pseudoField.setDisable(true);
		pseudoField.setOpacity(0.7);
		LauncherMain.connectionHandler.setOnRunning(e -> {
			if (!this.listening){
				return;
			}
            //La tâche est en cours
            loadingSpin = new LauncherImage();
            loadingSpin.setImage(r.loadImage(theGameEngine, "loading.png"));
            loadingSpin.setBounds(theGameEngine.getWidth() / 2 - theGameEngine.getWidth() / 60, theGameEngine.getHeight() - theGameEngine.getHeight() / 6, theGameEngine.getHeight() / 24, theGameEngine.getHeight() / 24);
            CAnimation c = new CAnimation(loadingSpin.rotateProperty(), 360, 1200);
            c.loop();
        });
		LauncherMain.connectionHandler.setOnFailed(e -> {
          // La tâche a échoué.
          final Throwable error = LauncherMain.connectionHandler.getException();
        });
		LauncherMain.connectionHandler.setOnSucceeded(e -> {
          // La tâche s'est correctement terminée.
            switch (LauncherMain.connectionHandler.getValue()) {
                case Success: {
                    listening = false;
                    end();
                    CAnimation c = new CAnimation(blurRectangle.opacityProperty(), 0.0, 400);
                    c.run();
                    c = new CAnimation(title.opacityProperty(), 0.0, 400);
                    c.run();
                    c = new CAnimation(message.opacityProperty(), 0.0, 400);
                    c.run();
                    c = new CAnimation(message2.opacityProperty(), 0.0, 400);
                    c.run();
                    c = new CAnimation(sendButton.opacityProperty(), 0.0, 400);
                    c.run();
                    c = new CAnimation(loadingSpin.opacityProperty(), 0.0, 400);
                    c.run();
                    c = new CAnimation(pseudoField.opacityProperty(), 0.0, 400);
                    c.run();
                    return;
                }
                case BadSize: {
                     new ErrorAlert(theGameEngine, "Pseudo invalide !", "Votre pseudo doit comprendre entre 3 et 13 caractères", size).draw();
                     break;
                }
                case Invalid: {
                     new ErrorAlert(theGameEngine, "Pseudo invalide !", "Le pseudo choisi est invalide", size).draw();
                     break;
                }
                case AlreadyTaken: {
                     new ErrorAlert(theGameEngine, "Pseudo invalide !", "Ce pseudo n'est pas disponible", size).draw();
                     break;
                }
                default: {
                    break;
                }
            }
            loadingSpin.setVisible(false);
            sendButton.setText("Confirmer >");
            pseudoField.setDisable(false);
            pseudoField.setOpacity(1);
        });
		LauncherMain.connectionHandler.start(username, password, pseudoField.getText());
	}

	public static boolean containsAccents(String input) {
		Pattern pattern = Pattern.compile("[\\p{M}\\p{InGreek}\\p{InCyrillic}\\p{InArabic}\\p{InHebrew}\\p{InDevanagari}\\p{InCJK_Unified_Ideographs}\\p{InLatin_Extended_A}\\p{InLatin_Extended_B}\\p{Sc}\\p{P}\\x{1F600}-\\x{1F64F}]");
		Matcher matcher = pattern.matcher(Normalizer.normalize(input, Normalizer.Form.NFD));
		return matcher.find();
	}

	public void formatText(String newText) {
		if (!newText.isEmpty()) {
	    	int uppercount = 0;
	    	int cee = '-';
	    	char[] chars = newText.toCharArray();
	    	if (Character.isLetter(chars[0])) {
	    		chars[0] = Character.toUpperCase(chars[0]);
    			uppercount++;
	    	}
	    	int x = 0;
	    	for (char c : chars) {
	    		if (x != 0 && chars[x - 1] == cee) {
	    			if (Character.isLowerCase(c)) {
		    			chars[x] = Character.toUpperCase(c);
	    			}
	    			if (!Character.isDigit(c) && c != cee) {
		    			uppercount++;
	    			}
	    		}
	    		x++;
	    	}
	    	x = 0;
	    	for (char c : chars) {
	    		if (x != 0 && Character.isUpperCase(c) && chars[x - 1] != cee) {
	    			if (uppercount >= 3) {
		    			chars[x] = Character.toLowerCase(c);
	    			}
	    			uppercount++;
	    		}
	    		x++;
	    	}
	    	newText = new String(chars);
	    	this.pseudoField.setText(newText);
		}
	}

	public int getResponsive(int x) {
        return switch (x) {
            case 2 -> (int) (theGameEngine.getHeight() / 300);
            case 3 -> (int) (theGameEngine.getHeight() / 200);
            case 4 -> (int) (theGameEngine.getHeight() / 135);
            case 5 -> (int) (theGameEngine.getWidth() / 200);
            case 6 -> (int) (theGameEngine.getWidth() / 170);
            case 7 -> (int) (theGameEngine.getWidth() / 135);
            case 8 -> (int) (theGameEngine.getWidth() / 130);
            case 9 -> (int) (theGameEngine.getWidth() / 110);
            case 10 -> (int) (theGameEngine.getWidth() / 100);
            default -> 0;
        };
	}
	
	public void end() {
		Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                	sendButton.setDisable(true);
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(e -> {
            blurRectangle.setDisable(true);
            title.setDisable(true);
            message.setDisable(true);
            message2.setDisable(true);
            sendButton.setDisable(true);
            loadingSpin.setDisable(true);
            pseudoField.setDisable(true);
			LauncherPanel.phase2 = false;
			LauncherPanel.phase3 = false;
            panel.phase2(true);
        });
        new Thread(sleeper).start();
	}
}
