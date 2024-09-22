package packages.Jeu_vie;

import java.util.HashSet;
import java.util.Collection;
import java.util.HashMap;


import java.util.Iterator;

import java.awt.Point;


import gui.GUISimulator;
import java.util.ArrayList;
import java.awt.Color;

public class JeuImmigration extends JeuConWay {

 
    private HashMap<ArrayList<Color>,HashSet<Point>> hash; // ArrayList<Color> : contient en indice=0,la couleur des frontiére du rectangle
    // et en indice 0 la couleur de l'intérieur
    private HashMap<ArrayList<Color>,HashSet<Point>> hashInit = new HashMap<>();

    private HashMap<ArrayList<Color>,HashSet<Point>> hashSec = new HashMap<>() ;

    ArrayList<ArrayList<Color>> colororder = new ArrayList<>();
    

    

    public JeuImmigration(GUISimulator gui , HashMap<ArrayList<Color>,HashSet<Point>> hash, ArrayList<ArrayList<Color>> colororder)
    {
        super(gui);
        this.hash =hash;
        this.colororder = colororder;
        
        // itérateur sur les clés de hash


        // remplir les cases non spécifiée en blanc
        int hauteur = gui.getPanelHeight();
        int larg = gui.getPanelWidth();

        HashSet<Point> vals = new HashSet<>();
        Collection<HashSet<Point>> allArrayVals = hash.values();
        Iterator<HashSet<Point>> allArrayValIterator = allArrayVals.iterator();
        while(allArrayValIterator.hasNext())
        {
        Iterator<Point> points = allArrayValIterator.next().iterator();
        while(points.hasNext())
        {
        vals.add(points.next());
        }
        }

        ArrayList<Color> white = new ArrayList<>();
        HashSet<Point> ValWhite = new HashSet<>();

        white.add(Color.white);
        white.add(Color.black);

        for(int i=0; i<larg; i+=50)
        {
        for(int j=0; j<hauteur; j+=50)
        {

        if(!vals.contains(new Point(i,j)))
        {
        ValWhite.add(new Point(i,j));
        }
        }
        }
        hash.put(white,ValWhite);
        // this.hash.putAll(hashh);
        





        
        Iterator<ArrayList<Color>> KeyIterator = hash.keySet().iterator();

        //remplir hashInit pour garder les valeurs hash initiale
        while (KeyIterator.hasNext()) {
        // je veais faire une copies indépendante des valeurs de has dans hasinit
        ArrayList<Color> key = new ArrayList<>();
        ArrayList<Color> hashkey = KeyIterator.next();


        // faire une copie indépendante de la clé
        int rgb0 = hashkey.get(0).getRGB();
        int rgb1 = hashkey.get(1).getRGB();
        key.add(0,new Color(rgb0));
        key.add(1,new Color(rgb1));

        // key.add(new Color(rgb0));
        // key.add(new Color(rgb1));

        //faire une copie indépendante de la valeur correspondante 
        HashSet<Point> valeurshasinit = new HashSet<>();
        HashSet<Point> hashvaleur = hash.get(hashkey);

        Iterator<Point> ValIterator =hashvaleur.iterator() ;
        while(ValIterator.hasNext())
        {
        Point pointval = new Point(ValIterator.next());
        valeurshasinit.add(pointval);
        }

        // remplir hasInit
        hashInit.put(key,valeurshasinit);
        }
        gui.reset();
        Init_defauts();

    }



    @Override
    protected void Init_defauts()
    {
    

        // itérateur sur les clés
        Iterator<ArrayList<Color>> KeyIterator = this.hashInit.keySet().iterator();

        //parcour des clés
        while(KeyIterator.hasNext())
        {
            ArrayList<Color> color = KeyIterator.next();
            //parcours les valeurs de la clé
            HashSet<Point> valeur = this.hashInit.get(color);
            Iterator<Point> ValIterator =valeur.iterator() ;
            while(ValIterator.hasNext())
            {
                // ColorPoint(ValIterator.next(),color.get(0),color.get(1));
                Color in =color.get(1);
                Color front =color.get(0);
                ColorPoint(ValIterator.next(),front,in);
            }

        }

        // remplir de nouveau hash par hash


        Iterator<ArrayList<Color>> KIterator = this.colororder.iterator();

        //remplir hashInit pour garder les valeurs hash initiale
        while (KIterator.hasNext()) {
        // je veais faire une copies indépendante des valeurs de has dans hasinit
        ArrayList<Color> key = new ArrayList<>();
        ArrayList<Color> hashkey = KIterator.next();


        // faire une copie indépendante de la clé
        int rgb0 = hashkey.get(0).getRGB();
        int rgb1 = hashkey.get(1).getRGB();
        key.add(0,new Color(rgb0));
        key.add(1,new Color(rgb1));

        // key.add(new Color(rgb0));
        // key.add(new Color(rgb1));

        //faire une copie indépendante de la valeur correspondante 
        HashSet<Point> valeurshasinit = new HashSet<>();
        HashSet<Point> hashvaleur = hashInit.get(hashkey);

        Iterator<Point> ValIterator =hashvaleur.iterator() ;
        while(ValIterator.hasNext())
        {
        Point pointval = new Point(ValIterator.next());
        valeurshasinit.add(pointval);
        }

        // remplir hasInit
        hash.put(key,valeurshasinit);
        }

        gui.reset();
        draw();
        
    }
    


    

    @Override
    public void next()
    {


        // remplir hashSecour 
        //Iterator<ArrayList<Color>> KeyIterator = hash.keySet().iterator();
        Iterator<ArrayList<Color>> KeyIterator = this.colororder.iterator();
        //remplir hashInit pour garder les valeurs hash initiale
        while (KeyIterator.hasNext()) 
        {
            // je veais faire une copies indépendante des valeurs de has dans hasinit
            ArrayList<Color> key = new ArrayList<>();
            ArrayList<Color> hashkey = KeyIterator.next();


            // faire une copie indépendante de la clé
            int rgb0 = hashkey.get(0).getRGB();
            int rgb1 = hashkey.get(1).getRGB();
            key.add(0,new Color(rgb0));
            key.add(1,new Color(rgb1));

            // key.add(new Color(rgb0));
            // key.add(new Color(rgb1));

            //faire une copie indépendante de la valeur correspondante 
            HashSet<Point> valeurshasinit = new HashSet<>();
            HashSet<Point> hashvaleur = hash.get(hashkey);

            Iterator<Point> ValIterator =hashvaleur.iterator() ;
            while(ValIterator.hasNext())
            {
                Point pointval = new Point(ValIterator.next());
                valeurshasinit.add(pointval);
            }

            // remplir hasInit
            hashSec.put(key,valeurshasinit);
        }





        // essaie
        // itérateur sur les clés
        
        ArrayList<ArrayList<Color>> K = this.colororder;
        int taille = K.size();

        for(int i =0;i<taille;i++)
        { 
            ArrayList<Color> color = K.get(i);
            HashSet<Point> valeur = this.hash.get(color);
            Iterator<Point> ValIterator =valeur.iterator() ;

            ArrayList<Color> colornext = new ArrayList<>();
            if(i==taille-1)
            {
                colornext = K.get(0);
            }else{
                colornext = K.get(i+1);
            }
            
            HashSet<Point> valeurNext = this.hash.get(colornext);
            HashSet<Point> valtodel = new HashSet<>();

            while(ValIterator.hasNext())
            {
                int count=0;
                // les voisins de ValIterator.next()
                Point Val = ValIterator.next();
                ArrayList<Point> entourage = entourage(Val);

                for(int j =0; j <8;j++)
                {
                    if(valeurNext.contains(entourage.get(j)))
                    {
                        count++;
                    }
                }





                //HashSet<Point> entourage = entourage(Val);
                // Iterator<Point> entouragIterator = entourage(Val).iterator();
                
                // while(entouragIterator.hasNext())
                // {
                //     if(valeurNext.contains(entouragIterator.next()))
                //     {
                //         count++;
                //     }
                // }
                
                if(count >2)
                {
                    // ajouter val à la nouvelle couleur
                    //valeurNext.add(Val);
                    // supprimer val de l'ancienne couleur
                    valtodel.add(Val);
                    // valeur.remove(Val);
                    // // mettre à jour hash
                    // this.hash.put(colornext,valeurNext);
                    // this.hash.put(color,valeur);
                }
                
            }
            for(Point p:valtodel)
            {
                valeur.remove(p);
                valeurNext.add(p);

            }
            this.hashSec.put(colornext,valeurNext);
            this.hashSec.put(color,valeur);



        }

        // recopier les valeurs de hasSec dans hash 
        //Iterator<ArrayList<Color>> KeyIterator = hash.keySet().iterator();
        Iterator<ArrayList<Color>> KIterator = this.colororder.iterator();
        //remplir hashInit pour garder les valeurs hash initiale
        while (KIterator.hasNext()) 
        {
            // je veais faire une copies indépendante des valeurs de has dans hasinit
            ArrayList<Color> key = new ArrayList<>();
            ArrayList<Color> hashkey = KIterator.next();


            // faire une copie indépendante de la clé
            int rgb0 = hashkey.get(0).getRGB();
            int rgb1 = hashkey.get(1).getRGB();
            key.add(0,new Color(rgb0));
            key.add(1,new Color(rgb1));

            // key.add(new Color(rgb0));
            // key.add(new Color(rgb1));

            //faire une copie indépendante de la valeur correspondante 
            HashSet<Point> valeurshasinit = new HashSet<>();
            HashSet<Point> hashvaleur = hashSec.get(hashkey);

            Iterator<Point> ValIterator =hashvaleur.iterator() ;
            while(ValIterator.hasNext())
            {
                Point pointval = new Point(ValIterator.next());
                valeurshasinit.add(pointval);
            }

            // remplir hasInit
            hash.put(key,valeurshasinit);
        }


        
        



        // déssiner la nouvelle 
        this.draw();

    
    }

    @Override
    public void restart()
    {
        this.Init_defauts();
    }


    public void draw()
    {
        // itérateur sur les clés
        gui.reset();
        //Iterator<ArrayList<Color>> KeyIteratorr = this.hash.keySet().iterator();
        Iterator<ArrayList<Color>> KeyIteratorr = this.colororder.iterator();
        //parcour des clés
        while(KeyIteratorr.hasNext())
        {
            ArrayList<Color> colorr = KeyIteratorr.next();
            //parcours les valeurs de la clé
            HashSet<Point> valeur = this.hash.get(colorr);
            Iterator<Point> ValIterator =valeur.iterator() ;
            while(ValIterator.hasNext())
            {
                // ColorPoint(ValIterator.next(),color.get(0),color.get(1));
                Color in =colorr.get(1);
                Color front =colorr.get(0);
                ColorPoint(ValIterator.next(),front,in);
            }

        }
 }
}
