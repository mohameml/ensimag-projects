
package vue.vuefonction;

import java.awt.event.ActionEvent;


import controle.*;
import model.Action;
import model.Marche;


public class vendre extends acheter {
    

    public vendre() {
        super("vendre");
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


                Boolean bool = Controle.validerVent(nb , action );

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

}