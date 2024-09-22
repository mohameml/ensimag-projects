package vue.util;

import javax.swing.*;
import java.awt.*;

public class Util {
    
    public static JFrame findParentFrame(Component component) {
        while (component != null) {
            if (component instanceof JFrame) {
                return (JFrame) component;
            }
            component = component.getParent();
        }
        return null;
    }

    public static Object[][] getData() {

        
        Object[][] data = {
            {1 , "Appel" , 200 , 3.2} ,
            {2 , "Google" , 180 , 4} ,
            {3 , "Amazon"  , 120 , 10} , 
            {4 , "Appel" , 200 , 3.2} ,
            {5, "Google" , 180 , 4} ,
            {6, "Amazon"  , 120 , 10} ,
            {7, "Appel" , 200 , 3.2} ,
            {8, "Google" , 180 , 4} ,
            {9, "Amazon"  , 120 , 10} ,
            {10, "Appel" , 200 , 3.2} ,
            {11, "Google" , 180 , 4} ,
            {12 , "Amazon"  , 120 , 10} ,
            {13 , "Appel" , 200 , 3.2} ,
            {14 , "Google" , 180 , 4} ,
            {15 , "Amazon"  , 120 , 10} ,
            {16 , "Appel" , 200 , 3.2} ,
            {17 , "Google" , 180 , 4} ,
            {18 , "Amazon"  , 120 , 10} , 
            {19 , "Appel" , 200 , 3.2} ,
            {20 , "Google" , 180 , 4} ,
            {21, "Amazon"  , 120 , 10} ,
            {22, "Appel" , 200 , 3.2} ,
            {23 , "Google" , 180 , 4} ,
            {24 , "Amazon"  , 120 , 10}

        };

        return data ; 
    
    }


    public static String[] getColnames() {
        String[] names = {"ID" , "Action" , "Prix" , "Dividende"};

        return names ;
    }
}
