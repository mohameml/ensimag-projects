package packages.schelling;

import java.awt.Color;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

public class SchellingSimulator implements Simulable {
    private GUISimulator gui;
    private Grid grid;
    private int thresholdK;
    private int spacing;
    private int windowWidth;
    private int windowHeight;


    public SchellingSimulator(GUISimulator gui, int lines, int columns, int numberOfColors, int thresholdK, double vacantPercentage) {
        this.gui = gui;
        this.grid = new Grid(lines, columns, numberOfColors, vacantPercentage);
        this.thresholdK = thresholdK;
        this.spacing = 2;
        this.windowWidth = gui.getWidth(); // Obtenez la largeur de la fenêtre de l'interface utilisateur
        this.windowHeight = gui.getHeight(); // Obtenez la hauteur de la fenêtre de l'interface utilisateur
        this.gui.setSimulable(this);

        updateGui();
    }

    private void updateGui() {
        gui.reset();

        int totalSpacingWidth = (grid.getColumns() + 1) * spacing;
        int totalSpacingHeight = (grid.getLines() + 1) * spacing;
        int cellWidth = (windowWidth - totalSpacingWidth) / grid.getColumns();
        int cellHeight = (windowHeight - totalSpacingHeight) / grid.getLines();
        int cellSize = Math.min(cellWidth, cellHeight);

        for (int i = 0; i < grid.getLines(); i++) {
            for (int j = 0; j < grid.getColumns(); j++) {
                Cell cell = grid.getCell(i, j);
                if (cell != null) {
                    Color color = cell.getColor(); // Assurez-vous que cell.getColor() est disponible
                    int x = j * (cellSize + spacing) + spacing;
                    int y = i * (cellSize + spacing) + spacing;
                    // Dessiner la cellule sur le simulateur
                    gui.addGraphicalElement(new Rectangle(x + spacing / 2, y + spacing / 2, color, color, cellSize));
                }
            }
        }
    }

    @Override
    public void next() {
        // logic to advance the simulation of one step
        grid.updateGrid(thresholdK);
        updateGui();
    }

    @Override
    public void restart() {
        // logic to restart the simulation
        grid.resetToInitialState();
        updateGui();
    }
}
