package fr.trxyy.alternative.alternative_api_ui.components;

import fr.trxyy.launcher.template.LauncherMain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @author Trxyy
 */
public class LauncherImage extends ImageView {
	
	/**
	 * The Constructor
	 */
	public LauncherImage() {
		LauncherMain.pane.getChildren().add(this);
	}
	
	/**
	 * The Constructor
	 * @param image The image
	 */
	public LauncherImage(Image image) {
		super(image);
		LauncherMain.pane.getChildren().add(this);
	}
	
	/**
	 * Set the size
	 * @param x The position X
	 * @param y The position Y
	 */
	public void setSize(int w, int h) {
		this.setFitWidth(w);
		this.setFitHeight(h);
	}

	/**
	 * Set the bounds
	 * @param x The position X
	 * @param y The position Y
	 */
	public void setBounds(int x, int y, int w, int h) {
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setFitWidth(w);
		this.setFitHeight(h);
	}

}
