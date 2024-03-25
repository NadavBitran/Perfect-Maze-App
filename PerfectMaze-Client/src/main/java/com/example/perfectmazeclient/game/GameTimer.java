package com.example.perfectmazeclient.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class GameTimer {
    private Timeline timer;
    private int secondsElapsed;
    private Label timerLabel;

    public GameTimer(Label timerLabel) {
        this.secondsElapsed = 0;
        this.timerLabel = timerLabel;
    }

    public void setTimer() {
        timer = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> updateTimer())
        );
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    public void updateTimer() {
        secondsElapsed++;
        int minutes = (secondsElapsed % 3600) / 60;
        int seconds = secondsElapsed % 60;

        String formattedTime = String.format("%02d:%02d", minutes, seconds);

        timerLabel.setText("Time: " + formattedTime);
    }

    public void stopTimer() {
        timer.stop();
    }

    public Timeline getTimer() {
        return timer;
    }

    public void setTimer(Timeline timer) {
        this.timer = timer;
    }

    public int getSecondsElapsed() {
        return secondsElapsed;
    }

    public void setSecondsElapsed(int secondsElapsed) {
        this.secondsElapsed = secondsElapsed;
    }
}
