package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.game.Game.SCALE;

public class AIController extends Controller {

    static Random rand;
    AIPoint target;
    static ArrayList<AIPoint> points;
    double randSpeed;

    AIController(Car car, AIPoint startingTarget){
        super(car);
        rand = new Random();
        // Connects the AIController to the list of AIPoints created in Game when the maps are loaded
        if(points==null){
            points = Game.aiPoints;
        }
        // Starting point, also inside the AIPoints list
        target = startingTarget;
        double temp = 0;
        while (temp<.5 || temp>.8 ){
            temp = rand.nextDouble();
        }
        randSpeed = temp;
    }

    /**
     * Sets a new AIPoint target for the AIController
     * @param newTarget The new AIPoint target
     */
    public void newTarget(AIPoint newTarget){
        target = newTarget;
        double temp = 0;
        while (temp<.5 || temp>.8 ){
            temp = rand.nextDouble();
        }
        randSpeed = temp;
    }

    /**
     * Checks to see if the cars x and y values are within the targets values by calling the isWithin command
     * from target. Sets new target off of next AIPoint in point if within target.
     */
    public void checkTarget(){
        if(target.isWithin(car.getX()/SCALE+20/SCALE,car.getY()/SCALE+40/SCALE)){
            try {
                newTarget(points.get(points.indexOf(target)+1));
            }catch (IndexOutOfBoundsException ex){
                newTarget(points.get(0));
            }
        }
    }

    /**
     * Method running the AI Pathfinding, (Eventually ALL AI Descisions), and Car Turning
     */
    public void drive(){
        checkTarget();
        Vector2 p2 = new Vector2(target.midx,target.midy);
        p2.sub(car.getBody().getPosition());
        float degree = Util.boundAngle(p2.angle());
        float currentDegree = (float)Util.boundAngle(Math.toDegrees(car.getBody().getAngle())-90);

        // Todo: Set up AI going at different speeds. based off the Points and stuff....
        // Decides direction to turn and turns the car
        car.turn(Util.compareAngles(currentDegree,degree), 1.2);
        // AI accelerates forward
        car.accelerate(1);
    }
}
