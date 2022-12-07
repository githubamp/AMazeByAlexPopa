package edu.wm.cs.cs301.AlexPopa.gui;

import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.TOGGLEFULLMAP;
import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.TOGGLELOCALMAP;
import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.TOGGLESOLUTION;
import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.ZOOMIN;
import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.ZOOMOUT;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

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

    private Thread mUpdateTimeTask = new Thread() {

        int speedAnimation = 1;

        public synchronized void run() {
            TextView left = (TextView) findViewById(R.id.LeftSensor);
            TextView right = (TextView) findViewById(R.id.RightSensor);
            TextView forward = (TextView) findViewById(R.id.Forward);
            TextView backward = (TextView) findViewById(R.id.Backward);

            ProgressBar energyConsumption = (ProgressBar) findViewById(R.id.energy);

            SeekBar speedbar = (SeekBar) findViewById(R.id.speed);

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

            if(wiz != null){
                try {
                    if(wiz.getRobot().isAtExit() && wiz.getRobot().canSeeThroughTheExitIntoEternity(Robot.Direction.FORWARD)){
                        Intent intentG = new Intent(context, WinningActivity.class);
                        //populate the intent with information regarding the steps taken, the shortest path, the energy consumption, and the fact that a robot was used
                        intentG.putExtra("Steps taken", wiz.getRobot().getOdometerReading());
                        intentG.putExtra("Shortest steps", totalSteps-1);
                        intentG.putExtra("Energy", (int)wiz.getEnergyConsumption());
                        intentG.putExtra("Robot", "y");
                        //start WinningActivity
                        startActivity(intentG);
                    }else if(wiz.getRobot().hasStopped()) {
                        Intent intentG = new Intent(context, LosingActivity.class);
                        //populate the intent with information regarding the steps taken, the shortest path, the energy consumption, and the fact that a robot was used
                        intentG.putExtra("Steps taken", wiz.getRobot().getOdometerReading());
                        intentG.putExtra("Shortest steps", totalSteps-1);
                        intentG.putExtra("Energy", (int)wiz.getEnergyConsumption());
                        intentG.putExtra("Robot", "y");
                        //start LosingActivity
                        startActivity(intentG);
                    }else{
                        wiz.drive1Step2Exit();
                        energyConsumption.setProgress(3500 - (int)wiz.getEnergyConsumption());
                        if(wiz.getRobot().getSensor(Robot.Direction.LEFT) instanceof UnreliableSensor && !(((UnreliableSensor) wiz.getRobot().getSensor(Robot.Direction.LEFT)).getOperational())){
                            left.setBackgroundColor(Color.RED);
                        }else{
                            left.setBackgroundColor(Color.GREEN);
                        }
                        if(wiz.getRobot().getSensor(Robot.Direction.RIGHT) instanceof UnreliableSensor && !(((UnreliableSensor) wiz.getRobot().getSensor(Robot.Direction.RIGHT)).getOperational())){
                            right.setBackgroundColor(Color.RED);
                        }else{
                            right.setBackgroundColor(Color.GREEN);
                        }
                        if(wiz.getRobot().getSensor(Robot.Direction.BACKWARD) instanceof UnreliableSensor && !(((UnreliableSensor) wiz.getRobot().getSensor(Robot.Direction.BACKWARD)).getOperational())){
                            backward.setBackgroundColor(Color.RED);
                        }else{
                            backward.setBackgroundColor(Color.GREEN);
                        }
                        if(wiz.getRobot().getSensor(Robot.Direction.FORWARD) instanceof UnreliableSensor && !(((UnreliableSensor) wiz.getRobot().getSensor(Robot.Direction.FORWARD)).getOperational())){
                            forward.setBackgroundColor(Color.RED);
                        }else{
                            forward.setBackgroundColor(Color.GREEN);
                        }
                        mHandler.postDelayed(this, speedAnimation*100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(wallFollower != null){
                try {
                    if(wallFollower.getRobot().isAtExit() && wallFollower.getRobot().canSeeThroughTheExitIntoEternity(Robot.Direction.FORWARD)){
                        Intent intentG = new Intent(context, WinningActivity.class);
                        //populate the intent with information regarding the steps taken, the shortest path, the energy consumption, and the fact that a robot was used
                        intentG.putExtra("Steps taken", wallFollower.getRobot().getOdometerReading());
                        intentG.putExtra("Shortest steps", totalSteps-1);
                        intentG.putExtra("Energy", wallFollower.getEnergyConsumption());
                        intentG.putExtra("Robot", "y");
                        //start WinningActivity
                        startActivity(intentG);
                    }else{
                        wallFollower.drive1Step2Exit();
                        mHandler.postDelayed(this, 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

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

        StatePlaying state = new StatePlaying();
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

        if(info.getDriver() instanceof WallFollower){
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
                wallFollower.getRobot().startFailureAndRepairProcess(Robot.Direction.FORWARD, 4000, 2000);
                wallFollower.getRobot().startFailureAndRepairProcess(Robot.Direction.BACKWARD, 4000, 2000);
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
                wallFollower.getRobot().startFailureAndRepairProcess(Robot.Direction.BACKWARD, 4000, 2000);
                wallFollower.getRobot().startFailureAndRepairProcess(Robot.Direction.LEFT, 4000, 2000);
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
                wiz.getRobot().startFailureAndRepairProcess(Robot.Direction.BACKWARD, 4000, 2000);
                wiz.getRobot().startFailureAndRepairProcess(Robot.Direction.LEFT, 4000, 2000);
                wiz.getRobot().startFailureAndRepairProcess(Robot.Direction.RIGHT, 4000, 2000);
                info.setDriver(wiz);
                info.setPrevRobot(uRobot);
                info.setRobot(uRobot);
                info.setPrevDriver(wiz);
            }
        }

        try {
            if (mStartTime == 0L) {
                mStartTime = System.currentTimeMillis();
                mHandler.removeCallbacks(mUpdateTimeTask);
                mHandler.postDelayed(mUpdateTimeTask, 100);
            }
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
                Snackbar solut = Snackbar.make(findViewById(android.R.id.content), "Solution pressed", 500);
                solut.show();
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
                Snackbar st = Snackbar.make(findViewById(android.R.id.content), "Start/pause pressed", 500);
                st.show();
                //make a log message saying the button was clicked
                Log.v("Button", "Start/pause pressed");
                if(paused == true){
                    //notifyAll();
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
}
