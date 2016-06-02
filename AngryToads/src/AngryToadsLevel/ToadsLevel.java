/*
 * Class to initiate the level.
 * 
 * @param levelNum.
 */
package AngryToadsLevel;

import AngryToadsApplication.AngryToadsArea;
import AngryToadsCharacters.AngryToadsGround;
import AngryToadsCharacters.AngryToadsModel;
import AngryToadsCharacters.AngryToadsObstacles;
import AngryToadsCharacters.AngryToadsEnemy;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.*;

public class ToadsLevel extends AngryToadsArea {
	public int levelNum = 0;

	public ToadsLevel() {
		super();
	}

	public ToadsLevel(int num) {
		super();
		this.levelNum = num;
	}

	@Override
	public void initStage() {

		AngryToadsGround nGround = new AngryToadsGround();
		AngryToadsModel nModel = new AngryToadsModel();
		AngryToadsObstacles nObs = new AngryToadsObstacles();
		AngryToadsEnemy nEnemy = new AngryToadsEnemy();

		this.ground = nGround.createGround(this.sworld);

		Vec2 pos = new Vec2();
		for (int i = 0; i < 3; i++) {
			pos.set(2 + i / 2, 3f);
			this.toadList.add(nModel.createBody(this.sworld, 0, pos));
		}
		pos.set(40f, 7.4f);
		this.piglist.add(nEnemy.createEnemy(this.sworld, 0, pos));

		pos.set(38f,4f);
		this.obList.add(nObs.createObstacles(this.sworld, 0, pos.set(pos.x, pos.y), 2f, 0.3f,
				(float) (Math.PI / 2)));
		this.obList
				.add(nObs.createObstacles(this.sworld, 0, pos.set(pos.x + 4, pos.y), 2f, 0.3f, (float) (Math.PI / 2)));
		this.obList.add(nObs.createObstacles(this.sworld, 0, pos.set(pos.x - 2, pos.y+2f), 2.6f, 0.3f, 0));

		toadBullets = 0;
		// set the position of sling.
		pos.set(5f, 7f);
		WeldJointDef wd = new WeldJointDef();
		slingAnchor.set(pos);

		wd.bodyA = this.ground;
		wd.bodyB = toadList.get(0);
		wd.localAnchorA.set(pos.sub(this.ground.getPosition()));
		attachDef = wd;
		attach = (WeldJoint) sworld.createJoint(wd);
		toadList.get(0).setTransform(pos, 0);

	}

}
