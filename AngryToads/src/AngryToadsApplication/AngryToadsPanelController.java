package AngryToadsApplication;

import java.awt.CardLayout;
import java.awt.LayoutManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AngryToadsPanelController extends ComponentAdapter implements Runnable {
	AngryToadsMenuController menuController;
	AngryToadsController controller;
	JPanel showPanel;
	CardLayout layout;
	AngryToadsMusic music;

	int mainthread = 0;

	AngryToadsPanelController(JPanel show, AngryToadsMenuController mc, AngryToadsController ct) {
		menuController = mc;
		controller = ct;
		showPanel = show;
		layout = (CardLayout) show.getLayout();
		music = new AngryToadsMusic();
		music.setLoop(true);
	}

	@Override
	synchronized public void run() {
		whoShow();
		while (true) {
			whoShow();
			switch (mainthread) {

				case 0:
					displayMenu();
					break;
				case 1:
					displayGame();
					break;

			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(AngryToadsPanelController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	synchronized public void whoShow() {

		if (menuController.menuThread == null && controller.gamethread == null) {
			mainthread = 0;
		}

		if (menuController.menuThread != null)
			if (menuController.stop && menuController.menuThread.isAlive() && controller.gamethread == null) {
				mainthread = 1;
			}

		if (menuController.menuThread != null && controller.gamethread != null) {
			if (!menuController.menuThread.isAlive() && !controller.gamethread.isAlive()) {
				mainthread = 0;
			}
			if (menuController.menuThread.isAlive() && controller.gamethread.isAlive()) {
				mainthread = 1;
			}
		}
	}

	public void displayMenu() {
		music.setFile("music/title_theme.wav");
		music.start();

		if (menuController.menuThread == null) {
			menuController.start();
			layout.show(showPanel, "menu");
		} else if (!menuController.isPainting()) {
			menuController.resume();
			layout.show(showPanel, "menu");
		}
	}

	public void displayGame() {
		music.setFile("music/ambient_red_savannah.wav");
		music.start();

		if (controller.gamethread == null) {
			controller.start();
			layout.show(showPanel, "game");
		} else {
			controller.resume();
			layout.show(showPanel, "game");
		}
	}
}
