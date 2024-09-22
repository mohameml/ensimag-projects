package packages.schelling;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.util.List;


public class Grid {
    private int lines;
    private int columns;
    private Cell[][] cells;
    private ArrayList<Point> vacantCells;
    private Random random;
    private Cell[][] initialCells;
    private Color[] availableColors; // Tableau de couleurs disponibles
    private List<Color> selectedColors; // Liste de couleurs sélectionnées
    private int numberOfColors;

    public Grid(int lines, int columns,int numberOfColors, double vacantPercentage) {
        this.lines = lines;
        this.columns = columns;
        cells = new Cell[lines][columns];
        vacantCells = new ArrayList<>();
        random = new Random();
        this.numberOfColors = numberOfColors;
        availableColors = new Color[numberOfColors];
        for (int i = 0; i < numberOfColors; i++) {
            int red = random.nextInt(256); // Valeur aléatoire pour le composant rouge
            int green = random.nextInt(256); // Valeur aléatoire pour le composant vert
            int blue = random.nextInt(256); // Valeur aléatoire pour le composant bleu
            availableColors[i] = new Color(red, green, blue);
        }
        initializeCells(vacantPercentage);
        saveInitialState();
    }

    private void initializeCells(double vacantPercentage) {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                if (random.nextDouble() < vacantPercentage) {
                    cells[i][j] = new Cell(Color.WHITE);
                    vacantCells.add(new Point(i, j));
                } else {
                    // Sélectionnez une couleur aléatoire parmi les couleurs disponibles
                    int randomColorIndex = random.nextInt(availableColors.length);
                    cells[i][j] = new Cell(availableColors[randomColorIndex]);
                }
            }
        }
    }

    private void saveInitialState() {
        initialCells = new Cell[lines][columns];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                // Copiez correctement la cellule depuis cells
                Cell currentCell = cells[i][j];
                Color currentColor = currentCell != null ? currentCell.getColor() : Color.WHITE;
                initialCells[i][j] = new Cell(currentColor);
            }
        }
    }

    public void resetToInitialState() {
        vacantCells.clear(); // Videz la liste des cellules vacantes
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j].setColor(initialCells[i][j].getColor());
                if (cells[i][j].isVacant()) {
                    vacantCells.add(new Point(i, j)); // Ajoutez les cellules vacantes
                }
            }
        }
    }

    public void setCell(int line, int column, Color color) {
        cells[line][column].setColor(color);
        if (color.equals(Color.WHITE)) {
            vacantCells.add(new Point(line, column));
        }
    }

    public Cell getCell(int line, int column) {
        return cells[line][column];
    }

    public int getLines(){
        return this.lines;
    }

    public int getColumns(){
        return this.columns;
    }

    public void updateGrid(int thresholdK) {
        ArrayList<Point> cellsToMove = new ArrayList<>();

        // we identify all cells(families) that need to move
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                if (!cells[i][j].isVacant() && needToMove(i, j, thresholdK)) {
                    cellsToMove.add(new Point(i, j));
                }
            }
        }

        // we move them
        for (Point cellCoords : cellsToMove) {
            moveFamily(cellCoords);
        }
    }

    private void moveFamily(Point cellCoords) {
        if (vacantCells.isEmpty()) {
            // Pas de cellule libre, la famille ne peut pas bouger
            return;
        }

        int originalX = cellCoords.x;
        int originalY = cellCoords.y;
        Color familyColor = cells[originalX][originalY].getColor();

        // Trouver une nouvelle cellule libre (nouveau domicile)
        int index = random.nextInt(vacantCells.size());
        Point newHome = vacantCells.get(index);

        // Déplacer la famille
        cells[newHome.x][newHome.y].setColor(familyColor);

        // La cellule d'origine devient vacante
        cells[originalX][originalY].setColor(Color.WHITE);

        // Mettre à jour la liste des cellules vacantes
        vacantCells.set(index, new Point(originalX, originalY));
    }


    // verifies if family at position (x, y) needs to move
    public boolean needToMove(int x, int y, int thresholdK) {
        int differentNeighborsCount = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // we verify if the neighbor cell is in limits of the grid
                if (x + i >= 0 && x + i < lines && y + j >= 0 && y + j < columns) {
                    // we ignore the cell we're dealing with
                    if (i == 0 && j == 0) continue;

                    Cell neighbor = cells[x + i][y + j];
                    Cell currentCell = cells[x][y];

                    // we increment our count by 1 if this neighbor is not vacant and has different color than our concerned cell
                    if (!neighbor.isVacant() && neighbor.getColor() != currentCell.getColor()) {
                        differentNeighborsCount++;
                    }
                }
            }
        }

        //returns true if the number of homes with different color is more that the threshold K
        return differentNeighborsCount >= thresholdK;
    }
}