package com.mygdx.game;

public class Util {
    /**
     * Compares deg1 to deg2, and returns an int based on if deg2 is closest clockwise, counterclockwise, or is not.
     * @param deg1 degree 1
     * @param deg2 degree 2
     * @return -1(CCW), 0(N), 1(CW)
     */
    public static int compareAngles(float deg1, float deg2) {
        deg1 = Util.boundAngle(deg1);

        float diff180 = 180 - deg1;
        deg1 = deg1 + diff180;
        deg2 = deg2 + diff180;
        deg2 = Util.boundAngle(deg2);

        if(deg2>220){
            return -1;
        }
        if(deg2<140){
            return 1;
        }
        return 0;
    }
    /**
     * Compares deg1 to deg2, and returns an int based on if deg2 is closest clockwise, counterclockwise, or is not.
     * @param deg1 degree 1
     * @param deg2 degree 2
     * @return -1(CCW), 0(N), 1(CW)
     */
    public static int compareAngles(double deg1, double deg2) {
        deg1 = Util.boundAngle(deg1);

        double diff180 = 180 - deg1;
        deg1 = deg1 + diff180;
        deg2 = deg2 + diff180;
        deg2 = Util.boundAngle(deg2);

        if(deg2>180){
            return -1;
        }
        if(deg2<180){
            return 1;
        }
        return 0;
    }
    /**
     * Compares deg1 to deg2, and returns an int based on if deg2 is closest clockwise, counterclockwise, or is not.
     * @param deg1 degree 1
     * @param deg2 degree 2
     * @return -1(CCW), 0(N), 1(CW)
     */
    public static int compareAngles(int deg1, int deg2) {
        deg1 = Util.boundAngle(deg1);

        int diff180 = 180 - deg1;
        deg1 = deg1 + diff180;
        deg2 = deg2 + diff180;
        deg2 = Util.boundAngle(deg2);

        if(deg2>180){
            return -1;
        }
        if(deg2<180){
            return 1;
        }
        return 0;
    }

    /**
     * Takes an unbound angle and bounds it between 360>deg>=0
     * @param deg unbounded angle
     * @return bounded angle
     */
    public static double boundAngle(double deg){
        if(deg>=360){
            return boundAngle(deg-360);
        }
        else if(deg<0){
            return boundAngle(deg+360);
        }
        else{
            //System.out.println("Util.boundAngle: "+(int)deg);
            return deg;
        }
    }
    /**
     * Takes an unbound angle and bounds it between 360>deg>=0
     * @param deg unbounded angle
     * @return bounded angle
     */
    public static float boundAngle(float deg){
        if(deg>=360){
            return boundAngle(deg-360);
        }
        else if(deg<0){
            return boundAngle(deg+360);
        }
        else{
            //System.out.println("Util.boundAngle: "+(int)deg);
            return deg;
        }
    }
    /**
     * Takes an unbound angle and bounds it between 360>deg>=0
     * @param deg unbounded angle
     * @return bounded angle
     */
    public static int boundAngle(int deg){
        if(deg>=360){
            return boundAngle(deg-360);
        }
        else if(deg<0){
            return boundAngle(deg+360);
        }
        else{
            //System.out.println("Util.boundAngle: "+(int)deg);
            return deg;
        }
    }
}
