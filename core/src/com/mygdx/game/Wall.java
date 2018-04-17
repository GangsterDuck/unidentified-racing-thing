package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.Game.SCALE;

public class Wall {
    static World physWorld = null;
    Body body;
    int x;
    int y;
    int width;
    int height;
    float midx;
    float midy;
    double rotation;

    /**
     * Constructor for Wall
     * WARNING: Physics World gotten from Game
     * @param x Lower left corner x-position of Wall
     * @param y Lower left corner y-position of Wall
     * @param height Height of Wall
     * @param width Width of Wall
     * @param rotation Rotation in degrees
     */
    public Wall(int x, int y, int height, int width, double rotation){
        if(physWorld == null){
            physWorld = Game.getPhysWorld();
        }
        //TODO: Clean up?
        BodyDef wallDef;
        PolygonShape wallShape = new PolygonShape();
        FixtureDef wallFixDef = new FixtureDef();
        wallFixDef.density = .25f;
        wallFixDef.friction = .2f;
        wallFixDef.restitution = .00000001f;
        midy = ((height/2)+y) /SCALE;
        midx = ((width/2)+x)/SCALE;
        wallDef = new BodyDef();
        wallDef.angle = (float)Math.toRadians(rotation);
        wallDef.type = BodyDef.BodyType.StaticBody;
        wallDef.position.set((float)midx,(float)midy);
        wallShape.setAsBox((width/2)/SCALE,(height/2)/SCALE);
        wallFixDef.shape = wallShape;
        body = physWorld.createBody(wallDef);
        body.createFixture(wallFixDef);
        wallShape.dispose();
    }

    /**
     * Removes the wall from the world.
     * WARNING: Run before object removal, otherwise it's bad!
     */
    public void destroyBody(){
        physWorld.destroyBody(body);
    }

}
