package edu.wm.cs.cs301.AlexPopa.gui;

import edu.wm.cs.cs301.AlexPopa.generation.CardinalDirection;
import edu.wm.cs.cs301.AlexPopa.generation.Maze;
import edu.wm.cs.cs301.AlexPopa.gui.Robot.Direction;

/**
 * 	Class: ReliableSensor
 * 
 * 	Responsibilities: return the distance to a Wallboard
 * 					  get a maze to know where the Wallboards are
 * 					  set a direction for the sensor
 * 					  get the energy needed to use a sensor
 * 					  throw UnsupportedOperationException for other methods
 * 
 * 	Collaborators: Maze
 * 				   Direction
 * 				   
 * 	@author ALEX POPA
 */

public class ReliableSensor implements DistanceSensor{

	protected Maze maze; //maze that the robot will be in
	protected Direction dir; //direction of the sensor
	
	/**
	 * 	Constructor that accepts a Direction parameter
	 */
	public ReliableSensor(Direction d) {
		dir = d;
	}
	
	/**
	 * 	Default constructor
	 */
	public ReliableSensor() {
	}
	
	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		/**
		 * 	if powersupply is not sufficient{
		 * 		throw an IndexOutOfBoundsException and a message saying that there is no more power
		 * 	}
		 * 	if any parameter is null or if currentPosition is outside of legal range{
		 * 		throw IllegalArgumentException
		 * 	}
		 * 	currx is equal to the first index of currentPosition
		 * 	curry is equal to the second index of currentPosition
		 * 	count variable will start at 0
		 * 
		 * 	if robot is facing the exit
		 * 		return Integer.MaxValue
		 * 	
		 * 	while there is no wall in front of the current position{
		 * 		check the next cell by increasing/decreasing either currx or curry depending on currentDirection
		 * 		increase count by 1
		 * 	}
		 * 	
		 * 	return count
		 */		
		
		assert(maze != null);
		
		int currx = currentPosition[0];
		int curry = currentPosition[1];
		int count = 0;
		
		if(currentPosition == null || currentDirection == null || powersupply == null || !(maze.isValidPosition(currx, curry))) {
			throw new IllegalArgumentException();
		}
		if(powersupply[0] < getEnergyConsumptionForSensing()) {
			throw new Exception("PowerFailure"); 
		}else if (powersupply[0] < 0) {
			throw new IndexOutOfBoundsException("Out of Power"); 
		}
				
		while(!maze.hasWall(currx, curry, currentDirection)) {
			if(currentDirection == CardinalDirection.North) {
				curry--;
				count++;
			}else if (currentDirection == CardinalDirection.East) {
				currx++;
				count++;
			}else if (currentDirection == CardinalDirection.South) {
				curry++;
				count++;
			}else if (currentDirection == CardinalDirection.West) {
				currx--;
				count++;
			}
			if(!maze.isValidPosition(currx, curry)) {
				return Integer.MAX_VALUE;
			}
		}
		return count;
	}

	@Override
	public void setMaze(Maze m) {
		/**
		 * 	Set maze variable to m
		 */
		maze = m;
	}

	@Override
	public void setSensorDirection(Direction mountedDirection) {
		/**
		 * 	Set dir variable to mountedDirection
		 */
		dir = mountedDirection;
	}

	@Override
	public float getEnergyConsumptionForSensing() {
		/**
		 * 	Return energy cost for using a sensor (in this case it would be 1)
		 */
		return 1;
	}

	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair) throws UnsupportedOperationException {
		/**
		 * 	Not to be implemented until Project 4
		 * 	Currently will throw UnsupportedOperationException
		 */
		throw new UnsupportedOperationException();
	}

	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		/**
		 * 	Not to be implemented until Project 4
		 * 	Currently will throw UnsupportedOperationException
		 */
		throw new UnsupportedOperationException();
	}
}
