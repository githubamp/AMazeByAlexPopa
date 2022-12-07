package edu.wm.cs.cs301.AlexPopa.gui;

import edu.wm.cs.cs301.AlexPopa.generation.CardinalDirection;
import edu.wm.cs.cs301.AlexPopa.gui.Robot.Direction;

/**
 * 	Class: UnreliableSensor
 * 
 * 	Responsibilities: return the distance to a Wallboard
 * 					  set a direction for the sensor
 * 					  cause the sensor to fail and start a thread to repair itself
 * 					  make the sensor operational again and start a thread for how long it can stay active
 * 
 * 	Collaborators: Maze
 * 				   Direction
 * 				   ReliableSensor
 * 				   Thread
 * 				   
 * 	@author ALEX POPA
 */

public class UnreliableSensor extends ReliableSensor{
	
	private boolean operational; //tells whether the sensor is working
	
	private Thread goTime;	//thread for how long the sensor can work
	
	/**
	 * 	constructor that takes a direction and calls the ReliableSensor constructor method
	 */
	public UnreliableSensor(Direction d) {
		dir = d;
		operational = true;
	}
	
	/**
	 * 	default constructor that should not be used
	 */
	public UnreliableSensor() {
		operational = true;
	}

	public Thread getGoTime(){
		return goTime;
	}
	
	/**
	 * 	returns if the sensor is currently working or not
	 */
	public boolean getOperational(){
		return operational;
	}
	
	/**
	 * 	does the same as the super class, but if the sensor is down, throw an Exception
	 */
	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		if(operational == true) { //if sensor is not down
			return super.distanceToObstacle(currentPosition, currentDirection, powersupply);
		}else {
			throw new Exception();
		}
	}
	
	/**
	 * 	begin the cycle of up and down time
	 * 
	 * 	make a separate thread that sleeps for when the sensor is up and down
	 * 
	 * 	each time the sensor wakes up, switch operational to the opposite of what it currently is
	 * 	
	 * 	this cycle ends when stopFailureAndRepairProcess() is called
	 */
	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair) throws UnsupportedOperationException {
		goTime = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	try {
					while(goTime.isInterrupted() == false) {
						if(operational == true) {
							goTime.sleep(meanTimeBetweenFailures); //represents the sensor working
						}else {
							goTime.sleep(meanTimeToRepair); //represents the sensor failing
						}
						operational = !operational; //if operational, make not operational and vice versa
					}
					operational = false;	//when thread is interrupted, make operational false
				} catch (InterruptedException e) {
					operational = false;
				}
		    }
		});  
		goTime.start(); //begin
	}

	/**
	 * 	if called with no running failure and repair process, this method will return an UnsupportedOperationException
	 * 
	 * 	end the up and down time cycle of this sensor by interrupting goTime
	 */
	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		if(goTime == null) {
			throw new UnsupportedOperationException();
		}else {
			goTime.interrupt();
		}
	}
}
