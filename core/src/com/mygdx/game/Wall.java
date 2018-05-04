package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.Game.SCALE;

public class Wall {
    public static final float DENSITY = .25f;
    public static final float FRICTION = .2f;
    public static final float RESTITUTION = .001f;

    static World physWorld = null;
    Body body;
    float midx;
    float midy;
    float rotation;

    public Wall(float midx, float midy, float rotation){
        if(physWorld == null){
            physWorld = Game.getPhysWorld();
        }
        this.midx = midx;
        this.midy = midy;
        this.rotation = rotation;

        BodyDef wallDef = new BodyDef();
        wallDef.angle = rotation;
        wallDef.type = BodyDef.BodyType.StaticBody;
        wallDef.position.set(midx, midy);
        body = physWorld.createBody(wallDef);
    }

    /**
     * Removes the wall from the world.
     * WARNING: Run before object removal, otherwise it's bad!
     */
    public void destroyBody(){
        physWorld.destroyBody(body);
    }

}
