package edu.wm.cs.cs301.AlexPopa.gui;


import java.util.Arrays;

import edu.wm.cs.cs301.AlexPopa.generation.Maze;
import edu.wm.cs.cs301.AlexPopa.generation.Wall;
import edu.wm.cs.cs301.AlexPopa.gui.Robot.Direction;
import edu.wm.cs.cs301.AlexPopa.gui.Robot.Turn;

/**
 *  Class: WallFollower
 *  
 * 	Responsibilities: only have a sensor on the front and left of the robot
 * 					  drive to the exit
 * 					  pause when repairing sensors
 * 
 * 	Collaborators: RobotDriver
 * 				   Robot 
 * 				   Maze
 * 
 * 	@author ALEX POPA
 */

public class WallFollower extends Wizard implements RobotDriver {

	public WallFollower(){

	}
	/**
	 * 	constructor that takes a robot and maze and calls the Wizard constructor method
	 */
	public WallFollower(Robot r, Maze m) {
		robot = r;
		maze = m;
	}

	public Robot getRobot(){
		return robot;
	}
	
	/**
	 * 	while robot has energy and has not crashed
	 * 		keep moving toward the exit by using the drive1Step2Exit() method
	 * 	if we are at the exit, rotate to face it and return true
	 * 	
	 * 	if robot crashes or runs out of energy
	 * 		throw an exception
	 * 	
	 */
	
	@Override
	public boolean drive2Exit() throws Exception {
		while(robot.hasStopped() != true) {
			drive1Step2Exit();
			
			//if the robot won by being at the exit cell and looking at the exit
			if(robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD) && robot.isAtExit()) {
				return true;
			}
		}
		return false;
	}

	/**
	 *	move one step toward the exit by hugging the left wall
	 *
	 *	if a sensor is down, wait until it's repaired
	 *
	 *	if there is a wall to the left and no wall forward, move forward
	 *
	 *	if there is no wall to the left, turn left and move forward
	 *	
	 *	if there is a wall to the left and front of the robot, turn right
	 *
	 *	if we are at the exit position, rotate to face it and return false
	 * 	
	 * 	if robot crashes or runs out of energy
	 * 		throw an exception
	 */
	@Override
	public boolean drive1Step2Exit() throws Exception {
		if(robot.hasStopped() != true) { // if robot has not crashed or ran out of energy
			if(robot.isAtExit()) {
				if(robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD)) { // at exit
					return false;
				}else if(robot.canSeeThroughTheExitIntoEternity(Direction.LEFT)){ // exit is to the left
					robot.rotate(Turn.LEFT);
					return false;
				}else {	//exit is backwards or to the right
					robot.rotate(Turn.LEFT);
					return true;
				}
			}
			
			if(robot.distanceToObstacle(Direction.LEFT) == 0 && robot.distanceToObstacle(Direction.FORWARD) != 0) { // wall on left but not forward
				robot.move(1);
				return true;
			}else if(robot.distanceToObstacle(Direction.LEFT) != 0){ // no wall to the left
				robot.rotate(Turn.LEFT);
				robot.move(1);
				return true;
			}else if(robot.distanceToObstacle(Direction.LEFT) == 0 && robot.distanceToObstacle(Direction.FORWARD) == 0) { //wall forward and left
				robot.rotate(Turn.RIGHT);
				return true;
			}
		}
		return false;
	}
}