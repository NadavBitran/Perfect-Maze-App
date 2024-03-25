package com.example.perfectmazeclient.game;

import com.example.perfectmazeclient.dm.Point;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player {
    public static final Color PLAYER_COLOR = Color.rgb(0, 123, 255);
    private Rectangle playerShape;
    private Point currentPlayerLocation;

    public Player(int playerWidth, int playerHeight, Point currentPlayerLocation) {
        this.playerShape = new Rectangle(playerWidth, playerHeight, PLAYER_COLOR);
        this.currentPlayerLocation = currentPlayerLocation;
    }

    public Rectangle getPlayerShape() {
        return playerShape;
    }

    public void setPlayerShape(Rectangle playerShape) {
        this.playerShape = playerShape;
    }

    public Point getCurrentPlayerLocation() {
        return currentPlayerLocation;
    }

    public void setCurrentPlayerLocation(Point currentPlayerLocation) {
        this.currentPlayerLocation = currentPlayerLocation;
    }
}
