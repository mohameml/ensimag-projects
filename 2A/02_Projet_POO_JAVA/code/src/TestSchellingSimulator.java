import gui.GUISimulator;
import java.awt.Color;
import packages.schelling.SchellingSimulator;

public class TestSchellingSimulator {
    public static void main(String[] args) {
        int windowWidth = 500; // Largeur de la fenêtre
        int windowHeight = 500; // Hauteur de la fenêtre
        int thresholdK = 2;
        double vacantPercentage = 0.4; // 40% de cellules vacantes
        int spacing = 2; // Définir l'espacement entre les cellules

        // Calculez le nombre de lignes et de colonnes
        int cellSize = 10; // Taille initiale des cellules
        int lines = (windowWidth - spacing) / (cellSize + spacing); // Ajout d'espacement autour de la grille
        int columns = (windowHeight - spacing) / (cellSize + spacing); // Ajout d'espacement autour de la grille
        int numberOfColors = 3;

        // Ajustez la taille des cellules si nécessaire
        cellSize = Math.min((windowWidth - spacing) / columns, (windowHeight - spacing) / lines);

        GUISimulator gui = new GUISimulator(windowWidth, windowHeight, Color.WHITE);
        SchellingSimulator schellingSimulator = new SchellingSimulator(gui, lines, columns, numberOfColors, thresholdK, vacantPercentage);
    }
}