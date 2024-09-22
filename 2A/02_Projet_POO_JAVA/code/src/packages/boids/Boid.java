package packages.boids;
import java.awt.Point;
import java.util.ArrayList;

import java.util.Random;


public class Boid
{

    // -- pour génere des points aleatoire :
    protected Random random = new Random();



    /*----------------------------------- les attributs : -------------------------------- */

    /* la positions de boid : */    
    protected Vecteur position = new Vecteur(random.nextInt(600) , random.nextInt(800))   ;
    

    /* la vitesse de boid : */
    protected Vecteur vitesse = new Vecteur(random.nextInt(800) , random.nextInt(800)); 
 
    /* l'accéleration de boids : */
    protected Vecteur accéleration = new Vecteur(0,0) ;


    /* la norma de la vitesse  maximale */
    protected static  double normeVitesseMax = 30;

    /* la distance de boid : pour connaitre les voisinages de ce boid  */
    protected static double distance = 50 ;

    /* la seuille maximale entre deux boid : */
    protected static double seuil = 40 ;






    

    /*-----------------------------------  les acsesseur ------------------------------------ */

    /*  getPosition : */
    public Vecteur getPosition()
    {
        return this.position;
    }

    /* la méthode getVitesse  */
    public Vecteur getVitesse()
    {
        return this.vitesse;
    }

    /* la méthode getAccéleration : */
    public Vecteur getAccéleration()
    {
        return this.accéleration;
    }




    /*-------------------------------  les constructeures : ----------------------------------- */

    public Boid()
    {
        this.vitesse.setNorme((double)random.nextInt(5));
    }

    public Boid(Point p , Point v)
    {

        this.position = new Vecteur((int)p.getX(), (int)p.getY());
        this.vitesse = new Vecteur((int)v.getX(), (int)v.getY());

    }

    public Boid(Boid b)
    {
        this.position = new Vecteur(b.getPosition());
        this.vitesse = new Vecteur(b.getVitesse());
        this.accéleration = new Vecteur(b.getAccéleration());

    }





    /* --------------- la méthode calculDistance : ----------------------- */



    public static double calculDistance(Point p1 , Point p2)
    {
        double x = Math.pow(p2.getX() - p1.getX(), 2);
        double y = Math.pow(p2.getY() - p1.getY(), 2);

        double d = Math.sqrt(x+y);

        return d ;
    }






    /* la méthode update pour modéliser le mouvement du boid : update position , vitesse ,accéleration   */

    public void upadet(double L , double l , ArrayList<Boid> boids)
    {


        // mis à jour de l'accéleration du boid :

        Vecteur v1 = this.Alignement(boids);
        Vecteur v2 = this.Cohésion(boids);
        Vecteur v3 = this.Séparation(boids);

        this.accéleration.add(v1);
        this.accéleration.add(v2);
        this.accéleration.add(v3);



        
        
        // mis à jour de la vitesse de boid : 

        this.vitesse.add(this.accéleration);
        this.vitesse.limite(Boid.normeVitesseMax);



        // mis à jour de la postion du boid : 
        this.position.add(this.vitesse);



        // les efets de bord : 
        this.bord(L, l);


        this.accéleration.setPoint(0, 0);



        

    }




    /* méthode pour les effets de bors : */

    public void bord( double L, double l)
    {
        double x = this.position.getPoint().getX();
        double y = this.position.getPoint().getY() ;


        if(x > L)
        {
            x = 10 ;     
        }
        else if( x < 0 )
        {
            x = L ;
        }


        if( y > l)
        {
            y = 10 ;     
        }
        else if(y < 0 )
        {
            y = l ;
        }    
    
        this.position.setPoint((int)x, (int)y);


    
    }


    /*----------------------------------- les 3 principes de system de boids :  -------------------- */


    /*-------------------------------- Cohésion un agent se dirige vers la position moyenne (centre de masse) des ses voisins :------------- */


    public Vecteur Cohésion(ArrayList<Boid> boids)
    {
        Vecteur v = new Vecteur((int)this.getPosition().getPoint().getX(),(int)this.getPosition().getPoint().getY());

        int total = 1 ;

        for(Boid boid : boids)
        {
            double dis = calculDistance(this.position.getPoint() , boid.getPosition().getPoint());


            if( !boid.equals(this) && dis < Boid.distance)
            {
                v.add(boid.getPosition());
                total++;
            }
        }

        if(total > 1)
        {

            double c = (double)1/total;

            v.mult(c);

            v.sub(this.position);

            v.setNorme(Boid.normeVitesseMax);


            v.sub(this.vitesse);

        }
        else if(total==1)
        {
            // donc ce boid n'a pas des voisins :
            v.setNorme(Boid.normeVitesseMax);
            v.sub(this.vitesse);

        }


        return v;
    }


    /* ------------------------------ Alignement un agent tend à se déplacer dans la même direction que ses voisins : ------------- */


    public Vecteur Alignement(ArrayList<Boid> boids)
    {
        Vecteur v = new Vecteur(0,0);

        int total = 1 ;

        for(Boid boid : boids)
        {
            double dis = calculDistance(this.position.getPoint() , boid.getPosition().getPoint());

            if( !boid.equals(this) && dis < Boid.distance)
            {
                v.add(boid.getVitesse());
                total++;
            }
        }

        if(total > 1)
        {

            double c = (double)1/total;

            v.mult(c);

            v.setNorme(Boid.normeVitesseMax);


            // v.sub(this.vitesse);

        }
        else if(total==1)
        {
            // donc ce boid n'a pas des voisins :
            v.setNorme(Boid.normeVitesseMax);
            // v.sub(this.vitesse);

        }

        return v;
    }




    /* ---------------------- Séparation les agents trop proches se repoussent, pour éviter les collisions : ------------------- */

    public Vecteur Séparation(ArrayList<Boid> boids)
    {
        
        Vecteur v = new Vecteur(0,0);

        int total = 1 ;

        for(Boid boid : boids)
        {
            double dis = calculDistance(this.position.getPoint() , boid.getPosition().getPoint());


            if( !boid.equals(this) && dis < Boid.seuil)
            {
                Vecteur diff =  new Vecteur((int)this.position.getPoint().getX() , (int)this.position.getPoint().getY());
                diff.sub(boid.getPosition());
                
                double invDis = (double)1/dis;

                diff.mult(invDis);
                
                v.add(diff);
                total++;
            }
        }

        if(total > 0)
        {

            double c = (double)1/total;

            v.mult(c);

            v.setNorme(Boid.normeVitesseMax);


            // v.sub(this.vitesse);


        }



        return v;
    }




    @Override
    public boolean equals(Object objet)
    {
        if(objet instanceof Boid)
        {
            Boid boid = (Boid)objet;
            
            return this.position.getPoint().equals(boid.getPosition().getPoint());   
        }

        return false ;
    }


    @Override
    public String toString()
    {

        return this.position.toString() ;
    }
}
