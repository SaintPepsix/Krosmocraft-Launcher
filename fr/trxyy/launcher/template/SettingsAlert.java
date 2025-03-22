package fr.trxyy.launcher.template;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.ResourceLocation;
import fr.trxyy.alternative.alternative_api.utils.config.EnumConfig;
import fr.trxyy.alternative.alternative_api.utils.config.LauncherConfig;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherImage;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class SettingsAlert {
	private LauncherRectangle blurRectangle;
	private LauncherLabel title;
	private LauncherLabel subTitle;
	private LauncherImage low;
	private LauncherImage med;
	private LauncherImage high;
	private LauncherLabel shaderLabel;
	private LauncherLabel shaderDesc;
	private final GameEngine theGameEngine;
	private final ResourceLocation loc;
	private final LauncherConfig config;
	private final LauncherPanel panel;
	private final int size;
	
	
	public SettingsAlert(GameEngine engine, LauncherConfig config, ResourceLocation loc, int size, LauncherPanel panel){
		this.theGameEngine = engine;
		this.config = config;
		this.loc = loc;
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
		this.title.setText("Paramètres graphiques");
		this.title.setAlignment(Pos.CENTER);
		this.title.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size * 2));
		this.title.setStyle("-fx-font-weight: bold; -fx-font-family: \"Helvetica\"; -fx-background-color: transparent; -fx-text-fill: white;");
		this.title.setBounds(0, 12 * getResponsive(10), theGameEngine.getWidth(), 30);
		this.title.setOpacity(0);
    	c = new CAnimation(title.opacityProperty(), 1, 500);
    	c.run();
    	
		this.subTitle = new LauncherLabel();
		this.subTitle.setText("Choisissez une option graphique adaptée");
		this.subTitle.setAlignment(Pos.CENTER);
		this.subTitle.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.subTitle.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.subTitle.setBounds(0, 16 * getResponsive(10), theGameEngine.getWidth(), 30);
		this.subTitle.setOpacity(0);
    	c = new CAnimation(subTitle.opacityProperty(), 1, 500);
    	c.run();
    	
		this.low = new LauncherImage(this.loc.loadImage(theGameEngine, "low.png"));
		this.low.setBounds(theGameEngine.getWidth() / 3 - theGameEngine.getWidth() / 14 + 1, theGameEngine.getHeight() / 2 - theGameEngine.getHeight() / 8 - getResponsive(5), getResponsive(10) * 15, getResponsive(10) * 10 + getResponsive(2));
    	c = new CAnimation(low.opacityProperty(), 0.2, 500);
    	c.run();
    	
		this.med = new LauncherImage(this.loc.loadImage(theGameEngine, "med.png"));
		this.med.setBounds(theGameEngine.getWidth() / 2 - theGameEngine.getWidth() / 15 - getResponsive(2), theGameEngine.getHeight() / 2 - theGameEngine.getHeight() / 8 - getResponsive(5), getResponsive(10) * 15, getResponsive(10) * 10 + getResponsive(2));
    	c = new CAnimation(med.opacityProperty(), 0.2, 500);
    	c.run();
    	
		this.high = new LauncherImage(this.loc.loadImage(theGameEngine, "high.png"));
		this.high.setBounds(theGameEngine.getWidth() / 2 + theGameEngine.getWidth() / 10 + 1, theGameEngine.getHeight() / 2 - theGameEngine.getHeight() / 8 - getResponsive(5), getResponsive(10) * 15, getResponsive(10) * 10 + getResponsive(2));
    	c = new CAnimation(high.opacityProperty(), 0.2, 500);
    	c.run();
		
		this.shaderLabel = new LauncherLabel();
		this.shaderLabel.setText("");
		this.shaderLabel.setAlignment(Pos.CENTER);
		this.shaderLabel.setFont(FontLoader.loadFont("Comfortaa-Bold.ttf", "Comfortaa", size + 4));
		this.shaderLabel.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: white;");
		this.shaderLabel.setBounds(0, theGameEngine.getHeight() / 2 + theGameEngine.getHeight() / 18, theGameEngine.getWidth(), getResponsive(10) * 4);
		this.shaderLabel.setOpacity(0);
    	c = new CAnimation(shaderLabel.opacityProperty(), 1, 500);
    	c.run();
		
		this.shaderDesc = new LauncherLabel();
		this.shaderDesc.setText("");
		this.shaderDesc.setTextAlignment(TextAlignment.CENTER);
		this.shaderDesc.setAlignment(Pos.CENTER);
		this.shaderDesc.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size));
		this.shaderDesc.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.shaderDesc.setBounds(0, theGameEngine.getHeight() / 2 + theGameEngine.getHeight() / 12, theGameEngine.getWidth(), getResponsive(10) * 16);
		this.shaderDesc.setOpacity(0);
    	c = new CAnimation(shaderDesc.opacityProperty(), 1, 500);
    	c.run();
    	

    	
    	this.low.setOnMouseEntered(t -> {
            CAnimation c16;
            c16 = new CAnimation(low.opacityProperty(), 1, 100);
            c16.run();
            shaderLabel.setText("Faible");
            shaderDesc.setText(vanilla);
        });
    	this.low.setOnMouseExited(t -> {
            CAnimation c15;
            c15 = new CAnimation(low.opacityProperty(), 0.2, 100);
            c15.run();
            shaderLabel.setText("");
            shaderDesc.setText("");
        });
    	this.med.setOnMouseEntered(t -> {
            CAnimation c14;
            c14 = new CAnimation(med.opacityProperty(), 1, 100);
            c14.run();
            shaderLabel.setText("Normal");
            shaderDesc.setText(mediumEnd);
        });
    	this.med.setOnMouseExited(t -> {
            CAnimation c13;
            c13 = new CAnimation(med.opacityProperty(), 0.2, 100);
            c13.run();
            shaderLabel.setText("");
            shaderDesc.setText("");
        });
    	this.high.setOnMouseEntered(t -> {
            CAnimation c12;
            c12 = new CAnimation(high.opacityProperty(), 1, 100);
            c12.run();
            shaderLabel.setText("HD");
            shaderDesc.setText(highEnd);
        });
    	this.high.setOnMouseExited(t -> {
            CAnimation c1;
            c1 = new CAnimation(high.opacityProperty(), 0.2, 100);
            c1.run();
            shaderLabel.setText("");
            shaderDesc.setText("");
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
            low.setOnMouseClicked(t -> {
                end();
                config.updateValue("quality", "1");
                config.updateValue(EnumConfig.RAM.getOption(), "4.0");
            });
            med.setOnMouseClicked(t -> {
                end();
                config.updateValue("quality", "2");
                config.updateValue(EnumConfig.RAM.getOption(), "6.0");
            });
            high.setOnMouseClicked(t -> {
                end();
                config.updateValue("quality", "3");
                config.updateValue(EnumConfig.RAM.getOption(), "8.0");
            });
        });
        new Thread(sleeper).start();
	}
	
	public int getResponsive(int x) {
		switch(x) {
		case 2:
			return theGameEngine.getHeight() / 300;
		case 3:
			return theGameEngine.getHeight() / 200;
		case 4:
			return theGameEngine.getHeight() / 135;
		case 5:
			return theGameEngine.getWidth() / 200;
		case 6:
			return theGameEngine.getWidth() / 170;
		case 7:
			return theGameEngine.getWidth() / 135;
		case 8:
			return theGameEngine.getWidth() / 130;
		case 9:
			return theGameEngine.getWidth() / 110;
		case 10:
			return theGameEngine.getWidth() / 100;
		default:
			return 0;
		}
	}
	
	public void end() {
		Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                	CAnimation c = new CAnimation(low.opacityProperty(), 0, 700);
                	c.run();
                	c = new CAnimation(med.opacityProperty(), 0, 700);
                	c.run();
                	c = new CAnimation(high.opacityProperty(), 0, 700);
                	c.run();
                	c = new CAnimation(blurRectangle.opacityProperty(), 0, 700);
                	c.run();
                	c = new CAnimation(title.opacityProperty(), 0, 700);
                	c.run();
                	c = new CAnimation(subTitle.opacityProperty(), 0, 700);
                	c.run();
                	c = new CAnimation(shaderLabel.opacityProperty(), 0, 700);
                	c.run();
                	c = new CAnimation(shaderDesc.opacityProperty(), 0, 700);
                	c.run();
                	low.setDisable(true);
                	med.setDisable(true);
                	high.setDisable(true);
                	low.setVisible(false);
                	med.setVisible(false);
                	high.setVisible(false);
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(e -> {
            blurRectangle.setDisable(true);
            title.setDisable(true);
            subTitle.setDisable(true);
            shaderLabel.setDisable(true);
            shaderDesc.setDisable(true);
            blurRectangle.setVisible(false);
            title.setVisible(false);
            subTitle.setVisible(false);
            shaderLabel.setVisible(false);
            shaderDesc.setVisible(false);
			LauncherPanel.phase2 = false;
			LauncherPanel.phase3 = false;
            panel.setupMainScreen(true);
        });
        new Thread(sleeper).start();
	}
	
	private final String vanilla = "Graphismes réduits" + '\n' + '\n' + "Garde tout de même l'aspect graphique général de Krosmocraft mais" + '\n' + "sacrifie de la qualité pour gagner en performance" + '\n' + '\n' + "Peut être tourné par tout types de toasters" + '\n' + "4 Go ou + de RAM allouée conseillé";
	private final String mediumEnd = "Graphismes optimisés" + '\n' + '\n' + "Le look de base de Krosmocraft en optimisant un maximum les" + '\n' + "paramètres pour garantir de bonnes performances" + '\n' + '\n' + "Peut être tourné par tout types de PC... normalement" + '\n' + "6 Go ou + de RAM allouée conseillé";
	private final String highEnd = "Graphismes HD" + '\n' + '\n' + "Krosmocraft, encore plus beau. Si vous avez confiance en votre PC, il" + '\n' + "est conseillé de choisir cette option, après tout, vous le valez bien ♥" + '\n' + '\n' + "Peut être tourné par tout types de PC modernes" + '\n' + "8 Go de RAM allouée conseillé";
}
