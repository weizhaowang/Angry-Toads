/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AngryToadsCharacters;

import javax.swing.ImageIcon;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;


public class AngryToadsPig extends AngryToadsCharacter{
    ImageIcon pig1=new ImageIcon("src/AngryToadsImagePack/pigs.png");
    public AngryToadsPig() {
        super();
        this.getCharacterinfo().setAppearance(pig1.getImage());
        this.getCharacterfixdef().filter.groupIndex=2;
        this.getCharacterinfo().setHafwidth(0.5f);
        this.getCharacterinfo().setHafheight(0.5f);
        
    }
    
}
