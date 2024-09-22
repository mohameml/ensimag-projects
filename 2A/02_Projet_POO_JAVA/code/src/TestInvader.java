import java.awt.Color;

import gui.GUISimulator;

import packages.invader.Invader; 

public class TestInvader {
    public static void main(String[] args) 
    {

        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);

        // crée l'invader, en l'associant à la fenêtre graphique précédente
        new Invader(gui, Color.decode("#f2ff28"));
    }
}


