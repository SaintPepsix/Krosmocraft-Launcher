package fr.trxyy.alternative.alternative_api_ui.components;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.utils.ResourceLocation;
import fr.trxyy.launcher.template.LauncherMain;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * @author Trxyy
 */
public class LauncherProgressBar extends ProgressBar {
	
	/**
	 * The current file name downloading
	 */
	public String currentFile;
	

	public GameEngine theGameEngine;
	
	/**
	 * The Constructor
	 */
	public LauncherProgressBar() {}
	
	/**
	 * The Constructor
	 */
	public LauncherProgressBar(GameEngine eng) {
		LauncherMain.pane.getChildren().add(this);
		this.theGameEngine = eng;
	}
	
	/**
	 * Set the size of the Progress bar
	 * @param width_ The width
	 * @param height_ The height
	 */
	public void setSize(int width_, int height_) {
		this.setPrefSize(width_, height_);
		this.setWidth(width_);
		this.setHeight(height_);
	}
	
	/**
	 * Set the size of the Progress bar (double)
	 * @param width_ The width
	 * @param height_ The height
	 */
	public void setSize(double width_, double height_) {
		this.setPrefSize(width_, height_);
		this.setWidth(width_);
		this.setHeight(height_);
	}
	
	/**
	 * Set the progress bar invisible
	 */
	public void setInvisible() {
		this.setBackground(null);
	}

	/**
	 * Set the position
	 * @param posX The position X
	 * @param posY The position Y
	 */
	public void setPosition(int posX, int posY) {
		this.setLayoutX(posX);
		this.setLayoutY(posY);
	}
	
	/**
	 * Set the position (double)
	 * @param posX The position X (double)
	 * @param posY The position Y (double)
	 */
	public void setPosition(double posX, double posY) {
		this.setLayoutX(posX);
		this.setLayoutY(posY);
	}
	/**
	 * Set the current file name
	 * @param file The file name
	 */
	public void setCurrentFile(String file) {
		this.currentFile = file;
	}
	
	/**
	 * @return The current file name
	 */
	public String getCurrentFile() {
		return this.currentFile;
	}

	/**
	 * Set the bounds
	 * @param posX The position X
	 * @param posY The position Y
	 * @param width The size X
	 * @param height The size Y
	 */
	public void setBounds(int posX, int posY, int width, int height) {
		this.setLayoutX(posX);
		this.setLayoutY(posY);
		this.setPrefSize(width, height);
	}
	
	/**
	 * Set the Style
	 * @param value The value
	 */
    public final void addStyle(String value) {
    	String finalValue = this.getStyle() + value;
        styleProperty().set(finalValue);
    }
    
    public void big() {
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("smol"), false);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("big"), true);
    }
    
    public void small() {
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("smol"), true);
        this.pseudoClassStateChanged(PseudoClass.getPseudoClass("big"), false);
    }
    
    public void animate() {
        // Set the max status
        int maxStatus = 12;
        // Create the Property that holds the current status count
        IntegerProperty statusCountProperty = new SimpleIntegerProperty(1);
        // Create the timeline that loops the statusCount till the maxStatus
        Timeline timelineBar = new Timeline(
                new KeyFrame(
                        // Set this value for the speed of the animation
                        Duration.millis(700),
                        new KeyValue(statusCountProperty, maxStatus)
                )
        );
        // The animation should be infinite
        timelineBar.setCycleCount(Timeline.INDEFINITE);
        timelineBar.play();
        // Add a listener to the statusproperty
        statusCountProperty.addListener((ov, statusOld, statusNewNumber) -> {
            int statusNew = statusNewNumber.intValue();
            // Remove old status pseudo from progress-bar
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusOld.intValue()), false);
            // Add current status pseudo from progress-bar
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusNew), true);
        });
    }
    /** {@inheritDoc} */
    @Override
    public String getUserAgentStylesheet() {
        return ResourceLocation.class.getResource(String.valueOf(theGameEngine.getLauncherPreferences().getResourceLocation()) + "progressbar.css").toExternalForm();
    }
}
