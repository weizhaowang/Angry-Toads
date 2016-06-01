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
<<<<<<< HEAD
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
		pos.set(40f, 7f);
		this.enemyList.add(nEnemy.createEnemy(this.sworld, 0, pos));

		pos.set(40f, 6f);
		this.obList.add(nObs.createObstacles(this.sworld, 0, pos.set(pos.x - 2, pos.y - 2.1f), 2f, 0.3f,
				(float) (Math.PI / 2)));
		this.obList
				.add(nObs.createObstacles(this.sworld, 0, pos.set(pos.x + 4, pos.y), 2f, 0.3f, (float) (Math.PI / 2)));
		this.obList.add(nObs.createObstacles(this.sworld, 0, pos.set(pos.x - 2, pos.y + 2.1f), 2.4f, 0.3f, 0));

		toadbullets = 0;
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
=======

    public ToadsLevel() {
        super();
    }

    @Override
    public void initStage() {

        AngryToadsGround gu = new AngryToadsGround();
        AngryToadsModel bu = new AngryToadsModel();
        AngryToadsObstacles ou = new AngryToadsObstacles();
        AngryToadsPig pig = new AngryToadsPig();

        ground = gu.createGround(sworld);

        Vec2 pos = new Vec2();
        for (int i = 0; i < 2; i++) {
            pos.set(2 + i / 2, 0.5f);
            this.birdlist.add(bu.createBirds(sworld, 1, pos));

            pos.set(40 + 2 * i, 1f);
            float dheight = 1.2f;
            for (int j = 0; j < 1; j++) {
                
                if (j == 0) {
                    
                    dheight *= 0;
                }
                this.oblist.add(ou.createObstacles(sworld, 4, pos.set(pos.x, pos.y + dheight), (float) Math.PI / 2));

                dheight = 1.2f;
                this.oblist.add(ou.createObstacles(sworld, 4, pos.set(pos.x, pos.y + dheight), 0));

            }

        }




        birdbullets = 0;
        pos.set(5f, 5.4f);
        WeldJointDef wd = new WeldJointDef();
        slingAnchor.set(pos);
        wd.bodyA = ground;
        wd.bodyB = birdlist.get(0);
        wd.localAnchorA.set(pos.sub(ground.getPosition()));
        attachDef = wd;
        attach = (WeldJoint) sworld.createJoint(wd);
        birdlist.get(0).setTransform(pos, 0);



    }
>>>>>>> origin/master
}
