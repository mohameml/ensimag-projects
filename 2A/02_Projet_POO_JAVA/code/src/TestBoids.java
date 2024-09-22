import java.awt.Color;

import packages.boids.BoidsSimulator;
import gui.GUISimulator;


public class TestBoids {

    public static void main(String[] args) {
        
        GUISimulator gui = new GUISimulator(600, 800, Color.BLACK);

    
        new BoidsSimulator(gui);



    }
    
}
