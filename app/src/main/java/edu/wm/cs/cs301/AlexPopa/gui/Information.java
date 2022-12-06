package edu.wm.cs.cs301.AlexPopa.gui;

import static edu.wm.cs.cs301.AlexPopa.generation.Order.*;
import static edu.wm.cs.cs301.AlexPopa.generation.Order.Builder.*;

import edu.wm.cs.cs301.AlexPopa.generation.Maze;
import edu.wm.cs.cs301.AlexPopa.generation.Order;

public class Information {
    //create an object of SingleObject
    private static Information info = new Information();
    private int skill = 0, seed = 86422, prevSkill = 0, prevSeed = 86422;
    private Order.Builder generator = DFS, prevGenerator = DFS;
    private boolean rooms = true, prevRooms = true;
    private Maze maze, prevMaze;
    private Robot robot = new ReliableRobot(), prevRobot;
    private RobotDriver driver = new Wizard(), prevDriver;
    private String config = "Premium", prevConfig = "Premium";

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

    public void setDriver(RobotDriver d) {
        driver = d;
    }

    public RobotDriver getDriver() {
        return driver;
    }

    public void setRobot(Robot r){
        robot = r;
    }

    public Robot getRobot() {
        return robot;
    }

    public void setPrevSkill(int i){
        prevSkill = i;
    }

    public int getPrevSkill(){
        return prevSkill;
    }

    public void setPrevSeed(int i){
        prevSeed = i;
    }

    public int getPrevSeed(){
        return prevSeed;
    }

    public void setPrevGen(Builder i){
        prevGenerator = i;
    }

    public Order.Builder getPrevGen() {
        return prevGenerator;
    }

    public void setPrevRooms(boolean i){
        prevRooms = i;
    }

    public boolean getPrevRooms(){
        return prevRooms;
    }

    public void setPrevMaze(Maze m){
        prevMaze = m;
    }

    public Maze getPrevMaze(){
        return prevMaze;
    }

    public void setPrevDriver(RobotDriver d) {
        prevDriver = d;
    }

    public RobotDriver getPrevDriver() {
        return prevDriver;
    }

    public void setPrevRobot(Robot r){
        prevRobot = r;
    }

    public Robot getPrevRobot() {
        return prevRobot;
    }

    public void setConfig(String s){
        config = s;
    }

    public String getConfig(){
        return config;
    }

    public void setPrevConfig(String s) {
        prevConfig = s;
    }

    public String getPrevConfig(){
        return prevConfig;
    }
}
