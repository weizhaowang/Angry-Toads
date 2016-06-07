package AngryToadsApplication;

import java.io.IOException;

import AngryToadsLevel.ToadsLevel;


public class AngryToadsMain {

    /**
     * Entrance of This Game.
     */
    public static void main(String[] args) throws InterruptedException, IOException{// NoPlayerException, CannotRealizeException {


        AngryToadsArea ts=new ToadsLevel(0).createLevel();

        AngryToadsMenu menu=new AngryToadsMenu();
        AngryToadsPanel game=new AngryToadsPanel();

        AngryToadsViewFrame mainframe=new AngryToadsViewFrame(menu,game);

        AngryToadsController tc=new AngryToadsController(ts,game);
        AngryToadsMenuController mc=new AngryToadsMenuController(menu);
        AngryToadsPanelController pc=new AngryToadsPanelController(mainframe.getLayoutpanel(),mc,tc);

        ts.getWorld().setContactListener(tc);
        
        Thread switcher=new Thread(pc);

        switcher.start();
        mainframe.setVisible(true);
        //tc.restart();
    }
}
