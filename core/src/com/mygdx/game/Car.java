package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.Scanner;

import static com.mygdx.game.Game.SCALE;
import static com.mygdx.game.Game.getPhysWorld;

public class Car
{
    String name;
    Body body;
    static World physWorld;
    private Texture texture;
    int zone = -1;
    // TODO: Move to Controller?
    int lapOn = 1;

    /**
     * TEST WORLD CAR CONSTRUCTOR
     * DO NOT USE!!!
     */
    public Car(){
        if(physWorld == null){
            physWorld = getPhysWorld();
        }
        name = "";
        texture = new Texture("LongerTestCar.png");
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(780/SCALE,900/SCALE);
        def.angle = (float)Math.toRadians(-90);
        body = physWorld.createBody(def);
        body.setSleepingAllowed(false);
        PolygonShape rect = new PolygonShape();
        rect.setAsBox(20/SCALE,40/SCALE);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = rect;
        fDef.density = 0.25f;
        fDef.friction = 84f;
        fDef.restitution = .000000000001f;
        body.createFixture(fDef);
        body.setLinearDamping(5f);
        body.setAngularDamping(5f);
        rect.dispose();
    }
    public Car(int x, int y){
        if(physWorld == null){
            physWorld = getPhysWorld();
        }
        name = "";
        texture = new Texture("LongerTestCar.png");
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x/SCALE,y/SCALE);
        def.angle = (float)Math.toRadians(-90);
        body = physWorld.createBody(def);
        body.setSleepingAllowed(false);
        PolygonShape rect = new PolygonShape();
        rect.setAsBox(20/SCALE,40/SCALE);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = rect;
        fDef.density = 0.25f;
        fDef.friction = 84f;
        fDef.restitution = .000000000001f;
        body.createFixture(fDef);
        body.setLinearDamping(5f);
        body.setAngularDamping(5f);
        rect.dispose();
    }

    // TODO: Continue work on Car Constructor and work on different car values
    // REMINDER: No file IO in Car Class!!
    /**
     * WIP Default Car Constructor
     * @param x Car's x position
     * @param y Car's y position
     * @param rotation Car's rotation in degrees
     * @param carFileContents Arraylist of the contents of the cars text file. May be switched to string and loading happens in here
     */
    public Car(int x, int y, double rotation, ArrayList<String> carFileContents){
        if(physWorld == null){
            physWorld = getPhysWorld();
        }
        name = carFileContents.get(0);
        texture = new Texture("D:\\# - Java\\test\\cars\\"+carFileContents.get(1));
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.angle = (float)Math.toRadians(rotation);
        body = physWorld.createBody(bodyDef);
        body.setSleepingAllowed(false);
        PolygonShape rect = new PolygonShape();
        rect.setAsBox(texture.getWidth()/(2*SCALE),texture.getHeight()/(2*SCALE));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rect;
        rect.dispose();
        fixtureDef.density = .25f;
        fixtureDef.friction = 5f;
        fixtureDef.restitution = .0001f;
        body.createFixture(fixtureDef);
        body.setLinearDamping(5f);
        body.setAngularDamping(5f);
    }

    /**
     * Properly destroys car
     * WARNING: Run to remove texture and physWorld body! Needed before removing access to Car!
     */
    public void destroy(){
        physWorld.destroyBody(body);
        texture.dispose();
    }

    //TODO: Decide if unneeded?
    /**
     * Accessor for car's physWorld body
     * @return car's physWorld body
     */
    public Body getBody(){
        return body;
    }

    /**
     * Accessor for car texture
     * @return car's texture
     */
    public Texture getTexture(){
        return texture;
    }

    // TODO: Check if actually midpoint, have feeling it may be wrong
    /**
     * Accessor for Cars X position. Gotten from body and moved to midpoint!
     * @return Cars x position (midpoint)
     */
    public float getX(){
        return body.getPosition().x*SCALE- texture.getWidth()/2;
    }

    // TODO: Check if actually midpoint, have feeling it may be wrong
    /**
     * Accessor for Cars Y position. Gotten from body and moved to midpoint!
     * @return Cars y position. (midpoint)
     */
    public float getY(){
        return body.getPosition().y*SCALE-texture.getHeight()/2;
    }

    /**
     * Accessor of Cars angle. Converted into degrees.
     * @return angle of car in degrees
     */
    public float getAngle(){
        return (float)(Math.toDegrees(body.getAngle()));
    }


}
