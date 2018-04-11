
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Model: Contains all the state and logic
 * Does not contain anything about images or graphics, must ask view for that
 *
 * has methods to
 *  detect collision with boundaries
 * decide next direction
 * provide direction
 * provide location
 **/

//LAB5    

public class Model{
	
	final int frameCount = 10;
    int xloc = 0;
    int yloc = 0;
    final int xIncr = 4;
    final int yIncr = 4;
    int frameWidth;
    int frameHeight;
    int imgWidth;
    int imgHeight;
    int frameStartSize;
    Direction d;
	
    
    public Model(int frameStartSize, int imgWidth, int imgHeight){
    	//this.frameWidth = frameWidth;
    	//this.frameHeight = frameHeight;
    	this.frameStartSize = frameStartSize;
    	this.imgHeight = imgHeight;
    	this.imgWidth = imgWidth;
    	
	}
    
    public void changeDirection(Direction direction) {
    		switch(direction) {
    		case NORTH:
    			d = Direction.NORTH;
    			break;
    		case EAST:
    			d = Direction.EAST;
    			break;
    		case SOUTH:
    			d = Direction.SOUTH;
    			break;
    		case WEST:
    			d = Direction.WEST;
    			break;
    		}
    }
    
    public void jump() {
    		
    }
   
   /* public void randomDirection() {
    		Random random = new Random();
    		int rand = random.nextInt(4);
    		
    		ArrayList<Direction> dirList = new ArrayList<Direction>();
    		dirList.add(Direction.NORTH);
    		dirList.add(Direction.SOUTH);
    		dirList.add(Direction.EAST);
    		dirList.add(Direction.WEST);
    		
    		boolean notEqual = true;
    		while(notEqual) {
    			if(d == dirList.get(rand)) {
    				rand = random.nextInt(4);
    			} else {
    				notEqual = false;
    				d = dirList.get(rand);
    			}
    		}
    }*/
    
    public void updateLocationAndDirection(){
    	if (yloc >= frameStartSize-imgHeight) { // orc has hit bottom wall
			d = Direction.NORTH;
		} else if (xloc >= frameStartSize-imgWidth) { // orc has hit right wall
			d = Direction.WEST;
		} else if (yloc <= 0) { // orc has hit top wall
			d = Direction.SOUTH;
		} else if (xloc <= 0) {// orc has hit left wall
			d = Direction.EAST;
		}
    	
    	switch(d){
    	case NORTH:
    		yloc-=yIncr;
    		break;
    	case SOUTH:
    		yloc+=yIncr;
    		break;
    	case EAST:
    		xloc+=xIncr;
    		break;
    	case WEST:
    		xloc-=xIncr;
    		break;
    	}
    }
    
    public int getX(){
    	return xloc;
    }
    
    public int getY(){
    	return yloc;
    }
    
    public Direction getDirect(){
    	return d;
    }
    
}
