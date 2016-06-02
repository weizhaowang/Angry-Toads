/*
 * Class to create obstacles.
 * type 0-wood 1-glass 2-box.
 */

package AngryToadsCharacters;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public class AngryToadsObstacles extends AngryToadsCharacter {

	PolygonShape obsShape = new PolygonShape();
	ArrayList<ImageIcon> wood=new ArrayList<ImageIcon>(){{
		add(new ImageIcon("src/AngryToadsImagePack/wood.png"));add(new ImageIcon("src/AngryToadsImagePack/wood-damaged.png"))
		;add(new ImageIcon("src/AngryToadsImagePack/wood-destroyed.png"));
	}};
	ArrayList<ImageIcon> stone=new ArrayList<ImageIcon>(){{
		add(new ImageIcon("src/AngryToadsImagePack/stone.png"));add(new ImageIcon("src/AngryToadsImagePack/stone-damaged.png"));
		add(new ImageIcon("src/AngryToadsImagePack/stone-destroyed.png"));
	}};
	ArrayList<ImageIcon> ice=new ArrayList<ImageIcon>(){{
		add(new ImageIcon("src/AngryToadsImagePack/ice.png"));add(new ImageIcon("stc/AngryToadsImagePack/ice-damaged.png"));
		add(new ImageIcon("src/AngryToadsImagePack/ice-destroyed.png"));
	}};
	ImageIcon box = new ImageIcon();
	float halfWidth, halfHeight, health,angle;
	int typeNum;

	public AngryToadsObstacles() {
		super();
		super.setCharactershape(obsShape);
		super.getCharacterfixdef().filter.groupIndex = 1;
	}
	public void setHealth(float health){
		this.health=health;
		//changeImage();
	}
	/*
	public void changeImage(){
		int ImageNum=0;
		if(this.health<)
			ImageNum=1;
		else if(this.health<)
			ImageNum=2;
		switch(this.typeNum){
			case(0):
			this.getCharacterinfo().setAppearance(wood.get(ImageNum).getImage());
			break;
			case(1):
			this.getCharacterinfo().setAppearance(stone.get(ImageNum).getImage());
			break;
			case(2):
			this.getCharacterinfo().setAppearance(ice.get(ImageNum).getImage());
			break;
		}
	}
	*/
	public void setCharacter(float hWidth,float hHeight,float angle,float density,float restitution,float friction){
		this.halfWidth=hWidth;
		this.halfHeight=hHeight;
		this.angle=angle;
		obsShape.setAsBox(this.halfWidth,this.halfHeight);
		this.getCharacterfixdef().shape = obsShape;
		this.getCharacterinfo().setHalfheight(this.halfHeight);
		this.getCharacterinfo().setHalfwidth(this.halfWidth);
		this.getCharacterfixdef().userData = this.getCharacterinfo();
	}
	public Body createObstacles(World mom, int type, Vec2 pos,float hWidth,float hHeight, float angle) {
		this.typeNum=type;
		this.getCharacterdef().position.set(pos);
		characterDef.angle = angle;
		Body obs = mom.createBody(characterDef);
		switch (type) {
		/*
		 * wood.
		 * health is 15f.
		 */
			case 0:
				this.getCharacterinfo().setName("wood");
				this.getCharacterinfo().setAppearance(wood.get(0).getImage());
				this.setCharacter(hWidth,hHeight,angle,1f,0.1f,0.7f);
				obs.m_userData = this.getCharacterinfo();
				obs.createFixture(this.characterfixdef);
				break;
		/*
		 * stone.
		 * can't be destroyed.
		 */
			case 1:
				this.getCharacterinfo().setName("stone");
				this.getCharacterinfo().setAppearance(stone.get(0).getImage());
				this.setCharacter(hWidth,hHeight,angle,3f,0.01f,0.5f);
				obs.m_userData=this.getCharacterinfo();
				obs.createFixture(this.characterfixdef);
				break;
		/*
		 * ice.
		 * health is 
		 */
			case 2:
				this.getCharacterinfo().setName("ice");
				this.getCharacterinfo().setAppearance(ice.get(0).getImage());
				this.setCharacter(hWidth,hHeight,angle,0.8f,0.01f,0.5f);
				obs.m_userData=this.getCharacterinfo();
				obs.createFixture(this.characterfixdef);
				break;
		}
		return obs;
	}
}
