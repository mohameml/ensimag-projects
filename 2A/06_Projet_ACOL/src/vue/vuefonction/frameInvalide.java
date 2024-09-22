package vue.vuefonction;




import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class frameInvalide extends frame{


    
    JLabel label ;
    
    public frameInvalide(String title) {
        super(title);

        label = new JLabel();
        label.setIcon(new ImageIcon("images/invalide.png"));

    
        this.add(label);
    
        this.setVisible(true);

    }

        
    
    
}
