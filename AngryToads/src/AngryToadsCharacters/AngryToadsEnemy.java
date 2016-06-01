package AngryToadsCharacters;

import javax.swing.ImageIcon;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public class AngryToadsEnemy extends AngryToadsCharacter {
	CircleShape enemyShape=new CircleShape();
	ImageIcon pig0 = new ImageIcon("src/AngryToadsImagePack/pigs.png");
	ImageIcon pig1 = new ImageIcon();
	
	public AngryToadsEnemy() {
		super();
		setCharacterinfo(0.7f, 0.7f, 10f);
	}

	public AngryToadsEnemy(float fWidth, float fHeight) {
		super();
		setCharacterinfo(fWidth, fHeight, 10f);
	}

	public void setCharacterinfo(float halfWidth, float halfHeight, float health) {
		this.getCharacterfixdef().filter.groupIndex = 1;
		super.getCharacterdef().angle = 0f;
		this.getCharacterinfo().setHalfwidth(halfWidth);
		this.getCharacterinfo().setHalfheight(halfHeight);
		this.getCharacterinfo().setHealth(health);
	}

	public Body createEnemy(World mom, int type, Vec2 pos) {
		this.getCharacterdef().position.set(pos);
		Body enemy = mom.createBody(characterDef);
		switch (type) {
		case 0:
			super.setCharactershape(this.enemyShape);
			this.enemyShape.m_radius=0.7f;
			this.getCharacterinfo().setName("pig");
			this.getCharacterinfo().setAppearance(pig0.getImage());
            this.getCharacterfixdef().userData = this.getCharacterinfo();
            enemy.m_userData = this.getCharacterinfo();
            characterfixdef.density=0.5f;
            enemy.createFixture(characterfixdef);
            break;
		}
		return enemy;
	}
}
