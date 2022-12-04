package edu.wm.cs.cs301.AlexPopa.gui;

import edu.wm.cs.cs301.AlexPopa.gui.Robot.Direction;

/**
 *  Class: UnreliableRobot
 *  
 * 	Responsibilities: rotate, move, and jump
 * 					  have at least one UnreliableSensor which tells the distance to a wall
 * 					  keep track of UnreliableSensors if there are multiple so they don't all malfunction at the same time
 * 
 * 	Collaborators: ReliableRobot
 * 				   UnreliableSensor 
 * 				   Maze
 * 				   Thread
 * 
 * 	@author ALEX POPA
 */

public class UnreliableRobot extends ReliableRobot{
	
	//protected DistanceSensor forwardSensor, leftSensor, backwardSensor, rightSensor;
	
	/**
	 * 	constructor that takes a controller and calls the ReliableRobot constructor method
	 */
	public UnreliableRobot(StatePlaying c) {
		controller = c;
		maze = c.getMaze();
	}
	
	/**
	 * 	default constructor
	 */
	public UnreliableRobot() {
	}
	
	/**
	 * 	does the same as the super class, but needs to check if the sensor is unreliable or not first
	 */
	@Override
	public void addDistanceSensor(DistanceSensor sensor, Direction mountedDirection) {
		//if reliable sensor
		if(sensor.getClass() == ReliableSensor.class) {
			super.addDistanceSensor(sensor, mountedDirection);
		}else { //if unreliable sensor
			if(mountedDirection == Direction.FORWARD) {
				forwardSensor = new UnreliableSensor(mountedDirection);
				forwardSensor.setMaze(maze);
			}else if(mountedDirection == Direction.RIGHT) {
				rightSensor = new UnreliableSensor(mountedDirection);
				rightSensor.setMaze(maze);
			}else if(mountedDirection == Direction.LEFT) {
				leftSensor = new UnreliableSensor(mountedDirection);
				leftSensor.setMaze(maze);
			}else if(mountedDirection == Direction.BACKWARD) {
				backwardSensor = new UnreliableSensor(mountedDirection);
				backwardSensor.setMaze(maze);
			}
		}
	}
	
	/**
	 * 	does the same as the super class, but if the sensor is down, throw an Exception
	 */
	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		switch(direction){ //each case checks if the sensor is currently operational
			case LEFT:{
				if(leftSensor instanceof UnreliableSensor && ((UnreliableSensor) leftSensor).getOperational() == false) { //wait for sensor to work
					try {
						while((leftSensor instanceof UnreliableSensor && ((UnreliableSensor)leftSensor).getOperational() == false) || (rightSensor instanceof UnreliableSensor && ((UnreliableSensor)rightSensor).getOperational() == false) || (backwardSensor instanceof UnreliableSensor && ((UnreliableSensor)backwardSensor).getOperational() == false) || (forwardSensor instanceof UnreliableSensor && ((UnreliableSensor)forwardSensor).getOperational() == false)) {
							Thread.sleep(50);
							System.out.println("waiting for sensor to repair");
						}
					} catch (InterruptedException e) {
					}	
					//throw new UnsupportedOperationException();
				}
			}
			case RIGHT:{
				if(rightSensor instanceof UnreliableSensor && ((UnreliableSensor) rightSensor).getOperational() == false) {	//wait for sensor to work
					try {
						while((leftSensor instanceof UnreliableSensor && ((UnreliableSensor)leftSensor).getOperational() == false) || (rightSensor instanceof UnreliableSensor && ((UnreliableSensor)rightSensor).getOperational() == false) || (backwardSensor instanceof UnreliableSensor && ((UnreliableSensor)backwardSensor).getOperational() == false) || (forwardSensor instanceof UnreliableSensor && ((UnreliableSensor)forwardSensor).getOperational() == false)) {
							Thread.sleep(50);
							System.out.println("waiting for sensor to repair");
						}
					} catch (InterruptedException e) {
					}	
					//throw new UnsupportedOperationException();
				}
			}
			case FORWARD:{
				if(forwardSensor instanceof UnreliableSensor && ((UnreliableSensor) forwardSensor).getOperational() == false) {	//wait for sensor to work
					try {
						while((leftSensor instanceof UnreliableSensor && ((UnreliableSensor)leftSensor).getOperational() == false) || (rightSensor instanceof UnreliableSensor && ((UnreliableSensor)rightSensor).getOperational() == false) || (backwardSensor instanceof UnreliableSensor && ((UnreliableSensor)backwardSensor).getOperational() == false) || (forwardSensor instanceof UnreliableSensor && ((UnreliableSensor)forwardSensor).getOperational() == false)) {
							Thread.sleep(50);
							System.out.println("waiting for sensor to repair");
						}
					} catch (InterruptedException e) {
					}	
					//throw new UnsupportedOperationException();
				}
			}
			case BACKWARD:{
				if(backwardSensor instanceof UnreliableSensor && ((UnreliableSensor) leftSensor).getOperational() == false) {	//wait for sensor to work
					try {
						while((leftSensor instanceof UnreliableSensor && ((UnreliableSensor)leftSensor).getOperational() == false) || (rightSensor instanceof UnreliableSensor && ((UnreliableSensor)rightSensor).getOperational() == false) || (backwardSensor instanceof UnreliableSensor && ((UnreliableSensor)backwardSensor).getOperational() == false) || (forwardSensor instanceof UnreliableSensor && ((UnreliableSensor)forwardSensor).getOperational() == false)) {
							Thread.sleep(50);
							System.out.println("waiting for sensor to repair");
						}
					} catch (InterruptedException e) {
					}	
				}
			}
		}
		return super.distanceToObstacle(direction);
	}
	
	/**
	 * 	does the same as the super class, but first check if a the sensor is down
	 * 
	 * 	if it is, wait until its repaired
	 */
	@Override
	public void rotate(Turn turn) {
		try {
			//wait for sensor to work
			while((leftSensor instanceof UnreliableSensor && ((UnreliableSensor)leftSensor).getOperational() == false) || (rightSensor instanceof UnreliableSensor && ((UnreliableSensor)rightSensor).getOperational() == false) || (backwardSensor instanceof UnreliableSensor && ((UnreliableSensor)backwardSensor).getOperational() == false) || (forwardSensor instanceof UnreliableSensor && ((UnreliableSensor)forwardSensor).getOperational() == false)) {
				Thread.sleep(50);
				System.out.println("waiting for sensor to repair");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.rotate(turn);
	}
	
	/**
	 * 	does the same as the super class, but first check if a the sensor is down
	 * 
	 * 	if it is, wait until its repaired
	 */
	@Override
	public void move(int distance) {
		try {
			//wait for sensor to work
			while((leftSensor instanceof UnreliableSensor && ((UnreliableSensor)leftSensor).getOperational() == false) || (rightSensor instanceof UnreliableSensor && ((UnreliableSensor)rightSensor).getOperational() == false) || (backwardSensor instanceof UnreliableSensor && ((UnreliableSensor)backwardSensor).getOperational() == false) || (forwardSensor instanceof UnreliableSensor && ((UnreliableSensor)forwardSensor).getOperational() == false)) {
				Thread.sleep(50);
				System.out.println("waiting for sensor to repair");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.move(distance);
	}
	
	/**
	 * 	does the same as the super class, but first check if a the sensor is down
	 * 
	 * 	if it is, wait until its repaired
	 */
	@Override
	public void jump() {
		super.jump();
	}
	
	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
		try {
			//wait for sensor to work
			while((leftSensor instanceof UnreliableSensor && ((UnreliableSensor)leftSensor).getOperational() == false) || (rightSensor instanceof UnreliableSensor && ((UnreliableSensor)rightSensor).getOperational() == false) || (backwardSensor instanceof UnreliableSensor && ((UnreliableSensor)backwardSensor).getOperational() == false) || (forwardSensor instanceof UnreliableSensor && ((UnreliableSensor)forwardSensor).getOperational() == false)) {
				Thread.sleep(50);
				System.out.println("waiting for sensor to repair");
			}
		} catch (Exception e) {
		}
		return super.canSeeThroughTheExitIntoEternity(direction);
	}
	/**
	 * 	start the startFailureAndRepairProcess of the appropriate sensor
	 */
	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair) throws UnsupportedOperationException {
		switch(direction) { //start the failure and repair process of the appropriate sensor
			case LEFT:{
				leftSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
				break;
			}
			case RIGHT:{
				rightSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
				break;
			}
			case FORWARD:{
				forwardSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
				break;			
			}
			case BACKWARD:{
				backwardSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
				break;
			}
		}
	}

	/**
	 * 	end the startFailureAndRepairProcess of the appropriate sensor
	 */
	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		switch(direction) {
			case LEFT:{
				leftSensor.stopFailureAndRepairProcess();
				break;
			}
			case RIGHT:{
				rightSensor.stopFailureAndRepairProcess();
				break;
			}
			case FORWARD:{
				forwardSensor.stopFailureAndRepairProcess();
				break;			
			}
			case BACKWARD:{
				backwardSensor.stopFailureAndRepairProcess();
				break;
			}
		}
	}
}