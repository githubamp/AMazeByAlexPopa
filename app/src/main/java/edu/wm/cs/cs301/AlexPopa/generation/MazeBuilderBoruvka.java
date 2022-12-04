package edu.wm.cs.cs301.AlexPopa.generation;


import java.io.ObjectInputStream.GetField;
import java.time.Year;
import java.util.ArrayList;
import java.util.logging.Logger;

public class MazeBuilderBoruvka extends MazeBuilder implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(MazeBuilderBoruvka.class.getName());
	private Component[][] components;
	private ArrayList<Component> laterMerge = new ArrayList<Component>(); //might need for merging components, but possibly not
	private ArrayList<Component> total = new ArrayList<Component>(); //contains total amount of components
	private ArrayList<BoruvWall> merged = new ArrayList<BoruvWall>(); //might need for containing merged components, but possibly not
	private ArrayList<Integer> weights = new ArrayList<Integer>(); //used for assigning weights
	private ArrayList<BoruvWall> walls = new ArrayList<BoruvWall>();
	
	public MazeBuilderBoruvka() {
		super();
		LOGGER.config("Using Boruvka's algorithm to generate maze.");
	}

	public void makeComponents() {
		for(int i = 0; i < (width*(height-1))+((width-1)*height); i++) {
			weights.add(i);
		}
		
		components = new Component[width][height];
		/**
		 * create BoruvWalls and give them edgeWeights
		 */
		int tempWeight;
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				components[i][j] = new Component(i, j);
				if(i < width - 1) {
					tempWeight = weights.remove((random.nextIntWithinInterval(0, weights.size()-1)));
					components[i][j].setEast(new BoruvWall(i, j, tempWeight, CardinalDirection.East));
				}
				if(i > 0) {
					components[i][j].setWest(new BoruvWall(i, j, getEdgeWeight(i-1, j, CardinalDirection.East), CardinalDirection.West));
				}
				if(j < height - 1) {
					tempWeight = weights.remove((random.nextIntWithinInterval(0, weights.size()-1)));
					components[i][j].setSouth(new BoruvWall(i, j, tempWeight, CardinalDirection.South));
				}
				if(j > 0) {
					components[i][j].setNorth(new BoruvWall(i, j, getEdgeWeight(i, j-1, CardinalDirection.South), CardinalDirection.North));
				}
				total.add(components[i][j]);
			}
		}
	}
	
	/**Method will use Boruvka's algorithm to produce a maze
	 * Will work in tandem with the Component and Wall class to create a minimal spanning tree
	 * The Floorplan method deleteWallboard() will be used to remove walls that correspond
	 * with edges in Boruvka
	 * Edge weights will be compared to see what is the smallest at each node
	 */
	@Override
	protected void generatePathways() {
		/** BORUVKA'S METHOD FROM CLASS
		 * 
		 * completed = false
		 * while(not completed)
		 * Find the connected components of F and assign to each vertex its component
		 * Initialize the cheapest edge for each component to "None"
		 * 	for(each edge uv in E):
		 * 		let wx be the cheapest edge for the component of u
		 *  if(is-preferred-over(uv, wx)) then
		 *  Set uv as the cheapest edge for the component of u
		 *  let yz be the cheapest edge for the component of v
		 *   if(is-preferred-over(uv, yz)) then
		 *   Set uv as the cheapest edge for the component of v
		 *   if(all components have cheapest edge set to "None") then
		 *   // no more trees can be merged -- we are finished
		 *   completed = true
		 *    else
		 *    completed = false
		 *     for each(component whose cheapest edge is not "None")
		 *     Add its cheapest edge to E'
		 */     
		
		makeComponents();
		
		ArrayList<BoruvWall> compCandidates = new ArrayList<BoruvWall>();

		while(total.size() > 1) {
			for(Component x: total) {
				BoruvWall[] min = new BoruvWall[4];
				BoruvWall toBeMerged = new BoruvWall();
				int smallest = Integer.MAX_VALUE;
				/**
				 * goes through every edge and finds the smallest edge in the component
				 */
				for(int[] j: x.getCoordinates()) {
					if(x.getNorth() != null && !x.getCoordinates().contains(new int[]{x.getX(), x.getY()-1})) {
						min[0] = getBoruvWall(j[0], j[1], CardinalDirection.North);
					}
					if(x.getEast() != null && !x.getCoordinates().contains(new int[]{x.getX()+1, x.getY()})) {
						min[1] = getBoruvWall(j[0], j[1], CardinalDirection.East);
					}
					if(x.getSouth() != null && !x.getCoordinates().contains(new int[]{x.getX(), x.getY()+1})) {
						min[2] = getBoruvWall(j[0], j[1], CardinalDirection.South);
					}
					if(x.getWest() != null && !x.getCoordinates().contains(new int[]{x.getX()-1, x.getY()})) {
						min[3] = getBoruvWall(j[0], j[1], CardinalDirection.West);
					}
					for(int k = 0; k < min.length; k++) {
						if(min[k] != null && min[k].getWeight() < smallest && min[k].getWeight() != 0) {
								smallest = min[k].getWeight();
								toBeMerged = min[k];
						}
					}
					compCandidates.add(toBeMerged);
				}
				smallest = Integer.MAX_VALUE;
				toBeMerged = new BoruvWall();
				for(int k = 0; k < compCandidates.size(); k++) {
					if(compCandidates.get(k).getWeight() < smallest && compCandidates.get(k).getWeight() != 0) {
							smallest = compCandidates.get(k).getWeight();
							toBeMerged = compCandidates.get(k);
						}
				}
				walls.add(toBeMerged);
				compCandidates.clear();
			}
				/*if(smallest == min[0] && !walls.contains(x.getNorth())) {
					x.setSmallest(x.getNorth());
					laterMerge.add(x);
					walls.add(x.getSmallest());
				}else if (smallest == min[1] && !walls.contains(x.getEast())) {
					x.setSmallest(x.getEast());
					laterMerge.add(x);
					walls.add(x.getSmallest());
				}else if (smallest == min[2]&& !walls.contains(x.getEast())) {
					x.setSmallest(x.getSouth());
					laterMerge.add(x);
					walls.add(x.getSmallest());
				}else if (smallest == min[3]&& !walls.contains(x.getWest())){
					x.setSmallest(x.getWest());
					laterMerge.add(x);
					walls.add(x.getSmallest());
				}*/
			CardinalDirection direct;
			Component curr, toMerge;
			/**
			 * add walls to be removed
			 */
			while(walls.size() > 0) {
				curr = components[walls.get(0).getX()][walls.get(0).getY()];
				direct = walls.get(0).dir();
				if(direct == CardinalDirection.North) {
					toMerge = components[curr.getX()][curr.getY()-1];
					if(!curr.isInCoordinates(toMerge)) {
						curr.addCoordinates(toMerge.getX(), toMerge.getY());
						total.remove(components[curr.getX()][curr.getY()-1]);
						merged.add(walls.remove(0));
					}else
						walls.remove(0);
				}
				if(direct == CardinalDirection.East) {
					toMerge = components[curr.getX()+1][curr.getY()];
					if(!curr.isInCoordinates(toMerge)) {
						curr.addCoordinates(toMerge.getX(), toMerge.getY());
						total.remove(components[curr.getX()+1][curr.getY()]);
						merged.add(walls.remove(0));
					}else
						walls.remove(0);
				}
				if(direct == CardinalDirection.South) {
					toMerge = components[curr.getX()][curr.getY()+1];
					if(!curr.isInCoordinates(toMerge)) {
						curr.addCoordinates(toMerge.getX(), toMerge.getY());
						total.remove(components[curr.getX()][curr.getY()+1]);
						merged.add(walls.remove(0));
					}else
						walls.remove(0);
				}
				if(direct == CardinalDirection.West) {
					toMerge = components[curr.getX()-1][curr.getY()];
					if(!curr.isInCoordinates(toMerge)) {
						curr.addCoordinates(toMerge.getX(), toMerge.getY());
						total.remove(components[curr.getX()-1][curr.getY()]);
						merged.add(walls.remove(0));
					}else {
						walls.remove(0);
					}
				}
			}
			/*
			 * delete any components from total that didn't get deleted somehow
			 */
			ArrayList<Component> deleter = new ArrayList<Component>();
			for(Component x: total) {
				for(int[] j: x.getCoordinates()) {
					if(total.contains(components[x.getX()][x.getY()])) {
						deleter.add(components[x.getX()][x.getY()]);
					}
				}
			}
			for(int i = 0; i < deleter.size(); i++) {
				total.remove(deleter.get(i));
			}
			//total.remove(0);
			//System.out.println("round finished");
		}
		
		/**
		 * delete wallboards
		 */
		BoruvWall temp;
		Wallboard delete;
		while(merged.size() > 0){
			temp = merged.remove(0);
			delete = new Wallboard(temp.getX(), temp.getY(), temp.dir());
			floorplan.deleteWallboard(delete);
		}
		
		super.generatePathways();
			
			/*CardinalDirection direct;
			Component curr, toMerge;
			while(laterMerge.size() > 0) {
				curr = laterMerge.get(0);
				direct = laterMerge.get(0).getSmallest().dir();
				if(direct == CardinalDirection.North) {
					toMerge = components[curr.getX()][curr.getY()-1];
					if(!curr.isInMerger(toMerge)) {
						curr.merge(toMerge);
						merged.add(laterMerge.remove(0));
					}else
						laterMerge.remove(0);
				}
				if(direct == CardinalDirection.East) {
					toMerge = components[curr.getX()+1][curr.getY()];
					if(!curr.isInMerger(toMerge)) {
						curr.merge(toMerge);
						merged.add(laterMerge.remove(0));
					}else
						laterMerge.remove(0);
				}
				if(direct == CardinalDirection.South) {
					toMerge = components[curr.getX()][curr.getY()+1];
					if(!curr.isInMerger(toMerge)) {
						curr.merge(toMerge);
						merged.add(laterMerge.remove(0));
					}else
						laterMerge.remove(0);
				}
				if(direct == CardinalDirection.West) {
					toMerge = components[curr.getX()-1][curr.getY()];
					if(!curr.isInMerger(toMerge)) {
						curr.merge(toMerge);
						merged.add(laterMerge.remove(0));
					}else {
						laterMerge.remove(0);
					}
				}
			}*/
			
			/*for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					int[] min = new int[4];
					int smallest = Integer.MAX_VALUE;
					if(components[i][j].getNorth() != null) {
						min[0] = getEdgeWeight(i, j, CardinalDirection.North);
					}
					if(components[i][j].getEast() != null) {
						min[1] = getEdgeWeight(i, j, CardinalDirection.East);
					}
					if(components[i][j].getSouth() != null) {
						min[2] = getEdgeWeight(i, j, CardinalDirection.South);
					}
					if(components[i][j].getWest() != null) {
						min[3] = getEdgeWeight(i, j, CardinalDirection.West);
					}
					for(int k = 0; k < min.length; k++) {
					if(min[k] < smallest && min[k] != 0) {
							smallest = min[k];
						}
					}
					if(smallest == min[0]) {
						laterMerge.add(components[i][j].getNorth());
					}else if (smallest == min[1]) {
						laterMerge.add(components[i][j].getEast());
					}else if (smallest == min[2]) {
						laterMerge.add(components[i][j].getSouth());
					}else {
						laterMerge.add(components[i][j].getWest());
					}
				}
			}*/
		
		
		
		
		/**	POTENTIAL IMPLEMENTATION WITH COMPONENTS AND WALLS
		 *	
		 *	while(the size of the components ArrayList is greater than 1)
		 *		for each (component in the components ArrayList)
		 *			for each (int[] in component)
		 *				check each valid direction (be careful about the border) and see if the neighboring component is in ArrayList
		 *				if it is not, add it
		 *				find minimum edge weight and add it to the int[] array
		 *			find the minimum edge in the components and add it to laterMerge ArrayList
		 *		while(merge ArrayList has content)
		 *			find neighboring component, and if they are not in the same component, merge them
		 *			if merged, add to merged ArrayList and remove components from the laterMerge ArrayList
		 *	for each (component in merged ArrayList)
		 *		remove the wallboard at the specified coordinates of the component
		 */	
	}

	/**
	 * Return the edge weight for the wallboard facing north, east, south, or west at coordinates x,y 
	 * 	Method is deterministic, so calling this method with the same input will return the same wall
	 */
	public int getEdgeWeight(int x, int y, CardinalDirection cd) {
		if(cd == CardinalDirection.East) {
			return components[x][y].getEast().getWeight();
		}
		if(cd == CardinalDirection.West) {
			return components[x][y].getWest().getWeight();
		}
		if(cd == CardinalDirection.North) {
			return components[x][y].getNorth().getWeight();
		}
		if(cd == CardinalDirection.South) {
			return components[x][y].getSouth().getWeight();
		}
		return 0; //temporary return value as to not get an error
	}
	
	/**
	 * returns a wall at a specified locaiton
	 */
	public BoruvWall getBoruvWall(int x, int y, CardinalDirection cd) {
		if(cd == CardinalDirection.East) {
			return components[x][y].getEast();
		}
		if(cd == CardinalDirection.West) {
			return components[x][y].getWest();
		}
		if(cd == CardinalDirection.North) {
			return components[x][y].getNorth();
		}
		if(cd == CardinalDirection.South) {
			return components[x][y].getSouth();
		}
		return null;
	}
	
	
	/**
	 * Component class is used to represent the components and nodes for Boruvka's algorithm
	 * 	Class will allow for easier access of values
	 */
	private class Component{
		
		private BoruvWall north, east, south, west;
		private int x = 0, y = 0;
		private Component merger;;
		private ArrayList<int[]> coordinates = new ArrayList<int[]>();
		private BoruvWall smallestBoruvWall;
		
		public Component(int xcoor, int ycoor) {
			north = null;
			east = null;
			south = null;
			west = null;
			x = xcoor;
			y = ycoor;
			smallestBoruvWall = null;
			coordinates.add(new int[] {x,y});
		}
		
		public void addCoordinates(int x, int y) {
			coordinates.add(new int[] {x,y});
		}
		
		public BoruvWall getNorth() {
			return north;
		}
		
		public BoruvWall getEast() {
			return east;
		}
		
		public BoruvWall getSouth() {
			return south;
		}
		
		public BoruvWall getWest() {
			return west;
		}
		
		public ArrayList<int[]> getCoordinates(){
			return coordinates;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public BoruvWall getSmallest() {
			return smallestBoruvWall;
		}	
		
		public Component getComponent(){
			return merger;
		}
		
		public void setNorth(BoruvWall w){
			north = w;
		}
		
		public void setEast(BoruvWall w){
			east = w;
		}
		
		public void setSouth(BoruvWall w){
			south = w;
		}
		
		public void setWest(BoruvWall w){
			west = w;
		}
		
		public void setX(int coor) {
			x = coor;
		}
		
		public void setY(int coor) {
			y = coor;
		}
		
		public void setSmallest(BoruvWall small) {
			smallestBoruvWall = small;
		}
		
		public boolean isInMerger(Component temp) {
			if(merger == null) {
				return false;
			}else {
				return true;
			}
		}
		
		public boolean isInCoordinates(Component temp) {
			if(coordinates.contains(new int[] {temp.getX(), temp.getY()})) {
				return true;
			}else
				return false;
		}
				
		//add a method to merge two components
		public void merge(Component temp) {
			merger = temp;
		}
	}
	
	/**
	 * Wall class will store information about certain wallboards
	 */
	public class BoruvWall{
		
		private int x, y, edgeWeight; //coordinates
		private CardinalDirection direction; //direction of wall
		
		//constructor
		public BoruvWall(int a, int b, int w, CardinalDirection dir) {
			x = a;
			y = b;
			edgeWeight = w;
			direction = dir;
		}
		
		public BoruvWall() {
		}
		
		//return x coordinate
		public int getX() {
			return x;
		}
		
		//return y coordinate
		public int getY() {
			return y;
		}
		
		public int getWeight() {
			return edgeWeight;
		}
		
		public void setWeight(int w) {
			edgeWeight = w;
		}
		
		public void setDirection(CardinalDirection d) {
			direction = d;
		}
		
		//return direction
		public CardinalDirection dir() {
			return direction;
		}
	}
}