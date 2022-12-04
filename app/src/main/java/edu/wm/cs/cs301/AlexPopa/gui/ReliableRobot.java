package edu.wm.cs.cs301.AlexPopa.gui;


import java.util.Arrays;

import edu.wm.cs.cs301.AlexPopa.generation.*;
import edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput;

/**
 * 	Class: ReliableRobot
 * 
 * 	Responsibilities: turns 90 degrees
 * 					  describe directions of the robot 
 * 					  setController gives the robot the maze
 * 					  addDistanceSensor adds a sensor to detect how far until a wall is reached
 * 					  getCurrentPosition returns the current position of the robot
 * 					  getCurrentDirection returns which direction the robot is facing
 * 					  getBatteryLevel returns the current battery level
 * 					  setBatteryLevel sets the battery level to some number
 * 					  getEnergyForFullRotation returns how much energy is needed to spin 360 degrees
 * 					  getEnergyForStepForward returns how much energy is needed to move one space forward
 * 					  getOdometerReading returns the distance traveled by the robot
 * 					  resetOdometer resets the odometer to 0
 * 					  rotate rotates the robot
 * 					  move the robot a certain number of spaces
 * 					  can jump over walls
 * 					  return if at the exit
 * 					  return if inside a room
 * 					  return if the robot has stopped moving
 * 					  return the distance to a wall
 * 					  determine if the robot is looking through the exit out of the maze
 * 					  throw UnsupportedOperationException for other methods
 * 					  
 * 	Collaborators: Robot
 * 				   Direction
 * 				   Control
 * 				   DistanceSensor
 * 				   ReliableSensor
 * 				   CardinalDireciton
 * 				   Maze
 * 
 * 	@author ALEX POPA
 */

public class ReliableRobot implements Robot{

	private final float initial = 3500;
	protected float battery = initial; //stores powerSupply
	protected int odometer = 0; //stores how many steps the robot has taken
	protected StatePlaying controller;
	protected DistanceSensor forwardSensor, leftSensor, backwardSensor, rightSensor;
	protected Maze maze;
	protected boolean stopped = false;

	/**
	 * 	constructor that accepts a controller and also sets the maze
	 */
	public ReliableRobot(StatePlaying c) {
		controller = c;
		maze = c.getMaze();
	}
	
	/**
	 * 	Default constructor
	 */
	public ReliableRobot() {
	}
	
	public void setController(StatePlaying c) {
		/**
		 * 	set controller to c;
		 */
		controller = c;
		maze = controller.getMaze();
	}

	@Override
	public void addDistanceSensor(DistanceSensor sensor, Direction mountedDirection) {
		/**
		 * 	Set the distance sensor to the appropriate direction (ex: left would be assigned to leftSensor)
		 */
		if(mountedDirection == Direction.FORWARD) {
			forwardSensor = new ReliableSensor(mountedDirection);
			forwardSensor.setMaze(maze);
		}else if(mountedDirection == Direction.RIGHT) {
			rightSensor = new ReliableSensor(mountedDirection);
			rightSensor.setMaze(maze);
		}else if(mountedDirection == Direction.LEFT) {
			leftSensor = new ReliableSensor(mountedDirection);
			leftSensor.setMaze(maze);
		}else if(mountedDirection == Direction.BACKWARD) {
			backwardSensor = new ReliableSensor(mountedDirection);
			backwardSensor.setMaze(maze);
		}
	}

	@Override
	public int[] getCurrentPosition() throws Exception {
		/**
		 * 	return current position as an int array with the first index being the x value and the second being the y
		 * 
		 * 	throw an exception if out of the maze
		 */
		
		assert(controller != null);
		assert(maze != null);
		
		int[] coor = controller.getCurrentPosition();
		
		if(!maze.isValidPosition(coor[0], coor[1])) {
			throw new Exception();
		}else {
			return coor;
		}
	}

	@Override
	public CardinalDirection getCurrentDirection() {
		/**
		 * 	return the cardinal direction the robot is currently facing
		 */
		return controller.getCurrentDirection();
	}

	@Override
	public float getBatteryLevel() {
		/**
		 * 	return battery
		 */
		return battery;
	}
	
	public float getInitialBattery() {
		return initial;
	}

	@Override
	public void setBatteryLevel(float level) {
		/**
		 * 	set battery to level
		 */
		battery = level;
	}

	@Override
	public float getEnergyForFullRotation() {
		/**
		 * 	return 12 (each turn takes 3 energy, so 4 turns would equal 12)
		 */
		return 12;
	}

	@Override
	public float getEnergyForStepForward() {
		/**
		 * 	return 6 as that is the energy needed to take a step forward 
		 */
		return 6;
	}

	@Override
	public int getOdometerReading() {
		/**
		 * 	return odometer
		 */
		return odometer;
	}

	@Override
	public void resetOdometer() {
		/**
		 * 	set odometer to 0
		 */
		odometer = 0;
	}

	@Override
	public void rotate(Turn turn) {
		/**
		 * 	if hasStopped() return true or if there isn't enough battery, don't do anything
		 * 	else turn the robot in the specified manner and decrease energy
		 */
		
		assert(controller != null);
		assert(maze != null);
		
		if(hasStopped() == false) {
			switch(turn) {
				//rotate right and decrease battery
				case RIGHT:{
					if(battery - 3 > -1) {
						controller.handleUserInput(UserInput.RIGHT, 0);
						battery = battery - 3;
						break;
					}else {
						stopped = true;
						break;
					}
				}
				//rotate left and decrease battery
				case LEFT:{
					if(battery - 3 > -1) {
						controller.handleUserInput(UserInput.LEFT, 0);
						battery = battery - 3;
						break;
					}else {
						stopped = true;
						break;
					}
				}
				//rotate around and decrease battery
				case AROUND:{
					if(battery - 6 > -1) {
						controller.handleUserInput(UserInput.RIGHT, 0);
						controller.handleUserInput(UserInput.RIGHT, 0);
						battery = battery - 6;
						break;
					}else {
						stopped = true;
						break;
					}
				}
			}
		}
	}

	@Override
	public void move(int distance) {
		/**
		 * 	create a new int stepsLeft that equals distance to store how many more steps needs to be taken
		 * 
		 * 	while stepsLeft is greater than 0 and hasStopped keeps returning false
		 * 		move forward one cell
		 * 		increment odometer
		 * 		decrease stepsLeft by 1
		 * 		adjust energy cost
		 * 		
		 */
		assert(controller != null);
		assert(maze != null);
		
		try {
			int stepsLeft = distance;	
			
			if(battery < getEnergyForStepForward()) {
				stopped = true;
			}
			
			//while there are still more cells to go, the robot has not crashed, and there is enough battery
			while(stepsLeft > 0 && hasStopped() == false && battery - getEnergyForStepForward() > 0) {
				int[] curr = getCurrentPosition();
				if(battery < getEnergyForStepForward() || maze.hasWall(curr[0], curr[1], getCurrentDirection())) {
					stopped = true;
					break;
				}
				//move forward, increase odometer, and decrease battery
				controller.handleUserInput(UserInput.UP, 0);
				odometer++;
				stepsLeft--;
				battery = battery - 6;
			}
		} catch (Exception e) {
			System.out.println("current position is invalid");
		}
	}

	@Override
	public void jump() {
		/**
		 * 	if hasStopped() return true or if there isn't enough battery, don't do anything
		 * 
		 * 	if the wallboard in front of the robot is part of the border, do nothing and make hasStopped() return true
		 * 
		 * 	if the wallboard can be jumped over
		 * 		move one step forward over the wall
		 * 		adjust energy
		 * 		increment odometer
		 */
		assert(controller != null);
		assert(maze != null);
		
		try{
			if(hasStopped() == false && battery - 40 > 0) {
				int[] coor = getCurrentPosition();
				int[] tmpDxDy = controller.getCurrentDirection().getDxDyDirection();
				//if the location the robot plans to jump to is invalid (out of the maze)
	        	if (!maze.isValidPosition(coor[0] + tmpDxDy[0], coor[1] + tmpDxDy[1])) {
	        		stopped = true;
				}else {
					controller.handleUserInput(UserInput.JUMP, 0);
					battery = battery - 40;
					odometer++;
				}
			}
		}catch(Exception e) {
			System.out.println("current position is invalid");
		}
	}
	
	/**
	 * 	this method adjusts a direction into a cardinal direction
	 * 	@returns cardinal direction
	 */
	public CardinalDirection adjust(Direction d) {
		CardinalDirection curr = controller.getCurrentDirection();
		switch(d) {
			case LEFT:{
				if(curr == CardinalDirection.East) {
					return CardinalDirection.South;
				}else if(curr == CardinalDirection.North) {
					return CardinalDirection.East;
				}else if(curr == CardinalDirection.West) {
					return CardinalDirection.North;
				}else if(curr == CardinalDirection.South) {
					return CardinalDirection.West;
				}
			}
			case RIGHT:{
				if(curr == CardinalDirection.East) {
					return CardinalDirection.North;
				}else if(curr == CardinalDirection.North) {
					return CardinalDirection.West;
				}else if(curr == CardinalDirection.West) {
					return CardinalDirection.South;
				}else if(curr == CardinalDirection.South) {
					return CardinalDirection.East;
				}
			}
			case FORWARD:{
				return curr;
			}
			case BACKWARD:{
				if(curr == CardinalDirection.East) {
					return CardinalDirection.West;
				}else if(curr == CardinalDirection.North) {
					return CardinalDirection.South;
				}else if(curr == CardinalDirection.West) {
					return CardinalDirection.East;
				}else if(curr == CardinalDirection.South) {
					return CardinalDirection.North;
				}
			}
		}
		return curr;
	}

	@Override
	public boolean isAtExit() {
		/**
		 * 	returns true if the robot is one step away from the exit (does not need to be facing it)
		 */
		assert(controller != null);
		assert(maze != null);
		
		try{
			if(Arrays.equals(maze.getExitPosition(), getCurrentPosition())) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			System.out.println("current position is illegal");
		}
		return false;
	}

	@Override
	public boolean isInsideRoom() {
		/**
		 * 	returns true if the current position of the robot is in a room and false otherwise
		 */
		assert(controller != null);
		assert(maze != null);
		
		try{
			int[] coor = getCurrentPosition();
			if(maze.isInRoom(coor[0], coor[1])) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			System.out.println("current position is illegal");
		}
		return false;
	}

	@Override
	public boolean hasStopped() {
		/**
		 * 	if battery is less than 0 or we tried to go into a wall, return true
		 * 	otherwise return false
		 */
		if(stopped == true) {
			return true;
		}else {
			if(battery <= 0) {
				stopped = true;
				return true;
			}
		}
		return false;
	}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		/**
		 * 	get the cardinal direction the robot is currently facing from the controller
		 * 	
		 * 	adjust the direction given from the method call so that it references a cardinal direction instead (ex: "east" instead of "left")
		 * 
		 * 	call distanceToObstacle from the appropriate sensor and return the value
		 */
		assert(controller != null);
		assert(maze != null);
		
		try {
			switch(direction) {
				//all cases decrease battery and return the distanceToObstacle of the corresponding sensor after adjusting the direction to its appropriate Cardinal Direction
				case LEFT:{
					if(leftSensor != null) {
						battery = battery - leftSensor.getEnergyConsumptionForSensing();
						return leftSensor.distanceToObstacle(getCurrentPosition(), adjust(direction), new float[] {battery});
					}else {
						throw new UnsupportedOperationException();
					}
				}
				case RIGHT:{
					if(rightSensor != null) {
						battery = battery - rightSensor.getEnergyConsumptionForSensing();
						return rightSensor.distanceToObstacle(getCurrentPosition(), adjust(direction), new float[] {battery});
					}else {
						throw new UnsupportedOperationException();
					}
				}
				case FORWARD:{
					if(forwardSensor != null) {
						battery = battery - forwardSensor.getEnergyConsumptionForSensing();
						return forwardSensor.distanceToObstacle(getCurrentPosition(), adjust(direction), new float[] {battery});
					}else {
						throw new UnsupportedOperationException();
					}
				}
				case BACKWARD:{
					if(backwardSensor != null) {
						battery = battery - backwardSensor.getEnergyConsumptionForSensing();
						return backwardSensor.distanceToObstacle(getCurrentPosition(), adjust(direction), new float[] {battery});
					}else {
						throw new UnsupportedOperationException();
					}
				}
			}
		} catch (Exception e) {
			System.out.println("current position is illegal, or sensor is null");
		}
		return 0;
	}

	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
		/**
		 * 	get the cardinal direction the robot is currently facing from the controller
		 * 	
		 * 	adjust the direction given from the method call so that it references a cardinal direction instead (ex: "east" instead of "left")
		 * 
		 * 	call distanceToObstacle from the appropriate sensor
		 * 
		 * 	if the value returned is Integer.MaxValue, return true
		 * 
		 * 	Otherwise return false
		 */
		assert(controller != null);
		assert(maze != null);
		
		try {
			switch(direction) {
				//all cases decrease battery and return the distanceToObstacle of the corresponding sensor after adjusting the direction to its appropriate Cardinal Direction
				case LEFT:{
					if(leftSensor.distanceToObstacle(getCurrentPosition(), adjust(direction), new float[] {battery}) == Integer.MAX_VALUE){
						battery = battery - leftSensor.getEnergyConsumptionForSensing();
						return true;
					}
				}
				case RIGHT:{
					if(rightSensor.distanceToObstacle(getCurrentPosition(), adjust(direction), new float[] {battery}) == Integer.MAX_VALUE){
						battery = battery - rightSensor.getEnergyConsumptionForSensing();
						return true;
					}				
				}
				case FORWARD:{
					if(forwardSensor.distanceToObstacle(getCurrentPosition(), adjust(direction), new float[] {battery}) == Integer.MAX_VALUE){
						battery = battery - forwardSensor.getEnergyConsumptionForSensing();
						return true;
					}				
				}
				case BACKWARD:{
					if(backwardSensor.distanceToObstacle(getCurrentPosition(), adjust(direction), new float[] {battery}) == Integer.MAX_VALUE){
						battery = battery - backwardSensor.getEnergyConsumptionForSensing();
						return true;
					}				
				}
			}
		} catch (Exception e) {
			System.out.println("current position is illegal");
		}
		return false;
	}

	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair) throws UnsupportedOperationException {
		/**
		 * 	Not to be implemented until Project 4
		 * 	Currently will throw UnsupportedOperationException
		 */
		throw new UnsupportedOperationException();
	}

	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		/**
		 * 	Not to be implemented until Project 4
		 * 	Currently will throw UnsupportedOperationException
		 */
		throw new UnsupportedOperationException();
	}

}
