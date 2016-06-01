package AngryToadsApplication;

import java.io.File;
import javax.media.*; 

/**
 * 调用方法:<br />
 * AngryToadsMusic music = new AngryToadsMusic("music/title_theme.wav");<br />
 * music.setLoop(true);<br />
 * music.start();<br />
 * ...<br />
 * music.stop();
 * 
 * @author 范志康
 */

public class AngryToadsMusic implements ControllerListener {
	
	// JMF的播放器
    private Player player;
    
    // 记录下文件以循环
    private File file;

    // 标示是否需要循环
    private boolean loop = false;
    
    // 构造方法
    public AngryToadsMusic(String fileName) {
    	super();
    	setFile(fileName);
    }
    
    public void setFile(String fileName) {
    	file = new File("src/AngryToadsMusic/" + fileName);
    }
    
    public void controllerUpdate(ControllerEvent e) {  
        if (e instanceof EndOfMediaEvent) {
           if (loop) {
        	   start();
           }
           return;
        }
        if (e instanceof PrefetchCompleteEvent) {
            player.start();
            return;
        }
    }
    
    public void start() { 
    	try {
            // 创建一个打开选择文件的播放器
            player = Manager.createPlayer(file.toURI().toURL());
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (player == null) {
            System.out.println("无法创建播放器.");
            return;
        }
        
        player.addControllerListener(this);
        player.prefetch(); 
    }  

	public void stop() {
		 player.stop();
		 player.close();
	}

	// 处理“循环”
	public void setLoop(boolean newLoop) {
	    loop = newLoop;
	}
	
}
