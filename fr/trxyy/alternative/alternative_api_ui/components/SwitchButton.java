package fr.trxyy.alternative.alternative_api_ui.components;

import fr.trxyy.launcher.template.LauncherMain;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;


public class SwitchButton extends StackPane {
    private final Rectangle back;
    public final Button button = new Button();

    private String buttonStyleOff = "-fx-background-color: white; -fx-background-radius: 50%;";
    private String buttonStyleOn = "-fx-background-color: #00d0d4; -fx-background-radius: 50%;";

    private boolean state = false;
    private TranslateTransition transition;

    public SwitchButton(double screenWidth) {
        // Ajustement dynamique de la taille
        double barWidth = Math.max(35, Math.min(screenWidth / 25, 60)); // Barre entre 35px et 60px
        double barHeight = barWidth / 3; // Ajustement de la hauteur
        double buttonSize = barHeight * 1.5; // Bouton légèrement plus grand que la barre

        back = new Rectangle(barWidth, barHeight, Color.web("#ced5da"));
        button.setShape(new Circle(buttonSize / 2));

        button.setStyle(buttonStyleOff);
        setAlignment(button, Pos.CENTER_LEFT);

        getChildren().addAll(back, button);
        setMinSize(barWidth, barHeight);

        back.setArcHeight(barHeight);
        back.setArcWidth(barHeight);

        // Correction de l'erreur d'effet CSS
        button.setStyle(buttonStyleOff);

        // Initialisation de l'animation
        transition = new TranslateTransition(Duration.millis(150), button);
        transition.setCycleCount(1);

        // Appliquer les tailles dynamiques
        button.setMinSize(buttonSize, buttonSize);
        button.setMaxSize(buttonSize, buttonSize);
        button.setPrefSize(buttonSize, buttonSize);

        LauncherMain.pane.getChildren().add(this);

        // Gestion du clic pour animer le bouton
        EventHandler<Event> click = new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
                if (state) {
                    button.setStyle(buttonStyleOff);
                    back.setFill(Color.web("#ced5da"));
                    transition.setToX(0); // Revient à gauche
                    state = false;
                } else {
                    button.setStyle(buttonStyleOn);
                    back.setFill(Color.web("#00d0d4"));
                    transition.setToX(barWidth - buttonSize + buttonSize / 2);
                    state = true;
                }
                transition.play();
            }
        };

        button.setFocusTraversable(false);
        setOnMouseClicked(click);
        button.setOnMouseClicked(click);
    }
}