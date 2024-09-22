package vue.vuefonction;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class vueChoixSimulation extends frame implements ActionListener {

    /*------------ choix de l'action pour la simulation -------------- */ 
    JPanel panel2 ;
    JComboBox<String> choixComboBox;
    JLabel action ;

    /*---------------- button valider ----------------- */
    JPanel panel3 ;
    JButton valider ;

    /*----------- le choix -------------- */
    String choix ; 
    

    public vueChoixSimulation() {
        super("choix de Simulation ");
    
        /*------------- JPanel : choix de l'action ------------------ */
        panel2 = new JPanel();

        action = new JLabel();
        action.setText("Action De :");

        String[] vals = this.getVals();
        choixComboBox = new JComboBox<>(vals);
        choixComboBox.setPreferredSize(new Dimension(250 , 40));
        choixComboBox.addActionListener(this);

        panel2.add(action);
        panel2.add(choixComboBox);
    
    
        /*------------------------- Panel 3 : ------------------------------ */
        panel3 = new JPanel();

        valider = new JButton();
        valider.setText("Valider");
        valider.setFocusable(false);
        valider.addActionListener(this);
        valider.setPreferredSize(new Dimension(250 , 50));

        panel3.add(valider);

        

        /*---------------- Add JPanle to JFrame ------------------ */
        this.add(panel2);
        this.add(panel3);


        // this.pack();
        this.setSize(390 , 200);
        this.setLayout(new GridLayout(2, 1 , 20 , 20));
        this.setVisible(true);
        
        this.setVisible(true);
    }


    private String[] getVals() {
        String[] vals  = new String[40] ;

        for (int i = 0; i < 40; i++) {
            vals[i] = "Action"+(i+1);
        }

        return vals ;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == valider) {

            this.dispose();
            this.choix = choixComboBox.getSelectedItem().toString();
            new simuler(this.choix);
        }
        
    }
}
