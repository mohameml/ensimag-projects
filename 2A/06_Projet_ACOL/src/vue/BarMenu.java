package vue ;


import vue.vuefonction.acheter;

import vue.vuefonction.simuler;
import vue.vuefonction.vendre;
import vue.vuefonction.vueChoixSimulation;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import javax.swing.JMenu;
import javax.swing.JMenuBar;


import java.awt.event.*;


public class BarMenu extends JMenuBar implements MouseListener  {
    
    JMenu acheterMenu;
    JMenu vendreMenu;
    JMenu consulterMenu ;
    JMenu simulerMenu ;
    JMenu updateMeun;

    ArrayList<JMenu> listIJMenu = new ArrayList<>();



    ImageIcon acheterIcon ;
    ImageIcon vendreIcon;
    ImageIcon consulterIcon;
    ImageIcon simulerIcon; 
    ImageIcon updateIcon;

    ArrayList<ImageIcon> listIcon = new ArrayList<>();

    private dashbordUser dash ; 
    
    public BarMenu(dashbordUser dash) {		

        // 
        this.dash = dash;
        // this.setSize(100,100);
        this.setLayout(new FlowLayout(FlowLayout.CENTER , 50 , 10));
        
        acheterMenu = new JMenu();
        vendreMenu = new JMenu();
        consulterMenu = new JMenu();
        simulerMenu = new JMenu();
        updateMeun = new JMenu();

        listIJMenu.add(acheterMenu);
        listIJMenu.add(vendreMenu);
        listIJMenu.add(consulterMenu);
        listIJMenu.add(simulerMenu);
        listIJMenu.add(updateMeun);





        acheterIcon  = new ImageIcon("images/acheter.png");
        vendreIcon = new ImageIcon("images/sell.png");
        consulterIcon = new ImageIcon("images/zoom.png");
        simulerIcon= new ImageIcon("images/simuler.png") ; 
        updateIcon = new ImageIcon("images/update.png");

        listIcon.add(acheterIcon);
        listIcon.add(vendreIcon);
        listIcon.add(consulterIcon);
        listIcon.add(simulerIcon);
        listIcon.add(updateIcon);


        acheterMenu.setIcon(acheterIcon);
        vendreMenu.setIcon(vendreIcon);
        simulerMenu.setIcon(simulerIcon);
        consulterMenu.setIcon(consulterIcon);
        updateMeun.setIcon(updateIcon);


        


        
        
        
        
        for (JMenu menu : listIJMenu) {
            this.add(menu);
            menu.addMouseListener(this);
        }
        

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // Traitement à effectuer lors du clic de souris
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Traitement à effectuer lors de l'appui sur le bouton de la souris
        
        //JFrame frame = Util.findParentFrame(this);

        if(e.getSource() == acheterMenu) {

            new acheter("achéter");
        }
        else if(e.getSource() == vendreMenu) {
            new vendre();
        }
        else if(e.getSource() == consulterMenu ) {

        }
        else if(e.getSource() == simulerMenu) {
            new vueChoixSimulation();

        }
        else if(e.getSource() == updateMeun) {
            // pour le mise à jour :

            dash.upadteSolde();
        }

    
    
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Traitement à effectuer lors du relâchement du bouton de la souris
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Traitement à effectuer lorsque la souris entre dans la zone du composant
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Traitement à effectuer lorsque la souris quitte la zone du composant
    }



    
}
