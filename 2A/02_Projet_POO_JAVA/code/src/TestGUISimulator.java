
import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;

import java.awt.Color;

public class TestGUISimulator {
    
    public static void main(String[] args) {
        


        // pour tester GUISimulator :*

        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);

        gui.addGraphicalElement(new Rectangle(200 ,100, Color.BLUE, Color.green, 100));
        gui.addGraphicalElement(new Oval(300 ,400, Color.BLUE, Color.red, 100));
        gui.addGraphicalElement(new Oval(300 ,400, Color.BLUE, Color.red, 100 , 200));





    }
}
