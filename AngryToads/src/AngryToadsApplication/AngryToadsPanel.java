package AngryToadsApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.jbox2d.common.Vec2;

/**
 *   This class is for displaying the game screen.
 */
class AngryToadsPanel extends JPanel {
	
    private final AngryToadsDraw myDraw;
    private  AngryToadsController myController=null;
    
    public static final int PREF_WIDTH = 1200;
    public static final int PREF_HEIGHT = 800;
    
    private Graphics2D dbg = null;
    private Image dbImage = null;
    private ImageIcon bg=null;
    private ImageIcon pausebutton=new ImageIcon("src/AngryToadsImagePack/pause.png");
    private ImageIcon resumebutton=new ImageIcon("src/AngryToadsImagePack/resume.png");
    ImageIcon finger=new ImageIcon("src/AngryToadsImagePack/Finger.png");
    
    private boolean dragflag=false,inside1=false,inside2=false;
    private boolean flag=false;
    private int lor,mark;
    private float b1s=1f,b2s=1f;
    Vec2 fingerpoint=new Vec2();
    AngryToadsPanel() {
        super();
        myDraw=new AngryToadsDraw(this);
        this.setBackground(null);
        this.setPreferredSize(new Dimension(PREF_WIDTH,PREF_HEIGHT));
        bg=new ImageIcon("src/AngryToadsImagePack/background.jpg");
        
        /*
         * Add MouseListner for Transforming this viewport,which might 
         * change viewport scale and display center. 
         * 
         */
        this.addMouseWheelListener(new MouseWheelListener( ){

            @Override
            public void mouseWheelMoved(MouseWheelEvent mwe) {
                
                int scaletimes=-mwe.getWheelRotation();
                myDraw.vpt.mouseWheelTransform(mwe.getX(), scaletimes);
             
            }
        });
        
        this.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                if(inside1) {
                    myController.stop=true;
                }
                if(inside2) {
                    myController.stop=false;
                }
                    
            }

            @Override
            public void mousePressed(MouseEvent e) {
                dragflag=e.getButton()==MouseEvent.BUTTON3;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
            
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                  fingerpoint.set(me.getX(), me.getY());                 
                  lor=me.getX();                  
                  if(dragflag) {
                      
                      if(lor-mark>0)
                        myDraw.vpt.ScrollLeft();
                      if(lor-mark<0)
                        myDraw.vpt.ScrollRight();
                  }
              
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                 mark=e.getX();
                 fingerpoint.set(e.getX(), e.getY());
                 if(e.getX()>0&&e.getX()<50*b1s&&e.getY()>0&&e.getY()<50*b1s) {
                               if(!inside1) {
                                  b1s=1.15f;
                                  inside1=true;
                                 }    
                     else {
                       inside1=true;
                       b1s=1.15f;
                 }
                }
                 else {
                     inside1=false;b1s=1f;
                 }
                
                 if(e.getX()>60&&e.getX()<55+50*b2s&&e.getY()>0&&e.getY()<50*b2s){
                    if(!inside2) {
                    b2s=1.15f;
                    inside2=true;
                    }    
                    else {
                       inside2=true;
                       b2s=1.15f;
                 }                    
                }
                 else {
                     b2s=1f;inside2=false;
                 }
                      
            }
               
        });
        
    }
    

    public AngryToadsDraw getSDDraw() {
        return myDraw;
    }
    
    public Graphics2D getDBDraw(){
        return dbg;
    }

    @Override
    public int getHeight() {
        return PREF_HEIGHT;
    }

    @Override
    public int getWidth() {
        return PREF_WIDTH;
    }
    
 public  boolean  render() {
    if (dbImage == null) {

      dbImage = createImage(PREF_WIDTH, PREF_HEIGHT);
        if (dbImage == null) {
          return false;
        }
       dbg = (Graphics2D) dbImage.getGraphics();
     }
       dbg.setColor(null);
       int width=0;
       int btx=(int)(bg.getImage().getWidth(this) *myDraw.vpt.bgscale)-bg.getImage().getWidth(this);
       int bty=(int)(bg.getImage().getHeight(this) *myDraw.vpt.bgscale)-bg.getImage().getHeight(this);
       
       //draw sky.
       for(int i=0;i<=1;i++) {
        dbg.drawImage(bg.getImage(),width-btx,0-bty,(int)(bg.getImage().getWidth(this) *myDraw.vpt.bgscale),(int)(bg.getImage().getHeight(this) *myDraw.vpt.bgscale)+130,null); 
        width=(int)(bg.getImage().getWidth(this) *myDraw.vpt.bgscale);
        }    
       
        
       dbg.drawImage(pausebutton.getImage(), 0,0, (int) (50*b1s), (int) (50*b1s),null);
       dbg.drawImage(resumebutton.getImage(),55,0, (int) (50*b2s), (int) (50*b2s),null);
       
        return true;
  }
    public  void paintscence(){
        
        if(PREF_WIDTH!=0&&PREF_HEIGHT!=0) {
            flag=true;
        }
        Graphics2D g=(Graphics2D) this.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
        if(g!=null&&dbImage!=null) {
	        g.drawImage(dbImage, 0, 0, null);
	        Toolkit.getDefaultToolkit().sync();
	        g.dispose();
        }
    }
    
    public void setStageController(AngryToadsController st) {
        myController=st;
    }
    public void drawCursor(Graphics2D g) {
       // if(cursorpoint.lengthSquared()=0)
     //   g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_SPEED);
        g.drawImage(finger.getImage(),(int) fingerpoint.x,(int) fingerpoint.y, 25, 35, this);
    }
    
    public boolean isPainting() {
        return flag;
    }
}
