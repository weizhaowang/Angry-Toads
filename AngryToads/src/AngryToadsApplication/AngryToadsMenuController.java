package AngryToadsApplication;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AngryToadsMenuController extends MouseMotionAdapter implements Runnable, MouseListener {
	private final int PREF_WIDTH;
	private final int PREF_HEIGHT;
	private final AngryToadsMenu myMenu;
	Thread menuThread;
	boolean stop = true;
	int k = 0;

	AngryToadsMenuController(AngryToadsMenu menu) {
		PREF_HEIGHT = menu.getHeight();
		PREF_WIDTH = menu.getWidth();
		menu.addController(this);
		myMenu = menu;
	}

	public void start() {
		menuThread = new Thread(this);
		stop = false;
		menuThread.start();
	}

	public void resume() {
		stop = false;
	}

	@Override
	synchronized public void run() {
		while (true) {
			try {
				if (!myMenu.shut || !stop) // keeping painting menu when menu
											// painting operation is not set to false
					if (myMenu.render())
						myMenu.paintscence();
				Thread.sleep(5);
			} catch (InterruptedException e) {
				System.out.println("InterruptedException!");
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		myMenu.fingerPoint.set(e.getX(), e.getY());

		if (e.getX() < PREF_WIDTH / 2 + 156 && e.getX() > PREF_WIDTH / 2 - 156 && e.getY() > PREF_HEIGHT / 2 - 100
				&& e.getY() < PREF_HEIGHT / 2 + 100) {

			if (!myMenu.inside) {
				myMenu.buttonscale = 1;
				myMenu.tx = 164;
				myMenu.ty = 209 / 2;
				myMenu.inside = true;
			}
		} else {
			myMenu.inside = false;
			myMenu.buttonscale = 0;
			myMenu.tx = 150;
			myMenu.ty = 191 / 2;
		}
		if (e.getX() < 85 && e.getX() > 0 && e.getY() > PREF_HEIGHT - 85 && e.getY() < PREF_HEIGHT) {

			if (!myMenu.inside1) {
				myMenu.buttonscale1 = 1;
				myMenu.ty1 = 87 / 2;
				myMenu.inside1 = true;
			}
		} else {
			myMenu.inside1 = false;
			myMenu.buttonscale1 = 0;
			myMenu.ty1 = 75 / 2;
		}
	}

	public boolean isPainting() {
		return myMenu.isPainting();
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		return;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (myMenu.inside) {
			myMenu.shut = true;
			stop = true;
		}

		if (myMenu.inside1) {
			System.exit(0);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		return;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		return;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		return;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		return;
	}

}
