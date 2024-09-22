import packages.Jeu_vie.*;
import java.awt.Point;
import java.awt.Color;
import gui.GUISimulator;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;


public class TestJeuImmigration {

    public static void main (String[] args ) 
    {
 

        int L = 800 ;
        int l = 800 ;

        HashMap<ArrayList<Color>,HashSet<Point>> hash = new HashMap<>();

        // les points noirs

        HashSet<Point> npoints = new HashSet<>();
        npoints.add(new Point(50,50));
        npoints.add(new Point(50,100));
        npoints.add(new Point(100,250));
        npoints.add(new Point(150,150));
        npoints.add(new Point(300,100));
        npoints.add(new Point(300,150));
        npoints.add(new Point(300,200));


        

        ArrayList<Color> nc = new ArrayList<>();
        
        
        
        nc.add(Color.black);
        nc.add(Color.white);
       
        hash.put(nc,npoints);


        


        // les points gris
        HashSet<Point> cpoints = new HashSet<>();
        cpoints.add(new Point(150,200));
        cpoints.add(new Point(150,250));
        cpoints.add(new Point(200,150));
        cpoints.add(new Point(200,200));
        cpoints.add(new Point(200,250));
        cpoints.add(new Point(250,100));
        cpoints.add(new Point(250,150));
        cpoints.add(new Point(250,200));

        
        cpoints.add(new Point(50,300));
        cpoints.add(new Point(100,300));
        cpoints.add(new Point(150,300));

        ArrayList<Color> cc = new ArrayList<>();
        
        cc.add(Color.gray);
        cc.add(Color.white);

        hash.put(cc,cpoints);


        //les points LIGHT_GRAY
        HashSet<Point> gpoints = new HashSet<>();
        gpoints.add(new Point(100,100));
        gpoints.add(new Point(100,150));
        gpoints.add(new Point(100,200));
        gpoints.add(new Point(150,50));
        gpoints.add(new Point(150,100));
        gpoints.add(new Point(200,50));
        gpoints.add(new Point(200,100));
        gpoints.add(new Point(250,250));
        gpoints.add(new Point(50,150));

        gpoints.add(new Point(0,0));
        gpoints.add(new Point(0,50));
        gpoints.add(new Point(0,100));

        gpoints.add(new Point(350,50));
        gpoints.add(new Point(350,100));
        gpoints.add(new Point(350,150));
        gpoints.add(new Point(350,200));



        ArrayList<Color> gc = new ArrayList<>();
        
        gc.add(Color.LIGHT_GRAY);
        gc.add(Color.white);

        hash.put(gc,gpoints);

        // couleur blanc
        ArrayList<Color> b = new ArrayList<>();
        b.add(Color.white);
        b.add(Color.black);

        ArrayList<ArrayList<Color>> colorsordre = new ArrayList<>();
        colorsordre.add(b);
        colorsordre.add(gc);
        colorsordre.add(cc);
        colorsordre.add(nc);    // ColorPoint(ValIterator.next(),color.get(0),color.get(1));
        
        
        




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

        // initlivepoints.add(new Point(200,250));
        // initlivepoints.add(new Point(250,250));
        // initlivepoints.add(new Point(300,250));
        

        new JeuImmigration(gui, hash,colorsordre);
    }
    
}
