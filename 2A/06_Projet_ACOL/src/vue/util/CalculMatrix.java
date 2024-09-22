package vue.util;

import Jama.Matrix;

public class CalculMatrix {
    public static void main(String[] args) {
        double[][] data = {{1, 2}, {3, 4}};
        Matrix matrix = new Matrix(data);

        try {
            Matrix inverse = matrix.inverse();
            System.out.println("Inverse of the matrix:");
            inverse.print(5, 2); // Affiche la matrice avec une précision de 5 chiffres après la virgule
        } catch (RuntimeException e) {
            System.out.println("Matrix is singular, cannot compute inverse.");
        }
    }

    // l'inverse d'une matrice
    public static Matrix InvMatr()
}
