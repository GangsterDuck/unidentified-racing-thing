package com.mygdx.game;

public abstract class Controller {
    //TODO: Make Controller
    protected Car car;
    int currentLaps;
    int finishLaps;
    boolean finished;

    /**
     * Constructor for Controller
     * @param car Car the controller is controlling
     */
    Controller(Car car){
        this.car = car;
        currentLaps = 1;
        finishLaps = 3;
        finished = false;
    }

    /**
     * Abstract Method - Moves Car Based off Desicions made in method.
     */
    abstract void drive();

    //TODO: Continue work. Add stuff needed.
}
