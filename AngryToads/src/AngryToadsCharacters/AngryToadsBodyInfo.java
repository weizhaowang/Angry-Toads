/*
 * Class contains character information;
 */
package AngryToadsCharacters;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;


public class AngryToadsBodyInfo{
    ImageIcon appearance;
    ArrayList<ImageIcon> touching =new ArrayList<ImageIcon>();
    boolean colliding = false;
    float health;
    String name;
    float hafwidth=0;
    float hafheight=0;
    
    AngryToadsBodyInfo(String image,float hp,String name) {
        appearance=new ImageIcon(image);
        health=hp;
        this.name=name;
    }
    AngryToadsBodyInfo() {
        appearance=new ImageIcon("src/AngryToadsImagePack/cross.png");
        this.name="unknown";
        health=10.0f;
    }
    
    public void setAppearance(Image newApp){
        appearance.setImage(newApp);
    }
    
    public void setName(String newName) {
        this.name=newName;
    }
    public void setHealth(float newHp) {
        this.health=newHp;
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
    
}
