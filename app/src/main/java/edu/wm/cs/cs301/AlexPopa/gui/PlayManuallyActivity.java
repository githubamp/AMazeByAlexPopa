package edu.wm.cs.cs301.AlexPopa.gui;

import static edu.wm.cs.cs301.AlexPopa.gui.Constants.UserInput.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import edu.wm.cs.cs301.AlexPopa.R;

/**
 * author @ALEX POPA
 */
public class PlayManuallyActivity extends AppCompatActivity {

    //keeps track of how many steps were taken
    //sent off to win screen when player finishes
    private int count = 0;

    /**
     * when the activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the screen to the manual_maze xml
        setContentView(R.layout.manual_maze);

        Information info = Information.getInformation();

        StatePlaying state = new StatePlaying();
        state.setMaze(info.getMaze());

        //button that shows walls on screen
        Button wall = (Button) findViewById(R.id.Walls);
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
        Button fmaze = (Button) findViewById(R.id.Maze);
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
        Button sol = (Button) findViewById(R.id.Solution);
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
        Button small = (Button) findViewById(R.id.button);
        small.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                state.handleUserInput(ZOOMOUT, 0);
                //make a pop up message saying the button was clicked
                Snackbar sma = Snackbar.make(findViewById(android.R.id.content), "Smaller pressed", 500);
                sma.show();
                //make a log message saying the button was clicked
                Log.v("Button", "Smaller pressed");
            }
        });

        //button that enlarges the size of the map on screen
        Button big = (Button) findViewById(R.id.button2);
        big.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                state.handleUserInput(ZOOMIN, 0);
                //make a pop up message saying the button was clicked
                Snackbar b = Snackbar.make(findViewById(android.R.id.content), "Bigger pressed", 500);
                b.show();
                //make a log message saying the button was clicked
                Log.v("Button", "Bigger pressed");
            }
        });

        //button that moves the player forward
        ImageButton up = (ImageButton) findViewById(R.id.Up);
        up.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                state.handleUserInput(UP, 0);
                //make a pop up message saying the button was clicked
                Snackbar u = Snackbar.make(findViewById(android.R.id.content), "Pressed up", 500);
                u.show();
                //make a log message saying the button was clicked
                Log.v("Direction", "Up pressed");
                //increase count
                count++;
            }
        });

        //button that moves the player backward
        ImageButton down = (ImageButton) findViewById(R.id.Down);
        down.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                state.handleUserInput(DOWN, 0);
                //make a pop up message saying the button was clicked
                Snackbar d = Snackbar.make(findViewById(android.R.id.content), "Pressed down", 500);
                d.show();
                //make a log message saying the button was clicked
                Log.v("Direction", "Down pressed");
                //increase count
                count++;
            }
        });

        //button that turns the player left
        ImageButton left = (ImageButton) findViewById(R.id.Left);
        left.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                state.handleUserInput(LEFT, 0);
                //make a pop up message saying the button was clicked
                Snackbar l = Snackbar.make(findViewById(android.R.id.content), "Pressed left", 500);
                l.show();
                //make a log message saying the button was clicked
                Log.v("Direction", "Left pressed");
                //increase count
                count++;
            }
        });

        //button that turns the player right
        ImageButton right = (ImageButton) findViewById(R.id.Right);
        right.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                state.handleUserInput(RIGHT, 0);
                //make a pop up message saying the button was clicked
                Snackbar r = Snackbar.make(findViewById(android.R.id.content), "Pressed right", 500);
                r.show();
                //make a log message saying the button was clicked
                Log.v("Direction", "Right pressed");
                //increase count
                count++;
            }
        });

        //button that makes the player jump forward
        Button jump = (Button) findViewById(R.id.Jump);
        jump.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                state.handleUserInput(JUMP, 0);
                //make a pop up message saying the button was clicked
                Snackbar j = Snackbar.make(findViewById(android.R.id.content), "Pressed jump", 500);
                j.show();
                //make a log message saying the button was clicked
                Log.v("Direction", "Jump pressed");
                //increase count
                count++;
            }
        });

        //temporary button that goes to the win screen
        Button shortcut = (Button) findViewById(R.id.Shortcut);
        shortcut.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
                //make a pop up message saying the button was clicked
                Snackbar cut = Snackbar.make(findViewById(android.R.id.content), "Pressed shortcut", 500);
                cut.show();
                //make a log message saying the button was clicked
                Log.v("Cheats", "Shortcut pressed");
                //make an intent to go to WinningActivity
                Intent intentG = new Intent(view.getContext(), WinningActivity.class);
                //populate the intent with information regarding the steps taken (count), the shortest path, and the fact no robot was used
                intentG.putExtra("Steps taken", count);
                intentG.putExtra("Shortest steps", 500);
                intentG.putExtra("Robot", "n");
                //start WinningActivity
                startActivity(intentG);
            }
        });
    }
}
