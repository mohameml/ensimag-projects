import packages.Jeu_vie.*;
import gui.GUISimulator ;
import java.awt.Color ;
import java.awt.Point;
import java.util.HashSet;




public class TestJeuConWay {

    public static void main (String[] args ) 
    {
 
        int L = 600 ;
        int l = 600 ;
        HashSet<Point> initlivepoints = new HashSet<>();

        // initialiser les points
        // initlivepoints.add(new Point(250,250));
        // initlivepoints.add(new Point(250,300));
        // initlivepoints.add(new Point(300,250));
        // initlivepoints.add(new Point(350,300));
        // initlivepoints.add(new Point(450,400));
        GUISimulator gui = new GUISimulator (L , l , Color.white ) ;
        gui.resizePanel(600,600);
        // int hauteur = gui.getPanelHeight();
        // int larg = 600;
        // initlivepoints.add(new Point(larg-100,0));
        // initlivepoints.add(new Point(larg-50,0));
        // initlivepoints.add(new Point(larg-100,50));
        // initlivepoints.add(new Point(0,50));
        // initlivepoints.add(new Point(50,200));

        //GUISimulator gui = new GUISimulator (L , l , Color.white ) ;

        initlivepoints.add(new Point(200,250));
        initlivepoints.add(new Point(250,250));
        initlivepoints.add(new Point(300,250));
        initlivepoints.add(new Point(150,250));
        initlivepoints.add(new Point(350,250));
        

        new JeuConWay(gui, initlivepoints);
    }

}
