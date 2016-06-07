package AngryToadsLevel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.WeldJoint;
import org.jbox2d.dynamics.joints.WeldJointDef;

import AngryToadsApplication.AngryToadsArea;
import AngryToadsCharacters.AngryToadsEnemy;
import AngryToadsCharacters.AngryToadsGround;
import AngryToadsCharacters.AngryToadsModel;
import AngryToadsCharacters.AngryToadsObstacles;

public class ToadsLevelNum2 extends AngryToadsArea{
	@Override
	public void initStage(){
		//level2
		AngryToadsGround nGround = new AngryToadsGround();
		AngryToadsModel nModel = new AngryToadsModel();
		
		this.ground = nGround.createGround(this.sworld);
		this.setLevelNum(2);
		
		Vec2 pos = new Vec2();
		for (int i = 0; i < 3; i++) {
			pos.set(2 + i / 2, 3f);
			this.birdList.add(nModel.createBody(this.sworld, 1, pos));
		}
		
		Vec2 pos1=new Vec2();
		pos1.set(30f,2.8f);
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 0, pos1, 2f, 0.2f, 0));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 2, pos.set(pos1.x-1f,pos1.y+0.5f), 0.35f, 1, 0));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 2, pos.set(pos1.x+1,pos1.y+0.5f), 0.35f, 1, 0));
		
		pos1.set(pos1.x,pos1.y+2f);
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 2, pos1, 2f, 0.2f, 0));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 2, pos.set(pos1.x-1f,pos1.y+0.5f), 0.35f, 1f, 0));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 2, pos.set(pos1.x+1,pos1.y+0.5f), 0.35f, 1f, 0));
		this.toadList.add(new AngryToadsEnemy().createEnemy(this.sworld, 0, pos.set(pos1.x,pos1.y+0.5f)));
		pos1.set(pos1.x,pos1.y+2f);
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 1, pos1, 2f, 0.2f, 0));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 2, pos.set(pos1.x-1f,pos1.y+0.5f), 0.35f, 1f, 0));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 0, pos.set(pos1.x+1,pos1.y+0.5f), 0.35f, 1f, 0));
		this.toadList.add(new AngryToadsEnemy().createEnemy(this.sworld, 0, pos.set(pos1.x,pos1.y+0.5f)));
		pos1.set(pos1.x,pos1.y+2f);
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 0, pos1, 2f, 0.2f, 0));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 1, pos.set(pos1.x,pos1.y+0.5f), 1f, 1.5f, 0));
		
		pos1.set(35f,2.8f);
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 2, pos1, 0.5f, 0.5f, 0));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 2, pos.set(pos1.x+2f,pos1.y), 0.5f, 0.5f, 0));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 2, pos.set(pos1.x+4f,pos1.y), 0.5f, 0.5f, 0));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 2, pos.set(pos1.x+2f,pos1.y+0.5f),3f, 0.3f, 0));
		this.obList.add(new AngryToadsObstacles().createObstacles(this.sworld, 1, pos.set(pos1.x,pos1.y+1f),0.3f, 0.3f, 0));
		this.toadList.add(new AngryToadsEnemy().createEnemy(this.sworld, 0, pos.set(pos1.x+0.5f,pos1.y+1f)));
		this.toadList.add(new AngryToadsEnemy().createEnemy(this.sworld, 0, pos.set(pos1.x+2.5f,pos1.y+1f)));
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
