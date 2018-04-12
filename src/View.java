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

	final static int imgWidth = 165;
	final static int imgHeight = 165;
	//final int xIncr = 1;
	//final int yIncr = 1;
	int picNum = 0;
	int picNumJump = 0;
	int picNumFire = 0;
	final int frameCount = 10;
	final int frameCountJump = 8;
	final int frameCountFire = 4;
	BufferedImage[][] pics;
	BufferedImage[][] picsJumping;
	BufferedImage[][] picsFire;
	private int xloc;
	private int yloc;
	private Direction d = Direction.EAST;
	JFrame frame; 

	public boolean getMoving = true;
	public boolean jump = false;
	public boolean fire = false;
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
		for (Direction d: Direction.values()) {
			fileNames.add("orc_fire_" + d.getName() + ".png");
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

		pics = new BufferedImage[fileNames.size()/3][];
		picsJumping = new BufferedImage[fileNames.size()/3][];
		picsFire = new BufferedImage[fileNames.size()/3][];
		for (Direction d : Direction.values()) {
			BufferedImage img = createImage(fileNames.get(d.ordinal()));
			BufferedImage imgJump = createImage(fileNames.get(d.ordinal()+8));
			BufferedImage imgFire = createImage(fileNames.get(d.ordinal()+16));
			pics[d.ordinal()] = new BufferedImage[frameCount];
			picsJumping[d.ordinal()] = new BufferedImage[frameCountJump];
			picsFire[d.ordinal()] = new BufferedImage[frameCountFire];
			for (int i = 0; i < frameCount; i++) {
				pics[d.ordinal()][i] = img.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
			}
			for (int i = 0; i < frameCountJump; i++) {
				picsJumping[d.ordinal()][i] = imgJump.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
			}
			for (int i = 0; i < frameCountFire; i++) {
				picsFire[d.ordinal()][i] = imgFire.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
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
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.gray);
			setBackground(Color.gray);
			picNum = (picNum + 1) % frameCount;

			if(picNumJump>=8) {
				picNumJump = 0;
				jump = false;
			}

			if(picNumFire>=4) {
				picNumFire = 0;
				fire = false;
			}

			if(jump == true) {
				g.drawImage(picsJumping[d.ordinal()][picNumJump], xloc, yloc, Color.gray, this);
				picNumJump++;
				System.out.println("run:" + picNumJump);
			} else {
				g.drawImage(pics[d.ordinal()][picNum], xloc, yloc, Color.gray, this);
			}

			if(fire == true) {
				g.drawImage(picsFire[d.ordinal()][picNumFire], xloc, yloc, Color.gray, this);
				picNumFire++;
				System.out.println("run:" + picNumFire);
			}
		}

		public Dimension getPreferredSize() {
			return new Dimension(frameStartSize, frameStartSize);
		}

	}
	public void jump() {
		jump = true;
	}

	public void fire() {
		fire = true;
	}

}