/*
 * Class to create the body of toads or birds;
 * 
 * @param typeNum of class createBirds：
 * 0-Angry Toad  1-Angry Bird  2-BlueBird  3-Yellow Bird;
 * 
 */
package AngryToadsCharacters;

import javax.swing.ImageIcon;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public class AngryToadsModel extends AngryToadsCharacter {

	public ImageIcon toad=new ImageIcon("src/AngryToadsImagePack/AngryToad.png");
	public ImageIcon birds1 = new ImageIcon("src/AngryToadsImagePack/AngryBird.png");
	public ImageIcon birds2 = new ImageIcon("src/AngryToadsImagePack/BlueBird.png");
	public ImageIcon birds3 = new ImageIcon("src/AngryToadsImagePack/YellowBird.png");

	public AngryToadsModel() {
		super();
	}

	public Body createBody(World mom, int typeNum, Vec2 pos) {
		this.getCharacterdef().position.set(pos);
		this.getCharacterdef().linearDamping = 0.05f;
		// group of objects that will never collide.
		this.getCharacterfixdef().filter.groupIndex = -1;
		this.characterShape = new CircleShape();
		Body mybody = mom.createBody(characterDef);
		switch (typeNum) {
		case 0:
			this.characterShape.m_radius=0.6f;
			this.getCharacterfixdef().shape = this.characterShape;
			this.getCharacterinfo().setName("AngryToad");
			this.getCharacterinfo().setHalfheight(0.6f);
			this.getCharacterinfo().setHalfwidth(0.6f);
			this.getCharacterinfo().setAppearance(toad.getImage());
			mybody.m_userData = this.getCharacterinfo();
			mybody.createFixture(characterfixdef);
			break;
		case 1:
			this.characterShape.m_radius = 0.5f;
			this.getCharacterfixdef().shape = this.characterShape;
			this.getCharacterinfo().setName("AngryBird");
			this.getCharacterinfo().setHalfheight(0.5f);
			this.getCharacterinfo().setHalfwidth(0.5f);
			this.getCharacterinfo().setAppearance(birds1.getImage());
			mybody.m_userData = this.getCharacterinfo();
			mybody.createFixture(characterfixdef);
			break;
		case 2:
			this.characterShape.m_radius = 0.7f;
			this.getCharacterfixdef().shape = this.characterShape;
			this.getCharacterinfo().setName("BlueBird");
			this.getCharacterinfo().setHalfheight(0.7f);
			this.getCharacterinfo().setHalfwidth(0.7f);
			this.getCharacterinfo().setAppearance(birds2.getImage());
			mybody.m_userData = this.getCharacterinfo();
			mybody.createFixture(characterfixdef);
			break;
		case 3:
			this.characterShape.m_radius = 1f;
			this.getCharacterfixdef().shape = this.characterShape;
			this.getCharacterinfo().setName("YellowBird");
			this.getCharacterinfo().setHalfheight(1f);
			this.getCharacterinfo().setHalfwidth(1f);
			this.getCharacterinfo().setAppearance(birds3.getImage());
			mybody.m_userData = this.getCharacterinfo();
			mybody.createFixture(characterfixdef);
			break;
		}
		return mybody;
	}

}
