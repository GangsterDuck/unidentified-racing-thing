package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static com.mygdx.game.Game.SCALE;

public class RectangleWall extends Wall{

    float height;
    float width;

    /**
     * Constructor for Wall
     * WARNING: Physics World gotten from Game
     * @param midX Mid x-position of Wall
     * @param midY Mid y-position of Wall
     * @param height Height of Wall
     * @param width Width of Wall
     * @param rotation Rotation in degrees
     */
    public RectangleWall(float midX, float midY, float height, float width, float rotation) {
        super(midX, midY, rotation);

        this.height = height;
        this.rotation = rotation;

        // physWorld wall creation
        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsBox((width / 2), (height / 2));

        FixtureDef wallFixDef = new FixtureDef();
        wallFixDef.density = DENSITY;
        wallFixDef.friction = FRICTION;
        wallFixDef.restitution = RESTITUTION;
        wallFixDef.shape = wallShape;

        body.createFixture(wallFixDef);
        wallShape.dispose();
    }
}
