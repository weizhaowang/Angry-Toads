/*
 * Class to initiate the level.
 * 
 * @param levelNum.
 */
package AngryToadsLevel;

import AngryToadsApplication.AngryToadsArea;

public class ToadsLevel{
	public int levelNum = 0;
	
	public ToadsLevel() {
		super();
	}

	public ToadsLevel(int num) {
		super();
		this.levelNum = num;
	}
	
	public AngryToadsArea createLevel(){
		switch(this.levelNum){
		case 1:
			return new ToadsLevelNum1();
		case 2:
			return new ToadsLevelNum2();
		default:
			return new ToadsLevelNum1();
		}
	}
	
	public void setLevelNum(int num){
		this.levelNum=num;
	}
	public int getLevelNum(int num){
		return this.levelNum;
	}
}
