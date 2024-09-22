package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jfree.data.category.DefaultCategoryDataset;

public class DataBase {
    

    private static final String url = "jdbc:sqlite:./database/database.db";

    public DataBase() {

    }
    
    


    public static  DefaultCategoryDataset getDataOfSimulation(String nom) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            Connection conncetion = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = conncetion.prepareStatement("SELECT * FROM Actions WHERE nom = ?");


            preparedStatement.setString(1, nom);
            
            ResultSet resultat= preparedStatement.executeQuery();
        
            while (resultat.next()) {
    
                int prix = resultat.getInt("Prix");
                String date = resultat.getString("date");
                dataset.addValue(prix, "", date);
                
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;

    }
}
