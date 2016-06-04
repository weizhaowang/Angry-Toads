/*
 * Class contains character information;
 * typeNum:wood-0,stone-1,ice-2,toad-3.
 */
package AngryToadsCharacters;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import AngryToadsApplication.AngryToadsMusic;

public class AngryToadsBodyInfo {
	ImageIcon appearance;
	ArrayList<ImageIcon> touching = new ArrayList<ImageIcon>();
	boolean colliding = false;
	float health = 20f;
	String name;
	int typeNum;
	float hafwidth = 0;
	float hafheight = 0;
	boolean isDamaged = false;
	boolean isDamaged2 = false;

	// List of images of wood/stone/ice
	@SuppressWarnings("serial")
	ArrayList<ImageIcon> wood = new ArrayList<ImageIcon>() {
		{
			add(new ImageIcon("src/AngryToadsImagePack/wood.png"));
			add(new ImageIcon("src/AngryToadsImagePack/wood-damaged.png"));
			add(new ImageIcon("src/AngryToadsImagePack/wood-destroyed.png"));
		}
	};
	@SuppressWarnings("serial")
	ArrayList<ImageIcon> stone = new ArrayList<ImageIcon>() {
		{
			add(new ImageIcon("src/AngryToadsImagePack/stone.png"));
			add(new ImageIcon("src/AngryToadsImagePack/stone-damaged.png"));
			add(new ImageIcon("src/AngryToadsImagePack/stone-destroyed.png"));
		}
	};
	@SuppressWarnings("serial")
	ArrayList<ImageIcon> ice = new ArrayList<ImageIcon>() {
		{
			add(new ImageIcon("src/AngryToadsImagePack/ice.png"));
			add(new ImageIcon("src/AngryToadsImagePack/ice-damaged.png"));
			add(new ImageIcon("src/AngryToadsImagePack/ice-destroyed.png"));
		}
	};
	@SuppressWarnings("serial")
	ArrayList<ImageIcon> toad = new ArrayList<ImageIcon>() {
		{
			add(new ImageIcon("src/AngryToadsImagePack/AngryToad"));
			add(new ImageIcon("src/AngryToadsImagePack/AngryToad-Explode"));
			add(new ImageIcon("src/AngryToadsImagePack/AngryToad-Splited"));
		}
	};

	// Threshold value used to judge the state of Obstacles/Enemy
	float woodThreshold = 10f;
	float woodThreshold2 = 5f;
	float iceThreshold = 7f;
	float iceThreshold2 = 4f;
	float stoneThreshold = 14f;
	float stoneThreshold2 = 7f;
	float toadThreshold = 10f;
	float toadThreshold2 = 5f;

	AngryToadsBodyInfo(String image, float hp, String name) {
		appearance = new ImageIcon(image);
		health = hp;
		this.name = name;
	}

	AngryToadsBodyInfo() {
		appearance = new ImageIcon("src/AngryToadsImagePack/cross.png");
		this.name = "unknown";
		health = 20.0f;
	}

	public void setAppearance(Image newApp) {
		appearance.setImage(newApp);
	}

	// figure health lost under the impulse.
	public boolean figureHealth(float impulse) {
		float healthLost = impulse / 10f;
		float tempHealth = this.health;
		this.setHealth(this.health - healthLost);
		if (healthLost > tempHealth) {
			return true;
		}
		return false;
	}

	/*
	 * change ImageIcon of the body. and play the music of damage.
	 */

	int ImageNum = 0;

	public void changeImage() {
		switch (this.typeNum) {
		// wood.
		case 0:
			if (this.health < this.woodThreshold && !this.isDamaged) {
				this.ImageNum = 1;
				new AngryToadsMusic("sfx/wood damage a1.wav").start();
				this.isDamaged = true;
			}
			if (this.health < this.woodThreshold2 && !this.isDamaged2) {
				this.ImageNum = 2;
				new AngryToadsMusic("sfx/wood damage a2.wav").start();
				this.isDamaged2 = false;
			}
			this.setAppearance(this.wood.get(ImageNum).getImage());
			break;
		// stone.
		case 1:
			if (this.health < this.stoneThreshold && !this.isDamaged) {
				this.ImageNum = 1;
				new AngryToadsMusic("sfx/rock damage a1.wav").start();
				this.isDamaged = true;
			}
			if (this.health < this.stoneThreshold2 && !this.isDamaged2) {
				this.ImageNum = 2;
				new AngryToadsMusic("sfx/rock damage a2.wav").start();
				this.isDamaged2 = true;
			}
			this.setAppearance(this.stone.get(ImageNum).getImage());
			break;
		// ice.
		case 2:
			if (this.health < this.iceThreshold && !this.isDamaged) {
				this.ImageNum = 1;
				new AngryToadsMusic("sfx/ice light collision a4.wav").start();
				this.isDamaged = true;
			}
			if (this.health < this.iceThreshold2 && !this.isDamaged2) {
				this.ImageNum = 2;
				new AngryToadsMusic("sfx/ice light collision a5.wav").start();
				this.isDamaged2 = true;
			}
			this.setAppearance(this.ice.get(ImageNum).getImage());
			break;
		// toad.
		case 10:
			if (this.health < this.toadThreshold && !this.isDamaged) {
				this.ImageNum = 1;
				new AngryToadsMusic("sfx/piglette damage a7.wav").start();
				this.isDamaged = true;
			}
			if(this.health<this.toadThreshold2&&!this.isDamaged2){
				this.ImageNum=2;
				new AngryToadsMusic("sfx/piglette damage a7.wav").start();
				this.isDamaged2=true;
			}
			this.setAppearance(this.toad.get(ImageNum).getImage());
			break;
		}

	}

	public void setHealth(float newHp) {
		this.health = newHp;
		this.changeImage();
	}

	public float getHealth() {
		return this.health;
	}

	public Image getAppearance() {
		return appearance.getImage();
	}

	public float getHalfwidth() {
		return hafwidth;
	}

	public float getHalfheight() {
		return hafheight;
	}

	public void setHalfheight(float hafHeight) {
		this.hafheight = hafHeight;
	}

	public void setHalfwidth(float halfWidth) {
		this.hafwidth = halfWidth;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		this.name = newName;
	}

	public int getTypeNum() {
		return this.typeNum;
	}

	public void setTypeNum(int type) {
		this.typeNum = type;
	}

}
