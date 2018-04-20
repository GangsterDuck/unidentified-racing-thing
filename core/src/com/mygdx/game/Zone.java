package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;

import static com.mygdx.game.Game.SCALE;

public abstract class Zone {
    // ALL SUBCLASSES SHOULD HAVE A STATIC DESTROY FUNCTION
    float x1;
    float y1;
    float x2;
    float y2;
    float midx;
    float midy;

    /**
     * Constructor for Zone
     * @param midX x midpoint of the point
     * @param midY y midpoint of the point TOD
     * @param width width of the catching zone of the point
     * @param height height of the catching zone of the point
     */
    Zone(float midX, float midY, float width, float height){
        // TODO: Fix scaling stuff
        this.midx = midX/SCALE;
        this.midy = midY/SCALE;
        this.x1 = (midX - (width/2))/SCALE;
        this.x2 = (midX + (width/2))/SCALE;
        this.y1 = (midY - (height/2))/SCALE;
        this.y2 = (midY + (height/2))/SCALE;
    }

    /**
     * Checks if point at (y, x) is within the the zones area
     * @param x points x-coordinate
     * @param y points y-coordinate
     * @return boolean of if point is within zone or not
     */
    public boolean isWithin(float x, float y){
        // x1 is always smaller than x2. y1 is also always smaller than y2.
        if(x>x1 && x<x2 && y>y1 && y < y2){
            return true;
        }
        return false;
    }

    public abstract Texture getDebugTexture();
}
