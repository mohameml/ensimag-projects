package vue.vuefonction;

import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controle.*;
import model.Action;
import model.Marche;

public class acheter extends frame implements ActionListener {
    
    
    // 1 : JPanel
    JPanel panel1 ;
    JTextField nbFiled ; 
    JLabel quantite ;
    
    
    // 2 : 
    JPanel panel2 ;
    JComboBox<String> choixComboBox;
    JLabel action ;

    // 3: 
    JPanel panel3 ;
    JButton valider ;

    int nb;
    String choix ; 


    public acheter(String title) {
        super(title);

        /*--------------------- Panel 1 : ----------------------- */
        panel1 = new JPanel();

        quantite = new JLabel();
        quantite.setText("nombre :");

        nbFiled = new JTextField();
        nbFiled.setPreferredSize(new Dimension(250 , 40));
        

        panel1.add(quantite);
        panel1.add(nbFiled);


        /*----------------------------- Panel 2 : --------------------------------------- */

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

        

        this.add(panel1);
        this.add(panel2);
        this.add(panel3);


        // this.pack();
        this.setSize(350 , 390);
        this.setLayout(new GridLayout(3, 1 , 20 , 20));
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()== valider)
        {
            String text = nbFiled.getText();

            try {
                nb = Integer.parseInt(text);

                Action action = Marche.getAction(choix);
                if(action == null) {
                    System.err.println("===================> Erreur : choix d'action invalide");
                    System.exit(1);
                }


                Boolean bool = Controle.validerAchat(nb , action );

                if(bool) {
                    this.dispose();
                    new frameValide("Valide");
                }
                else {
                    this.dispose();;
                    new frameInvalide("Invalide");
                }

            } catch (Exception exception) {

                System.err.println("==================> Erreur :  nombre des actions  Invalide ===========================");
                System.exit(1);
            }

            
        }

        if(e.getSource() == choixComboBox ) {
            this.choix = choixComboBox.getSelectedItem().toString();
            System.out.println(choixComboBox.getSelectedItem());
        }


    }

    public String[] getVals() {
        String[] vals  = new String[40] ;

        for (int i = 0; i < 40; i++) {
            vals[i] = "Action"+(i+1);
        }

        return vals ;
    }

}

