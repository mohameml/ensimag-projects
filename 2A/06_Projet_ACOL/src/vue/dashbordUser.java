package vue;


import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.User;


public class dashbordUser extends JFrame {

    private User user ;
    private BarMenu Menu  ;
    private InfoSide info ;
    private Table table ; 
    



    public dashbordUser(User user) {

        /*------------- titre du page ----------------- */
        super("Compte");
        this.user = user;
        /*----------- Layout manager : BorderLayout ----------------------- */
        this.setLayout(new BorderLayout(20 , 0));
        
        /*------------------ les eléments du DashBord -------------------- */
        table = new Table();
        Menu  = new BarMenu(this);
        info = new InfoSide(this.user.getSolde() , this.user.getNom());

        
        
        
        
        /*---------------------------- Ajouter les élements à JFrame ----------------------- */

        this.add(this.table , BorderLayout.CENTER);
        this.add(this.info , BorderLayout.NORTH);
        this.add( new JPanel(), BorderLayout.EAST);
        this.add(new JPanel() , BorderLayout.WEST);
        this.add(new JPanel() , BorderLayout.SOUTH);
        this.setJMenuBar(this.Menu);
        

        /*---------------------- Configuration du JFrame ------------------- */
        this.setSize(600 , 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }


    public void upadteSolde() {

        // mise à jour du solde :
        this.info.getLableSolde().setText("Solde :"+user.getSolde()+"€");

        this.info.repaint();

        // mise à jour du Table de transactions :
        this.table.getModel().setRowCount(0);
        Object[][] actions = user.getActions();

        for (Object[] row : actions) {
            
            this.table.getModel().addRow(row);
        }

        this.table.repaint();
    }
    
    


}
