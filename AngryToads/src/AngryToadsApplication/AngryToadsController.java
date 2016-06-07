/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AngryToadsApplication;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import AngryToadsCharacters.AngryToadsBodyInfo;
import AngryToadsLevel.ToadsLevel;

public class AngryToadsController extends MouseAdapter implements Runnable, MouseMotionListener, ContactListener {

    private AngryToadsArea m_stage;
    public AngryToadsPanel m_view;
    private final AngryToadsDraw drawer;
    // private final MusicController music;
    Thread gamethread;
    boolean stop = true;
    private boolean hasEnd = false;

    AngryToadsController(AngryToadsArea m, AngryToadsPanel v){//, MusicController mc) {
        this.m_stage = m;
        m_view = v;
        drawer = v.getSDDraw();
        v.setStageController(this);
        m.initStage();
        //music = mc;
        drawer.setStage(m);
        this.addListener();

    }

    @Override
    public void run() {
        while (true) {
            try {
                while (!stop) {
                    m_stage.update();
                    Body nowbird = m_stage.birdList.get(m_stage.nowbullet);
                    m_stage.distance += nowbird.getLinearVelocity().length();//根据一定距离画飞行轨迹
                    if(m_stage.distance/150 > m_stage.track.size()) {
                        m_stage.track.add(new Vec2(nowbird.getPosition()));
                    }
                    drawer.drawStage(m_stage.nowbullet,m_stage.track);
                    Thread.sleep(5);
                }
                drawer.drawStage(m_stage.nowbullet,m_stage.track);
                Thread.sleep(12);
            } catch (InterruptedException ex) {
                Logger.getLogger(AngryToadsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void start() {
        if (gamethread == null) {
            stop = false;
            gamethread = new Thread(this);
            gamethread.start();
        }
    }

    public void restart(boolean nextLevel) {
        int temp=this.m_stage.getLevelNum();
        if (nextLevel) {
            temp++;
        }
        this.m_stage=null;
        this.m_stage=new ToadsLevel(temp).createLevel();
        this.m_stage.initStage();
        this.m_stage.getWorld().setContactListener(this);
        drawer.setStage(this.m_stage);
        hasEnd = false;
    }

    public void backToMenu () {
        // TODO: 返回主菜单
    }


    public void resume() {
        if (!isPainting()) {
            stop = false;
        }
    }

    public boolean isPainting() {
        return m_view.isPainting();
    }

    /*
     * synchronized public void setRunthread(boolean runthread) {
     * if(this.stop!=runthread){ this.stop = runthread; }
     *
     *
     * }
     *
     */
    public void addListener() {
        m_view.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                if (m_stage != null) {
                    Vec2 pos = new Vec2(e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        drawer.getScreenToWorldToOut(pos, pos);
                        m_stage.queueMouseDown(pos);

                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (m_stage != null) {
                    Vec2 pos = new Vec2(e.getX(), e.getY());
                    drawer.getScreenToWorldToOut(pos, pos);
                    m_stage.queueMouseUp(pos);
                }
            }
        });

        m_view.addMouseMotionListener(new MouseMotionListener() {

            final Vec2 pos = new Vec2();
            final Vec2 pos2 = new Vec2();

            public void mouseDragged(MouseEvent e) {
                pos.set(e.getX(), e.getY());


                if (m_stage != null) {
                    drawer.getScreenToWorldToOut(pos, pos);
                    m_stage.queueMouseMove(pos);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                pos2.set(e.getX(), e.getY());
                if (m_stage != null) {
                    drawer.getScreenToWorldToOut(pos2, pos2);
                    m_stage.queueMouseMove(pos2);
                }
            }
        });
    }


    @Override
    public synchronized void beginContact(Contact contact) {

        //   drawer.drawContact();
        //   System.out.print("listener thread !!!\n");
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    //    Fixture fix ;
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    	/*
    	 * may use:
    	 * ((AngryToadsBodyInfo)(body.m_userData)).function();
    	 * contact.getManifold().pointCount;
    	 * impulse.normalImpulses[];
    	 * m_stage.getWorld().distroyBody();
    	 * body.setActive(true/false);
    	 */

        boolean bodyADead=false;
        boolean bodyBDead=false;
        Body bodyA = contact.m_fixtureA.getBody();
        Body bodyB = contact.m_fixtureB.getBody();
        int TypeA = ((AngryToadsBodyInfo)(bodyA.m_userData)).getTypeNum();
        int TypeB = ((AngryToadsBodyInfo)(bodyB.m_userData)).getTypeNum();
        ArrayList<Body> toadList = m_stage.getToads();
        ArrayList<Body> obsList=m_stage.getObstacles();
        ArrayList<Body> birdList=m_stage.getBirds();
        for (int i = 0; i < contact.getManifold().pointCount; i++) {
            if(impulse.normalImpulses[i]>6f){
                if(birdList.contains(bodyA)||birdList.contains(bodyB))
                    new AngryToadsMusic("sfx/bird 01 collision a4.wav").start();
                if(!bodyADead&&(toadList.contains(bodyA)||obsList.contains(bodyA)))
                    bodyADead=((AngryToadsBodyInfo)(bodyA.m_userData)).figureHealth(impulse.normalImpulses[i]);
                if(!bodyBDead&&(toadList.contains(bodyB)||obsList.contains(bodyB)))
                    bodyBDead=((AngryToadsBodyInfo)(bodyB.m_userData)).figureHealth(impulse.normalImpulses[i]);
            }
        }


        if(bodyADead || bodyBDead) {
            boolean hasPlayed=false;
            int type = bodyADead ? TypeA : TypeB;
            Body body = bodyADead ? bodyA : bodyB;
            switch(type) {
                //wood
                case 0:
                    new AngryToadsMusic("sfx/wood destroyed a1.wav").start();
                    hasPlayed=true;
                    //stone
                case 1:
                    if(!hasPlayed){
                        new AngryToadsMusic("sfx/rock damage a1.wav").start();
                        hasPlayed=true;
                    }
                    //ice
                case 2:
                    if(!hasPlayed)
                        new AngryToadsMusic("sfx/ice light collision a8.wav").start();
                    if(obsList.contains(body))
                        obsList.remove(body);
                    break;
                //toad
                case 10:
                    new AngryToadsMusic("sfx/piglette destroyed.wav").start();
                    if(toadList.contains(body))
                        toadList.remove(body);
                    break;
            }
            body.setActive(false);
            m_stage.getWorld().destroyBody(body);
        }

        if (toadList.size() == 0) {
            gameEnd(true);
        }
        if (birdList.size() == 0) {
            gameEnd(false);
        }
/*
        if (contact.m_fixtureA.m_filter.groupIndex == -1 || contact.m_fixtureB.m_filter.groupIndex == -1) {
            fix = contact.m_fixtureA.m_filter.groupIndex == -1?contact.m_fixtureA:contact.m_fixtureB;

            for (int i = 0; i < contact.getManifold().pointCount; i++) {

                if (impulse.normalImpulses[i] > 0.8) {
                    //System.out.print("pushing point \n");
                    drawer.pushContactPoint(fix.m_body.getPosition());
    			   try {
                        //music.birdScream();
                    } catch (IOException ex) {                    }
                }
            }
        }
        if (contact.m_fixtureA.m_filter.groupIndex == 1 || contact.m_fixtureB.m_filter.groupIndex == 1) {
            // System.out.print(" impulse point count  : "+impulse.normalImpulses.length+"\n");
            for (int i = 0; i < contact.getManifold().pointCount; i++) {
                //System.out.print("wood impulse : "+impulse.normalImpulses[i]+"\n");
                if (impulse.normalImpulses[i] > 3.1f) {
                    //System.out.print("wood impulse : "+impulse.normalImpulses[i]+"\n");
                    try {
                        //music.woodCollision();
                    } catch (IOException ex) {
                    }
                    return;
                }
            }
        }
*/
    }

    private void gameEnd(boolean win) {
        if (hasEnd) {
            return;
        }
        hasEnd = true;
        System.out.println("游戏结束");
        if (win) {
            new AngryToadsMusic("sfx/level clear military a1.wav").start();
        }else {
            new AngryToadsMusic("sfx/level failed piglets a1.wav").start();
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                m_view.gameOver = true;
                m_view.hasWin = win;
                if (win) {
                    new AngryToadsMusic("music/level_complete.wav").start();
                }
            }
        };
        new Timer().schedule(task, 1000); // 1s 后显示
    }

}
