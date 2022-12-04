package edu.wm.cs.cs301.AlexPopa.gui;

import static edu.wm.cs.cs301.AlexPopa.generation.Order.*;
import static edu.wm.cs.cs301.AlexPopa.generation.Order.Builder.*;

import edu.wm.cs.cs301.AlexPopa.generation.Maze;
import edu.wm.cs.cs301.AlexPopa.generation.Order;

public class Information {
    //create an object of SingleObject
    private static Information info = new Information();
    private int skill = 0, seed = 86422;
    private Order.Builder generator = DFS;
    private boolean rooms = true;
    private Maze maze;

    //make the constructor private so that this class cannot be
    //instantiated
    private Information(){}

    //Get the only object available
    public static Information getInformation(){
        return info;
    }

    public void setSkill(int i){
        skill = i;
    }

    public int getSkill(){
        return skill;
    }

    public void setSeed(int i){
        seed = i;
    }

    public int getSeed(){
        return seed;
    }

    public void setGen(Builder i){
        generator = i;
    }

    public Order.Builder getGen() {
        return generator;
    }

    public void setRooms(boolean i){
        rooms = i;
    }

    public boolean getRooms(){
        return rooms;
    }

    public void setMaze(Maze m){
        maze = m;
    }

    public Maze getMaze(){
        return maze;
    }
}
