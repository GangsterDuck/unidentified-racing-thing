package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;

import static com.mygdx.game.Game.SCALE;

public class AIController extends Controller {
    
    AIPoint target;
    static ArrayList<AIPoint> points; // TODO: Remove AIPoints from Game class and consolidate them in just AIController?
    
    AIController(Car car, AIPoint startingTarget){
        super(car);
        // Connects the AIController to the list of AIPoints created in Game when the maps are loaded
        if(points==null){
            points = Game.aiPoints;
        }
        // Starting point, also inside the AIPoints list
        target = startingTarget;
    }

    /**
     * Sets a new AIPoint target for the AIController
     * @param newTarget The new AIPoint target
     */
    public void newTarget(AIPoint newTarget){
        target = newTarget;
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
     * A Trash Heap Attacks! Choose your move!:
     * TODO: Clean Up
     * TODO: Make Actual Java Doc
     */
    public void drive(){
        checkTarget();
        Body body = car.getBody();
        Vector2 p2 = new Vector2(target.midx,target.midy);
        float carX = body.getPosition().x;
        float carY = body.getPosition().y;
        p2.sub(body.getPosition());
        float degree = Util.boundAngle(p2.angle());
        float currentDegree = (float)Util.boundAngle(Math.toDegrees(body.getAngle())-90);
        //Todo: Fix it all...
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
        if(Util.compareAngles(currentDegree,degree)==-1){
            newAngle = Util.boundAngle(body.getAngle() - (.25 * turnRate));
        }
        else if(Util.compareAngles(currentDegree,degree)==1){
            newAngle = Util.boundAngle(body.getAngle() + (.25 * turnRate));
        }
        body.setTransform(body.getPosition().x, body.getPosition().y, (float) newAngle);
        double xAccelChange = -Math.sin(body.getAngle());
        double yAccelChange = Math.cos(body.getAngle());
        body.setLinearVelocity((float) (velocity * xAccelChange), (float) (velocity * yAccelChange));

        // This Always forces the AI to Drive forwards at a speed. This Should be removed when implementing actual AI or changed to accommodate AI changes
        xAccelChange = -Math.sin(body.getAngle());
        yAccelChange = Math.cos(body.getAngle());
        body.setLinearVelocity((float) (body.getLinearVelocity().x + .125 * xAccelChange), (float) (body.getLinearVelocity().y + .125 * yAccelChange));
    }
}
