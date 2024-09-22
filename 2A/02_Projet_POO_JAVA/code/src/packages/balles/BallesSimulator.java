package packages.balles;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;


public class BallesSimulator implements Simulable 
{

    /** L'interface graphique associée */
    private GUISimulator gui;

    /* des balles pour la simulation :  */
    private Balles balles ;


    /* un acsesseur pour l'attribut gui  */
    public GUISimulator geSimulator()
    {
        return this.gui;
    }





    /* constructeur : */
    public BallesSimulator(GUISimulator gui )
    {
        /* simulation par défaut : */
        this.gui=gui;
        gui.setSimulable(this);
        
        this.balles = new Balles();
        this.balles.addBalle(new Point(50,50));
        this.balles.addBalle(new Point(100,120));
        this.balles.addBalle(new Point(300,40));
        this.balles.addBalle(new Point(150,150));
        this.balles.addBalle(new Point(400,60));
        this.balles.addBalle(new Point(30,240));        
        
        draw();
    }

    /* surcharge du constructeur : */
    public BallesSimulator(GUISimulator gui , ArrayList<Point> balles)
    {
        this.gui=gui;
        gui.setSimulable(this);
        
        this.balles = new Balles(balles);
        draw();
    }


    /*la méthode next pour le boutton : Suivant */
    @Override
    public void next()
    {

        int dx = 20 ;
        int dy = 20 ;

        int L = gui.getWidth();
        int l = gui.getHeight();
        int Rayon = 50;

        this.balles.translateRebond(dx , dy , L, l , Rayon);
        
        draw();
    }

    /* la méthode restart  pour le button : Début  */
    @Override
    public void restart()
    {
        this.balles.reInit();

        draw();
    }




    /* fonction d'affiche des balles */

    private void draw()
    {
        gui.reset(); // clear the window 

        for(Point point : balles.getBalles())
        {
            int Rayon = 50 ;
            // on affiche chaque balle 
            gui.addGraphicalElement(new Oval( (int)point.getX(), (int)point.getY(), Color.YELLOW, Color.BLACK, Rayon));

        }

    }
}
