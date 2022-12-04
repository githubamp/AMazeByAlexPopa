package edu.wm.cs.cs301.AlexPopa.gui;

import java.util.Arrays;

import edu.wm.cs.cs301.AlexPopa.generation.*;
import edu.wm.cs.cs301.AlexPopa.gui.Robot.Direction;
import edu.wm.cs.cs301.AlexPopa.gui.Robot.Turn;

/**
 *  Class: Wizard
 *  
 * 	Responsibilities: Assign robot
 * 					  Set maze
 * 					  drive2Exit goes toward the exit
 * 					  drive1Step2Exit goes one step toward the exit
 * 					  getEnergyConsumption returns how much energy was used
 * 					  getPathLength returns how many cells have been traveled through
 * 					  know the path out of the maze
 * 
 * 	Collaborators: RobotDriver
 * 				   Robot
 * 				   Maze
 * 
 * 	@author ALEX POPA
 */

public class Wizard implements RobotDriver{

	protected Robot robot; //Robot that will be in the maze
	protected Maze maze; //maze to traverse through
	
	/**
	 * 	Constructor that accepts a maze and robot as parameters
	 */
	public Wizard(Robot r, Maze m){
		robot = r;
		maze = m;
	}
	
	/**
	 * 	Default constructor
	 */
	public Wizard() {	
	}
	
	public Robot getRobot() {
		return robot;
	}
	
	@Override
	public void setRobot(Robot r) {
		/**
		 * 	Set robot variable to r
		 */
		robot = r;
	}

	@Override
	public void setMaze(Maze m) {
		/**
		 * 	Set maze variable to m
		 */
		maze = m;
	}

	@Override
	public boolean drive2Exit() throws Exception {
		/**
		 * 	while robot has energy and has not crashed
		 * 		keep moving toward the exit by using the getNeighborClosertoExit() method
		 * 	if we are at the exit, rotate to face it and return true
		 * 	
		 * 	if robot crashes or runs out of energy
		 * 		throw an exception
		 * 	
		 */
		
		while(robot.hasStopped() != true) {
			drive1Step2Exit();
			
			//if the robot won by being at the exit cell and looking at the exit
			if(robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD) && Arrays.equals(robot.getCurrentPosition(), maze.getExitPosition())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		/**
		 * 	move one step toward the exit by using the getNeighborClosertoExit() method and return true
		 * 
		 * 	if we are at the exit, rotate to face it and return false
		 * 	
		 * 	if robot crashes or runs out of energy
		 * 		throw an exception
		 * 	
		 */
		int[] next, curr;
		
		assert(robot != null);
		assert(maze != null);
		
		if(robot.hasStopped() != true) {
			curr = robot.getCurrentPosition();
			next = maze.getNeighborCloserToExit(curr[0], curr[1]);
			
			//if the robot is at the exit cell, turn it so it faces the exit
			if(Arrays.equals(curr, maze.getExitPosition())) {
				if(robot.canSeeThroughTheExitIntoEternity(Direction.LEFT)) {
					robot.rotate(Turn.LEFT);
					return false;
				}else if(robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD)) {
					return false;
				}else if(robot.canSeeThroughTheExitIntoEternity(Direction.RIGHT)) {
					robot.rotate(Turn.RIGHT);
					return false;
				}else if(robot.canSeeThroughTheExitIntoEternity(Direction.BACKWARD)) {
					robot.rotate(Turn.AROUND);
					return false;
				}
			}
			
			//if the cell closer to the exit is up
			if(curr[0] == next[0] && (curr[1]-1) == next[1]) {
				if(robot.getCurrentDirection() == CardinalDirection.West) {
					robot.rotate(Turn.LEFT);
					robot.move(1);
					return true;

				}else if (robot.getCurrentDirection() == CardinalDirection.North) {
					robot.move(1);
					return true;

				}else if(robot.getCurrentDirection() == CardinalDirection.East) {
					robot.rotate(Turn.RIGHT);
					robot.move(1);
					return true;

				}else if(robot.getCurrentDirection() == CardinalDirection.South) {
					robot.rotate(Turn.AROUND);
					robot.move(1);
					return true;

				}
			//if the cell closer to the exit is down
			}else if(curr[0] == next[0] && (curr[1]+1) == next[1]) {
				if(robot.getCurrentDirection() == CardinalDirection.West) {
					robot.rotate(Turn.RIGHT);
					robot.move(1);
					return true;

				}else if (robot.getCurrentDirection() == CardinalDirection.North) {
					robot.rotate(Turn.AROUND);
					robot.move(1);
					return true;

				}else if(robot.getCurrentDirection() == CardinalDirection.East) {
					robot.rotate(Turn.LEFT);
					robot.move(1);
					return true;

				}else if(robot.getCurrentDirection() == CardinalDirection.South) {
					robot.move(1);
					return true;

				}
			//if the cell closer to the exit is left
			}else if(curr[0]-1 == next[0] && (curr[1]) == next[1]) {
				if(robot.getCurrentDirection() == CardinalDirection.West) {
					robot.move(1);
					return true;

				}else if (robot.getCurrentDirection() == CardinalDirection.North) {
					robot.rotate(Turn.RIGHT);
					robot.move(1);
					return true;

				}else if(robot.getCurrentDirection() == CardinalDirection.East) {
					robot.rotate(Turn.AROUND);
					robot.move(1);
					return true;

				}else if(robot.getCurrentDirection() == CardinalDirection.South) {
					robot.rotate(Turn.LEFT);
					robot.move(1);
					return true;

				}
			//if the cell closer to the exit is right
			}else if(curr[0]+1 == next[0] && (curr[1]) == next[1]) {
				if(robot.getCurrentDirection() == CardinalDirection.West) {
					robot.rotate(Turn.AROUND);
					robot.move(1);
					return true;

				}else if (robot.getCurrentDirection() == CardinalDirection.North) {
					robot.rotate(Turn.LEFT);
					robot.move(1);
					return true;

				}else if(robot.getCurrentDirection() == CardinalDirection.East) {
					robot.move(1);
					return true;

				}else if(robot.getCurrentDirection() == CardinalDirection.South) {
					robot.rotate(Turn.RIGHT);
					robot.move(1);
					return true;
				}
			}
		}else if(robot.hasStopped() == true) {
			throw new Exception();
		}
		return false;
	}

	@Override
	public float getEnergyConsumption() {
		/**
		 * 	return the initial battery value (3500) minus what is the current battery level
		 */
		return ((ReliableRobot) robot).getInitialBattery() - robot.getBatteryLevel();
	}

	@Override
	public int getPathLength() {
		/**
		 * 	return odometer from the ReliableRobot class
		 */
		return robot.getOdometerReading();
	}
}
