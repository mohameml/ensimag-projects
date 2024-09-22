package vue.vuefonction;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import util.DataBase;

public class simuler extends frame {
    
    /*--------- data de simualtion --------- */
    private DefaultCategoryDataset dataSet ;
    String choix ;
    public simuler (String choix ) {
        
        super("simuler");
        this.choix = choix ;

        this.dataSet = dataOfSimulation();

        
        /*-------------- graphique avce JFreeChart ---------------------  */
        JFreeChart chart = ChartFactory.createLineChart("Simulation", "Date","Prix de l'Action", this.dataSet);

        /*---------------- convertir vers chartPanel pour ajouter le graphe à JFrame -------------- */
        ChartPanel chartPanel = new ChartPanel(chart);
        
        /*----------------------------------- Ajouter le panneau de graphique au JFrame ------------------------------*/
        setContentPane(chartPanel);
        
        this.setVisible(true);
    }


    public DefaultCategoryDataset dataOfSimulation() {

        // Créer un ensemble de données pour le graphique
        // DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // dataset.addValue(120, "", "01/01");
        // dataset.addValue(130, "", "02/01");
        // dataset.addValue(150, "", "03/01");
        // dataset.addValue(125, "", "04/01");
        // dataset.addValue(110, "", "05/01");
        // dataset.addValue(115, "", "06/01");
        // dataset.addValue(140, "", "07/01");
        // dataset.addValue(100, "", "08/01");
        // dataset.addValue(130, "", "09/01");
        // dataset.addValue(110, "", "10/01");
        // dataset.addValue(130, "", "11/01");
        // dataset.addValue(200, "", "12/01");
        // dataset.addValue(220, "", "13/01");
        // dataset.addValue(230, "", "14/01");
        // dataset.addValue(190, "", "15/01");
        // dataset.addValue(200, "", "16/01");
        // dataset.addValue(180, "", "17/01");
        // dataset.addValue(140, "", "18/01");
    
        DefaultCategoryDataset data = DataBase.getDataOfSimulation(this.choix);

        return data; 

    }
}