import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;


/**
 * View: Contains everything about graphics and images
 * Know size of world, which images to load etc
 *
 * has methods to
 * provide boundaries
 * use proper images for direction
 * load images for all direction (an image should only be loaded once!!! why?)
 **/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * View: Contains everything about graphics and images Know size of world, which
 * images to load etc
 *
 * has methods to provide boundaries use proper images for direction load images
 * for all direction (an image should only be loaded once!!! why?)
 **/
//LAB5  
 //LAB 8
public class View extends JPanel {
	int picNumJump = 0;
	final static int imgWidth = 165;
	final static int imgHeight = 165;
	//final int xIncr = 1;
    //final int yIncr = 1;
	int picNum = 0;
	final int frameCount = 10;
	final int frameCountJump = 8;
    BufferedImage[][] pics;
    BufferedImage[][] picsJumping;
    private int xloc;
    private int yloc;
    private Direction d = Direction.EAST;
    JFrame frame; 
    int jumpCount = 0;
    
    String jumpDirection;
    public boolean getMoving = true;
    public boolean jump =false;
    final int frameStartSize = 800;
    final int drawDelay = 30; //msec
    DrawPanel drawPanel = new DrawPanel();
    Action drawAction;
    static Controller c;
    
    
	public View() {
		ArrayList<String> fileNames = new ArrayList<String>(); 
		for (Direction d : Direction.values()) {
			fileNames.add("orc_forward_" + d.getName() + ".png");
		}
		for (Direction d : Direction.values()) {
			fileNames.add("orc_jump_" + d.getName() + ".png");
		}
		
		frame = new JFrame();
		
		drawAction = new AbstractAction(){
			//this is the loop - every 30 msec (drawDelay) this is called 
			public void actionPerformed(ActionEvent e){
				if(getMoving == true) {
					drawPanel.repaint();
					c.start();
				}
			}
		};
		add(drawPanel); 
		
		pics = new BufferedImage[fileNames.size()/2][];
		for (Direction d : Direction.values()) {
			BufferedImage img = createImage(fileNames.get(d.ordinal()));
			pics[d.ordinal()] = new BufferedImage[10];
			for (int i = 0; i < frameCount; i++) {
				pics[d.ordinal()][i] = img.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
			}
		}
		
		picsJumping = new BufferedImage[fileNames.size()/2][];
		for (Direction d : Direction.values()) {
			BufferedImage img = createImage(fileNames.get(d.ordinal()+8));
			picsJumping[d.ordinal()] = new BufferedImage[10];
			for (int i = 0; i < frameCountJump; i++) {
				picsJumping[d.ordinal()][i] = img.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
			}
		}
		
		JButton b1 = new JButton();
	   	 try {
	   		    Image imgButton = ImageIO.read(new File("images/buttons/engine-start-stop-button-300px.png"));
	   		    b1.setIcon(new ImageIcon(imgButton));
	   		  } catch (Exception ex) {
	   		    System.out.println(ex);
	   		}
	   	 	b1.setSize(200,100);
	   		b1.setVisible(true);
	   		b1.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
	   		
	   		b1.addActionListener(new ActionListener() {
	   	       public void actionPerformed(ActionEvent ae){
	   	    	   //Whenever the button is clicked it prints out clicked, we want it to stop movement 
	   	           if(getMoving == false) {
	   	        	   getMoving = true;
	   	           }
	   	           else {
	   	        	   getMoving = false;
	   	           }
	   	           
	   	       } 
	   	    });
	   	drawPanel.add(b1);
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameStartSize, frameStartSize);
		frame.setBackground(Color.gray);
		frame.setVisible(true);
		frame.pack();
	}
	
	//Read image from file and return
    private BufferedImage createImage(String file){
    	BufferedImage bufferedImage;
    	try {
    		bufferedImage = ImageIO.read(new File("images/orc/" + file));
    		return bufferedImage;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return null;
    }

	//main method to run the program
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				c = new Controller();
				Timer t = new Timer(c.drawDelay, c.drawAction);
				t.start();
			}
		});
	}


	public int getImageWidth() {
		return imgWidth;
	}

	public int getImageHeight() {
		return imgHeight;
	}
	
	public int getFrameStartSize(){
		return frameStartSize;
	}
	
	public Action getdrawAction(){
		return drawAction;
	}

	public void update(int x, int y, Direction d1) {
		this.xloc = x;
		this.yloc = y;
		this.d = d1;
	}
	
	public void directionListener(Component c) {
		frame.add(c);
	}
	
	@SuppressWarnings("serial")
	private class DrawPanel extends JPanel{
		int picNum = 0;
		//int picNumJump = 0;
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.gray);
			setBackground(Color.gray);
	    	picNum = (picNum + 1) % frameCount;
	   // 	picNumJump = (picNumJump + 1) % frameCountJump;
    	  	
	    	if(picNumJump>=8) {
	    		System.out.println("here");
	    		picNumJump = 0;
	    		jump = false;
	    	}
	    	
	    	if(jump == true) {
	    		System.out.println(picNumJump);
	    		//System.out.print(picsJumping[d.ordinal()][picNumJump]);
		    	//jumpCount++;
	    		g.drawImage(picsJumping[d.ordinal()][picNumJump], xloc, yloc, Color.gray, this);
	    		picNumJump++;
	    		System.out.println("run:" + picNumJump);
	    	} else {
	    		g.drawImage(pics[d.ordinal()][picNum], xloc, yloc, Color.gray, this);
	    	}
		}
		
		public Dimension getPreferredSize() {
			return new Dimension(frameStartSize, frameStartSize);
		}
		
	}
	public void jump() {
		jump = true;
		//picNumJump = 0;
	}

}