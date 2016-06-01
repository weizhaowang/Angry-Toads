/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AngryToadsApplication;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.media.CannotRealizeException;
//import javax.media.NoPlayerException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import AngryToadsLevel.ToadsLevel;


public class AngryToadsMain {

    /**
     * Entrance of This Game.
     */
    public static void main(String[] args) throws InterruptedException, IOException{// NoPlayerException, CannotRealizeException {

            
            ToadsLevel ts=new ToadsLevel();
          
            AngryToadsMenu menu=new AngryToadsMenu();
            AngryToadsPanel game=new AngryToadsPanel();

            AngryToadsViewFrame mainframe=new AngryToadsViewFrame(menu,game);

            AngryToadsController tc=new AngryToadsController(ts,game);
            AngryToadsMenuController mc=new AngryToadsMenuController(menu);
            AngryToadsPanelController pc=new AngryToadsPanelController(mainframe.getLayoutpanel(),mc,tc);        

            ts.getWorld().setContactListener(tc);

            Thread switcher=new Thread(pc);
            
            AngryToadsMusic music = new AngryToadsMusic("music/title_theme.wav");
            music.setLoop(true);
            music.start();
            
            switcher.start();
            mainframe.setVisible(true);
        
    }
}
