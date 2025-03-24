package fr.trxyy.launcher.template;

import java.lang.management.ManagementFactory;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameSize;
import fr.trxyy.alternative.alternative_api.Infos;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.ResourceLocation;
import fr.trxyy.alternative.alternative_api.utils.config.EnumConfig;
import fr.trxyy.alternative.alternative_api.utils.config.LauncherConfig;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherImage;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Settings {
	private LauncherRectangle blurRectangle;
	private LauncherLabel settingsTitle;
	private LauncherLabel compte;
	private LauncherLabel minecraft;
	private LauncherLabel shaders;
	private LauncherLabel java;
	private LauncherImage line;
	private LauncherLabel done;
	private final GameEngine theGameEngine;
	private final Pane root;
	private final LauncherConfig config;
	private final int size;
	private boolean ending = false;
	private boolean clicked = false;
	private final ResourceLocation loc;
	private LauncherLabel selected;
	private final String USERNAME;
	private LauncherLabel titleLabel;
	
	public Settings(LauncherConfig config, GameEngine engine, ResourceLocation loc, String username, int size, LauncherLabel titleLabel){
		this.root = LauncherMain.pane;
		this.config = config;
		this.theGameEngine = engine;
		this.loc = loc;
		this.USERNAME = username;
		this.size = size;
		this.titleLabel = titleLabel;
	}
	
	public void draw() {
		ArrayList<LauncherLabel> labels = new ArrayList<LauncherLabel>();
		this.blurRectangle = new LauncherRectangle(0, 0, theGameEngine.getWidth(), theGameEngine.getHeight());
		this.blurRectangle.setOpacity(0);
		this.blurRectangle.setFill(Color.rgb(10, 10, 10, 0.9));
    	CAnimation c = new CAnimation(blurRectangle.opacityProperty(), 1, 500);
    	c.run();
    	
		this.settingsTitle = new LauncherLabel();
		this.settingsTitle.setText("Settings");
		this.settingsTitle.setAlignment(Pos.CENTER);
		this.settingsTitle.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size * 2));
		this.settingsTitle.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.settingsTitle.setBounds(getResponsive(10) * 12, getResponsive(10) * 9, getResponsive(10) * 12, getResponsive(10) * 4);
		this.settingsTitle.setOpacity(0);
    	c = new CAnimation(settingsTitle.opacityProperty(), 1, 500);
    	c.run();
    	
		this.compte = new LauncherLabel();
		this.compte.setText("Compte");
		this.compte.setAlignment(Pos.CENTER_LEFT);
		this.compte.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.compte.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.compte.setBounds(getResponsive(10) * 15, getResponsive(10) * 15, getResponsive(10) * 7, getResponsive(10) * 4);
		this.compte.setOpacity(0);
    	c = new CAnimation(compte.opacityProperty(), 1, 500);
    	c.run();
		Glow g = new Glow(0);
		this.compte.setEffect(g);
    	c = new CAnimation(g.levelProperty(), 1, 500);
    	c.run();
    	
		this.minecraft = new LauncherLabel();
		this.minecraft.setText("Minecraft");
		this.minecraft.setAlignment(Pos.CENTER_LEFT);
		this.minecraft.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.minecraft.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.minecraft.setBounds(getResponsive(10) * 15, getResponsive(10) * 19 - getResponsive(5), getResponsive(10) * 7, getResponsive(10) * 4);
		this.minecraft.setOpacity(0);
    	c = new CAnimation(minecraft.opacityProperty(), 0.4, 500);
    	c.run();
    	
		this.shaders = new LauncherLabel();
		this.shaders.setText("Graphisme");
		this.shaders.setAlignment(Pos.CENTER_LEFT);
		this.shaders.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.shaders.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.shaders.setBounds(getResponsive(10) * 15, getResponsive(10) * 22, getResponsive(10) * 9, getResponsive(10) * 4);
		this.shaders.setOpacity(0);
    	c = new CAnimation(shaders.opacityProperty(), 0.4, 500);
    	c.run();
    	
		this.java = new LauncherLabel();
		this.java.setText("Java");
		this.java.setAlignment(Pos.CENTER_LEFT);
		this.java.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.java.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.java.setBounds(getResponsive(10) * 15, getResponsive(10) * 25 + getResponsive(5), getResponsive(10) * 7, getResponsive(10) * 4);
		this.java.setOpacity(0);
    	c = new CAnimation(java.opacityProperty(), 0.4, 500);
    	c.run();
    	
		this.line = new LauncherImage(this.loc.loadImage(theGameEngine, "grayline.png"));
		this.line.setBounds(getResponsive(10) * 14, getResponsive(10) * 30 + getResponsive(5), getResponsive(10) * 6 + getResponsive(5), 1);
		this.line.setOpacity(0);
    	c = new CAnimation(line.opacityProperty(), 0.5, 500);
    	c.run();
    	
		this.done = new LauncherLabel();
		this.done.setText("Fermer");
		this.done.setAlignment(Pos.CENTER_LEFT);
		this.done.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.done.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.done.setBounds(getResponsive(10) * 15, getResponsive(10) * 33 - getResponsive(5), getResponsive(10) * 8, getResponsive(10) * 4);
		this.done.setOpacity(0);
    	c = new CAnimation(done.opacityProperty(), 0.8, 500);
    	c.run();
    	
    	labels.add(compte);
    	labels.add(minecraft);
    	labels.add(shaders);
    	labels.add(java);
    	
    	selected = this.compte;
		accountTab();
    	
    	this.done.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (ending) {
		    		return;
		    	}
	    		Glow g = new Glow(0);
	    		done.setEffect(g);
		    	CAnimation c = new CAnimation(g.levelProperty(), 1, 200);
		    	c.run();
		    	c = new CAnimation(done.opacityProperty(), 1, 200);
		    	c.run();
		    }
		});
    	this.done.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (ending) {
		    		return;
		    	}
	    		Glow g = new Glow(1);
	    		done.setEffect(g);
		    	CAnimation c = new CAnimation(g.levelProperty(), 0, 200);
		    	c.run();
		    	c = new CAnimation(done.opacityProperty(), 0.8, 200);
		    	c.run();
		    }
		});
    	
    	for (LauncherLabel l : labels) {
    		l.setOnMouseEntered(new EventHandler<MouseEvent>() {
    		    @Override
    		    public void handle(MouseEvent t) {
    		    	if (ending || selected == l) {
    		    		return;
    		    	}
    	    		Glow g = new Glow(0);
    	    		l.setEffect(g);
    		    	CAnimation c = new CAnimation(g.levelProperty(), 1, 200);
    		    	c.run();
    		    	c = new CAnimation(l.opacityProperty(), 0.6, 200);
    		    	c.run();
    		    }
    		});
    		l.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		    @Override
    		    public void handle(MouseEvent t) {
    		    	if (ending || selected == l || clicked) {
    		    		return;
    		    	}
    		    	clicked = true;
    	    		Glow g = new Glow(1);
    	    		selected.setEffect(g);
    		    	CAnimation c = new CAnimation(g.levelProperty(), 0, 200);
    		    	c.run();
    		    	c = new CAnimation(selected.opacityProperty(), 0.4, 200);
    		    	c.run();
    		    	g = new Glow(1);
    	    		l.setEffect(g);
    		    	c = new CAnimation(g.levelProperty(), 1, 200);
    		    	c.run();
    		    	c = new CAnimation(l.opacityProperty(), 1, 200);
    		    	c.run();
    				Task<Void> sleeper = new Task<Void>() {
    		            @Override
    		            protected Void call() throws Exception {
    		                try {
    		    		    	if (selected.getText().equals("Compte")) {
    		    		    		endAccount();
    		    		    	}
    		    		    	else if (selected.getText().equals("Minecraft")) {
    		    		    		endMinecraft();
    		    		    	}
    		    		    	else if (selected.getText().equals("Graphisme")) {
    	        		    		endShaders();
    		    		    	}
    		    		    	else {
    		    		    		endJava();
    		    		    	}
    		                    Thread.sleep(200);
    		                } catch (InterruptedException e) {
    		                }
    		                return null;
    		            }
    		        };
    		        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
    		            @Override
    		            public void handle(WorkerStateEvent event) {
    	    		    	if (l.getText().equals("Compte")) {
    	    		    		accountTab();
    	    		    	}
    	    		    	else if (l.getText().equals("Minecraft")){
    	    		    		minecraftTab();
    	    		    	}
    	    		    	else if (l.getText().equals("Graphisme")){
    	    		    		shadersTab();
    	    		    	}
    	    		    	else {
    	    		    		javaTab();
    	    		    	}
    	    		    	selected = l;
    	    				Task<Void> sleeperr = new Task<Void>() {
    	    		            @Override
    	    		            protected Void call() throws Exception {
    	    		                try {
    	    		                    Thread.sleep(200);
    	    		                } catch (InterruptedException e) {
    	    		                }
    	    		                return null;
    	    		            }
    	    		        };
    	    		        sleeperr.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
    	    		            @Override
    	    		            public void handle(WorkerStateEvent event) {
    	    	    		    	clicked = false;
    	    	    		    	for (LauncherLabel ll : labels) {
    	    	    		    		if (ll != selected) {
        	    	    		    		ll.setEffect(null);
    	    	    		    		}
    	    	    		    	}
    	    		            }
    	    		        });
    	    		        new Thread(sleeperr).start();
    		            }
    		        });
    		        new Thread(sleeper).start();
    		    }
    		});
    		l.setOnMouseExited(new EventHandler<MouseEvent>() {
    		    @Override
    		    public void handle(MouseEvent t) {
    		    	if (ending || selected == l || clicked) {
    		    		return;
    		    	}
    	    		Glow g = new Glow(1);
    	    		l.setEffect(g);
    		    	CAnimation c = new CAnimation(g.levelProperty(), 0, 200);
    		    	c.run();
    		    	c = new CAnimation(l.opacityProperty(), 0.4, 200);
    		    	c.run();
    		    }
    		});
    	}

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
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
            	done.setOnMouseClicked(new EventHandler<MouseEvent>() {
        		    @Override
        			public void handle(MouseEvent event) {
        	        	end();
        		    	if (selected.getText().equals("Compte")) {
        		    		endAccount();
        		    	}
        		    	else if (selected.getText().equals("Minecraft")) {
        		    		endMinecraft();
        		    	}
        		    	else if (selected.getText().equals("Graphisme")) {
        		    		endShaders();
        		    	}
        		    	else {
        		    		endJava();
        		    	}
        		    	CAnimation c = new CAnimation(blurRectangle.opacityProperty(), 0.0, 400);
        		    	c.run();
        		    	c = new CAnimation(settingsTitle.opacityProperty(), 0.0, 400);
        		    	c.run();
        		    	c = new CAnimation(compte.opacityProperty(), 0.0, 400);
        		    	c.run();
        		    	c = new CAnimation(minecraft.opacityProperty(), 0.0, 400);
        		    	c.run();
        		    	c = new CAnimation(shaders.opacityProperty(), 0.0, 400);
        		    	c.run();
        		    	c = new CAnimation(java.opacityProperty(), 0.0, 400);
        		    	c.run();
        		    	c = new CAnimation(line.opacityProperty(), 0.0, 400);
        		    	c.run();
        		    	c = new CAnimation(done.opacityProperty(), 0.0, 400);
        		    	c.run();
        			}
        		});
            }
        });
        new Thread(sleeper).start();
	}

	private LauncherLabel accountTitle;
	private LauncherLabel accountSubtitle;
	private LauncherImage image;
	private LauncherLabel boxAccount;
	private LauncherLabel usernameLabel;
	private LauncherLabel username;
	private LauncherLabel gradeLabel;
	private LauncherLabel grade;
	private LauncherLabel jvoteLabel;
	private LauncherLabel jvote;
	private LauncherLabel ogrinesLabel;
	private LauncherLabel ogrines;
	private LauncherLabel logout;
	private LauncherLabel accountHitbox;
	private boolean isGood = false;
	private void accountTab() {
		this.accountTitle = new LauncherLabel();
		this.accountTitle.setText("Paramètres du compte");
		this.accountTitle.setAlignment(Pos.CENTER_LEFT);
		this.accountTitle.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + getResponsive(4)));
		this.accountTitle.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.accountTitle.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 9 - getResponsive(5), getResponsive(10) * 20, getResponsive(10) * 4);
		this.accountTitle.setOpacity(0);
    	CAnimation c = new CAnimation(accountTitle.opacityProperty(), 1, 500);
    	c.run();
		this.accountSubtitle = new LauncherLabel();
		this.accountSubtitle.setText("Gérez votre compte");
		this.accountSubtitle.setAlignment(Pos.CENTER_LEFT);
		this.accountSubtitle.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.accountSubtitle.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.accountSubtitle.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 12, getResponsive(10) * 20, getResponsive(10) * 4);
		this.accountSubtitle.setOpacity(0);
    	c = new CAnimation(accountSubtitle.opacityProperty(), 1, 500);
    	c.run();
		this.boxAccount = new LauncherLabel();
		this.boxAccount.setText("");
		this.boxAccount.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		this.boxAccount.setStyle("-fx-background-color: black; -fx-text-fill: white;");
		this.boxAccount.setBounds(getResponsive(10) * 33 - getResponsive(5), getResponsive(10) * 18 + getResponsive(3), getResponsive(10) * 50, getResponsive(10) * 14);
		this.boxAccount.setOpacity(0);
    	c = new CAnimation(boxAccount.opacityProperty(), 0.4, 500);
    	c.run();
		this.image = new LauncherImage(Infos.webImg);
		this.image.setBounds(getResponsive(10) * 35 - getResponsive(5), getResponsive(10) * 20 - getResponsive(5), getResponsive(10) * 6, getResponsive(10) * 12);
		this.image.setOpacity(0);
		this.image.setVisible(true);
    	c = new CAnimation(image.opacityProperty(), 1, 500);
    	c.run();
		this.usernameLabel = new LauncherLabel();
		this.usernameLabel.setText("Pseudo");
		this.usernameLabel.setAlignment(Pos.CENTER_LEFT);
		this.usernameLabel.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size));
		this.usernameLabel.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.usernameLabel.setBounds(getResponsive(10) * 44 - getResponsive(5), getResponsive(10) * 20 - getResponsive(5), getResponsive(10) * 15, getResponsive(10) * 4);
		this.usernameLabel.setOpacity(0);
    	c = new CAnimation(usernameLabel.opacityProperty(), 0.5, 500);
    	c.run();
		this.username = new LauncherLabel();
		this.username.setText(this.USERNAME);
		this.username.setAlignment(Pos.CENTER_LEFT);
		this.username.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.username.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.username.setBounds(getResponsive(10) * 44 - getResponsive(5), getResponsive(10) * 22 - getResponsive(5), getResponsive(10) * 15, getResponsive(10) * 4);
		this.username.setOpacity(0);
    	c = new CAnimation(username.opacityProperty(), 1, 500);
    	c.run();
		this.gradeLabel = new LauncherLabel();
		this.gradeLabel.setText("Grade");
		this.gradeLabel.setAlignment(Pos.CENTER_LEFT);
		this.gradeLabel.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size));
		this.gradeLabel.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.gradeLabel.setBounds(getResponsive(10) * 44 - getResponsive(5), getResponsive(10) * 25 - getResponsive(5), getResponsive(10) * 4, getResponsive(10) * 4);
		this.gradeLabel.setOpacity(0);
    	c = new CAnimation(gradeLabel.opacityProperty(), 0.5, 500);
    	c.run();
		this.grade = new LauncherLabel();
		this.grade.setText(Infos.grade);
		this.grade.setAlignment(Pos.CENTER_LEFT);
		this.grade.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.grade.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.grade.setBounds(getResponsive(10) * 44 - getResponsive(5), getResponsive(10) * 27 - getResponsive(5), getResponsive(10) * 15, getResponsive(10) * 4);
		this.grade.setOpacity(0);
    	c = new CAnimation(grade.opacityProperty(), 1, 500);
    	c.run();
		this.jvoteLabel = new LauncherLabel();
		this.jvoteLabel.setText("Jetons de vote");
		this.jvoteLabel.setAlignment(Pos.CENTER_LEFT);
		this.jvoteLabel.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size));
		this.jvoteLabel.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.jvoteLabel.setBounds(getResponsive(10) * 63 - getResponsive(3), getResponsive(10) * 20 - getResponsive(5), getResponsive(10) * 15, getResponsive(10) * 4);
		this.jvoteLabel.setOpacity(0);
    	c = new CAnimation(jvoteLabel.opacityProperty(), 0.5, 500);
    	c.run();
		this.jvote = new LauncherLabel();
		this.jvote.setText(Infos.jVotes);
		this.jvote.setAlignment(Pos.CENTER_LEFT);
		this.jvote.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.jvote.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.jvote.setBounds(getResponsive(10) * 63 - getResponsive(3), getResponsive(10) * 22 - getResponsive(5), getResponsive(10) * 15, getResponsive(10) * 4);
		this.jvote.setOpacity(0);
    	c = new CAnimation(jvote.opacityProperty(), 1, 500);
    	c.run();
		this.ogrinesLabel = new LauncherLabel();
		this.ogrinesLabel.setText("Ogrines");
		this.ogrinesLabel.setAlignment(Pos.CENTER_LEFT);
		this.ogrinesLabel.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size));
		this.ogrinesLabel.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.ogrinesLabel.setBounds(getResponsive(10) * 63 - getResponsive(3), getResponsive(10) * 25 - getResponsive(5), getResponsive(10) * 10, getResponsive(10) * 4);
		this.ogrinesLabel.setOpacity(0);
    	c = new CAnimation(ogrinesLabel.opacityProperty(), 0.5, 500);
    	c.run();
		this.ogrines = new LauncherLabel();
		this.ogrines.setText(Infos.ogrines);
		this.ogrines.setAlignment(Pos.CENTER_LEFT);
		this.ogrines.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.ogrines.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.ogrines.setBounds(getResponsive(10) * 63 - getResponsive(3), getResponsive(10) * 27 - getResponsive(5), getResponsive(10) * 10, getResponsive(10) * 4);
		this.ogrines.setOpacity(0);
    	c = new CAnimation(ogrines.opacityProperty(), 1, 500);
    	c.run();
		this.accountHitbox = new LauncherLabel();
		this.accountHitbox.setText("");
		this.accountHitbox.setStyle("-fx-background-color: black; -fx-text-fill: white;");
		this.accountHitbox.setBounds(getResponsive(10) * 33 - getResponsive(5), getResponsive(10) * 19 - getResponsive(7), getResponsive(10) * 50, getResponsive(10) * 14);
		this.accountHitbox.setOpacity(0);
		this.logout = new LauncherLabel();
		this.logout.setText("Déconnexion");
		this.logout.setAlignment(Pos.CENTER);
		this.logout.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		if (getResponsive(10) == 10) {
			this.logout.setBounds(theGameEngine.getWidth() / 2 + theGameEngine.getWidth() / 6, theGameEngine.getHeight() / 2 - theGameEngine.getHeight() / 40,  theGameEngine.getWidth() / 10, theGameEngine.getHeight() / 20);
		}
		else {
			this.logout.setBounds(theGameEngine.getWidth() - theGameEngine.getWidth() / 4 - getResponsive(10) * 5 - getResponsive(5), theGameEngine.getHeight() / 2,  theGameEngine.getWidth() / 10, theGameEngine.getHeight() / 20);
		}
		this.logout.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 3; -fx-background-color: transparent; -fx-text-fill: red;");
		this.logout.setOpacity(0);
		this.accountHitbox.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (ending) {
		    		return;
		    	}
				Task<Void> sleeper = new Task<Void>() {
		            @Override
		            protected Void call(){
		            	return null;
		            }
		        };
		        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		            @Override
		            public void handle(WorkerStateEvent event) {
		            	if (!isGood) {
		            		CAnimation c = new CAnimation(logout.opacityProperty(), 0.7, 200);
		    		    	c.run();
		            	}
		            }
		        });
		        new Thread(sleeper).start();
		    }
		});
		this.accountHitbox.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (ending) {
		    		return;
		    	}
				Task<Void> sleeper = new Task<Void>() {
		            @Override
		            protected Void call() {
		            	return null;
		            }
		        };
		        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		            @Override
		            public void handle(WorkerStateEvent event) {
		            	if (!isGood) {
		            		CAnimation c = new CAnimation(logout.opacityProperty(), 0, 200);
		    		    	c.run();
		            	}
		            }
		        });
		        new Thread(sleeper).start();
		    }
		});
		this.logout.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (ending) {
		    		return;
		    	}
		    	isGood = true;
	    		Glow g = new Glow(0);
	    		logout.setEffect(g);
		    	CAnimation c = new CAnimation(g.levelProperty(), 1, 200);
		    	c.run();
		    	c = new CAnimation(logout.opacityProperty(), 0.9, 200);
		    	c.run();
		    }
		});
		this.logout.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (ending) {
		    		return;
		    	}
		    	try {
					Stage stage = (Stage) logout.getScene().getWindow();
					stage.hide();
					Infos.connected = false;
					Infos.passwordt = "";
					Infos.password = false;
					LauncherMain.mediaPlayer.stop();
					LauncherMain.mediaPlayer = null;
					try {
						LauncherPanel.maxiButton.fire();
						LauncherPanel.maxiButton.fire();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		});
		this.logout.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (ending) {
		    		return;
		    	}
		    	isGood = false;
	    		Glow g = new Glow(1);
	    		logout.setEffect(g);
		    	CAnimation c = new CAnimation(g.levelProperty(), 0, 200);
		    	c.run();
		    	c = new CAnimation(logout.opacityProperty(), 0.7, 200);
		    	c.run();
		    }
		});
	}
	
	private void endAccount() {
    	CAnimation c = new CAnimation(accountTitle.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(accountSubtitle.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(image.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(boxAccount.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(usernameLabel.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(username.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(gradeLabel.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(grade.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(jvoteLabel.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(jvote.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(ogrinesLabel.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(ogrines.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(logout.opacityProperty(), 0.0, 400);
    	c.run();
    	this.accountTitle.setDisable(true);
    	this.accountSubtitle.setDisable(true);
    	this.image.setDisable(true);
    	this.boxAccount.setDisable(true);
    	this.usernameLabel.setDisable(true);
    	this.username.setDisable(true);
    	this.gradeLabel.setDisable(true);
    	this.grade.setDisable(true);
    	this.jvoteLabel.setDisable(true);
    	this.jvote.setDisable(true);
    	this.ogrinesLabel.setDisable(true);
    	this.ogrines.setDisable(true);
    	this.logout.setDisable(true);
    	this.accountHitbox.setDisable(true);
	}

	private LauncherLabel minecraftTitle;
	private LauncherLabel minecraftSubtitle;
	private LauncherImage lineMinecraft1;
	private LauncherImage lineMinecraft2;
	private LauncherImage lineMinecraft3;
	private LauncherLabel resolutionLabel;
	private ComboBox<String> resolutionField;
	private LauncherLabel fullScreenLabel;
	private ToggleSwitch fullScreenToggle;
	private LauncherLabel borderlessLabel;
	private LauncherLabel serverLabel;
	private ComboBox<String> serverChoice;
	private ToggleSwitch borderlessToggle;
	private void minecraftTab() {
		boolean isSmall = getResponsive(10) == 10;
		this.minecraftTitle = new LauncherLabel();
		this.minecraftTitle.setText("Paramètres Minecraft");
		this.minecraftTitle.setAlignment(Pos.CENTER_LEFT);
		this.minecraftTitle.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 4));
		this.minecraftTitle.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.minecraftTitle.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 8 + getResponsive(5), getResponsive(10) * 20, getResponsive(10) * 4);
		this.minecraftTitle.setOpacity(0);
    	CAnimation c = new CAnimation(minecraftTitle.opacityProperty(), 1, 500);
    	c.run();
		this.minecraftSubtitle = new LauncherLabel();
		this.minecraftSubtitle.setText("Options liées au lancement du jeu");
		this.minecraftSubtitle.setAlignment(Pos.CENTER_LEFT);
		this.minecraftSubtitle.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.minecraftSubtitle.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.minecraftSubtitle.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 12, getResponsive(10) * 30, getResponsive(10) * 4);
		this.minecraftSubtitle.setOpacity(0);
    	c = new CAnimation(minecraftSubtitle.opacityProperty(), 1, 500);
    	c.run();
		this.resolutionLabel = new LauncherLabel();
		this.resolutionLabel.setText("Résolution du mode fenêtré");
		this.resolutionLabel.setAlignment(Pos.CENTER_LEFT);
		this.resolutionLabel.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 1));
		this.resolutionLabel.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.resolutionLabel.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 17, getResponsive(10) * 20, getResponsive(10) * 4);
		this.resolutionLabel.setOpacity(0);
    	c = new CAnimation(resolutionLabel.opacityProperty(), 1, 500);
    	c.run();
    	
    	this.resolutionField = new ComboBox<String>();
		this.resolutionField.setPrefSize(getResponsive(10) * 13, getResponsive(10) * 3);
		
		this.populateSizeList();
		if (config.getValue(EnumConfig.GAME_SIZE) != null) {
			this.resolutionField.setValue(GameSize.getWindowSize(Integer.parseInt((String) config.getValue(EnumConfig.GAME_SIZE))).getDesc());
		}
		this.resolutionField.setLayoutX(getResponsive(10) * 32 + getResponsive(5));
		this.resolutionField.setLayoutY(getResponsive(10) * 21 + getResponsive(5));
		this.resolutionField.setVisibleRowCount(5);
		this.resolutionField.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));
		this.resolutionField.setStyle("-fx-background-color: black; -fx-text-fill: white;");
		this.resolutionField.setOpacity(0);
		this.resolutionField.setVisible(true);
		this.resolutionField.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
		    public ListCell<String> call(ListView<String> param) {
		        return new ListCell<String>() {
		            protected void updateItem(String item, boolean empty) {
		                super.updateItem(item, empty);
		                setText(item);
						setStyle(isSmall ? "-fx-font-size:13" : "-fx-font-size:25");
		                setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		                setTextFill(Color.WHITE);
		            }
		        };
		    }
		});
        this.resolutionField.setButtonCell(new ListCell<String>() {
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				setText(item);
				setStyle(isSmall ? "-fx-font-size:13" : "-fx-font-size:25");
				setTextFill(Color.WHITE);
				if (!empty) {
					config.updateValue("gamesize", GameSize.getWindowSize(item));
				}
			}
		});
		root.getChildren().add(this.resolutionField);
    	c = new CAnimation(resolutionField.opacityProperty(), 0.8, 500);
    	c.run();
    	
		this.lineMinecraft1 = new LauncherImage(this.loc.loadImage(theGameEngine, "grayline.png"));
		this.lineMinecraft1.setBounds(getResponsive(10) * 30 + getResponsive(2), getResponsive(10) * 27, getResponsive(10) * 50, 1);
		this.lineMinecraft1.setOpacity(0);
    	c = new CAnimation(lineMinecraft1.opacityProperty(), 0.5, 500);
    	c.run();

		this.fullScreenLabel = new LauncherLabel();
		this.fullScreenLabel.setText("Démarrer la fenêtre Minecraft en plein écran");
		this.fullScreenLabel.setAlignment(Pos.CENTER_LEFT);
		this.fullScreenLabel.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 1));
		this.fullScreenLabel.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.fullScreenLabel.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 28, getResponsive(10) * 38, getResponsive(10) * 4);
		this.fullScreenLabel.setOpacity(0);
		c = new CAnimation(fullScreenLabel.opacityProperty(), 1, 500);
		c.run();
    	
		this.fullScreenToggle = new ToggleSwitch(theGameEngine);
		if (isSmall) {
			this.fullScreenToggle.setLayoutX(getResponsive(10) * 30 + getResponsive(5));
		}
		else {
			this.fullScreenToggle.setLayoutX(getResponsive(10) * 31 + getResponsive(6));
		}
		this.fullScreenToggle.setLayoutY(getResponsive(10) * 32 + getResponsive(6));
		root.getChildren().add(this.fullScreenToggle);
		
    	if ((boolean) this.config.getValue(EnumConfig.FULLSCREEN)) {
    		this.fullScreenToggle.setSelected(true);
    	}
		this.fullScreenToggle.setOpacity(0);
    	c = new CAnimation(fullScreenToggle.opacityProperty(), 1, 500);
    	c.run();
    	fullScreenToggle.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
			public void handle(MouseEvent event) {
		    	if ((boolean) config.getValue(EnumConfig.FULLSCREEN)) {
		    		config.updateValue("fullscreen", false);
		    	}
		    	else {
		    		config.updateValue("fullscreen", true);
			    }
		    }
		});

		this.lineMinecraft2 = new LauncherImage(this.loc.loadImage(theGameEngine, "grayline.png"));
		this.lineMinecraft2.setBounds(getResponsive(10) * 30 + getResponsive(2), getResponsive(10) * 37, getResponsive(10) * 50, 1);
		this.lineMinecraft2.setOpacity(0);
		c = new CAnimation(lineMinecraft2.opacityProperty(), 0.5, 500);
		c.run();
    	
		this.borderlessLabel = new LauncherLabel();
		this.borderlessLabel.setText("Mode plein écran sans bordures");
		this.borderlessLabel.setAlignment(Pos.CENTER_LEFT);
		this.borderlessLabel.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 1));
		this.borderlessLabel.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.borderlessLabel.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 38 - getResponsive(1), getResponsive(10) * 24, getResponsive(10) * 4);
		this.borderlessLabel.setOpacity(0);
    	c = new CAnimation(borderlessLabel.opacityProperty(), 1, 500);
    	c.run();

		this.borderlessToggle = new ToggleSwitch(theGameEngine);
		if (isSmall) {
			this.borderlessToggle.setLayoutX(getResponsive(10) * 30 + getResponsive(5));
		}
		else {
			this.borderlessToggle.setLayoutX(getResponsive(10) * 31 + getResponsive(6));
		}
		this.borderlessToggle.setLayoutY(getResponsive(10) * 42 + getResponsive(8));
		root.getChildren().add(this.borderlessToggle);

		if ((boolean) this.config.getValue(EnumConfig.BORDERLESS)) {
			this.borderlessToggle.setSelected(true);
		}
		this.borderlessToggle.setOpacity(0);
		c = new CAnimation(borderlessToggle.opacityProperty(), 1, 500);
		c.run();
		borderlessToggle.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if ((boolean) config.getValue(EnumConfig.BORDERLESS)) {
					config.updateValue("borderless", false);
				}
				else {
					config.updateValue("borderless", true);
				}
			}
		});

		this.lineMinecraft3 = new LauncherImage(this.loc.loadImage(theGameEngine, "grayline.png"));
		this.lineMinecraft3.setBounds(getResponsive(10) * 30 + getResponsive(2), getResponsive(10) * 47, getResponsive(10) * 50, 1);
		this.lineMinecraft3.setOpacity(0);
		c = new CAnimation(lineMinecraft3.opacityProperty(), 0.5, 500);
		c.run();

		this.serverLabel = new LauncherLabel();
		this.serverLabel.setText("Serveur");
		this.serverLabel.setAlignment(Pos.CENTER_LEFT);
		this.serverLabel.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 1));
		this.serverLabel.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.serverLabel.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 48 - getResponsive(1), getResponsive(10) * 24, getResponsive(10) * 4);
		this.serverLabel.setOpacity(0);
		c = new CAnimation(serverLabel.opacityProperty(), 1, 500);
		c.run();

		this.serverChoice = new ComboBox<>();
		this.serverChoice.setPrefSize(getResponsive(10) * 13, getResponsive(10) * 3);

		String[] servers = {"Live", "Bêta Pepsi", "Bêta Nheo", "Serveur évènement"};
		this.serverChoice.getItems().addAll(servers);
//		this.populateSizeList();
		if (config.getValue(EnumConfig.SERVER) != null) {
			this.serverChoice.setValue((String) config.getValue(EnumConfig.SERVER));
		} else {
			this.serverChoice.setValue(servers[0]);
			config.updateValue("server", servers[0]);
		}
		this.serverChoice.setLayoutX(getResponsive(10) * 32 + getResponsive(5));
		this.serverChoice.setLayoutY(getResponsive(10) * 51 + getResponsive(8));
		this.serverChoice.setVisibleRowCount(5);
		this.serverChoice.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));
		this.serverChoice.setStyle("-fx-background-color: black; -fx-text-fill: white;");
		this.serverChoice.setOpacity(0);
		this.serverChoice.setVisible(true);
		this.serverChoice.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			public ListCell<String> call(ListView<String> param) {
				return new ListCell<String>() {
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(item);
						setStyle(isSmall ? "-fx-font-size:13" : "-fx-font-size:25");
						setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
						setTextFill(Color.WHITE);
						config.updateValue("server", item);
					}
				};
			}
		});
		this.serverChoice.setButtonCell(new ListCell<String>() {
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				setText(item);
				setStyle(isSmall ? "-fx-font-size:13" : "-fx-font-size:25");
				setTextFill(Color.WHITE);
				if (!empty) {
					config.updateValue("gamesize", GameSize.getWindowSize(item));
				}
				titleLabel.setText("Krosmocraft " +  "[" + item + "]");
			}
		});
		root.getChildren().add(this.serverChoice);
		c = new CAnimation(serverChoice.opacityProperty(), 0.8, 500);
		c.run();

	}



	
	private void endMinecraft() {
    	CAnimation c = new CAnimation(minecraftTitle.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(minecraftSubtitle.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(lineMinecraft1.opacityProperty(), 0.0, 400);
    	c.run();
		c = new CAnimation(lineMinecraft2.opacityProperty(), 0.0, 400);
		c.run();
		c = new CAnimation(lineMinecraft3.opacityProperty(), 0.0, 400);
		c.run();
    	c = new CAnimation(resolutionLabel.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(resolutionField.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(fullScreenLabel.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(fullScreenToggle.opacityProperty(), 0.0, 400);
    	c.run();
		c = new CAnimation(fullScreenToggle.opacityProperty(), 0.0, 400);
		c.run();
    	c = new CAnimation(borderlessLabel.opacityProperty(), 0.0, 400);
    	c.run();
		c = new CAnimation(serverLabel.opacityProperty(), 0.0, 400);
    	c.run();
		c = new CAnimation(serverChoice.opacityProperty(), 0.0, 400);
		c.run();
    	c = new CAnimation(borderlessToggle.opacityProperty(), 0.0, 400);
    	c.run();
    	this.minecraftTitle.setDisable(true);
    	this.minecraftSubtitle.setDisable(true);
    	this.lineMinecraft1.setDisable(true);
		this.lineMinecraft2.setDisable(true);
		this.lineMinecraft3.setDisable(true);
    	this.resolutionLabel.setDisable(true);
    	this.resolutionField.setDisable(true);
    	this.fullScreenLabel.setDisable(true);
    	this.fullScreenToggle.setDisable(true);
    	this.borderlessLabel.setDisable(true);
    	this.serverLabel.setDisable(true);
		this.serverChoice.setDisable(true);
    	this.borderlessToggle.setDisable(true);
	}

	private LauncherLabel shadersTitle;
	private LauncherLabel shadersSubtitle;
	private LauncherImage low;
	private LauncherImage med;
	private LauncherImage high;
	private LauncherLabel shaderLabel;
	private LauncherLabel shaderDesc;
	private void shadersTab() {
		this.shadersTitle = new LauncherLabel();
		this.shadersTitle.setText("Paramètres graphiques");
		this.shadersTitle.setAlignment(Pos.CENTER_LEFT);
		this.shadersTitle.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 4));
		this.shadersTitle.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.shadersTitle.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 8 + getResponsive(5), getResponsive(10) * 20, getResponsive(10) * 4);
		this.shadersTitle.setOpacity(0);
    	CAnimation c = new CAnimation(shadersTitle.opacityProperty(), 1, 500);
    	c.run();
		this.shadersSubtitle = new LauncherLabel();
		this.shadersSubtitle.setText("Choisissez une option graphique adaptée");
		this.shadersSubtitle.setAlignment(Pos.CENTER_LEFT);
		this.shadersSubtitle.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.shadersSubtitle.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.shadersSubtitle.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 12, getResponsive(10) * 35, getResponsive(10) * 4);
		this.shadersSubtitle.setOpacity(0);
    	c = new CAnimation(shadersSubtitle.opacityProperty(), 1, 500);
    	c.run();
    	
		this.low = new LauncherImage(this.loc.loadImage(theGameEngine, "low.png"));
		this.low.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 18, getResponsive(10) * 15, getResponsive(10) * 10 + getResponsive(2));
		this.low.setOpacity(0);
    	
		this.med = new LauncherImage(this.loc.loadImage(theGameEngine, "med.png"));
		this.med.setBounds(getResponsive(10) * 50, getResponsive(10) * 18, getResponsive(10) * 15, getResponsive(10) * 10 + getResponsive(2));
		this.med.setOpacity(0);
    	
		this.high = new LauncherImage(this.loc.loadImage(theGameEngine, "high.png"));
		this.high.setBounds(getResponsive(10) * 67 + getResponsive(5), getResponsive(10) * 18, getResponsive(10) * 15, getResponsive(10) * 10 + getResponsive(2));
		this.high.setOpacity(0);
		
		this.shaderLabel = new LauncherLabel();
		this.shaderLabel.setText("Faible");
		this.shaderLabel.setAlignment(Pos.CENTER);
		this.shaderLabel.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 4));
		this.shaderLabel.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.shaderLabel.setBounds(getResponsive(10) * 49 + getResponsive(6), getResponsive(10) * 30, getResponsive(10) * 15, getResponsive(10) * 4);
		this.shaderLabel.setOpacity(0);
    	c = new CAnimation(shaderLabel.opacityProperty(), 1, 500);
    	c.run();
		
		this.shaderDesc = new LauncherLabel();
		this.shaderDesc.setText("");
		this.shaderDesc.setTextAlignment(TextAlignment.CENTER);
		this.shaderDesc.setAlignment(Pos.CENTER);
		this.shaderDesc.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.shaderDesc.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.shaderDesc.setBounds(getResponsive(10) * 32 + getResponsive(2), getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 50, getResponsive(10) * 16);
		this.shaderDesc.setOpacity(0);
    	c = new CAnimation(shaderDesc.opacityProperty(), 1, 500);
    	c.run();
    	
    	if(this.config.getValue(EnumConfig.QUALITY).equals("1")) {
        	c = new CAnimation(low.opacityProperty(), 1, 500);
        	c.run();
        	c = new CAnimation(med.opacityProperty(), 0.2, 500);
        	c.run();
        	c = new CAnimation(high.opacityProperty(), 0.2, 500);
        	c.run();
    		this.shaderDesc.setText(vanilla);
    	}
    	else if (this.config.getValue(EnumConfig.QUALITY).equals("2")){
        	c = new CAnimation(low.opacityProperty(), 0.2, 500);
        	c.run();
        	c = new CAnimation(med.opacityProperty(), 1, 500);
        	c.run();
        	c = new CAnimation(high.opacityProperty(), 0.2, 500);
        	c.run();
        	shaderLabel.setText("Normal");
    		this.shaderDesc.setText(mediumEnd);
    	}
    	else if (this.config.getValue(EnumConfig.QUALITY).equals("3")){
        	c = new CAnimation(low.opacityProperty(), 0.2, 500);
        	c.run();
        	c = new CAnimation(med.opacityProperty(), 0.2, 500);
        	c.run();
        	c = new CAnimation(high.opacityProperty(), 1, 500);
        	c.run();
        	shaderLabel.setText("HD");
    		this.shaderDesc.setText(highEnd);
    	}
    	

    	
    	this.low.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	CAnimation c;
		    	if (config.getValue(EnumConfig.QUALITY).equals("2")){
		        	c = new CAnimation(low.opacityProperty(), 1, 100);
		        	c.run();
		        	c = new CAnimation(high.opacityProperty(), 0.2, 100);
		        	c.run();
		    	}
		    	else if (config.getValue(EnumConfig.QUALITY).equals("3")) {
		        	c = new CAnimation(low.opacityProperty(), 1, 100);
		        	c.run();
		        	c = new CAnimation(med.opacityProperty(), 0.2, 100);
		        	c.run();
		    	}
	        	shaderLabel.setText("Faible");
	    		shaderDesc.setText(vanilla);
		    }
		});
    	this.low.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	CAnimation c;
		    	if (config.getValue(EnumConfig.QUALITY).equals("2")){
		        	c = new CAnimation(low.opacityProperty(), 0.2, 100);
		        	c.run();
		        	c = new CAnimation(high.opacityProperty(), 0.2, 100);
		        	c.run();
		        	shaderLabel.setText("Normal");
		    	}
		    	else if (config.getValue(EnumConfig.QUALITY).equals("3")){
		        	c = new CAnimation(low.opacityProperty(), 0.2, 100);
		        	c.run();
		        	c = new CAnimation(med.opacityProperty(), 0.2, 100);
		        	c.run();
		        	shaderLabel.setText("HD");
		    	}
		    }
		});
    	this.med.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	CAnimation c;
		    	if (config.getValue(EnumConfig.QUALITY).equals("1")){
		        	c = new CAnimation(med.opacityProperty(), 1, 100);
		        	c.run();
		        	c = new CAnimation(high.opacityProperty(), 0.2, 100);
		        	c.run();
		    	}
		    	else if (config.getValue(EnumConfig.QUALITY).equals("3")) {
		        	c = new CAnimation(low.opacityProperty(), 0.2, 100);
		        	c.run();
		        	c = new CAnimation(med.opacityProperty(), 1, 100);
		        	c.run();
		    	}
	        	shaderLabel.setText("Normal");
	    		shaderDesc.setText(mediumEnd);
		    }
		});
    	this.med.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	CAnimation c;
		    	if (config.getValue(EnumConfig.QUALITY).equals("1")){
		        	c = new CAnimation(med.opacityProperty(), 0.2, 100);
		        	c.run();
		        	c = new CAnimation(high.opacityProperty(), 0.2, 100);
		        	c.run();
		        	shaderLabel.setText("Vanilla");
		    	}
		    	else if (config.getValue(EnumConfig.QUALITY).equals("3")){
		        	c = new CAnimation(low.opacityProperty(), 0.2, 100);
		        	c.run();
		        	c = new CAnimation(med.opacityProperty(), 0.2, 100);
		        	c.run();
		        	shaderLabel.setText("HD");
		    	}
		    }
		});
    	this.high.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	CAnimation c;
		    	if (config.getValue(EnumConfig.QUALITY).equals("1")){
		        	c = new CAnimation(med.opacityProperty(), 0.2, 100);
		        	c.run();
		        	c = new CAnimation(high.opacityProperty(), 1, 100);
		        	c.run();
		    	}
		    	else if (config.getValue(EnumConfig.QUALITY).equals("2")) {
		        	c = new CAnimation(low.opacityProperty(), 0.2, 100);
		        	c.run();
		        	c = new CAnimation(high.opacityProperty(), 1, 100);
		        	c.run();
		    	}
	        	shaderLabel.setText("HD");
	    		shaderDesc.setText(highEnd);
		    }
		});
    	this.high.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	CAnimation c;
		    	if (config.getValue(EnumConfig.QUALITY).equals("1")){
		        	c = new CAnimation(med.opacityProperty(), 0.2, 100);
		        	c.run();
		        	c = new CAnimation(high.opacityProperty(), 0.2, 100);
		        	c.run();
		        	shaderLabel.setText("Faible");
		    	}
		    	else if (config.getValue(EnumConfig.QUALITY).equals("2")){
		        	c = new CAnimation(low.opacityProperty(), 0.2, 100);
		        	c.run();
		        	c = new CAnimation(high.opacityProperty(), 0.2, 100);
		        	c.run();
		        	shaderLabel.setText("Normal");
		    	}
		    }
		});
    	this.low.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	CAnimation c;
		    	if (!config.getValue(EnumConfig.QUALITY).equals("1")){
		    		if (config.getValue(EnumConfig.QUALITY).equals("2")) {
			        	c = new CAnimation(med.opacityProperty(), 0.2, 100);
			        	c.run();
		    		}
		    		else if (config.getValue(EnumConfig.QUALITY).equals("3")) {
			        	c = new CAnimation(high.opacityProperty(), 0.2, 100);
			        	c.run();
		    		}
		    		config.updateValue("quality", "1");
		    		double ram = Double.parseDouble((String) config.getValue(EnumConfig.RAM));
		    		if (ram < 4) {
	    	    		config.updateValue(EnumConfig.RAM.getOption(), "4.0");
		    		}
		    	}
		    }
		});
    	this.med.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	CAnimation c;
		    	if (!config.getValue(EnumConfig.QUALITY).equals("2")){
		    		if (config.getValue(EnumConfig.QUALITY).equals("1")) {
			        	c = new CAnimation(low.opacityProperty(), 0.2, 100);
			        	c.run();
		    		}
		    		else if (config.getValue(EnumConfig.QUALITY).equals("3")) {
			        	c = new CAnimation(high.opacityProperty(), 0.2, 100);
			        	c.run();
		    		}
		    		config.updateValue("quality", "2");
		    		double ram = Double.parseDouble((String) config.getValue(EnumConfig.RAM));
		    		if (ram < 6) {
	    	    		config.updateValue(EnumConfig.RAM.getOption(), "6.0");
		    		}
		    	}
		    }
		});
    	this.high.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	CAnimation c;
		    	if (!config.getValue(EnumConfig.QUALITY).equals("3")){
		    		if (config.getValue(EnumConfig.QUALITY).equals("2")) {
			        	c = new CAnimation(med.opacityProperty(), 0.2, 100);
			        	c.run();
		    		}
		    		else if (config.getValue(EnumConfig.QUALITY).equals("1")) {
			        	c = new CAnimation(low.opacityProperty(), 0.2, 100);
			        	c.run();
		    		}
		    		config.updateValue("quality", "3");
		    		double ram = Double.parseDouble((String) config.getValue(EnumConfig.RAM));
		    		if (ram < 8) {
	    	    		config.updateValue(EnumConfig.RAM.getOption(), "8.0");
		    		}
		    	}
		    }
		});
	}

	private void endShaders() {
    	CAnimation c = new CAnimation(shadersTitle.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(shadersSubtitle.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(low.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(med.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(high.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(shaderLabel.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(shaderDesc.opacityProperty(), 0.0, 400);
    	c.run();
    	this.shadersTitle.setDisable(true);
    	this.shadersSubtitle.setDisable(true);
    	this.low.setDisable(true);
    	this.med.setDisable(true);
    	this.high.setDisable(true);
    	this.shaderLabel.setDisable(true);
    	this.shaderDesc.setDisable(true);
	}

	private LauncherLabel javaTitle;
	private LauncherLabel javaSubtitle;
	private LauncherLabel ramTitle;
	private Slider ramSlider;
	private LauncherLabel totalTitle;
	private LauncherLabel totalRam;
	private LauncherLabel avTitle;
	private LauncherLabel avRam;
	private LauncherLabel recommended;
	private LauncherLabel recommended2;
	private LauncherImage javaLine;
	private LauncherLabel jvmTitle;
	private LauncherTextField jvmBox;
	private void javaTab() {
		this.javaTitle = new LauncherLabel();
		this.javaTitle.setText("Paramètres Java");
		this.javaTitle.setAlignment(Pos.CENTER_LEFT);
		this.javaTitle.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 4));
		this.javaTitle.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.javaTitle.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 8 + getResponsive(5), getResponsive(10) * 20, getResponsive(10) * 4);
		this.javaTitle.setOpacity(0);
    	CAnimation c = new CAnimation(javaTitle.opacityProperty(), 1, 500);
    	c.run();
		this.javaSubtitle = new LauncherLabel();
		this.javaSubtitle.setText("Gérez votre configuration Java (avancé)");
		this.javaSubtitle.setAlignment(Pos.CENTER_LEFT);
		this.javaSubtitle.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.javaSubtitle.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.javaSubtitle.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 12, getResponsive(10) * 35, getResponsive(10) * 4);
		this.javaSubtitle.setOpacity(0);
    	c = new CAnimation(javaSubtitle.opacityProperty(), 1, 500);
    	c.run();
		this.ramTitle = new LauncherLabel();
		this.ramTitle.setAlignment(Pos.CENTER_LEFT);
		this.ramTitle.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 3));
		this.ramTitle.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.ramTitle.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 16 + getResponsive(5), getResponsive(10) * 35, getResponsive(10) * 4);
		this.ramTitle.setOpacity(0);
    	c = new CAnimation(ramTitle.opacityProperty(), 1, 500);
    	c.run();
		this.ramSlider = new Slider();
		this.ramSlider.setStyle("-fx-control-inner-background: rgba(46, 47, 48, 1);");
		this.ramSlider.setMin(1);
		this.ramSlider.setMax(8);
		if (config.getValue(EnumConfig.RAM) != null) {
			double d = Double.parseDouble((String) config.getValue(EnumConfig.RAM));
			this.ramSlider.setValue(d);
		}
		this.ramSlider.setLayoutX(getResponsive(10) * 32);
		this.ramSlider.setLayoutY(getResponsive(10) * 22);
		this.ramSlider.setPrefWidth(getResponsive(10) * 20);
		this.ramSlider.setBlockIncrement(1);
		this.ramSlider.setOpacity(0.0D);
		this.ramSlider.setVisible(true);
		this.ramSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				ramSlider.setValue(Math.round(new_val.doubleValue()));
			}
		});
		this.ramSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				ramTitle.setText("Ram allouée: " + newValue + " Go");
				config.updateValue("allocatedram", newValue);
			}
		});
		this.ramTitle.setText("Ram allouée: " + this.ramSlider.getValue() + " Go");
		root.getChildren().add(this.ramSlider);
    	c = new CAnimation(ramSlider.opacityProperty(), 1, 500);
    	c.run();
		this.totalTitle = new LauncherLabel();
		this.totalTitle.setText("RAM total");
		this.totalTitle.setAlignment(Pos.CENTER);
		this.totalTitle.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 2));
		this.totalTitle.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.totalTitle.setBounds(getResponsive(10) * 55 - getResponsive(2), getResponsive(10) * 23, getResponsive(10) * 20, getResponsive(10) * 4);
		this.totalTitle.setOpacity(0);
    	c = new CAnimation(totalTitle.opacityProperty(), 0.6, 500);
    	c.run();
		this.avTitle = new LauncherLabel();
		this.avTitle.setText("RAM disponible");
		this.avTitle.setAlignment(Pos.CENTER);
		this.avTitle.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 2));
		this.avTitle.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.avTitle.setBounds(getResponsive(10) * 55, getResponsive(10) * 18, getResponsive(10) * 20, getResponsive(10) * 4);
		this.avTitle.setOpacity(0);
    	c = new CAnimation(avTitle.opacityProperty(), 0.6, 500);
    	c.run();
    	String r = this.convertBytesToString(((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize());
		this.totalRam = new LauncherLabel();
		this.totalRam.setText(r);
		this.totalRam.setAlignment(Pos.CENTER);
		this.totalRam.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 4));
		this.totalRam.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.totalRam.setBounds(getResponsive(10) * 55, getResponsive(10) * 25, getResponsive(10) * 20, getResponsive(10) * 4);
		this.totalRam.setOpacity(0);
    	c = new CAnimation(totalRam.opacityProperty(), 1, 500);
    	c.run();
    	r = this.convertBytesToString(((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getFreePhysicalMemorySize());
		this.avRam = new LauncherLabel();
		this.avRam.setText(r);
		this.avRam.setAlignment(Pos.CENTER);
		this.avRam.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 4));
		this.avRam.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.avRam.setBounds(getResponsive(10) * 55, getResponsive(10) * 20, getResponsive(10) * 20, getResponsive(10) * 4);
		this.avRam.setOpacity(0);
    	c = new CAnimation(avRam.opacityProperty(), 1, 500);
    	c.run();
		this.recommended = new LauncherLabel();
		this.recommended.setText("Il est recommendé d'allouer au minimum");
		this.recommended.setAlignment(Pos.CENTER_LEFT);
		this.recommended.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size - 1));
		this.recommended.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.recommended.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 24 + getResponsive(5), getResponsive(10) * 35, getResponsive(10) * 4);
		this.recommended.setOpacity(0);
    	c = new CAnimation(recommended.opacityProperty(), 1, 500);
    	c.run();
		this.recommended2 = new LauncherLabel();
		this.recommended2.setText("4 Go de RAM et à l'idéal 8 Go");
		this.recommended2.setAlignment(Pos.CENTER_LEFT);
		this.recommended2.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size - 1));
		this.recommended2.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.recommended2.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 26 + getResponsive(5), getResponsive(10) * 35, getResponsive(10) * 4);
		this.recommended2.setOpacity(0);
    	c = new CAnimation(recommended2.opacityProperty(), 1, 500);
    	c.run();
		this.javaLine = new LauncherImage(this.loc.loadImage(theGameEngine, "grayline.png"));
		this.javaLine.setBounds(getResponsive(10) * 30 + getResponsive(5), getResponsive(10) * 31 + getResponsive(8), getResponsive(10) * 50, 1);
		this.javaLine.setOpacity(0);
    	c = new CAnimation(javaLine.opacityProperty(), 0.5, 500);
    	c.run();
		this.jvmTitle = new LauncherLabel();
		this.jvmTitle.setText("Arguments JVM");
		this.jvmTitle.setAlignment(Pos.CENTER_LEFT);
		this.jvmTitle.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 2));
		this.jvmTitle.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.jvmTitle.setBounds(getResponsive(10) * 32 + getResponsive(5), getResponsive(10) * 33, getResponsive(10) * 35, getResponsive(10) * 4);
		this.jvmTitle.setOpacity(0);
    	c = new CAnimation(jvmTitle.opacityProperty(), 1, 500);
    	c.run();
    	this.jvmBox = new LauncherTextField();
		this.jvmBox.setBounds(getResponsive(10) * 32 + getResponsive(4), getResponsive(10) * 38, getResponsive(10) * 25, getResponsive(10) * 3);
		this.jvmBox.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.jvmBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.jvmBox.setText("" + config.getValue(EnumConfig.VM_ARGUMENTS));
		this.jvmBox.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT)));
		this.jvmBox.textProperty().addListener((observable, oldValue, newValue) -> {
			config.updateValue("vmarguments", this.jvmBox.getText());
		});
	}
	
	private void endJava() {
    	CAnimation c = new CAnimation(javaTitle.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(javaSubtitle.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(ramTitle.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(ramSlider.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(totalTitle.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(totalRam.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(avTitle.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(avRam.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(recommended.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(recommended2.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(javaLine.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(jvmTitle.opacityProperty(), 0.0, 400);
    	c.run();
    	c = new CAnimation(jvmBox.opacityProperty(), 0.0, 400);
    	c.run();
    	this.javaTitle.setDisable(true);
    	this.javaSubtitle.setDisable(true);
    	this.ramTitle.setDisable(true);
    	this.ramSlider.setDisable(true);
    	this.totalTitle.setDisable(true);
    	this.totalRam.setDisable(true);
    	this.avTitle.setDisable(true);
    	this.avRam.setDisable(true);
    	this.recommended.setDisable(true);
    	this.recommended2.setDisable(true);
    	this.javaLine.setDisable(true);
    	this.jvmTitle.setDisable(true);
    	this.jvmBox.setDisable(true);
	}
	
	public void end() {
		ending = true;
		Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
    		    	done.setDisable(true);
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
		    	blurRectangle.setDisable(true);
		    	settingsTitle.setDisable(true);
		    	compte.setDisable(true);
		    	minecraft.setDisable(true);
		    	java.setDisable(true);
		    	line.setDisable(true);
		    	done.setDisable(true);
            }
        });
        new Thread(sleeper).start();
	}

	private void populateSizeList() {
		for (GameSize size : GameSize.values()) {
			this.resolutionField.getItems().add(size.getDesc());
		}
	}
	
	public int getResponsive(int x) {
		switch(x) {
		case 2:
			return (int) (root.getHeight() / 300);
		case 3:
			return (int) (root.getHeight() / 200);
		case 4:
			return (int) (root.getHeight() / 135);
		case 5:
			return (int) (root.getWidth() / 200);
		case 6:
			return (int) (root.getWidth() / 170);
		case 7:
			return (int) (root.getWidth() / 135);
		case 8:
			return (int) (root.getWidth() / 130);
		case 9:
			return (int) (root.getWidth() / 110);
		case 10:
			return (int) (root.getWidth() / 100);
		default:
			return 0;
		}
	}
	
	public String convertBytesToString(long bytes) {
	    if (-1000 < bytes && bytes < 1000) {
	        return bytes + " o";
	    }
	    CharacterIterator ci = new StringCharacterIterator("kMGTPE");
	    while (bytes <= -999_950 || bytes >= 999_950) {
	        bytes /= 1000;
	        ci.next();
	    }
	    return String.format("%.1f %co", bytes / 1000.0, ci.current());
	}
	
	private final String vanilla = "Graphismes réduits" + '\n' + '\n' + "Garde tout de même l'aspect graphique général de Krosmocraft mais" + '\n' + "sacrifie de la qualité pour gagner en performance" + '\n' + '\n' + "Peut être tourné par tout types de PC et toasters" + '\n' + "4 Go ou + de RAM allouée conseillé";
	private final String mediumEnd = "Graphismes optimisés" + '\n' + '\n' + "Le look de base de Krosmocraft en optimisant un maximum les" + '\n' + "paramètres pour garantir de bonnes performances" + '\n' + '\n' + "Peut être tourné par tout types de PC... normalement" + '\n' + "6 Go ou + de RAM allouée conseillé";
	private final String highEnd = "Graphismes HD" + '\n' + '\n' + "Krosmocraft, encore plus beau. Si vous avez confiance en votre PC, il" + '\n' + "est conseillé de choisir cette option, après tout, vous le valez bien ♥" + '\n' + '\n' + "Peut être tourné par tout types de PC modernes" + '\n' + "8 Go de RAM allouée conseillé";
}
