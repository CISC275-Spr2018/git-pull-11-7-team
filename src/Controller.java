

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Action;
import javax.swing.JTextField;

/**
 * Do not modify this file without permission from your TA.
 **/
public class Controller {

	private Model model;
	private View view;
	Action drawAction;
	final int drawDelay = 30; //msec
	
	
	public Controller(){
		view = new View();
		drawAction = view.getdrawAction();
		//CHANGE TO CONTROLLER
		model = new Model(view.getFrameStartSize(), view.getImageWidth(), view.getImageHeight());
		JTextField textField = new JTextField();
		textField.addKeyListener(new KeyListener());
		view.directionListener(textField);
	//	frame.add(textField);

	}

        //run the simulation
	public void start(){
//		for(int i = 0; i < 5000; i++)
//		{
			//increment the x and y coordinates, alter direction if necessary
			model.updateLocationAndDirection();
			//update the view
			view.update(model.getX(), model.getY(), model.getDirect());
//		}
	}

private class KeyListener extends KeyAdapter
{
	@Override
	public void keyPressed(KeyEvent event) {		
		if(event.getKeyCode() == KeyEvent.VK_UP) {
			model.changeDirection(Direction.NORTH);
		} else if(event.getKeyCode() == KeyEvent.VK_RIGHT) {
			model.changeDirection(Direction.EAST);
	} 		else if(event.getKeyCode() == KeyEvent.VK_DOWN) {
				model.changeDirection(Direction.SOUTH);
}				else if(event.getKeyCode() == KeyEvent.VK_LEFT) {
					model.changeDirection(Direction.WEST);
				}	else if(event.getKeyCode() == KeyEvent.VK_SPACE) {
							view.jump();

				}
	}
}
}