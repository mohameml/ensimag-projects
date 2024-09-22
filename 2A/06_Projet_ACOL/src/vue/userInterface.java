package vue;
/**
 * 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class userInterface extends JFrame implements ActionListener {

    JPanel TextPanel ;
    JLabel text;
    JButton commancer ;

    public userInterface(String title) {
        super(title);

        /*----------------- */

        String Text = "Bienvenue Dans le  jeu de simulation boursiére \n Dans ce jeu on vous propose des différents comptes pour simuler différents scenarios";
        text = new JLabel(Text);

        

        TextPanel = new JPanel();
        TextPanel.add(text);


        this.setSize(600 , 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);



        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}