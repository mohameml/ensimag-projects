package packages.boids;

import java.awt.Point;

/* 
    class qui modélise un vecteur 2D : 
*/

public class Vecteur 
{


    /* position : */
    private Point position ;

    /* norme de la vecteur : */
    private double norme ;

    /* sa direction : */
    private double direction ;


    /*------------------------- les acsesseures : ------------------------------ */

    public Point getPoint()
    {
        return this.position;   
    }

    public void setPoint(int x , int y)
    {
        this.position.setLocation(x, y);   
    }

    public double getNorme()
    {
        return Math.sqrt(Math.pow(this.position.getX(), 2)+Math.pow(this.position.getY(), 2));
    }

    public void setNorme(double c)
    {
        // chnager la norme du vecteur sans changer sa direction :
        double d = this.getNorme();
        double rapport = (double)c/d ;

        this.position.setLocation(rapport*this.position.getX() , rapport*this.position.getY());

    }


    /*------------------------ constructeures ------------------------ */

    public Vecteur(int x , int y)
    {
        this.position = new Point(x , y);
        this.norme = Math.sqrt(Math.pow(x,2) + Math.pow(y, 2));
        this.direction = Math.acos(x/this.norme);
    }

    public Vecteur(Vecteur v)
    {
        this.position = new Point(v.position);
        this.norme = v.norme;
        this.direction = v.direction;
    }


    /*-------------------- méthodes rédefinies --------------------- */

    @Override
    public String toString()
    {
        String str = "Vecteur(x = " + this.position.getX()+ 
            ", y = " + this.position.getY() +") ";   
        
        // ", norme =  " + this.getNorme() 
    
    
        
        return str ;
    } 

    /*------------------- les méthodes : ------------------- */

    public void add(Vecteur v)
    {
        this.position.setLocation(this.position.getX()+v.getPoint().getX(), this.position.getY()+v.getPoint().getY()); 

    }

    public void sub(Vecteur v)
    {

        this.position.setLocation(this.position.getX() - v.getPoint().getX(), this.position.getY() - v.getPoint().getY());
    }

    public void mult(double c)
    {
        this.position.setLocation(this.position.getX()*c, this.position.getY()*c);
    }

    public void limite(double seuilMax)
    {
        // assurer que la norma ne depasse pas certain limite :
        double x =  this.position.getX();
        double y = this.position.getY();


        double normeVceteur2 = x*x + y*y ;
        
        if(normeVceteur2 > seuilMax*seuilMax)
        {
            // rédimonsionnemnt de x et y :
            double normeVec = Math.sqrt(normeVceteur2);
            this.position.setLocation( (x/normeVec)*seuilMax, (y/normeVec)*seuilMax);

        }
        
    }
    


    
}
