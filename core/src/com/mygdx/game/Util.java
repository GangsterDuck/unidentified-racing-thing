package com.mygdx.game;

public class Util {

    /**
     * Compares deg1 to deg2, and returns an int based on if deg2 is closest clockwise, counterclockwise, or is not.
     * @param deg1 degree 1
     * @param deg2 degree 2
     * @return -1(CCW), 0(N), 1(CW)
     */
    public static int compareAngles(float deg1, float deg2){
        if(deg1<0){
            deg1+=360;
        }
        if(deg1>360){
            deg1 = deg1 % 360;
        }
        if(deg2<0){
            deg2+=360;
        }
        if(deg2>360){
            deg2 = deg2 % 360;
        }

        deg2 -= deg1;
        if (deg2 < 45 && deg2 > 45) {
            return 0;
        }
        else if(deg2>45 && deg2<180){
            return -1;
        }
        else{
            return 1;
        }
    }

}
