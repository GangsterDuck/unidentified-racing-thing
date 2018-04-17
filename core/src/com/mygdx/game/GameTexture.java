package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class GameTexture {
    int x;
    int y;
    Texture texture;

    /**
     * Constructor for GameTexture
     * @param x x position of the textures bottom left corner
     * @param y y position of the textures bottom left corner
     * @param texture libgdx texture
     */
    public GameTexture(int x, int y, Texture texture){
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    /**
     * Moves textures bottom left corner position to (x, y)
     * @param x new x position of textures bottom left corner
     * @param y new y position of textures bottom left corner
     */
    public void moveTo(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Disposes of the texture as it doesn't automatically happen
     * WARNING: Run before removing object!!
     */
    public void destroy(){
        texture.dispose();
    }

}
