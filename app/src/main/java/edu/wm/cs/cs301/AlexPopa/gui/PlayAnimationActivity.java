package edu.wm.cs.cs301.AlexPopa.gui;

import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.TOGGLEFULLMAP;
import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.TOGGLELOCALMAP;
import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.TOGGLESOLUTION;

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

    private int labelNo    = 0;
    private long currTime  = 0L;
    private long mStartTime = 0L;

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            final long start = mStartTime;
            long millis = SystemClock.uptimeMillis() - start;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            if(wiz != null){
                try {
                    wiz.drive1Step2Exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(wallFollower != null){
                try {
                    wallFollower.drive1Step2Exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            mHandler.postDelayed(this, 1000);
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
        int totalSteps = maze.getDistanceToExit(start[0], start[1]);
        state.start(panel);


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
                try {
                    if (mStartTime == 0L) {
                        mStartTime = System.currentTimeMillis();
                        mHandler.removeCallbacks(mUpdateTimeTask);
                        mHandler.postDelayed(mUpdateTimeTask, 100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(info.getPrevConfig().equals("Mediocre")){
                UnreliableRobot uRobot = new UnreliableRobot(state);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wallFollower = new WallFollower(uRobot, maze);
                info.setDriver(wallFollower);
                info.setPrevRobot(uRobot);
                info.setRobot(uRobot);
                info.setPrevDriver(wallFollower);
                try {
                    //wallFollower.drive2Exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(info.getPrevConfig().equals("Soso")){
                UnreliableRobot uRobot = new UnreliableRobot(state);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wallFollower = new WallFollower(uRobot, maze);
                info.setDriver(wallFollower);
                info.setPrevRobot(uRobot);
                info.setRobot(uRobot);
                info.setPrevDriver(wallFollower);
                try {
                    //wallFollower.drive2Exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(info.getPrevConfig().equals("Shaky")){
                UnreliableRobot uRobot = new UnreliableRobot(state);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wallFollower = new WallFollower(uRobot, maze);
                info.setDriver(wallFollower);
                info.setPrevRobot(uRobot);
                info.setRobot(uRobot);
                info.setPrevDriver(wallFollower);
                try {
                    //wallFollower.drive2Exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                try {
                    //wiz.drive2Exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(info.getPrevConfig().equals("Mediocre")){
                UnreliableRobot uRobot = new UnreliableRobot(state);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wiz = new Wizard(uRobot, maze);
                info.setDriver(wiz);
                info.setPrevRobot(uRobot);
                info.setRobot(uRobot);
                info.setPrevDriver(wiz);
                try {
                    //wiz.drive2Exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(info.getPrevConfig().equals("Soso")){
                UnreliableRobot uRobot = new UnreliableRobot(state);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                uRobot.addDistanceSensor(new ReliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wiz = new Wizard(uRobot, maze);
                info.setDriver(wiz);
                info.setPrevRobot(uRobot);
                info.setRobot(uRobot);
                info.setPrevDriver(wiz);
                try {
                    //wiz.drive2Exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(info.getPrevConfig().equals("Shaky")){
                UnreliableRobot uRobot = new UnreliableRobot(state);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.FORWARD), Robot.Direction.FORWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.BACKWARD), Robot.Direction.BACKWARD);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.LEFT), Robot.Direction.LEFT);
                uRobot.addDistanceSensor(new UnreliableSensor(Robot.Direction.RIGHT), Robot.Direction.RIGHT);
                wiz = new Wizard(uRobot, maze);
                info.setDriver(wiz);
                info.setPrevRobot(uRobot);
                info.setRobot(uRobot);
                info.setPrevDriver(wiz);
                try {
                    //wiz.drive2Exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //seekbar that represents difficulty
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
                Snackbar speed = Snackbar.make(findViewById(android.R.id.content), "Speed pressed", 500);
                speed.show();
            }
        });

        //make the text representing the left sensor green to represent that it's functional
        //turn it red when it is not
        TextView left = (TextView) findViewById(R.id.LeftSensor);
        left.setBackgroundColor(Color.GREEN);

        //make the text representing the right sensor green to represent that it's functional
        //turn it red when it is not
        TextView right = (TextView) findViewById(R.id.RightSensor);
        right.setBackgroundColor(Color.GREEN);

        //make the text representing the forward sensor green to represent that it's functional
        //turn it red when it is not
        TextView forward = (TextView) findViewById(R.id.Forward);
        forward.setBackgroundColor(Color.GREEN);

        //make the text representing the backward sensor green to represent that it's functional
        //turn it red when it is not
        TextView backward = (TextView) findViewById(R.id.Backward);
        backward.setBackgroundColor(Color.GREEN);

        //button that shows walls on screen
        Button wall = (Button) findViewById(R.id.walls);
        wall.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                state.handleUserInput(TOGGLELOCALMAP, 0);
                //make a pop up message saying the button was clicked
                Snackbar walls = Snackbar.make(findViewById(android.R.id.content), "Walls pressed", 500);
                walls.show();
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
                Snackbar full = Snackbar.make(findViewById(android.R.id.content), "Full maze pressed", 500);
                full.show();
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
                //make a pop up message saying the button was clicked
                Snackbar sma = Snackbar.make(findViewById(android.R.id.content), "Smaller pressed", 500);
                sma.show();
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
                //make a pop up message saying the button was clicked
                Snackbar b = Snackbar.make(findViewById(android.R.id.content), "Bigger pressed", 500);
                b.show();
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
            }
        });

        //temporary button that goes straight to the win screen
        /*Button win = (Button) findViewById(R.id.go2winning);
        win.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
        /*    public void onClick(View view){
                //make a pop up message saying the button was clicked
                Snackbar won = Snackbar.make(findViewById(android.R.id.content), "Pressed win", 500);
                won.show();
                //make a log message saying the button was clicked
                Log.v("Cheats", "Win pressed");
                //make an intent to go to WinningActivity
                Intent intentG = new Intent(view.getContext(), WinningActivity.class);
                //populate the intent with information regarding the steps taken, the shortest path, the energy consumption, and the fact that a robot was used
                intentG.putExtra("Steps taken", 500);
                intentG.putExtra("Shortest steps", 500);
                intentG.putExtra("Energy", 1000);
                intentG.putExtra("Robot", "y");
                //start WinningActivity
                startActivity(intentG);
            }
        });*/

        //temporary button that goes straight to the lose screen
        /*Button lose = (Button) findViewById(R.id.go2losing);
        lose.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
        /*    public void onClick(View view){
                //make a pop up message saying the button was clicked
                Snackbar lost = Snackbar.make(findViewById(android.R.id.content), "Pressed lose", 500);
                lost.show();
                //make a log message saying the button was clicked
                Log.v("Cheats", "Lose pressed");
                //make an intent to go to LosingActivity
                Intent intentG = new Intent(view.getContext(), LosingActivity.class);
                //populate the intent with information regarding the steps taken, the shortest path, the energy consumption, and the fact that a robot was used
                intentG.putExtra("Steps taken", 500);
                intentG.putExtra("Shortest steps", 500);
                intentG.putExtra("Energy", 3500);
                intentG.putExtra("Robot", "y");
                //start LosingActivity
                startActivity(intentG);
            }
        });*/
    }
}
