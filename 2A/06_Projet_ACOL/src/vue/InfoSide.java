package vue ;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoSide extends JPanel {
    

    JLabel labelName ;
    JLabel labelSolde ;
    
    public InfoSide(int solde ,  String name ) {

        this.setLayout(new FlowLayout(FlowLayout.CENTER , 20 , 0));
        
        this.labelName = new JLabel("Name :"+name);
        this.labelSolde = new JLabel("Solde :"+solde+"€");

        this.add(labelName);
        this.add(labelSolde);



    }


    public JLabel getLabelName() {
        return this.labelName;

    }


    public JLabel getLableSolde() {
        return this.labelSolde;
    }


}
