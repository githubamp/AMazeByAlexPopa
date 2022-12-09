package edu.wm.cs.cs301.AlexPopa.gui;

import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.TOGGLEFULLMAP;
import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.TOGGLELOCALMAP;
import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.TOGGLESOLUTION;
import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.ZOOMIN;
import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.ZOOMOUT;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import edu.wm.cs.cs301.AlexPopa.R;
import edu.wm.cs.cs301.AlexPopa.generation.Maze;

/**
 * author @ALEX POPA
 */
public class PlayAnimationActivity  extends AppCompatActivity {

    private Handler mHandler = new Handler(Looper.myLooper());
    private Wizard wiz;
    private WallFollower wallFollower;

    private Context context = this;

    private boolean paused = false;

    private int labelNo    = 0;
    private long currTime  = 0L;
    private long mStartTime = 0L;

    private int totalSteps;

    private MediaPlayer music;

    private Thread mUpdateTimeTask = new Thread() {

        int speedAnimation = 1;

        public synchronized void run() {
            TextView left = (TextView) findViewById(R.id.LeftSensor);
            TextView right = (TextView) findViewById(R.id.RightSensor);
            TextView forward = (TextView) findViewById(R.id.Forward);
            TextView backward = (TextView) findViewById(R.id.Backward);

            ProgressBar energyConsumption = (ProgressBar) findViewById(R.id.energy);

            SeekBar speedbar = (SeekBar) findViewById(R.id.speed);

            //start music
            if(music == null){
                music = MediaPlayer.create(PlayAnimationActivity.this, R.raw.hkpath);
                music.start();
                music.setLooping(true);
            }

            speedbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                /**
                 *  following two methods are just needed to override but do noting
                 */
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                /**
                 *  on change of the speed seekbar
                 */
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //make a log statement
                    Log.v("Seekbar", "Speed pressed");
                    //make a message appear from the bottom of the screen
                    /*Snackbar speed = Snackbar.make(findViewById(android.R.id.content), "Speed pressed", 500);
                    speed.show();*/
                    speedAnimation = progress;
                }
            });

            if(wiz != null){ //if wizard is being used
                try {
                    if(wiz.getRobot().isAtExit() && wiz.getRobot().canSeeThroughTheExitIntoEternity(Robot.Direction.FORWARD)){ // win
                        Intent intentG = new Intent(context, WinningActivity.class);
                        music.stop();
                        music.release();
                        Log.v("End", "Win");
                        //populate the intent with information regarding the steps taken, the shortest path, the energy consumption, and the fact that a robot was used
                        intentG.putExtra("Steps taken", wiz.getRobot().getOdometerReading());
                        intentG.putExtra("Shortest steps", totalSteps-1);
                        intentG.putExtra("Energy", (int)wiz.getEnergyConsumption());
                        intentG.putExtra("Robot", "y");
                        //start WinningActivity
                        startActivity(intentG);
                    }else if(wiz.getRobot().hasStopped()) { //lose
                        Intent intentG = new Intent(context, LosingActivity.class);
                        music.stop();
                        music.release();
                        Log.v("End", "Lose");
                        //populate the intent with information regarding the steps taken, the shortest path, the energy consumption, and the fact that a robot was used
                        intentG.putExtra("Steps taken", wiz.getRobot().getOdometerReading());
                        intentG.putExtra("Shortest steps", totalSteps-1);
                        intentG.putExtra("Energy", (int)wiz.getEnergyConsumption());
                        intentG.putExtra("Robot", "y");
                        //start LosingActivity
                        startActivity(intentG);
                    }else{ //move
                        Log.v("Movement", "Moving");
                        wiz.drive1Step2Exit();
                        energyConsumption.setProgress(3500 - (int)wiz.getEnergyConsumption());
                        mHandler.postDelayed(this, speedAnimation*100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(wallFollower != null){ //if wall follower is being used
                try {
                    if(wallFollower.getRobot().isAtExit() && wallFollower.getRobot().canSeeThroughTheExitIntoEternity(Robot.Direction.FORWARD)){ //win
                        Intent intentG = new Intent(context, WinningActivity.class);
                        music.stop();
                        music.release();
                        Log.v("End", "Win");
                        //populate the intent with information regarding the steps taken, the shortest path, the energy consumption, and the fact that a robot was used
                        intentG.putExtra("Steps taken", wallFollower.getRobot().getOdometerReading());
                        intentG.putExtra("Shortest steps", totalSteps-1);
                        intentG.putExtra("Energy", (int)wallFollower.getEnergyConsumption());
                        intentG.putExtra("Robot", "y");
                        //start WinningActivity
                        startActivity(intentG);
                    }else if(wallFollower.getRobot().hasStopped()) { //lose
                        Intent intentG = new Intent(context, LosingActivity.class);
                        music.stop();
                        music.release();
                        Log.v("End", "Lose");
                        //populate the intent with information regarding the steps taken, the shortest path, the energy consumption, and the fact that a robot was used
                        intentG.putExtra("Steps taken", wallFollower.getRobot().getOdometerReading());
                        intentG.putExtra("Shortest steps", totalSteps-1);
                        intentG.putExtra("Energy", (int)wallFollower.getEnergyConsumption());
                        intentG.putExtra("Robot", "y");
                        //start LosingActivity
                        startActivity(intentG);
                    }else{ //move
                        Log.v("Movement", "Moving");
                        wallFollower.drive1Step2Exit();
                        energyConsumption.setProgress(3500 - (int)wallFollower.getEnergyConsumption());
                        mHandler.postDelayed(this, speedAnimation*100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void updateText() { //method to update visual sensors
        TextView left = (TextView) findViewById(R.id.LeftSensor);
        TextView right = (TextView) findViewById(R.id.RightSensor);
        TextView forward = (TextView) findViewById(R.id.Forward);
        TextView backward = (TextView) findViewById(R.id.Backward);

        if (wiz != null) {
            Thread wizTextUpdate = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!wiz.getRobot().isAtExit()) { //while the robot is running
                        if (wiz.getRobot().getSensor(Robot.Direction.LEFT) instanceof UnreliableSensor && !(((UnreliableSensor) wiz.getRobot().getSensor(Robot.Direction.LEFT)).getOperational())) {
                            Log.v("Color", "Turning Red");
                            left.setBackgroundColor(Color.RED);
                        } else {
                            Log.v("Color", "Turning Green");
                            left.setBackgroundColor(Color.GREEN);
                        }
                        if (wiz.getRobot().getSensor(Robot.Direction.RIGHT) instanceof UnreliableSensor && !(((UnreliableSensor) wiz.getRobot().getSensor(Robot.Direction.RIGHT)).getOperational())) {
                            Log.v("Color", "Turning Red");
                            right.setBackgroundColor(Color.RED);
                        } else {
                            Log.v("Color", "Turning Green");
                            right.setBackgroundColor(Color.GREEN);
                        }
                        if (wiz.getRobot().getSensor(Robot.Direction.BACKWARD) instanceof UnreliableSensor && !(((UnreliableSensor) wiz.getRobot().getSensor(Robot.Direction.BACKWARD)).getOperational())) {
                            Log.v("Color", "Turning Red");
                            backward.setBackgroundColor(Color.RED);
                        } else {
                            Log.v("Color", "Turning Green");
                            backward.setBackgroundColor(Color.GREEN);
                        }
                        if (wiz.getRobot().getSensor(Robot.Direction.FORWARD) instanceof UnreliableSensor && !(((UnreliableSensor) wiz.getRobot().getSensor(Robot.Direction.FORWARD)).getOperational())) {
                            Log.v("Color", "Turning Red");
                            forward.setBackgroundColor(Color.RED);
                        } else {
                            Log.v("Color", "Turning Green");
                            forward.setBackgroundColor(Color.GREEN);
                        }
                        try {
                             Thread.sleep(50);
                        } catch (InterruptedException e) {
                             e.printStackTrace();
                        }
                    }
                }
            });
            wizTextUpdate.start();
        }else if (wallFollower != null) { //while the wallFollower is running
            Thread wallTextUpdate = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!wallFollower.getRobot().isAtExit()) {
                        if (wallFollower.getRobot().getSensor(Robot.Direction.LEFT) instanceof UnreliableSensor && !(((UnreliableSensor) wallFollower.getRobot().getSensor(Robot.Direction.LEFT)).getOperational())) {
                            Log.v("Color", "Turning Red");
                            left.setBackgroundColor(Color.RED);
                        } else {
                            Log.v("Color", "Turning Green");
                            left.setBackgroundColor(Color.GREEN);
                        }
                        if (wallFollower.getRobot().getSensor(Robot.Direction.RIGHT) instanceof UnreliableSensor && !(((UnreliableSensor) wallFollower.getRobot().getSensor(Robot.Direction.RIGHT)).getOperational())) {
                            Log.v("Color", "Turning Red");
                            right.setBackgroundColor(Color.RED);
                        } else {
                            Log.v("Color", "Turning Green");
                            right.setBackgroundColor(Color.GREEN);
                        }
                        if (wallFollower.getRobot().getSensor(Robot.Direction.BACKWARD) instanceof UnreliableSensor && !(((UnreliableSensor) wallFollower.getRobot().getSensor(Robot.Direction.BACKWARD)).getOperational())) {
                            Log.v("Color", "Turning Red");
                            backward.setBackgroundColor(Color.RED);
                        } else {
                            Log.v("Color", "Turning Green");
                            backward.setBackgroundColor(Color.GREEN);
                        }
                        if (wallFollower.getRobot().getSensor(Robot.Direction.FORWARD) instanceof UnreliableSensor && !(((UnreliableSensor) wallFollower.getRobot().getSensor(Robot.Direction.FORWARD)).getOperational())) {
                            Log.v("Color", "Turning Red");
                            forward.setBackgroundColor(Color.RED);
                        } else {
                            Log.v("Color", "Turning Green");
                            forward.setBackgroundColor(Color.GREEN);
                        }
                        try {
                             Thread.sleep(50);
                        } catch (InterruptedException e) {
                             e.printStackTrace();
                        }
                    }
                }
            });
            wallTextUpdate.start();
        }
    }

    /**
     * when the activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the screen to the driver_maze xml
        setContentView(R.layout.driver_maze);

        MazePanel panel = (MazePanel) findViewById(R.id.mazePanel);

        Information info = Information.getInformation();

        Intent assist = getIntent();

        //setup
        StatePlaying state = new StatePlaying();
        state.setRobotTime(true);
        state.setMaze(info.getMaze());
        Maze maze = state.getMaze();
        int[] start = maze.getStartingPosition();
        totalSteps = maze.getDistanceToExit(start[0], start[1]);
        state.start(panel);

        TextView left = (TextView) findViewById(R.id.LeftSensor);
        TextView right = (TextView) findViewById(R.id.RightSensor);
        TextView forward = (TextView) findViewById(R.id.Forward);
        TextView backward = (TextView) findViewById(R.id.Backward);

        //make the text representing the left sensor green to represent that it's functional
        //turn it red when it is not
        left.setBackgroundColor(Color.GREEN);

        //make the text representing the right sensor green to represent that it's functional
        //turn it red when it is not
        right.setBackgroundColor(Color.GREEN);

        //make the text representing the forward sensor green to represent that it's functional
        //turn it red when it is not
        forward.setBackgroundColor(Color.GREEN);

        //make the text representing the backward sensor green to represent that it's functional
        //turn it red when it is not
        backward.setBackgroundColor(Color.GREEN);

        if(info.getDriver() instanceof WallFollower){ //create appropriate sensors based on configuration
            if(info.getPrevConfig().equals("Premium")){
                ReliableRobot rRobot = new ReliableRobot(state);
                rRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                rRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                rRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                rRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wallFollower = new WallFollower(rRobot, maze);
                info.setDriver(wallFollower);
                info.setPrevRobot(rRobot);
                info.setRobot(rRobot);
                info.setPrevDriver(wallFollower);
            }else if(info.getPrevConfig().equals("Mediocre")){
                UnreliableRobot uRobot = new UnreliableRobot(state);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wallFollower = new WallFollower(uRobot, maze);
                wallFollower.getRobot().startFailureAndRepairProcess(Robot.Direction.RIGHT, 4000, 2000);
                try{
                    Thread.sleep(1300);
                }catch(Exception e){

                }
                wallFollower.getRobot().startFailureAndRepairProcess(Robot.Direction.LEFT, 4000, 2000);
                info.setDriver(wallFollower);
                info.setPrevRobot(uRobot);
                info.setRobot(uRobot);
                info.setPrevDriver(wallFollower);
            }else if(info.getPrevConfig().equals("Soso")){
                UnreliableRobot uRobot = new UnreliableRobot(state);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wallFollower = new WallFollower(uRobot, maze);
                wallFollower.getRobot().startFailureAndRepairProcess(Robot.Direction.BACKWARD, 4000, 2000);
                try{
                    Thread.sleep(1300);
                }catch(Exception e){

                }
                wallFollower.getRobot().startFailureAndRepairProcess(Robot.Direction.FORWARD, 4000, 2000);
                info.setDriver(wallFollower);
                info.setPrevRobot(uRobot);
                info.setRobot(uRobot);
                info.setPrevDriver(wallFollower);
            }else if(info.getPrevConfig().equals("Shaky")){
                UnreliableRobot uRobot = new UnreliableRobot(state);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wallFollower = new WallFollower(uRobot, maze);
                wallFollower.getRobot().startFailureAndRepairProcess(Robot.Direction.FORWARD, 4000, 2000);
                try{
                    Thread.sleep(1300);
                }catch(Exception e){

                }
                wallFollower.getRobot().startFailureAndRepairProcess(Robot.Direction.BACKWARD, 4000, 2000);
                try{
                    Thread.sleep(1300);
                }catch(Exception e){

                }
                wallFollower.getRobot().startFailureAndRepairProcess(Robot.Direction.LEFT, 4000, 2000);
                try{
                    Thread.sleep(1300);
                }catch(Exception e){

                }
                wallFollower.getRobot().startFailureAndRepairProcess(Robot.Direction.RIGHT, 4000, 2000);
                info.setDriver(wallFollower);
                info.setPrevRobot(uRobot);
                info.setRobot(uRobot);
                info.setPrevDriver(wallFollower);
            }
        }else if(info.getDriver() instanceof Wizard){
            if(info.getPrevConfig().equals("Premium")){
                ReliableRobot rRobot = new ReliableRobot(state);
                rRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                rRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                rRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                rRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wiz = new Wizard(rRobot, maze);
                info.setDriver(wiz);
                info.setPrevRobot(rRobot);
                info.setRobot(rRobot);
                info.setPrevDriver(wiz);
            }else if(info.getPrevConfig().equals("Mediocre")){
                UnreliableRobot uRobot = new UnreliableRobot(state);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wiz = new Wizard(uRobot, maze);
                wiz.getRobot().startFailureAndRepairProcess(Robot.Direction.LEFT, 4000, 2000);
                try{
                    Thread.sleep(1300);
                }catch(Exception e){

                }
                wiz.getRobot().startFailureAndRepairProcess(Robot.Direction.RIGHT, 4000, 2000);
                info.setDriver(wiz);
                info.setPrevRobot(uRobot);
                info.setRobot(uRobot);
                info.setPrevDriver(wiz);
            }else if(info.getPrevConfig().equals("Soso")){
                UnreliableRobot uRobot = new UnreliableRobot(state);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wiz = new Wizard(uRobot, maze);
                wiz.getRobot().startFailureAndRepairProcess(Robot.Direction.FORWARD, 4000, 2000);
                try{
                    Thread.sleep(1300);
                }catch(Exception e){

                }
                wiz.getRobot().startFailureAndRepairProcess(Robot.Direction.BACKWARD, 4000, 2000);
                info.setDriver(wiz);
                info.setPrevRobot(uRobot);
                info.setRobot(uRobot);
                info.setPrevDriver(wiz);
            }else if(info.getPrevConfig().equals("Shaky")){
                UnreliableRobot uRobot = new UnreliableRobot(state);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wiz = new Wizard(uRobot, maze);
                wiz.getRobot().startFailureAndRepairProcess(Robot.Direction.FORWARD, 4000, 2000);
                try{
                    Thread.sleep(1300);
                }catch(Exception e){

                }
                wiz.getRobot().startFailureAndRepairProcess(Robot.Direction.BACKWARD, 4000, 2000);
                try{
                    Thread.sleep(1300);
                }catch(Exception e){

                }
                wiz.getRobot().startFailureAndRepairProcess(Robot.Direction.LEFT, 4000, 2000);
                try{
                    Thread.sleep(1300);
                }catch(Exception e){

                }
                wiz.getRobot().startFailureAndRepairProcess(Robot.Direction.RIGHT, 4000, 2000);
                info.setDriver(wiz);
                info.setPrevRobot(uRobot);
                info.setRobot(uRobot);
                info.setPrevDriver(wiz);
            }
        }

        updateText();

        try {
            mStartTime = System.currentTimeMillis();
            mHandler.removeCallbacks(mUpdateTimeTask);
            mHandler.postDelayed(mUpdateTimeTask, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //button that shows walls on screen
        Button wall = (Button) findViewById(R.id.walls);
        wall.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                state.handleUserInput(TOGGLELOCALMAP, 0);
                //make a pop up message saying the button was clicked
                /*Snackbar walls = Snackbar.make(findViewById(android.R.id.content), "Walls pressed", 500);
                walls.show();*/
                //make a log message saying the button was clicked
                Log.v("Button", "Walls pressed");
            }
        });

        //button that shows the full maze on screen
        Button fmaze = (Button) findViewById(R.id.fullmaze);
        fmaze.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                state.handleUserInput(TOGGLEFULLMAP, 0);
                //make a pop up message saying the button was clicked
                /*Snackbar full = Snackbar.make(findViewById(android.R.id.content), "Full maze pressed", 500);
                full.show();*/
                //make a log message saying the button was clicked
                Log.v("Button", "Full maze pressed");
            }
        });

        //button that shows the solution on screen
        Button sol = (Button) findViewById(R.id.solution);
        sol.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                state.handleUserInput(TOGGLESOLUTION, 0);
                //make a pop up message saying the button was clicked
                //Snackbar solut = Snackbar.make(findViewById(android.R.id.content), "Solution pressed", 500);
                //solut.show();
                //make a log message saying the button was clicked
                Log.v("Button", "Solution pressed");
            }
        });

        //button that reduces the size of the map on screen
        Button small = (Button) findViewById(R.id.smaller);
        small.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                state.handleUserInput(ZOOMOUT, 0);
                //make a pop up message saying the button was clicked
                /*Snackbar sma = Snackbar.make(findViewById(android.R.id.content), "Smaller pressed", 500);
                sma.show();*/
                //make a log message saying the button was clicked
                Log.v("Button", "Smaller pressed");
            }
        });

        //button that enlarges the size of the map on screen
        Button big = (Button) findViewById(R.id.bigger);
        big.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                state.handleUserInput(ZOOMIN, 0);
                //make a pop up message saying the button was clicked
                /*Snackbar b = Snackbar.make(findViewById(android.R.id.content), "Bigger pressed", 500);
                b.show();*/
                //make a log message saying the button was clicked
                Log.v("Button", "Bigger pressed");
            }
        });

        //button that starts or stops the robot animation on screen
        Button sp = (Button) findViewById(R.id.start);
        sp.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                //make a pop up message saying the button was clicked
                //Snackbar st = Snackbar.make(findViewById(android.R.id.content), "Start/pause pressed", 500);
                //st.show();
                //make a log message saying the button was clicked
                Log.v("Button", "Start/pause pressed");
                if(paused == true){
                    paused = false;
                    mStartTime = 0L;
                    if (mStartTime == 0L) {
                        mStartTime = System.currentTimeMillis();
                        mHandler.removeCallbacks(mUpdateTimeTask);
                        mHandler.postDelayed(mUpdateTimeTask, 100);
                    }
                }else{
                    paused = true;
                    mHandler.removeCallbacks(mUpdateTimeTask);
                }
            }
        });
    }

    @Override
    public void onBackPressed(){ //if the back button was pressed, stop the appropriate failure and repair processes
        if (wiz != null && wiz.getRobot().getSensor(Robot.Direction.LEFT) instanceof UnreliableSensor) {
            wiz.getRobot().stopFailureAndRepairProcess(Robot.Direction.LEFT);
        } else if (wallFollower != null && wallFollower.getRobot().getSensor(Robot.Direction.LEFT) instanceof UnreliableSensor){
            wallFollower.getRobot().stopFailureAndRepairProcess(Robot.Direction.LEFT);        }
        if (wiz != null && wiz.getRobot().getSensor(Robot.Direction.RIGHT) instanceof UnreliableSensor) {
            wiz.getRobot().stopFailureAndRepairProcess(Robot.Direction.RIGHT);
        } else if (wallFollower != null && wallFollower.getRobot().getSensor(Robot.Direction.RIGHT) instanceof UnreliableSensor){
            wallFollower.getRobot().stopFailureAndRepairProcess(Robot.Direction.RIGHT);
        }
        if (wiz != null && wiz.getRobot().getSensor(Robot.Direction.BACKWARD) instanceof UnreliableSensor) {
            wiz.getRobot().stopFailureAndRepairProcess(Robot.Direction.BACKWARD);
        } else if (wallFollower != null && wallFollower.getRobot().getSensor(Robot.Direction.BACKWARD) instanceof UnreliableSensor){
            wallFollower.getRobot().stopFailureAndRepairProcess(Robot.Direction.BACKWARD);
        }
        if (wiz != null && wiz.getRobot().getSensor(Robot.Direction.FORWARD) instanceof UnreliableSensor) {
            wiz.getRobot().stopFailureAndRepairProcess(Robot.Direction.FORWARD);
        } else if (wallFollower != null && wallFollower.getRobot().getSensor(Robot.Direction.FORWARD) instanceof UnreliableSensor){
            wallFollower.getRobot().stopFailureAndRepairProcess(Robot.Direction.FORWARD);
        }
        if(wiz != null){
            wiz = null;
        }
        if(wallFollower != null){
            wallFollower = null;
        }

        Log.v("Back", "Back pressed");

        Toast.makeText(PlayAnimationActivity.this,"Back Button is clicked.", Toast.LENGTH_LONG).show();
        setResult(RESULT_CANCELED);

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //checks if back button was pressed
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
