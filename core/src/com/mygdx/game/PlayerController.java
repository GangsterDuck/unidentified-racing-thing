package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;

public class PlayerController extends Controller {

    public PlayerController(Car car){
        super(car);
    }

    /**
     * Takes player inputs and moves the car based off those.
     */
    public void drive(){
        Body body = car.getBody();
        //if()
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            double xAccelChange = -Math.sin(body.getAngle());
            double yAccelChange = Math.cos(body.getAngle());
            body.setLinearVelocity((float) (body.getLinearVelocity().x + .25 * xAccelChange), (float) (body.getLinearVelocity().y + .25 * yAccelChange));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            double velocity = Math.sqrt(Math.pow(body.getLinearVelocity().x, 2) + Math.pow(body.getLinearVelocity().y, 2)) - .0125;
            if (velocity < 0) {
                body.setLinearVelocity(0, 0);
            } else {
                double xAccelChange = -Math.sin(body.getAngle());
                double yAccelChange = Math.cos(body.getAngle());
                body.setLinearVelocity((float) (body.getLinearVelocity().x - .0125 * xAccelChange), (float) (body.getLinearVelocity().y - .0125 * yAccelChange));
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            double turnRate;
            double velocity;
            if (body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0) {
                turnRate = 0;
                velocity = 0;
            } else {
                velocity = Math.sqrt(Math.pow(body.getLinearVelocity().x, 2) + Math.pow(body.getLinearVelocity().y, 2));
            }

            if (velocity > 1) {
                turnRate = -Math.log(velocity) * .0225 + .2;//Math.PI;
            } else if (velocity > 0) {
                turnRate = .2;
            } else {
                turnRate = velocity;
            }
            double newAngle = body.getAngle() + (.25 * turnRate);
            body.setTransform(body.getPosition().x, body.getPosition().y, (float) newAngle);
            double xAccelChange = -Math.sin(body.getAngle());
            double yAccelChange = Math.cos(body.getAngle());
            body.setLinearVelocity((float) (velocity * xAccelChange), (float) (velocity * yAccelChange));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            double turnRate;
            double velocity;
            if (body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0) {
                turnRate = 0;
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
            double newAngle = body.getAngle() - (.25 * turnRate);
            body.setTransform(body.getPosition().x, body.getPosition().y, (float) newAngle);
            double xAccelChange = -Math.sin(body.getAngle());
            double yAccelChange = Math.cos(body.getAngle());
            body.setLinearVelocity((float) (velocity * xAccelChange), (float) (velocity * yAccelChange));
        }

    }
}
