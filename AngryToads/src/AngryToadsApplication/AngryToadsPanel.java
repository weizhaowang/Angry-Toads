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

    public boolean gameOver = false; // 设置此为true以显示游戏结束画面
    public boolean hasWin = false; // 此变量标示玩家输赢,仅在gameOver为true时起作用

    private final AngryToadsDraw myDraw;
    private  AngryToadsController myController=null;

    public static final int PREF_WIDTH = 1200;
    public static final int PREF_HEIGHT = 800;
    private int centerX = 500;
    private int centerY = 300;

    private Graphics2D dbg = null;
    private Image dbImage = null;
    private ImageIcon bg = null;
    private ImageIcon pausebutton = new ImageIcon("src/AngryToadsImagePack/pause.png");
    private ImageIcon continueButton = new ImageIcon("src/AngryToadsImagePack/continue.png");
    private ImageIcon menuButton = new ImageIcon("src/AngryToadsImagePack/menu.png");
    private ImageIcon restartbutton = new ImageIcon("src/AngryToadsImagePack/resume.png");
    private ImageIcon nextLevelButton = new ImageIcon("src/AngryToadsImagePack/nextLevel.png");
    ImageIcon finger=new ImageIcon("src/AngryToadsImagePack/Finger.png");

    private boolean dragflag = false, insidePause = false, insideRestart = false, insideMenu = false, insideResume = false;
    private boolean flag=false;
    private boolean isPause = false;
    private int lor,mark;
    private float b1s = 1f, b2s = 1f, b3s = 1f, b4s = 1f;
    private int menuX, menuY, menuW, menuH, resumeX, resumeY, resumeW, resumeH;
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
                if (!isPause && !gameOver) {
                    if (insidePause) {
                        System.out.println("游戏暂停");
                        isPause = true;
                        myController.stop = true;
                    }
                    if (insideRestart) {
                        System.out.println("重新游戏");
                        myController.restart();
                    }
                }
                if (isPause && !gameOver) {
                    if (insideMenu) {
                        System.out.println("回到主菜单");
                        myController.backToMenu();
                    }
                    if (insideResume) {
                        System.out.println("游戏继续");
                        isPause = false;
                        myController.stop = false;
                    }
                }
                if (!isPause && gameOver) {
                    if (insideMenu) {
                        System.out.println("回到主菜单");
                        myController.backToMenu();
                    }
                    if (insideResume) {
                        if (hasWin) {
                            System.out.println("下一关");
                            // TODO: 下一关
                        } else {
                            System.out.println("重新游戏");
                            myController.restart();
                        }
                        gameOver = false;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!isPause) {
                    dragflag = (e.getButton() == MouseEvent.BUTTON3);
                }
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
                if (!isPause && !gameOver) {
                    insideMenu = insideResume = false;
                    if (e.getX() > 0 && e.getX() < 50 * b1s && e.getY() > 0 && e.getY() < 50 * b1s) {
                            b1s = 1.15f;
                            insidePause = true;
                    } else {
                        insidePause = false;
                        b1s = 1f;
                    }

                    if (e.getX() > 60 && e.getX() < 55 + 50 * b2s && e.getY() > 0 && e.getY() < 50 * b2s) {
                            b2s = 1.15f;
                            insideRestart = true;
                    } else {
                        b2s = 1f;
                        insideRestart = false;
                    }
                }else {
                    insidePause = insideRestart = false;
                    if (e.getX() > menuX && e.getX() < menuX + menuH && e.getY() > menuY && e.getY() < menuY + menuW) {
                            b3s = 1.15f;
                            insideMenu = true;
                    } else {
                        insideMenu = false;
                        b3s = 1f;
                    }
                    if (e.getX() > resumeX && e.getX() < resumeX + resumeH && e.getY() > resumeY && e.getY() < resumeY + resumeW) {
                        b4s = 1.15f;
                        insideResume = true;
                    } else {
                        insideResume = false;
                        b4s = 1f;
                    }
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

    public boolean render() {
        if (dbImage == null) {

            dbImage = createImage(PREF_WIDTH, PREF_HEIGHT);
            if (dbImage == null) {
                return false;
            }
            dbg = (Graphics2D) dbImage.getGraphics();
        }
        dbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 平滑处理
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
        dbg.drawImage(restartbutton.getImage(),55,0, (int) (50*b2s), (int) (50*b2s),null);

        if (isPause || gameOver) {
            drwaAlertBox();

            // 游戏暂停四个字
            dbg.setColor(Color.WHITE);
            dbg.setFont(new Font("微软雅黑", Font.PLAIN, 25));
            String info;

            if (isPause) {
                info = "游戏暂停";
            } else {
                if (hasWin) {
                    info = "游戏胜利";
                } else {
                    info = "游戏失败";
                }
            }

            if (insideResume) {
                if (isPause) {
                    info = "继续游戏";
                } else if (gameOver) {
                    if (hasWin) {
                        info = "玩下一关";
                    } else {
                        info = "重玩此关";
                    }
                }
            }
            if (insideMenu) {
                info = "回主菜单";
            }
            dbg.drawString(info, centerX - 50, centerY - 50);

            // 两个按钮
            menuX = centerX - 50 - (int)(25 * b3s);
            menuY = centerY - (int)(25 * b3s);
            menuW = menuH = (int)(50 * b3s);
            resumeX = centerX + 50 - (int)(25 * b4s);
            resumeY = centerY - (int)(25 * b4s);
            resumeW = resumeH = (int)(50 * b4s);

            dbg.drawImage(menuButton.getImage(), menuX, menuY, menuW, menuH, null);
            if (isPause) {
                dbg.drawImage(continueButton.getImage(), resumeX, resumeY, resumeW, resumeH, null);
            } else if (gameOver) {
                if (hasWin) {
                    dbg.drawImage(nextLevelButton.getImage(), resumeX, resumeY, resumeW, resumeH, null);
                } else {
                    dbg.drawImage(continueButton.getImage(), resumeX, resumeY, resumeW, resumeH, null);
                }
            }
        }

        return true;
    }

    private void drwaAlertBox() {
        // 淡化背景
        dbg.setColor(new Color(0.0f, 0.0f, 0.0f, 0.25f));
        dbg.fillRect(0, 0, centerX * 2, centerY * 2);

        // 外框
        dbg.setColor(new Color(46, 134, 114));
        dbg.fillRoundRect(centerX - 120, centerY - 100, 240, 100, 20, 20);
        dbg.setStroke(new BasicStroke(3));
        dbg.setColor(new Color(252, 182, 74));
        dbg.drawRoundRect(centerX - 120, centerY - 100, 240, 100, 20, 20);
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
