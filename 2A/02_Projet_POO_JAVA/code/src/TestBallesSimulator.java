import gui.GUISimulator ;
import java.awt.Color ;
import packages.balles.*;

public class TestBallesSimulator 
{
    public static void main (String[] args ) 
    {
 
        int L = 500 ;
        int l = 500 ;

        GUISimulator gui = new GUISimulator (L , l , Color.BLACK ) ;
        


        new BallesSimulator(gui);
    }


}
