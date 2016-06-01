package AngryToadsApplication;

import java.awt.geom.AffineTransform;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;

/**
 * This class provide Rotation and Translation Position that used for rendering game.
 */
public class AngryToadsViewportTransform {
    Transform pTrans=new Transform();
    OBBViewportTransform vTrans=new OBBViewportTransform();
    float scale=20.0f;
    Vec2 center=new Vec2();
    Vec2 offset=new Vec2();
    public float bgscale=1f;
    int btx=0,bty=0;
    
    AngryToadsViewportTransform(AngryToadsPanel v) {
        vTrans.setYFlip(true);
        vTrans.setExtents(v.getWidth()/2,v.getHeight()/2);
        center.set(v.getWidth()/2,v.getHeight()/2+170f);
        offset.set(-v.getWidth()/2,170f);
    }
    
    //Transform world position to view position
    public void getWorldtoScreen(Vec2 worldPos,Vec2 out) {
        worldPos.x=(worldPos.x)*scale;
        worldPos.y=worldPos.y*scale;
        vTrans.getWorldToScreen(worldPos, out);
        out.addLocal(offset);
    }
    //Transform view position to world position
    public void getScreentoWorld(Vec2 screenPos,Vec2 worldpos) {
        screenPos.subLocal(offset);

        vTrans.getScreenToWorld(screenPos, worldpos);     
        
        screenPos.x=screenPos.x/scale;
        screenPos.y=screenPos.y/scale;
    }
    
    public AffineTransform rotatePoint(AffineTransform dtrans,float angle,Vec2 anchorp) {
    	//Rotate Round the anchor point.
        dtrans.rotate(angle,anchorp.x,anchorp.y);                  
        return dtrans;
    }
    public void setZoomCenter(int xpos) {
        this.center.x=xpos;
        
    }
    
    public Vec2 getOffset(int xpos,int scale) {
           offset.x=offset.x+xpos;
           return offset;
    }
    
    public void mouseWheelTransform(int xoffset,int scaletimes){   
        if(scale <25&&scaletimes>0) {
	        scale=scale+scaletimes*2;
	        offset.y+=2;
	        bgscale+=0.01f;
        }
        if(scale>18&&scaletimes<0) {
            scale=scale+scaletimes*2;
            offset.y-=2;
            bgscale-=0.01f;     
        }
    }
    
    public void ScrollLeft() {
        if(offset.x<-512)
        	offset.x+=7;
    } 
    
    public void ScrollRight() {
        //if(offset.x>-950)
        {
            if(offset.x>-700)
            	offset.x-=7;
            if(offset.x>-800)
                offset.x-=5;
            if(offset.x<-1000)
                offset.x-=3;
        }
            
    }
}
