package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

import static com.mygdx.game.Game.SCALE;

//TODO rename it as AIZone?
public class AIPoint extends Zone{
    String pointNum;
    private static Texture debugTexture;
    ArrayList<AICommand> commands; // Commands for point

    /**
     * Constructor for AIPoint,
     * @param midX x midpoint of the point
     * @param midY y midpoint of the point
     * @param width width of the catching zone of the point
     * @param height height of the catching zone of the point
     * @param pointNum Currently used only for debugnaming
     * @param commands TODO: Actually deal with this, currently just send in an empty arraylist. Sorry.
     */
    AIPoint(int midX, int midY, int width, int height, int pointNum, ArrayList<AICommand> commands){
        super(midX,midY,width,height);
        if(debugTexture==null){
            debugTexture = new Texture("debug\\AIPointSq.png");
        }
        this.pointNum = ""+pointNum;
        this.commands = commands;
    }

    /**
     * Accessor for debugTexture
     * @return debugTexture
     */
    public Texture getDebugTexture() {
        return debugTexture;
    }

    /**
     * Properly disposes of the texture. Destroy sounds cooler. Yes, I am lame.
     * WARNING: Run before ending the program.
     */
    public static void destroy(){
        // Proper Disposal
        if(debugTexture!=null) {
            debugTexture.dispose();
            debugTexture=null;
        }
    }

    /**
     * Overridden toString method.
     * @return string of AIPoint.
     */
    @Override
    public String toString() {
        return "P."+pointNum;
    }
}
