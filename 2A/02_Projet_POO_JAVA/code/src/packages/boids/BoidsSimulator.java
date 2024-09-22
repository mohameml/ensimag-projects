package packages.boids;
import java.awt.Point;

import java.awt.Color;

import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

public class BoidsSimulator implements Simulable 
{
    /* boids pour la simulation : */
    private Boids boids = new Boids();
    
    /* une interface graphique pour la simulation : */
    private GUISimulator gui ;



    /* un constructuer: */
    public BoidsSimulator(GUISimulator gui )
    {
        // simulation par défaut :
        this.gui = gui ;
        this.gui.setSimulable(this);

        // ajouter des boids :
        for(int i =0 ; i< 100 ; i++)
        {
            this.boids.addBoid(new Boid());

        }
        
        draw();

    }

    /* surcharge du constructuer : */
    public BoidsSimulator(GUISimulator gui , Boids boids )
    {
        this.gui = gui ;
        this.boids = boids ;
    
        this.gui.setSimulable(this);
        draw();
    }   




    @Override 
    public void next()
    {   
        double L = gui.getPanelHeight();
        int l = gui.getPanelWidth();



        gui.reset();

        for(Boid boid : this.boids.getBoids())
        {
            
            boid.upadet(L ,l , this.boids.getBoids());
            
            // on dessine :
            gui.addGraphicalElement
            (
            new Oval((int) boid.getPosition().getPoint().getX(),(int) boid.getPosition().getPoint().getY() , 
            Color.WHITE, Color.WHITE, 8)
            );

            
        }

    }

    @Override
    public void restart()
    {
        this.boids.reInit();
        draw();
    }


    public void draw()
    {
        gui.reset(); // supprimer tout 
        // pour lr dession du systeme de boids :
        for(Boid boid : boids.getBoids())
        {
            Point point = boid.getPosition().getPoint();

            gui.addGraphicalElement(new Oval( (int)point.getX(), (int)point.getY(), Color.WHITE, Color.WHITE, 8));


        }
    }
    
}
