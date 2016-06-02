/* 
 * class to create the game world
 */
package AngryToadsApplication;

import java.util.ArrayList;
import java.util.LinkedList;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;


import AngryToadsCharacters.AngryToadsBodyInfo;

/**
 * 输入类型，包括
 * 鼠标按下、鼠标移动、鼠标松开、键盘按下、键盘松开
 * @author Haley
 *
 */
enum QueueItemType {

	MouseDown, MouseMove, MouseUp, KeyPressed, KeyReleased
}

/**
 * 输入对象，包括输入类型、一个向量--描述位置或移动
 */
class QueueItem {

	public QueueItemType type;
	public Vec2 p;
	public char c;
	public int code;

	public QueueItem(QueueItemType t, Vec2 pt) {
		type = t;
		p = pt;
	}

}

//Callback class for AABB queries.
class FixtureQueryCallback implements QueryCallback {

    public final Vec2 point;
    public Fixture fixture;

    public FixtureQueryCallback() {
        point = new Vec2();
        fixture = null;
    }

    /**
     * Method called for each fixture found in the query AABB,
     * return false to terminate the query.
     */
    public boolean reportFixture(Fixture argFixture) {
        Body body = argFixture.getBody();
        if (body.getType() == BodyType.DYNAMIC) {
            boolean inside = argFixture.testPoint(point);
            if (inside) {
                fixture = argFixture;

                return false;
            }
        }

        return true;
    }
}

public abstract class AngryToadsArea {
	
	private MouseJoint mouseJoint;
	private Vec2 mouseWorld = new Vec2();
	public final World sworld; // 世界对象
	private final Vec2 gravity; // 重力向量
	public Vec2 slingAnchor; // 弹弓位置
	public ArrayList<Body> toadList; // 所有bird
	public ArrayList<Body> obList; // 所有障碍物
	public ArrayList<Body> piglist, sling; // 所有pig，弹弓吊绳
	public WeldJoint attach; // 焊接关节，描述bird与弹弓的接触
	public WeldJointDef attachDef; // 定义焊接关节
	public Body ground; // 地面
	public float scale = 1 / 64f; // 画面缩放比例
	float timeStep = 1.0f / 60.0f; // 时间步
	int velocityIterations = 6; // 速度迭代
	int positionIterations = 2; // 位置迭代
	public int toadBullets; // 当前轮到的bird索引
	private final LinkedList<QueueItem> inputQueue; // 输入队列

	public AngryToadsArea() {
		gravity = new Vec2(0, -10f); // 重力
		inputQueue = new LinkedList<QueueItem>();
		sworld = new World(gravity, true); // 重力；允许刚体休眠
		toadList = new ArrayList<Body>();
		obList = new ArrayList<Body>();
		piglist = new ArrayList<Body>();
		sling = new ArrayList<Body>(); // 弹弓
		slingAnchor = new Vec2(); // 弹弓位置

	}

	abstract public void initStage();

	long endtime = 0;
	long duration = 0; // duration of release the bird;
	long descountdown = 0;

	public void step() {

		sworld.step(timeStep, velocityIterations, positionIterations);

		if (mouseJoint == null && attach == null) {

			endtime = System.currentTimeMillis();
			duration = (endtime - releasetime) / 1000;

		}

		if (duration > 3 && attach == null) { // 射出时间大于3秒并且当前弹弓为空，将下一个bird架上弹弓

			if (toadBullets <= toadList.size()) { // 还有bird炮弹
				toadList.get(toadBullets).setTransform(slingAnchor, 0); // 新的bird架上弹弓

				attachDef.bodyB = toadList.get(toadBullets); //重新定义弹弓与新bird的接触

				attach = (WeldJoint) this.getWorld().createJoint(attachDef);
				duration = 0;

			}

		}

		/*
		 * for(int i=0;i<birdlist.size();i++) { if(!birdlist.get(i).isAwake())
		 * getWorld().destroyBody(birdlist.get(i)); birdlist.remove(i);
		 * 
		 * }
		 * 
		 */

	}

	public ArrayList<Body> getBirds() {
		return toadList;
	}

	public ArrayList<Body> getObstacles() {
		return obList;
	}

	public ArrayList<Body> getPigs() {
		return piglist;
	}

	public World getWorld() {
		return sworld;
	}

	public Body getGround() {
		return ground;
	}

	public void setGravity(Vec2 gra) {
		sworld.setGravity(gra);
	}

	public boolean isMouseJointNull() {
		if (mouseJoint == null)
			return true;
		return false;
	}

	public void update() {
		if (!inputQueue.isEmpty()) {
			synchronized (inputQueue) {
				while (!inputQueue.isEmpty()) {
					QueueItem i = inputQueue.pop();
					switch (i.type) {
					case MouseDown:
						mouseDown(i.p);
						break;
					case MouseMove:
						mouseMove(i.p);
						break;
					case MouseUp:
						mouseUp(i.p);
						break;
					}
				}
			}
		}
		step();
	}

	/**
	 * Called for mouse-up，鼠标松开
	 *
	 * @param p
	 */
	long releasetime = 0;

	public void mouseUp(Vec2 p) {

		float length = 0;
		Vec2 pos = new Vec2();
		pos = p.sub(slingAnchor); //bird在弹弓上的拖拽位移
		length = pos.length();

		/*
		 * 拖拽长度最大为3，更大则无效，化归为3
		 */
		if (length > 3) {

			pos.x /= length / 3;
			pos.y /= length / 3;

			p = pos;

		}

		if (mouseJoint != null) {
			mouseJoint.m_bodyB.setFixedRotation(false); //bird不固定自转
			mouseJoint.m_bodyB.setLinearVelocity(pos.negate().mul(7.5f)); //bird释放，设定初速度
			// and
			// shoot!
			sworld.destroyJoint(mouseJoint);
			mouseJoint = null;
			if (toadBullets < toadList.size() - 1)
				toadBullets++;
			releasetime = System.currentTimeMillis();

		}

		/*
		 * if (bombSpawning) { completeBombSpawn(p); }
		 *
		 */
	}

	private final AABB queryAABB = new AABB();
	private final FixtureQueryCallback callback = new FixtureQueryCallback();

	/**
	 * Called for mouse-down，鼠标按下
	 *
	 * @param p
	 */
	public void mouseDown(Vec2 p) {
		mouseWorld.set(p);

		if (mouseJoint != null) {
			return;
		}

		queryAABB.lowerBound.set(p.x - .001f, p.y - .001f);
		queryAABB.upperBound.set(p.x + .001f, p.y + .001f);
		callback.point.set(p);
		callback.fixture = null;
		sworld.queryAABB(callback, queryAABB);

		if (callback.fixture != null && callback.fixture.m_filter.groupIndex == -1) {

			if (attach != null) {
				sworld.destroyJoint(attach);
				attach = null;
			}

			Body body = callback.fixture.getBody();
			MouseJointDef def = new MouseJointDef();
			def.bodyA = ground;
			def.bodyB = body;
			def.target.set(p);
			def.maxForce = 1000f * body.getMass();
			body.setFixedRotation(true);// NOTE!!!!!!!!!!!!!!!!!
			mouseJoint = (MouseJoint) sworld.createJoint(def);
			body.setAwake(true);
		}
	}

	/**
	 * Process MouseEvent.
	 *
	 * @param p,length
	 */
	Vec2 length;

	public void mouseMove(Vec2 p) {
		mouseWorld.set(p);
		Vec2 force = p.sub(slingAnchor);
		float ol = force.length();

		if (force.length() > 3f) {
			force.x /= ol / 3;
			force.y /= ol / 3;
			force.addLocal(slingAnchor);
			p = force;
		}
		if (mouseJoint != null) {
			mouseJoint.setTarget(p);
		}
	}

	/**
	 * 将鼠标事件加入输入队列
	 * @param p
	 */
	public void queueMouseUp(Vec2 p) {
		synchronized (inputQueue) {
			inputQueue.addLast(new QueueItem(QueueItemType.MouseUp, p));
		}
	}

	public void queueMouseDown(Vec2 p) {
		synchronized (inputQueue) {
			inputQueue.addLast(new QueueItem(QueueItemType.MouseDown, p));
		}
	}

	public void queueMouseMove(Vec2 p) {
		synchronized (inputQueue) {
			inputQueue.addLast(new QueueItem(QueueItemType.MouseMove, p));
		}
	}
}
