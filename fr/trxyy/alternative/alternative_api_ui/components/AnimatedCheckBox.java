package fr.trxyy.alternative.alternative_api_ui.components;

import fr.trxyy.alternative.alternative_api.utils.config.EnumConfig;
import fr.trxyy.alternative.alternative_api.utils.config.LauncherConfig;
import fr.trxyy.launcher.template.CAnimation;
import fr.trxyy.launcher.template.LauncherMain;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class AnimatedCheckBox extends StackPane {

    public double size;
    private Rectangle background;
    private Label checkMark;
    private boolean isChecked = false;

    public AnimatedCheckBox(double size, LauncherLabel remindMe, LauncherConfig config) {
        super();
        this.size = size / 50;

        background = new Rectangle(this.size, this.size);
        background.setFill(Color.BLACK);
        background.setArcWidth(5);
        background.setArcHeight(5);

        checkMark = new Label("✔");
        checkMark.setTextFill(Color.WHITE);
        checkMark.setFont(new Font(this.size * 0.6));
        checkMark.setOpacity(0);

        this.setOnMouseClicked(event -> toggleCheckMark(remindMe, config, true));
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (!isChecked){
                    CAnimation c = new CAnimation(remindMe.opacityProperty(), 0.9, 200);
                    c.run();
                }
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (!isChecked){
                    CAnimation c = new CAnimation(remindMe.opacityProperty(), 0.4, 200);
                    c.run();
                }
            }
        });

        isChecked = (Boolean) config.getValue(EnumConfig.ISPASSWORDSAVED);
        if (isChecked) {
            toggleCheckMark(remindMe, config, false);
        } else {
            remindMe.setTextFill(Color.WHITE);
            background.setOpacity(0.6d);
        }

        getChildren().addAll(background, checkMark);
        LauncherMain.pane.getChildren().add(this);
    }

    public boolean isChecked(){
        return isChecked;
    }

    public void toggleCheckMark(LauncherLabel remindMe, LauncherConfig config, boolean animate) {
        this.isChecked = checkMark.getOpacity() <= 0;

        // Animation du check mark
        FadeTransition fade = new FadeTransition(Duration.millis(animate ? 200 : 1), checkMark);
        fade.setFromValue(!isChecked ? 1.0 : 0.0);
        fade.setToValue(!isChecked ? 0.0 : 1.0);

        ScaleTransition scale = new ScaleTransition(Duration.millis(animate ? 200 : 1), checkMark);
        scale.setFromX(!isChecked ? 1.0 : 0.0);
        scale.setFromY(!isChecked ? 1.0 : 0.0);
        scale.setToX(!isChecked ? 0.0 : 1.0);
        scale.setToY(!isChecked ? 0.0 : 1.0);

        background.setFill(Color.BLACK);
        background.setOpacity(0.6d);

        remindMe.setTextFill(Color.WHITE);
        if (isChecked) {
            remindMe.setOpacity(0.9);
        }
        config.updateValue("isPasswordSaved", isChecked);

        // Lancer les animations
        fade.play();
        scale.play();
    }
}
