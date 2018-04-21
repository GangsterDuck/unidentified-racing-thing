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
            car.accelerate(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            car.brake(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            car.turn(1,1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            car.turn(-1,1);
        }

    }
}
