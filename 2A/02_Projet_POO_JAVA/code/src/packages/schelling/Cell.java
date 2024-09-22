package packages.schelling;

import java.awt.Color;
import java.util.Random;

public class Cell {
    private Color color;

    public Cell() {
        this.color = generateRandomColor();
    }

    public Cell(Color color) {
        this.color = color;
    }

    private static Color generateRandomColor() {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isVacant() {
        return color.equals(Color.WHITE);
    }

    @Override
    public String toString() {
        // Updated to reflect the use of Color object
        return "Cell of color " + color.toString();
    }
}