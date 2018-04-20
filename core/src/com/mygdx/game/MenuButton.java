package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

import static com.mygdx.game.Game.SCALE;

public class MenuButton extends Zone{
    private boolean isWithin;
    private Texture texNH; // Texture: Non Highlighted
    private Texture texH; // Texture: Highlighted
    private static Texture debugTexture;
    private String loadType;
    private String loadName;

    /**
     * Constructor for MenuButton
     * @param bottomX x lower left of the button
     * @param bottomY y lower left of the button
     * @param width width of the catching zone of the point
     * @param height height of the catching zone of the point
     * @param NonHighlightedTexture Button Texture with nothing within button
     * @param HighlightedTexture Button Texture with something within button
     * @param loadType The type of object loaded when the button is pressed (Currently: Map, Menu, Exit)
     * @param loadName The name of the object to be loaded when the button is pressed
     */
    MenuButton(float bottomX, float bottomY, float width, float height, Texture NonHighlightedTexture, Texture HighlightedTexture, String loadType, String loadName ){
        super((bottomX+(width/2))*SCALE, (bottomY+(height/2))*SCALE, width*SCALE, height*SCALE);
        isWithin = false;
        texNH = NonHighlightedTexture;
        texH = HighlightedTexture;
        this.loadName = loadName;
        this.loadType = loadType;
        if(debugTexture==null){
            debugTexture = new Texture("debug\\AIPointSq.png");
        }
    }

    public boolean checkWithin(float x, float y){
        if(super.isWithin(x, y)){
            isWithin = true;
            return true;
        }
        isWithin = false;
        return false;
    }

    /**
     * Accessor for isWithin
     * @return isWithin
     */
    public boolean getIsWithin(){
        return isWithin;
    }

    /**
     * Accessor for debugTexture
     * @return debugTexture
     */
    public Texture getDebugTexture() {
        return debugTexture;
    }

    /**
     * Accessor for highlighted texture
     * @return highlighted texture
     */
    public Texture getHighlightTexture() {
        return texH;
    }

    /**
     * Accessor for Non Highlighted Texture
     * @return non highlighted texture
     */
    public Texture getNonHighlightedTexture() {
        return texNH;
    }

    /**
     * Off of isWithin, chooses current texture and returns it
     * @return current texture
     */
    public Texture getCurrentTexture(){
        if(isWithin){
            return texH;
        }
        else{
            return  texNH;
        }
    }

    /**
     * Returns data for loading. Returns in Arraylist Slot 0 the type of loading to happen. Returns in Arraylist Slot 1, the name of the file to load.
     * @return loading data
     */
    public ArrayList<String> getLoadType(){
        ArrayList<String> str = new ArrayList<String>();
        str.add(loadType);
        str.add(loadName);
        return str;
    }

    /**
     * Disposes of the texture as it doesn't automatically happen
     * WARNING: Run before removing object!!
     */
    public void destroy(){
        texH.dispose();
        texNH.dispose();
    }

    /**
     * Properly disposes of the texture. Destroy sounds cooler. Yes, I am lame.
     * WARNING: Run before ending the program.
     */
    public static void debugDestroy(){
        // Proper Disposal
        debugTexture.dispose();
    }
}
