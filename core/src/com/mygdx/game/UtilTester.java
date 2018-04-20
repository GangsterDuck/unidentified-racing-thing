package com.mygdx.game;

import java.util.Scanner;

public class UtilTester {
    public static void main(String[] args) {
       /*
        // Testing boundAngle(double deg)
        System.out.println("Tests: boundAngle(deg)");
        System.out.print("Test A: boundAngle(50)\n"+
                    "Expected: 50\n"+
                    "Got: "+Util.boundAngle(50)+"\n\n");

        System.out.print("Test B: boundAngle(360)\n"+
                "Expected: 0\n"+
                "Got: "+Util.boundAngle(360)+"\n\n");

        System.out.print("Test C: boundAngle(723)\n"+
                "Expected: 3\n"+
                "Got: "+Util.boundAngle(723)+"\n\n");

        System.out.print("Test D: boundAngle(0)\n"+
                "Expected: 0\n"+
                "Got: "+Util.boundAngle(0)+"\n\n");

        System.out.print("Test E: boundAngle(-50)\n"+
                "Expected: 310\n"+
                "Got: "+Util.boundAngle(-50)+"\n\n");

        System.out.print("Test F: boundAngle(-5000)\n"+
                "Expected: 40\n"+
                "Got: "+Util.boundAngle(-5000)+"\n\n");

        System.out.print("Test G: boundAngle(-360)\n"+
                "Expected: 0\n"+
                "Got: "+Util.boundAngle(-360)+"\n\n");
       */

        //Testing compareAngles(float deg1, float deg2)
        Scanner scan = new Scanner(System.in);
        String userInput = "";
        while(!userInput.equalsIgnoreCase("done")){
            float deg1 = scan.nextFloat();
            float deg2 = scan.nextFloat();
            Util.compareAngles(deg1, deg2);
        }
    }

}
