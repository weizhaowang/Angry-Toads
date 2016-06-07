/*
 * Class to create obstacles.
 * type 0-wood 1-stone 2-ice
 */

package AngryToadsCharacters;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public class AngryToadsObstacles extends AngryToadsCharacter {

	PolygonShape obsShape = new PolygonShape();
	float halfWidth, halfHeight,angle;

	public AngryToadsObstacles() {
		super();
		super.setCharactershape(obsShape);
		super.getCharacterfixdef().filter.groupIndex = 1;
	}
	
	
	public void setCharacter(float hWidth,float hHeight,float angle,float density,float restitution,float friction){
		this.halfWidth=hWidth;
		this.halfHeight=hHeight;
		this.angle=angle;
		obsShape.setAsBox(this.halfWidth,this.halfHeight);
		this.getCharacterfixdef().shape = obsShape;
		this.getCharacterinfo().setHalfheight(this.halfHeight);
		this.getCharacterinfo().setHalfwidth(this.halfWidth);
	}
	public Body createObstacles(World mom, int type, Vec2 pos,float hWidth,float hHeight, float angle) {
		this.getCharacterdef().position.set(pos);
		characterDef.angle = angle;
		Body obs = mom.createBody(characterDef);
		this.getCharacterinfo().setTypeNum(type);
		switch (type) {
		/*
		 * wood.
		 * health is 5f.
		 */
			case 0:
				this.getCharacterinfo().setHealth(5f);
				this.getCharacterinfo().setName("wood");
				this.getCharacterinfo().setAppearance(this.getCharacterinfo().wood.get(0).getImage());
				this.setCharacter(hWidth,hHeight,angle,1f,0.1f,0.7f);
				obs.m_userData = this.getCharacterinfo();
				obs.createFixture(this.characterfixdef);
				break;
		/*
		 * stone.
		 * health is 20f;
		 */
			case 1:
				this.getCharacterinfo().setHealth(20f);
				this.getCharacterinfo().setName("stone");
				this.getCharacterinfo().setAppearance(this.getCharacterinfo().stone.get(0).getImage());
				this.setCharacter(hWidth,hHeight,angle,3f,0.01f,0.5f);
				obs.m_userData=this.getCharacterinfo();
				obs.createFixture(this.characterfixdef);
				break;
		/*
		 * ice.
		 * health is 3f.
		 */
			case 2:
				this.getCharacterinfo().setHealth(3f);
				this.getCharacterinfo().setName("ice");
				this.getCharacterinfo().setAppearance(this.getCharacterinfo().ice.get(0).getImage());
				this.setCharacter(hWidth,hHeight,angle,0.8f,0.01f,0.5f);
				obs.m_userData=this.getCharacterinfo();
				obs.createFixture(this.characterfixdef);
				break;
		/*
		 * box.
		 * can't be destroyed.health is Float.MAX_VALUE.
		 */
			case 3:
			break;
		}
		this.getCharacterfixdef().userData = this.getCharacterinfo();
		return obs;
	}
}
