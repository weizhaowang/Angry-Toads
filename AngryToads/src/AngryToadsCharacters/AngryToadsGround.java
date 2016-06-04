package AngryToadsCharacters;

import javax.swing.ImageIcon;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;


public class AngryToadsGround {

    public AngryToadsBodyInfo gInfo = new AngryToadsBodyInfo();
    public BodyDef gDef = new BodyDef();
    public FixtureDef gFix = new FixtureDef();
    public PolygonShape gShape = new PolygonShape();
    ImageIcon gImage = new ImageIcon("src/AngryToadsImagePack/ground.png");

    public AngryToadsGround() {
        gDef.position.set(32f, 1f);
        gFix.friction = 0.7f;
        gFix.density = 0f;

        gShape.setAsBox(60f, 1f);

        gFix.shape = gShape;
        gFix.filter.groupIndex = 0;
        gInfo.setAppearance(gImage.getImage());
    }

    public Body createGround(World dad) {
        Body ground = dad.createBody(gDef);
        ground.m_userData = gInfo;
        ground.createFixture(gFix);
        return ground;
    }
}
