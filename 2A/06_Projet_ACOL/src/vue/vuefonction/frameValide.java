package vue.vuefonction;


import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class frameValide extends frame{

    JLabel label ;

    public frameValide(String title) {
        super(title);
        label = new JLabel();
        label.setIcon(new ImageIcon("images/valide.png"));

        this.add(label);

        this.setVisible(true);


    }
    
    
}
