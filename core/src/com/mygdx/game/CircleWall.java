package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class CircleWall extends Wall {

    float radius;

    public CircleWall(float midX, float midY, float radius){
        super(midX, midY, 0);

        this.radius = radius;

        // physWorld wall creation
        CircleShape wallShape = new CircleShape();
        //wallShape.setPosition(new Vector2(midx,midy));
        wallShape.setRadius(radius);

        FixtureDef wallFixDef = new FixtureDef();
        wallFixDef.density = DENSITY;
        wallFixDef.friction = FRICTION;
        wallFixDef.restitution = RESTITUTION;
        wallFixDef.shape = wallShape;

        body.createFixture(wallFixDef);
        wallShape.dispose();

    }
}
