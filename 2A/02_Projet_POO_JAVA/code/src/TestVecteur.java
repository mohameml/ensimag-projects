import packages.boids.Vecteur;


public class TestVecteur {
    

    public static void main(String[] args) {
        
        Vecteur v = new Vecteur(3, 4);


        // affichage duu vecteur :
        System.out.println(v);


        Vecteur v1 = new Vecteur(100, 0);
        System.out.println(v1);
        v.add(v1);
        System.out.println(v);

        v.sub(v1);
        System.out.println(v);

        v.mult(2);
        System.out.println(v);


        // test de setNorme 
        v.setNorme(7);
        System.out.println(v);

        // Test de la méthode limite :
        v.limite(5);
        System.out.println(v);

    }
}
