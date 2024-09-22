package packages.balles;

import java.awt.Point;
import java.util.ArrayList;




public class Balles 
{
    

    /* les postions courantes de la balles : */
    private ArrayList<Point> balles = new ArrayList<Point>();

    /* les positions intiales de la balles  */
    private ArrayList<Point> ballesPositionInit = new ArrayList<>();




    

    /* accseur de l'attribut balles */
    public ArrayList<Point> getBalles()
    {
        return this.balles;
    }

    /* accseur de l'attribut ballesPositionInit */
    public ArrayList<Point> getBallesInit()
    {
        return this.ballesPositionInit;
    }

    /* un constructuer  */
    public Balles()
    {

    }

    /* surcharge du constructeur  */
    public Balles(ArrayList<Point> balles)
    {
        for(Point point : balles)
        {
            Point point_nv = new Point(point);
            this.balles.add(point_nv);
            
            this.ballesPositionInit.add(new Point(point));

        }

    }






    /* méthode pour ajouter des balles  */
    public void addBalle(Point point)
    {
        Point point_nv = new Point(point);

        this.balles.add(point_nv);
        this.ballesPositionInit.add(new Point(point));

    }




    /* méthodes pour translater toutes les balles : */
    public void translate(int dx, int dy)
    {

        for(Point point : this.balles)
        {
            point.translate(dx, dy);
        }

    
    }

    /* sur charge de laméthode translate : */
    public void translateRebond(int dx , int dy , int L , int l , int Rayon)
    {
        for(Point point : this.balles)
        {
            int x = (int)point.getX();
            int y = (int)point.getY();


            int diff_x = x+dx + Rayon- L ;
            int diff_y = y+dy+Rayon-l;
            
            int dx_nv , dy_nv ;




            if(diff_x > 0 && diff_y > 0)
            {
                dx_nv =  diff_x - 100 ;
                dy_nv = diff_y - 100 ;



            }
            else if(diff_x > 0 && diff_y < 0)
            {
                dx_nv = diff_x - 100 ;
                dy_nv = dy ;


            }
            else if(diff_x < 0 && diff_y > 0 )
            {
                dx_nv = dx ;
                dy_nv = diff_y -100 ;

            }
            else 
            {
                dx_nv = dx ;
                dy_nv = dy;


            }

            point.translate(dx_nv, dy_nv);

        }
    }



    /* remet toutes les balles à leur position initiale ; */
    public void reInit()
    {

        for(int i =0 ; i<this.balles.size() ; i++)
        {
            this.balles.set(i, new Point(this.ballesPositionInit.get(i)));
        }
        
    }



    /* pour l'affiche d'une point  */
    public static String stringBalle(Point point)
    {
        String chaine = "Point(x="+point.getX()+",y="+point.getY()+")";
        chaine+="\n";
        return chaine;

    }


     
    /*  retourne une chaine de caractères avec les positions de toutes les balles.*/
    @Override
    public String toString()
    {
        String chaine = "------------ Balles ------------- \n" ;

        for(Point point : this.balles)
        {
            chaine+=stringBalle(point);
        }
    
        return chaine;
    }





}
