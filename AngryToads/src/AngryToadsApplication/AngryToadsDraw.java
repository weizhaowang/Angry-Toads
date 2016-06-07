package AngryToadsApplication;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import AngryToadsCharacters.AngryToadsBodyInfo;


public class AngryToadsDraw  {

    AngryToadsPanel viewport;
    AngryToadsArea stagetodraw;
    ArrayList<Body> birds;
    ArrayList<Body> pigs;
    ArrayList<Body> obstacles;
    LinkedList<Vec2> contactpoint;
    Body ground;
    AngryToadsViewportTransform vpt;
    AffineTransform transform = new AffineTransform();
    private ImageIcon grass = new ImageIcon("src/AngryToadsImagePack/grass.png");
    private ImageIcon planet = new ImageIcon("src/AngryToadsImagePack/planet.png");
    private ImageIcon sling = new ImageIcon("src/AngryToadsImagePack/slingstick.png");
    private ImageIcon trackImage=new ImageIcon("src/AngryToadsImagePack/trace.png");//track图片

    AngryToadsDraw(AngryToadsPanel v) {
        contactpoint = new LinkedList<Vec2>();
        vpt = new AngryToadsViewportTransform(v);
        viewport = v;

    }

    public void setStage(AngryToadsArea s) {
        stagetodraw = s;

        birds = s.getBirds();
        pigs = s.getToads();
        ground = s.getGround();
        obstacles = s.getObstacles();
    }

    public synchronized void drawStage(int birdIndex,ArrayList<Vec2>track) {
        //Initialize cam
        int initCamFlag = vpt.reportcaminitStatus();
        if(initCamFlag != 3)
        {
            switch(initCamFlag)
            {
                case 0:vpt.camPoint2Enemy();break;
                case 1:vpt.camPoint2Sling();break;
                case 2:vpt.camReset();break;
            }
        }
        if (viewport.render()) {
            drawBackground();

            drawtrack(birdIndex,track);
            drawBirds();
            Body bullet = stagetodraw.getBirds().get(stagetodraw.toadBullets);
            Boolean bulletFlying = bullet.getLinearVelocity().length() > 1.0f;
            if(bulletFlying)
            {
            	Vec2 bulletPos = bullet.getPosition().clone();
            	vpt.getWorldtoScreen(bulletPos, bulletPos);
            	vpt.camPoint2Bullet(bulletPos);
            }
            
            drawPigs();
            drawObstacles();
            drawSling();
            drawContact();
            viewport.drawCursor(this.getGraphics());
            viewport.paintscence();
        }
    }
    AngryToadsBodyInfo tempinfo;
    Body tempbody;
    Vec2 dpos = new Vec2();
    Vec2 cpos = new Vec2();
    float angle;
    int height, width;
    RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_SPEED);
    int translate = 0;

    private synchronized void drawSomething(ArrayList<Body> bodys) {

        Graphics2D pen = getGraphics();
        pen.setRenderingHints(rh);

        if (bodys.size() > 0) {

            for (int i = 0; i < bodys.size(); i++) {

                if (bodys.get(i) != null) {

                    tempbody = bodys.get(i);
                    //if(tempbody.getContactList()!=null)  {
                    //   System.out.print("the body "+tempbody.m_fixtureList.getFilterData().groupIndex+"is contacting "+tempbody.getContactList().other.m_fixtureList.m_filter.groupIndex+"\n");
                    //  }
                    /*
                     * change coordinates to draw...
                     */

                    angle = -tempbody.getAngle();
                    dpos = tempbody.getPosition().clone();
                    cpos = tempbody.getPosition().clone();
                    dpos.x = dpos.x - ((AngryToadsBodyInfo) tempbody.getUserData()).getHalfwidth();
                    dpos.y = dpos.y + ((AngryToadsBodyInfo) tempbody.getUserData()).getHalfheight();

                    this.getPosToDraw(dpos, dpos);
                    this.getPosToDraw(cpos, cpos);

                    tempinfo = (AngryToadsBodyInfo) tempbody.getUserData();
                    height = (int) (tempinfo.getHalfheight() * 2 * vpt.scale);
                    width = (int) (tempinfo.getHalfwidth() * 2 * vpt.scale);

                    if (angle != 0) {

                        this.getTransPos(angle, cpos);

                    }

                    pen.setTransform(transform);
                    pen.drawImage(tempinfo.getAppearance(), (int) dpos.x, (int) dpos.y, width, height, null);

                    //clear Transform,preparing to draw next .
                    transform.setToIdentity();
                    pen.setTransform(transform);
                }
            }
        }

    }

    public void drawtrack(int birdIndex,ArrayList<Vec2>track) {//画轨迹
        if (ableToDraw() == false) {
            return;
        }
        int i=0;

        Graphics2D pen = getGraphics();

        pen.setRenderingHints(rh);

        Body tempbody=this.birds.get(birdIndex);

        tempinfo = (AngryToadsBodyInfo) tempbody.getUserData();

        while(i < track.size()) {

            dpos = track.get(i).clone();
            cpos = track.get(i).clone();
            dpos.x = dpos.x - 2 * ((AngryToadsBodyInfo)tempbody.getUserData()).getHalfwidth();
            this.getPosToDraw(dpos, dpos);
            this.getPosToDraw(cpos, cpos);

            if(i % 2 == 0) {
                pen.drawImage(trackImage.getImage(), (int) dpos.x, (int) dpos.y, 12, 12, null);
            } else {
                pen.drawImage(trackImage.getImage(),(int)dpos.x, (int) dpos.y, 10, 10, null);
            }
            transform.setToIdentity();
            i++;
        }

    }

    private void drawPigs() {
        if (ableToDraw() == false) {
            return;
        }
        drawSomething(pigs);

    }

    private void drawBirds() {
        if (ableToDraw() == false) {
            return;
        }
        drawSomething(birds);

    }

    private void drawObstacles() {
        if (ableToDraw() == false) {
            return;
        }
        drawSomething(obstacles);

    }
    Vec2 slingAnchor1 = new Vec2(), slingAnchor2 = new Vec2();
    Vec2 slingpos = new Vec2();
    int swidth, sheight;

    private void drawSling() {

        swidth = (int) (30 * vpt.scale / 18f);
        sheight = (int) (115 * vpt.scale / 18f);

        Stroke rubberStroke = new BasicStroke(4.0f * vpt.scale / 18f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        Graphics2D pen = getGraphics();
        pen.setStroke(rubberStroke);
        pen.setColor(new Color(48, 23, 8));

        slingAnchor1 = stagetodraw.getBirds().get(stagetodraw.toadBullets).getPosition().clone();

        slingpos = stagetodraw.slingAnchor.clone();
        slingpos.y += 0.5;
        slingpos.x -= 0.5f;
        getPosToDraw(slingAnchor1, slingAnchor1);
        //getPosToDraw(slingAnchor2, slingAnchor2);
        getPosToDraw(slingpos, slingpos);
        if (!stagetodraw.isMouseJointNull()) {
            pen.drawLine((int) slingAnchor1.x, (int) slingAnchor1.y, (int) (slingpos.x + 16 * vpt.scale / 18f), (int) (slingpos.y + 12 * vpt.scale / 18f));
            pen.drawLine((int) slingAnchor1.x, (int) slingAnchor1.y, (int) (slingpos.x), (int) (slingpos.y + 12 * vpt.scale / 18f));

        }
        pen.drawImage(sling.getImage(), (int) slingpos.x - 5, (int) slingpos.y - 10, swidth, sheight, null);

    }

    Vec2 temp = new Vec2();
    public void drawContact( ) {

        if(this.getGraphics()!= null&& !contactpoint.isEmpty()) {
            temp = contactpoint.pop();
//        System.out.print("Contact point have " + contactpoint.size()+"\n");
//        Graphics2D pen = this.getGraphics();
//        System.out.print("contact is at "+temp.x+"\n");
//        pen.drawString("Now You Are Seeing the Contact", 300,200);
        }

    }

    public void pushContactPoint(Vec2 cp) {
        Vec2 tempcp = new Vec2();
        this.getPosToDraw(cp, tempcp);
        contactpoint.push(tempcp);

    }


    Vec2 gpos = new Vec2();
    float grasswidth;
    float grassheight;
    float planetwidth, planetheight;
    int groundwidth, groundheight, grassx, planetx;

    private void drawBackground() {
        if (ableToDraw() == false) {
            return;
        }

        Graphics2D pen = getGraphics();

        pen.setRenderingHints(rh);
        gpos = ground.getPosition().clone();
        gpos.x = gpos.x - 32.0f;
        gpos.y = gpos.y + 0.5f;
        this.getPosToDraw(gpos, gpos);

        grasswidth = grass.getImage().getWidth(null) + (vpt.scale - 18f) * 2;
        grassheight = grasswidth * (34 / 334f);

        planetwidth = (int) (planet.getImage().getWidth(null) + (vpt.scale - 18f) * 2);
        planetheight = planetwidth * (56 / 348f);

        groundwidth = (int) ((((AngryToadsBodyInfo) ground.getUserData()).getAppearance().getWidth(null) + (vpt.scale - 18f) * 5) / 1.6f);
        groundheight = (int) (groundwidth * (190 / 334f));
        
        gpos.x = 0;
        gpos.y = gpos.y -7f;
        
        grassx = (int) gpos.x;
        planetx = (int) gpos.x;

        for (int i = 0; i <= 6; i++) {
            pen.drawImage(planet.getImage(), planetx, (int) gpos.y - planet.getImage().getHeight(null) + 2, (int) planetwidth, (int) planetheight, null);
            planetx += planetwidth;
        }

        for (int i = 0; i <= 6; i++) {
            pen.drawImage(((AngryToadsBodyInfo) ground.getUserData()).getAppearance(), (int) gpos.x, (int) gpos.y, groundwidth, groundheight, null);
            pen.drawImage(grass.getImage(), grassx, (int) gpos.y - grass.getImage().getHeight(null) + 2, (int) grasswidth, (int) grassheight, null);
            gpos.x += groundwidth;
            grassx += grasswidth;
        }

    }

    public boolean ableToDraw() {
        if (stagetodraw != null) {
            return true;
        }
        System.out.print("cant draw \n");
        return false;
    }

    public Graphics2D getGraphics() {
        return viewport.getDBDraw();
    }

    public void getPosToDraw(Vec2 world, Vec2 out) {
        Vec2 tempworld = world.clone();
        vpt.getWorldtoScreen(tempworld, out);

    }

    public AffineTransform getTransPos(float angel, Vec2 anchorpoint) {
        return vpt.rotatePoint(transform, angle, anchorpoint);
    }

    public void getScreenToWorldToOut(Vec2 pos, Vec2 out) {
        vpt.getScreentoWorld(pos, out);
    }

}
