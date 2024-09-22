package packages.Jeu_vie;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;




public class JeuConWay implements Simulable{
    

    protected GUISimulator gui;
    private HashSet<Point> initlivepoints = new HashSet<>();
    private HashSet<Point> save_points = new HashSet<>();

    


    public JeuConWay(GUISimulator gui)
    {
        this.gui =gui;
        gui.setSimulable(this);
    }


    public JeuConWay(GUISimulator gui , HashSet<Point> initlivepoints)
    {
        this.gui=gui;
        gui.setSimulable(this);
        
        
        this.initlivepoints = initlivepoints ;
        for(Point p:initlivepoints)
        {
            this.save_points.add(new Point(p));
        }
        
        gui.reset();
        this.Init_defauts();
        
        
    }



    protected void Init_defauts()
    {
        int hauteur = gui.getPanelHeight();
        int larg = gui.getPanelWidth();
        

        for(int i=0; i<larg; i+=50)
        {
            for(int j=0; j<hauteur; j+=50)
            {
                Point p = new Point(i,j);
                //KillPoint(p);
                ColorPoint(p,Color.white, Color.BLACK) ;
            }
        }
        Iterator<Point> point_savIterator = initlivepoints.iterator();

        while(point_savIterator.hasNext())
        {
            //SavePoint(point_savIterator.next());
            ColorPoint(point_savIterator.next(), Color.BLACK ,Color.white) ;
        }

        this.save_points.clear();
        for(Point p:initlivepoints)
        {
            
            this.save_points.add(new Point(p));
        }

    }




    // private void SavePoint(Point p)
    // {
    //     this.gui.addGraphicalElement(new Rectangle((int)p.getX(), (int)p.getY(),Color.white, Color.black, 50));
    // }

    
    // private void KillPoint(Point p)
    // {
    //     this.gui.addGraphicalElement(new Rectangle((int)p.getX(), (int)p.getY(),Color.black, Color.white, 50));
    // }


    protected void ColorPoint(Point p,Color frontColor, Color inColor)
    {
        this.gui.addGraphicalElement(new Rectangle((int)p.getX(), (int)p.getY(),inColor, frontColor, 50));
    }

    
    protected ArrayList<Point> entourage(Point p)
    {
        Point hg = new Point((int)p.getX()-50,(int)p.getY()-50); // haut gauche k-1,m-1
        Point g = new Point((int)p.getX()-50,(int)p.getY());   // gauche k-1,m
        Point bg = new Point((int)p.getX()-50,(int)p.getY()+50);   // bas gauche k-1,m+1
        Point b = new Point((int)p.getX(),(int)p.getY()+50);    //bas k,m+1
        Point bd = new Point((int)p.getX()+50,(int)p.getY()+50);   // bas droite k+1,m+1
        Point d = new Point((int)p.getX()+50,(int)p.getY());    // droite k+1,m
        Point hd = new Point((int)p.getX()+50,(int)p.getY()-50);   // haut droite k+1,m-1
        Point h = new Point((int)p.getX(),(int)p.getY()-50);    // haut k,m-1
        int hauteur = gui.getPanelHeight();
        int larg = gui.getPanelWidth();

        //extrem droite
        if(p.getX()+50 == larg)
        {   
            d=new Point(0,(int)p.getY()); // à droite
            if(p.getY() ==0) // haut droite
            {
                hd = new Point(0,gui.getHeight()-50);
                bd = new Point(0,(int)p.getY()+50);
            }else if(p.getY() == larg-50) //bas droite
            {
                hd = new Point(0,(int) p.getY()-50);
                bd = new Point(0,0);
            }else{  //mileu droite
                hd = new Point(0,(int) p.getY()-50);
                bd = new Point(0,(int)p.getY()+50);
            }
        
        }else if(p.getX() ==0) //extrem gauche
        {
            g = new Point(gui.getWidth()-50,(int)p.getY());

            if(p.getY()==0) // haut gauche
            {   
                hg = new Point(gui.getWidth()-50,gui.getHeight()-50);
                bg = new Point(gui.getWidth()-50,(int)p.getY()+50);

            }else if(p.getY() == gui.getHeight()-50) //bas gauche
            {
                hg = new Point(gui.getWidth()-50,(int)p.getY()-50);
                bg = new Point(gui.getWidth()-50,0);
            }else{          // mileu gauche
                hg = new Point(gui.getWidth()-50,(int)p.getY()-50);
                bg = new Point(gui.getWidth()-50,(int)p.getY()+50);
            }
        }else   // milieu
        {   
            if(p.getY()==0) //milieu haut 
            {
                h = new Point((int)p.getX(),hauteur-50);
            }else if (p.getY() == hauteur-50)    // bas mileu
            {   
                b = new Point((int)p.getX(),0);
            }
        }





        ArrayList<Point> entourage_points = new ArrayList<>(8);
        entourage_points.add(hd); 
        entourage_points.add(d);
        entourage_points.add(bd);
        entourage_points.add(b);
        entourage_points.add(bg);
        entourage_points.add(g);
        entourage_points.add(hg);
        entourage_points.add(h);
      

        return entourage_points;
    }


    private HashSet<Point> PointRevivre(HashSet<Point> points)
    {
        HashSet<Point> pointsRevivre = new HashSet<>();
        Iterator<Point> pIterator = points.iterator();
        
        while(pIterator.hasNext())
        {
            Point pIteratorNext = new Point(pIterator.next());
            ArrayList<Point> EntouragepIterrator = entourage(pIteratorNext);
            Iterator<Point> EntourItrator = EntouragepIterrator.iterator();
            int Count = 0;

            while(EntourItrator.hasNext())
            {
                if(save_points.contains(EntourItrator.next()))
                {
                    Count++;
                }
            }
            if(Count == 3)
            {
                pointsRevivre.add(pIteratorNext);
            }


        }
        return pointsRevivre;
    }

    @Override
    public void next()
    {
        // les points à tuer
        HashSet<Point> points_à_tués = new HashSet<>();
        // les points à revivre
        
        HashSet<Point> points_possible_revivre = new HashSet<>();

        // tuer ou sauver ou créer un point vivant
        for(Point p:this.save_points)
        {   
            // lister les points qui entourent p
            ArrayList<Point> entourage_points = entourage(p);

            // sauver ou tuer le point

            int countàTuer =0;
            //int CountARvivre =0;

            Iterator<Point> point_entouragIterator = entourage_points.iterator();
            while(point_entouragIterator.hasNext())
            {
                Point pointimp = new Point(point_entouragIterator.next());
                if(save_points.contains(pointimp))
                    {   
                        countàTuer ++;
                    }
                else
                    {   
                        points_possible_revivre.add(pointimp);
                    }

            }
            if(countàTuer <2 || countàTuer >3 )
            {
                points_à_tués.add(p);
            }
        }
            
        
        Iterator<Point> point_àtués_Iterator = points_à_tués.iterator();
        HashSet<Point> points_revivre = PointRevivre(points_possible_revivre);
        Iterator<Point> point_revivre_Iterator = points_revivre.iterator();

        while(point_àtués_Iterator.hasNext() ) 
        {
            Point point_sup = point_àtués_Iterator.next();
            //KillPoint(point_sup);
            ColorPoint(point_sup,Color.white, Color.BLACK) ;
            this.save_points.remove(point_sup);
            //SavePoint(point_revivre_Iterator.next());

        }

        // revivre des points
        
        while(point_revivre_Iterator.hasNext())
        {
            Point revivPoint = point_revivre_Iterator.next();
            //SavePoint(revivPoint);
            ColorPoint(revivPoint, Color.BLACK, Color.white) ;
            save_points.add(revivPoint);

        }

            

    }


        

    

    @Override
    public void restart()
    {
        this.Init_defauts();

    }


}
