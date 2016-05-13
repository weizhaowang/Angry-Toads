/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AngryToadsCharacters;

import javax.swing.ImageIcon;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;


 abstract public  class AngryToadsCharacter {
    
    public BodyDef characterdef=new BodyDef();
    public FixtureDef characterfixdef=new FixtureDef();
    public Shape charactershape;
    public AngryToadsBodyInfo characterinfo=new AngryToadsBodyInfo();
    
     AngryToadsCharacter() {
        characterdef.bullet=false;
        characterdef.type=characterdef.type.DYNAMIC;
        characterdef.allowSleep=true;
        characterfixdef.friction=0.8f;
        characterfixdef.density=1f;
        characterfixdef.restitution=.4f;
        
        
    }
    
    
    public void setPosition(Vec2 worldpos) {
        characterdef.position.set(worldpos);
    }

    public void setCharactershape(Shape charactershape) {
         this.characterfixdef.shape=charactershape;
    }
    public void setCharacterdef(BodyDef characterdef) {
        this.characterdef=characterdef;
    }
    public void setCharacterfixturedef(FixtureDef characterfix) {
        this.characterfixdef=characterfix;
        
    }

    public void setCharacterinfo(AngryToadsBodyInfo characterinfo) {
        this.characterinfo = characterinfo;
    }

    public BodyDef getCharacterdef() {
        return characterdef;
    }

    public FixtureDef getCharacterfixdef() {
        return characterfixdef;
    }

    public AngryToadsBodyInfo getCharacterinfo() {
        return characterinfo;
    }
}
