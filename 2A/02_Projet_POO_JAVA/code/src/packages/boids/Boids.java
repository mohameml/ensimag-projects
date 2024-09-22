package packages.boids;
import java.util.ArrayList;



public class Boids 
{
    /* list des Boids */    
    private ArrayList<Boid> boids = new ArrayList<>();

    /* les postions initales de bois : */
    private ArrayList<Boid> boidsPositionInit = new ArrayList<>();


    /* acsesseur pour boids : */
    public ArrayList<Boid> getBoids()
    {
        return this.boids;
    }



    /* un constructuer : */

    public Boids()
    {

    }




    /* méthode addBaoid */

    public void addBoid(Boid b)
    {
        this.boids.add(new Boid(b));
        this.boidsPositionInit.add(new Boid(b));

    }



    /* la fonction reInit : */
    public void reInit()
    {
        for(int i =0 ; i<this.boids.size(); i++)
        {
            this.boids.set(i , new Boid(this.boidsPositionInit.get(i)));
        }
    }


}
