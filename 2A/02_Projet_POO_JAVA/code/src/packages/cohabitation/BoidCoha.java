package packages.cohabitation;
import packages.boids.Boid;


import java.awt.Color;
import java.util.ArrayList;

public class BoidCoha extends Boid
{
    /* rayon */
    private double rayon ;

    /* couleur */
    private Color col;



    ArrayList<Color> listColors = new ArrayList<>();


    public BoidCoha()
    {
        super();

        this.rayon = super.random.nextDouble(8 , 15);
        this.col = Color.WHITE; 

    }




    @Override
    public void upadet(double L , double l , ArrayList<Boid> boids)
    {
        super.upadet(L, l, boids);
        setRayon(null);

    }







    public void  setRayon(ArrayList<Boid> boids)
    {

        for(Boid boid_ : boids)
        {   
            BoidCoha boid = (BoidCoha)boid_;

            double dis = super.calculDistance(this.position.getPoint() , boid.getPosition().getPoint());

            if( !this.col.equals(boid.col) && dis < Boid.distance)
            {
                if(this.rayon > boid.rayon)
                {
                    boid.rayon = 0 ;
                    this.rayon += boid.rayon;
                }
            }
        }


    }


}
