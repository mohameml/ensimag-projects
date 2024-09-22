import java.awt.Point;


import packages.balles.Balles;

public class TestBalles {
    

    public static void main(String[] args) {
        

        // Test de la classe Balles :

        Balles balles = new Balles();


        balles.addBalle(new Point(0,0));
        balles.addBalle(new Point(10,10));
        balles.addBalle(new Point(5,5));
        balles.addBalle(new Point(20,20));
        balles.addBalle(new Point(100,200));

        System.out.println(balles);
        System.out.println(balles.getBallesInit().toString());



        // Test de translation et reInit() 

        balles.translate(10, 10);
        System.out.println(balles);





        balles.translate(20, 20);
        System.out.println(balles);

        balles.translate(10, 10);
        System.out.println(balles);


        balles.reInit();
        System.out.println(balles);

        System.out.println(balles.getBallesInit().toString());


        balles.translate(20, 20);
        System.out.println(balles);

        System.out.println(balles.getBallesInit().toString());


        balles.translate(10, 10);
        System.out.println(balles);


        balles.reInit();
        System.out.println(balles);

    }
}
