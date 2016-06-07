package AngryToadsLevel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.WeldJoint;
import org.jbox2d.dynamics.joints.WeldJointDef;

import AngryToadsApplication.AngryToadsArea;
import AngryToadsCharacters.AngryToadsEnemy;
import AngryToadsCharacters.AngryToadsGround;
import AngryToadsCharacters.AngryToadsModel;
import AngryToadsCharacters.AngryToadsObstacles;

public class ToadsLevelNum1 extends AngryToadsArea{
	@Override
	public void initStage(){
		AngryToadsGround nGround = new AngryToadsGround();
		AngryToadsModel nModel = new AngryToadsModel();
		
		this.ground = nGround.createGround(this.sworld);
		this.setLevelNum(1);
		
		Vec2 pos = new Vec2();
		for (int i = 0; i < 3; i++) {
			pos.set(2 + i / 2, 3f);
			this.birdList.add(nModel.createBody(this.sworld, 1, pos));
		}
		

		pos.set(40f, 6.7f);
		this.toadList.add(new AngryToadsEnemy().createEnemy(this.sworld, 0, pos));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 1, pos.set(pos.x+0.3f,pos.y), 0.2f, 0.2f, 0));
		pos.set(20f,3f);
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 1, pos, 3f, 4f, 0));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 1, pos.set(pos.x,pos.y+4f), 1, 1, 0));
		pos.set(34f,2.5f);
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 2, pos, 2f, 1f, 0));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 2, pos.set(pos.x,pos.y+1), 1, 1, 0));
		pos.set(34f-2.5f,2.5f);
		this.toadList.add(new AngryToadsEnemy().createEnemy(this.sworld, 0, pos));
		pos.set(38f,3.6f);
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 0, pos.set(pos.x, pos.y), 2f, 0.3f,(float) (Math.PI / 2)));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 0, pos.set(pos.x + 4.1f, pos.y), 2f, 0.3f, (float) (Math.PI / 2)));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 0, pos.set(pos.x - 2.05f, pos.y+2f), 2.6f, 0.3f, 0));

		toadBullets = 0;
		// set the position of sling.
		pos.set(5f, 7f);
		WeldJointDef wd = new WeldJointDef();
		slingAnchor.set(pos);

		wd.bodyA = this.ground;
		wd.bodyB = birdList.get(0);
		wd.localAnchorA.set(pos.sub(this.ground.getPosition()));
		attachDef = wd;
		attach = (WeldJoint) sworld.createJoint(wd);
		birdList.get(0).setTransform(pos, 0);

	}
}
