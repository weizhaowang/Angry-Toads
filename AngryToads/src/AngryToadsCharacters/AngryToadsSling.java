package AngryToadsCharacters;

import javax.swing.ImageIcon;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class AngryToadsSling {
        static PolygonShape slingShape=new PolygonShape();
        static BodyDef sd=new BodyDef();
        static AngryToadsBodyInfo si=new AngryToadsBodyInfo();
        static FixtureDef sf=new FixtureDef();
        static ImageIcon slingImage=new ImageIcon("src/AngryToadsImagePack/slingstick.png");
    
    static public Body createStick(World mom,Vec2 pos) {
        slingShape.setAsBox(0.5f, 2.7f);
        sd.type=BodyType.STATIC;
        sd.position=pos;
        si.setName("stick");si.setHalfwidth(0.8f);si.setHalfheight(2.7f);si.setAppearance(slingImage.getImage());
        sd.userData=si;
        sf.shape=slingShape;
        sf.density=1f;
        Body stick=mom.createBody(sd);
        slingShape.setAsBox(0.5f,0.1f);
        pos.y=pos.y+1.2f;
        Body plat=mom.createBody(sd);
        plat.createFixture(slingShape,1);
        stick.createFixture(sf);
        return stick;
    }

}
