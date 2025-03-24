package fr.trxyy.alternative.alternative_api_ui.components;

import fr.trxyy.alternative.alternative_api.utils.AESUtil;
import fr.trxyy.alternative.alternative_api.utils.config.EnumConfig;
import fr.trxyy.alternative.alternative_api.utils.config.LauncherConfig;
import fr.trxyy.launcher.template.LauncherMain;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class AnimatedCheckBox extends StackPane {

    public double size;
    private Rectangle background;
    private Label checkMark;

    public AnimatedCheckBox(double size, LauncherLabel remindMe, LauncherConfig config) {
        super();
        this.size = size / 50;

        background = new Rectangle(this.size, this.size);
        background.setFill(Color.LIGHTGRAY);
        background.setArcWidth(5);
        background.setArcHeight(5);

        checkMark = new Label("✔");
        checkMark.setTextFill(Color.WHITE);
        checkMark.setFont(new Font(this.size * 0.6));
        checkMark.setOpacity(0);

        this.setOnMouseClicked(event -> toggleCheckMark(remindMe, config));

        Boolean isPasswordSaved = (Boolean) config.getValue(EnumConfig.ISPASSWORDSAVED);
        if (isPasswordSaved) {
            toggleCheckMark(remindMe, config);
        } else {
            remindMe.setTextFill(Color.WHITE);
        }

        getChildren().addAll(background, checkMark);
        LauncherMain.pane.getChildren().add(this);
    }

    private void toggleCheckMark(LauncherLabel remindMe, LauncherConfig config) {
        boolean isChecked = checkMark.getOpacity() > 0;

        // Animation du check mark
        FadeTransition fade = new FadeTransition(Duration.millis(150), checkMark);
        fade.setFromValue(isChecked ? 1.0 : 0.0);
        fade.setToValue(isChecked ? 0.0 : 1.0);

        ScaleTransition scale = new ScaleTransition(Duration.millis(150), checkMark);
        scale.setFromX(isChecked ? 1.0 : 0.0);
        scale.setFromY(isChecked ? 1.0 : 0.0);
        scale.setToX(isChecked ? 0.0 : 1.0);
        scale.setToY(isChecked ? 0.0 : 1.0);

        background.setFill(isChecked ? Color.LIGHTGRAY : Color.web("#58eaef"));

        if (isChecked) {
            remindMe.setTextFill(Color.WHITE);
        } else {
            remindMe.setTextFill(Color.web("#58eaef"));
        }
        config.updateValue("isPasswordSaved", !isChecked);

        // Lancer les animations
        fade.play();
        scale.play();
    }
}
