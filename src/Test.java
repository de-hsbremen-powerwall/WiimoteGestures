import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Test{
	
	static final int PLAYERS = 1;
	static final int SCREEN_WIDTH = 1280;
	static final int SCREEN_HEIGHT = 720;
	static final boolean SENSORBAR_POS = false; // true = above, false = below
	
	WiiMoteManager wiiMgr;
	WiiPanel panel;
	JFrame frame;
	
	Player[] p = null;
	int[] score = null;
	int[] ammo = null;
	int[] reloader = null;
	int[][] playerhud = { {10,25} , {1100,25} ,{10,670} ,{1100,670} };
	
	public static Timer timer;
	public static int counterValue = 60; 
	
	boolean startmenu = true;
	static boolean gamefinished = false;
	
	public BufferedImage bg;
	
	
	public Test(WiiMoteManager mgr){
		
		wiiMgr = mgr;
		
		try {                
	          bg = ImageIO.read(new File("img/background.png"));
	       } catch (IOException ex) {
	            // handle exception...
	       }
		
		// We Create a Frame and add our special WiiPanel!
        frame = new JFrame();
        frame.setTitle("ClickClickClick!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        panel = new WiiPanel();  
        frame.getContentPane().add(panel);
        frame.setVisible(true);  
	}
	
	public static void main (String args[]){
		//We need some nesting here to make sure the WiiMoteManager is initialized before the actual game starts!
		WiiMoteManager mgr = new WiiMoteManager(PLAYERS, SCREEN_WIDTH, SCREEN_HEIGHT, SENSORBAR_POS);
		Test t = new Test(mgr);
		
		timer = new Timer(1000, new ActionListener() { 
		      
		      public void actionPerformed(ActionEvent e) { 
		        // 1 Sekunde abziehen 
		        counterValue--; 
		        System.out.println(counterValue); 
		        
		        // Falls ZŠhler = 0, Countdown abgelaufen! 
		        if(counterValue == 0){ 
		          System.out.println("Counterdown ausgelaufen!"); 
		          
		          // Timer stoppen 
		          timer.stop(); 
		          gamefinished = true;

		        } 
		      } 
		    }); 
		

		
		//mgr.setMotionMode();

		
		t.play();
	}
	
	public void play(){
		
		// Get all player Objects once and initialize all variables
		p = new Player[wiiMgr.getPlayerCount()];
		score = new int[wiiMgr.getPlayerCount()];
		ammo = new int[wiiMgr.getPlayerCount()];
		reloader = new int[wiiMgr.getPlayerCount()];
		for(int i = 0; i<p.length; i++){
			p[i] = wiiMgr.getPlayer(i);
			score[i] = 0;
			ammo[i] = 6;
			reloader[i] = 0;
		}
		
		// Create a circle in the middle and wait for all players to focus it at least once!
		boolean p1ready = false;
		boolean p2ready = false;
		boolean p3ready = false;
		boolean p4ready = false;
		
		while(true){
			for(int i = 0; i<p.length; i++){
				if(p[i].isAPressed() && wiiMgr.isPointerModeActive(i)) {
					if(
						    (p[i].getX() > (panel.getPointX() - 40) && p[i].getX() < (panel.getPointX() + 40) && 
							 p[i].getY() > (panel.getPointY() - 40) && p[i].getY() < (panel.getPointY() + 40)) ||
							((p[i].getX()-( (int)p[i].getZ()/10)) > (panel.getPointX() - 40) && (p[i].getX()-( (int)p[i].getZ()/10)) < (panel.getPointX() + 40) && 
							 (p[i].getY()-( (int)p[i].getZ()/10)) > (panel.getPointY() - 40) && (p[i].getY()-( (int)p[i].getZ()/10)) < (panel.getPointY() + 40)) ||
							((p[i].getX()+( (int)p[i].getZ()/10)) > (panel.getPointX() - 40) && (p[i].getX()+( (int)p[i].getZ()/10)) < (panel.getPointX() + 40) && 
							 (p[i].getY()+( (int)p[i].getZ()/10)) > (panel.getPointY() - 40) && (p[i].getY()+( (int)p[i].getZ()/10)) < (panel.getPointY() + 40)) 
							){
								if(i == 0){
									p1ready = true;
									System.out.println("P1 GO!");}
								else if(i == 1){
									p2ready = true;
								System.out.println("P2 GO!");}
								else if(i == 2)
									p3ready = true;
								else if(i == 3)
									p4ready = true;
					}
					p[i].setAPressed(false);	
				}
			}
			
			if((p.length == 1 && p1ready) ||
				(p.length == 2 && p1ready && p2ready) ||
				(p.length == 3 && p1ready && p2ready && p3ready) ||
				(p.length == 4 && p1ready && p2ready && p3ready && p4ready)){
				System.out.println("SUCCESS!");
				startmenu = false;
				panel.setPointInvisible();
				break;
			} /* else {
				p1ready = false;
				p2ready = false;
				p3ready = false;
				p4ready = false;
			} */
		}
		
		// Start the Timer
		timer.start();
		
		// Start playing!
		while(true){
			
			// Check Buttons for all players!
			for(int i = 0; i<p.length; i++){
				
				if(p[i].isAPressed() && wiiMgr.isPointerModeActive(i)) {		
					if(ammo[i] > 0){
						System.out.println("Player " + (i+1) + ": Shot!");
						ammo[i]--;
					
						// Check if the player shot the circle
						if(
					    (p[i].getX() > (panel.getPointX() - 20) && p[i].getX() < (panel.getPointX() + 20) && 
						 p[i].getY() > (panel.getPointY() - 20) && p[i].getY() < (panel.getPointY() + 20)) ||
						((p[i].getX()-( (int)p[i].getZ()/10)) > (panel.getPointX() - 20) && (p[i].getX()-( (int)p[i].getZ()/10)) < (panel.getPointX() + 20) && 
						 (p[i].getY()-( (int)p[i].getZ()/10)) > (panel.getPointY() - 20) && (p[i].getY()-( (int)p[i].getZ()/10)) < (panel.getPointY() + 20)) ||
						((p[i].getX()+( (int)p[i].getZ()/10)) > (panel.getPointX() - 20) && (p[i].getX()+( (int)p[i].getZ()/10)) < (panel.getPointX() + 20) && 
						 (p[i].getY()+( (int)p[i].getZ()/10)) > (panel.getPointY() - 20) && (p[i].getY()+( (int)p[i].getZ()/10)) < (panel.getPointY() + 20)) 
						){
							System.out.println("Player " + (i+1) + ": BLAM BLAM BLAM!");
							score[i]++;
							panel.setPointInvisible();
						}	
					}
					p[i].setAPressed(false);
				}
				
				if(p[i].isBPressed() && ammo[i] < 6 && wiiMgr.isPointerModeActive(i)) {
					System.out.println("Player " + (i+1) + ": B is pressed");
					if(wiiMgr.isPointerModeActive(i))
						wiiMgr.setMotionMode(i);
					p[i].setBPressed(false);
				}

				if(!wiiMgr.isPointerModeActive(i) && p[i].isMotionZActive()){
					System.out.println("RELOADING");
					reloader[i]++;
					p[i].setMotionZActive(false);
					if(reloader[i] > 5){
						System.out.println("RELOADED!!!!!!!");
						ammo[i] = 6;
						reloader[i] = 0;
						wiiMgr.setPointerMode(i);
					}
				}
				
				
			}
			
			
			// Force some wait before the next iteration starts
			try { 
				for(int i = 0; i<p.length; i++){
					p[i].resetAllButtons();
				}
				Thread.sleep(10);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			if(gamefinished){
		        panel.setPointInvisible();
				break;
			}

		}
		
		while(true){
			if(p[0].isAPressed() && wiiMgr.isPointerModeActive(0))
				if(
					    (p[0].getX() > (panel.getPointX() - 40) && p[0].getX() < (panel.getPointX() + 40) && 
						 p[0].getY() > (panel.getPointY() - 40) && p[0].getY() < (panel.getPointY() + 40)) ||
						((p[0].getX()-( (int)p[0].getZ()/10)) > (panel.getPointX() - 40) && (p[0].getX()-( (int)p[0].getZ()/10)) < (panel.getPointX() + 40) && 
						 (p[0].getY()-( (int)p[0].getZ()/10)) > (panel.getPointY() - 40) && (p[0].getY()-( (int)p[0].getZ()/10)) < (panel.getPointY() + 40)) ||
						((p[0].getX()+( (int)p[0].getZ()/10)) > (panel.getPointX() - 40) && (p[0].getX()+( (int)p[0].getZ()/10)) < (panel.getPointX() + 40) && 
						 (p[0].getY()+( (int)p[0].getZ()/10)) > (panel.getPointY() - 40) && (p[0].getY()+( (int)p[0].getZ()/10)) < (panel.getPointY() + 40)) 
						){
						break;
				}
			p[0].setAPressed(false);
		}
		
		System.exit(0);
	}
	
	// This Class has access to the wiiMgr and is responsible for drawing
	class WiiPanel extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private boolean pointVisible = false;
		private int pointX = 0;
		private int pointY = 0;

		public void paintComponent(Graphics g){
			//g.setColor(getBackground());
	        //g.fillRect(0, 0, getWidth(), getHeight());


			
	        g.drawImage(bg, 0,0,null);
			
	        if(startmenu || gamefinished){
	        	if(!pointVisible){
		        	// If no point is visible, calcucalte a new one
		        	pointX = (SCREEN_WIDTH/2);
		        	pointY = (SCREEN_HEIGHT/2);
		        	System.out.println("Draw point at: " + pointX + " and " + pointY);
		        	pointVisible = true;
		        } else {
		        	// If a point is visible, draw it!
		        	g.setColor(Color.BLACK);
		        	g.fillOval((pointX-40), (pointY-40), 80, 80);
		        }
	        } else {
		        if(!pointVisible){
		        	// If no point is visible, calcucalte a new one
		        	pointX = (int) ( Math.random() * (SCREEN_WIDTH-40) + 10 );
		        	pointY = (int) ( Math.random() * (SCREEN_HEIGHT-40) + 10 );
		        	System.out.println("Draw point at: " + pointX + " and " + pointY);
		        	pointVisible = true;
		        } else {
		        	// If a point is visible, draw it!
		        	g.setColor(Color.BLACK);
		        	g.fillOval((pointX-20), (pointY-20), 40, 40);
		        }
	        }
	        
	        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 24);
	        g.setFont(font);
	        for(int i = 0; i<wiiMgr.getPlayerCount(); i++){
	        	g.drawString("Player "+ (i+1) +": " + score[i] , playerhud[i][0], playerhud[i][1]);
	        }
	        
	        for(int i = 0; i<wiiMgr.getPlayerCount(); i++){
	        	
	        	if(wiiMgr.isPointerModeActive(i)){
		        	g.setColor(wiiMgr.getPlayer(i).getPlayerColor());
		        	g.fillOval( 
		        		(wiiMgr.getPlayer(i).getX() - ((int)wiiMgr.getPlayer(i).getZ()/10)  ), 
		        		(wiiMgr.getPlayer(i).getY() - ((int)wiiMgr.getPlayer(i).getZ()/10)  ), 
		        		( (int)wiiMgr.getPlayer(i).getZ()/10), 
		        		( (int)wiiMgr.getPlayer(i).getZ()/10)
		        	);
	        	}
	        }
			repaint();
	    	updateUI();
		}
		
		public int getPointX(){
			return pointX;
		}
		
		public int getPointY(){
			return pointY;
		}
		
		public void setPointInvisible(){
			pointVisible = false;
		}
	}
	
}