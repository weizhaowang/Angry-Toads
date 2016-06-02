/*
 * Class to define the template;
 */
package AngryToadsCharacters;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;


abstract public class AngryToadsCharacter {
    
    public BodyDef characterDef=new BodyDef();
    public FixtureDef characterfixdef=new FixtureDef();
    public Shape characterShape;
    public AngryToadsBodyInfo characterinfo=new AngryToadsBodyInfo();
    
    AngryToadsCharacter() {
        characterDef.bullet=false;
        characterDef.type=BodyType.DYNAMIC;
        characterDef.allowSleep=false;
        
        characterfixdef.friction=0.9f;
        characterfixdef.density=1f;
        characterfixdef.restitution=0.5f;
    }
    
    
    public void setPosition(Vec2 worldpos) {
        characterDef.position.set(worldpos);
    }

    public void setCharactershape(Shape charactershape) {
         this.characterfixdef.shape=charactershape;
    }
    public void setCharacterdef(BodyDef characterdef) {
        this.characterDef=characterdef;
    }
    public void setCharacterfixturedef(FixtureDef characterfix) {
        this.characterfixdef=characterfix;
        
    }

    public void setCharacterinfo(AngryToadsBodyInfo characterinfo) {
        this.characterinfo = characterinfo;
    }

    public BodyDef getCharacterdef() {
        return characterDef;
    }

    public FixtureDef getCharacterfixdef() {
        return characterfixdef;
    }

    public AngryToadsBodyInfo getCharacterinfo() {
        return characterinfo;
    }
}
