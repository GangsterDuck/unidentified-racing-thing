package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class LapZone extends Zone {
    // zoneNum should never be changed once set.
    private int zoneNum;
    private static Texture debugTexture;

    /**
     * Constructor for LapZone
     * @param midX x midpoint of the point
     * @param midY y midpoint of the point TOD
     * @param width width of the catching zone of the point
     * @param height height of the catching zone of the point
     * @param zoneNum Number of the zone for lap purposes (0 or 1)
     */
    public LapZone(float midX, float midY, float width, float height, int zoneNum){
        super(midX, midY, width, height);
        if(debugTexture==null){
            debugTexture = new Texture("debug\\zoneSq.png");
        }
        this.zoneNum = zoneNum;

    }

    /**
     * Accessor for debugTexture
     * @return debugTexture
     */
    public Texture getDebugTexture() {
        return debugTexture;
    }

    /**
     * Accessor for zoneNum
     * @return zoneNum
     */
    public int getZoneNum() {
        return zoneNum;
    }

    /**
     * Properly disposes of the texture. Destroy sounds cooler. Yes, I am lame.
     * WARNING: Run before ending program
     */
    public static void destroy(){
        if(debugTexture!=null) {
            debugTexture.dispose();
            debugTexture=null;
        }
    }
}
