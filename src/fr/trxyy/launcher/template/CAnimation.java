package fr.trxyy.launcher.template;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.util.Duration;

public class CAnimation {
	private DoubleProperty p;
	private double endValue;
	private int duration;
	
	public CAnimation(DoubleProperty p, double endValue, int duration) {
		this.p = p;
		this.endValue = endValue;
		this.duration = duration;
	}
	
	public void run() {
    	final Timeline timeline = new Timeline();
    	timeline.setCycleCount(1);
    	final KeyValue kv = new KeyValue(p, endValue);
    	final KeyFrame kf = new KeyFrame(Duration.millis(duration), kv);
    	timeline.getKeyFrames().add(kf);
    	timeline.play();
	}
	
	public void loop() {
    	final Timeline timeline = new Timeline();
    	timeline.setCycleCount(Animation.INDEFINITE);
    	final KeyValue kv = new KeyValue(p, endValue);
    	final KeyFrame kf = new KeyFrame(Duration.millis(duration), kv);
    	timeline.getKeyFrames().add(kf);
    	timeline.play();
	}
}
