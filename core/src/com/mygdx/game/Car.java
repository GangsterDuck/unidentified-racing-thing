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
    private Body body;
    static World physWorld;
    private Texture texture;
    int zone = -1;

    /**
     * Car Constructor
     * DEPRECATED!
     */
    public Car(){
        if(physWorld == null){
            physWorld = getPhysWorld();
        }
        name = "";
        texture = new Texture("NewTestCar.png");
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(780/SCALE,900/SCALE);
        def.angle = (float)Math.toRadians(-90);
        body = physWorld.createBody(def);
        body.setSleepingAllowed(false);
        PolygonShape rect = new PolygonShape();
        rect.setAsBox(texture.getWidth()/2/SCALE,texture.getHeight()/2/SCALE);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = rect;
        fDef.density = 0.25f;
        fDef.friction = 5f;
        fDef.restitution = .001f;
        body.createFixture(fDef);
        body.setLinearDamping(5f);
        body.setAngularDamping(5f);
        rect.dispose();
    }//DEPRECATED

    public Car(int x, int y, String textureLOC){
        if(physWorld == null){
            physWorld = getPhysWorld();
        }
        name = "";
        texture = new Texture(textureLOC);
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x/SCALE,y/SCALE);
        def.angle = (float)Math.toRadians(-90);
        body = physWorld.createBody(def);
        body.setSleepingAllowed(false);
        PolygonShape rect = new PolygonShape();
        rect.setAsBox(texture.getWidth()/2/SCALE,texture.getHeight()/2/SCALE);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = rect;
        fDef.density = 0.25f;
        fDef.friction = 5f;
        fDef.restitution = .0001f;
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

    public void testFixture(){
        PolygonShape rect = new PolygonShape();
        rect.setAsBox(texture.getWidth()/2/SCALE,texture.getHeight()/2/SCALE);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = rect;
        fDef.density = 84f;
        fDef.friction = 5f;
        fDef.restitution = .0001f;
        body.createFixture(fDef);
    }

    /**
     * Uses the Cars Brakes
     * @param str how hard the brakes are used (0 to 1)
     */
    public void brake(double str){
        double velocity = Math.sqrt(Math.pow(body.getLinearVelocity().x, 2) + Math.pow(body.getLinearVelocity().y, 2)) - .0125;
        if (velocity < 0) {
            body.setLinearVelocity(0, 0);
        } else {
            double xAccelChange = -Math.sin(body.getAngle());
            double yAccelChange = Math.cos(body.getAngle());
            body.setLinearVelocity((float) (body.getLinearVelocity().x - .0125 * xAccelChange * str), (float) (body.getLinearVelocity().y - .0125 * yAccelChange * str));
        }
    }

    /**
     * Accelerates Car forwards
     * @param str strength of acceleration (0 to 1)
     */
    public void accelerate(double str){
        double xAccelChange = -Math.sin(body.getAngle());
        double yAccelChange = Math.cos(body.getAngle());
        body.setLinearVelocity((float) (body.getLinearVelocity().x + .25 * xAccelChange * str), (float) (body.getLinearVelocity().y + .25 * yAccelChange* str));
    }

    /**
     * Turns the car
     * @param dir the direction turning
     * @param str strength of the turn (0 to 1)
     */
    public void turn(int dir, double str){
        double turnRate;
        double velocity;
        if (body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0) {
            velocity = 0;
        } else {
            velocity = Math.sqrt(Math.pow(body.getLinearVelocity().x, 2) + Math.pow(body.getLinearVelocity().y, 2));
        }

        if (velocity > 1) {
            turnRate = -Math.log(velocity) * .0225 + .2;//Math.PI;
        } else if (velocity > 0) {
            turnRate = .2;
        } else {
            turnRate = 0;
        }

        double newAngle = body.getAngle();
        if(dir == -1){
            newAngle = body.getAngle() - (.25 * turnRate * str);
        }
        else if(dir==1){
            newAngle = body.getAngle() + (.25 * turnRate * str);
        }
        body.setTransform(body.getPosition().x, body.getPosition().y, (float) newAngle);
        double xAccelChange = -Math.sin(body.getAngle());
        double yAccelChange = Math.cos(body.getAngle());
        body.setLinearVelocity((float) (velocity * xAccelChange), (float) (velocity * yAccelChange));
    }

    /**
     * Properly destroys car
     * WARNING: Run to remove texture and physWorld body! Needed before removing access to Car!
     */
    public void destroy(){
        physWorld.destroyBody(body);
        texture.dispose();
    }

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

    //Warning, May Possibly be wrong
    /**
     * Accessor for Cars X position. Gotten from body and moved to midpoint!
     * @return Cars x position (midpoint)
     */
    public float getX(){
        return body.getPosition().x*SCALE- texture.getWidth()/2;
    }

    //Warning, may possibly be wrong
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
