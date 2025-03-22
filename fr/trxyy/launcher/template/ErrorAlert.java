package fr.trxyy.launcher.template;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.Infos;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ErrorAlert {
	private LauncherRectangle blurRectangle;
	private LauncherLabel errorTitle;
	private LauncherLabel errorMessage;
	private LauncherLabel retry;
	private GameEngine theGameEngine;
	private Pane root;
	private String title;
	private String message;
	private int size;
	private boolean listening = true;
	
	
	public ErrorAlert(GameEngine engine, String title, String message, int size){
		this.root = LauncherMain.pane;
		this.theGameEngine = engine;
		this.title = title;
		this.message = message;
		this.size = size;
	}
	
	public void draw() {
		this.blurRectangle = new LauncherRectangle(0, 0, theGameEngine.getWidth(), theGameEngine.getHeight());
		this.blurRectangle.setOpacity(0);
		this.blurRectangle.setFill(Color.rgb(10, 10, 10, 0.9));
    	CAnimation c = new CAnimation(blurRectangle.opacityProperty(), 1, 500);
    	c.run();
    	
		this.errorTitle = new LauncherLabel();
		this.errorTitle.setText(title);
		this.errorTitle.setAlignment(Pos.CENTER);
		this.errorTitle.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size * 2));
		this.errorTitle.setStyle("-fx-font-weight: bold; -fx-font-family: \"Helvetica\"; -fx-background-color: transparent; -fx-text-fill: white;");
		this.errorTitle.setBounds(0, 0, theGameEngine.getWidth(), theGameEngine.getHeight() - getResponsive(10) * 7);
		this.errorTitle.setOpacity(0);
    	c = new CAnimation(errorTitle.opacityProperty(), 1, 500);
    	c.run();
    	
		this.errorMessage = new LauncherLabel();
		this.errorMessage.setText(message);
		this.errorMessage.setAlignment(Pos.CENTER);
		this.errorMessage.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.errorMessage.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.errorMessage.setBounds(0, 0, theGameEngine.getWidth(), theGameEngine.getHeight() + getResponsive(10) * 7);
		this.errorMessage.setOpacity(0);
    	c = new CAnimation(errorMessage.opacityProperty(), 1, 500);
    	c.run();
    	
		this.retry = new LauncherLabel();
		this.retry.setText("Réessayer >");
		this.retry.setAlignment(Pos.CENTER);
		this.retry.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.retry.setBounds(theGameEngine.getWidth() / 2 - theGameEngine.getWidth() / 20, theGameEngine.getHeight() / 2 + theGameEngine.getHeight() / 6,  theGameEngine.getWidth() / 10, theGameEngine.getHeight() / 20);
		this.retry.setStyle("-fx-font-weight: bold; -fx-font-family: \"Helvetica\"; -fx-border-color: white; -fx-border-color: white; -fx-border-width: 2px; -fx-background-color: transparent; -fx-text-fill: white;");
		this.retry.setOpacity(0.8);
		Glow g = new Glow(0.5);
		retry.setEffect(g);
		
		this.retry.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (Infos.username && Infos.password) {
		    		Glow g = new Glow(0.5);
		    		retry.setEffect(g);
			    	CAnimation c = new CAnimation(retry.opacityProperty(), 1, 200);
			    	CAnimation c2 = new CAnimation(g.levelProperty(), 1, 200);
			    	c.run();
			    	c2.run();
		    	}
		    }
		});
		this.retry.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (Infos.username && Infos.password) {
		    		Glow g = new Glow(1);
		    		retry.setEffect(g);
			    	CAnimation c = new CAnimation(retry.opacityProperty(), 0.8, 200);
			    	CAnimation c2 = new CAnimation(g.levelProperty(), 0.5, 200);
			    	c.run();
			    	c2.run();
		    	}
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
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
        		root.getScene().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
        		    public void handle(KeyEvent ke) {
        		        if (ke.getCode() == KeyCode.ENTER && listening) {
        		        	listening = false;
        		        	end();
        			    	CAnimation c = new CAnimation(blurRectangle.opacityProperty(), 0.0, 400);
        			    	c.run();
        			    	c = new CAnimation(errorTitle.opacityProperty(), 0.0, 400);
        			    	c.run();
        			    	c = new CAnimation(errorMessage.opacityProperty(), 0.0, 400);
        			    	c.run();
        			    	c = new CAnimation(retry.opacityProperty(), 0.0, 400);
        			    	c.run();
        		        }
        		    }
        		});
        		retry.setOnMouseClicked(new EventHandler<MouseEvent>() {
        		    @Override
        			public void handle(MouseEvent event) {
        		    	listening = false;
        	        	end();
        		    	CAnimation c = new CAnimation(blurRectangle.opacityProperty(), 0.0, 400);
        		    	c.run();
        		    	c = new CAnimation(errorTitle.opacityProperty(), 0.0, 400);
        		    	c.run();
        		    	c = new CAnimation(errorMessage.opacityProperty(), 0.0, 400);
        		    	c.run();
        		    	c = new CAnimation(retry.opacityProperty(), 0.0, 400);
        		    	c.run();
        			}
        		});
            }
        });
        new Thread(sleeper).start();
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
	
	public void end() {
		Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
    		    	retry.setDisable(true);
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
		    	errorTitle.setDisable(true);
		    	errorMessage.setDisable(true);
            }
        });
        new Thread(sleeper).start();
	}
}
