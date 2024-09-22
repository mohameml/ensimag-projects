package vue;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class Table extends JPanel {
    
    private Object[][] data ;
    private String[] colnames ;
    private JScrollPane scrollPane;
    private DefaultTableModel model ;
    private JTable table ;
    private JLabel label ;

    public Table() {
        
        this.data = null;
        this.colnames = getColnames();
        this.model = new DefaultTableModel(this.data , this.colnames);

        this.table  = new JTable(this.model);
        
        this.scrollPane = new JScrollPane(this);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.GRAY);

        this.label = new JLabel("Table des Transactions");
        label.setHorizontalAlignment(JLabel.CENTER);

        this.add(label , BorderLayout.NORTH);
        this.add(table , BorderLayout.CENTER);


        

    }

    public JScrollPane getScroll() {
        return this.scrollPane;
    }


    public JTable getTable() {
        return this.table;
    }

    public void  setTable(DefaultTableModel model ) {
        this.table.setModel(model);
    }


    public DefaultTableModel getModel() {
        return this.model;
    }


    public  String[] getColnames() {
        String[] names = {"Nom" , "quantité" , "Prix" , "PrixTotale"};
        return names ;
    }




    // public  Object[][] getData() {


        
    //     Object[][] data = {
    //         {1 , "Appel" , 200 , 3.2} ,
    //         {2 , "Google" , 180 , 4} ,
    //         {3 , "Amazon"  , 120 , 10} , 
    //         {4 , "Appel" , 200 , 3.2} ,
    //         {5, "Google" , 180 , 4} ,
    //         {6, "Amazon"  , 120 , 10} ,
    //         {7, "Appel" , 200 , 3.2} ,
    //         {8, "Google" , 180 , 4} ,
    //         {9, "Amazon"  , 120 , 10} ,
    //         {10, "Appel" , 200 , 3.2} ,
    //         {11, "Google" , 180 , 4} ,
    //         {12 , "Amazon"  , 120 , 10} ,
    //         {13 , "Appel" , 200 , 3.2} ,
    //         {14 , "Google" , 180 , 4} ,
    //         {15 , "Amazon"  , 120 , 10} ,
    //         {16 , "Appel" , 200 , 3.2} ,
    //         {17 , "Google" , 180 , 4} ,
    //         {18 , "Amazon"  , 120 , 10} , 
    //         {19 , "Appel" , 200 , 3.2} ,
    //         {20 , "Google" , 180 , 4} ,
    //         {21, "Amazon"  , 120 , 10} ,
    //         {22, "Appel" , 200 , 3.2} ,
    //         {23 , "Google" , 180 , 4} ,
    //         {24 , "Amazon"  , 120 , 10}

    //     };

    //     return data ; 
    
    // }







}
