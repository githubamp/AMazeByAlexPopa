package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import edu.wm.cs.cs301.AlexPopa.R;

/**
 * author @ALEX POPA
 */
public class PlayAnimationActivity  extends AppCompatActivity {

    /**
     * when the activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the screen to the driver_maze xml
        setContentView(R.layout.driver_maze);

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
        Button win = (Button) findViewById(R.id.go2winning);
        win.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
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
        });

        //temporary button that goes straight to the lose screen
        Button lose = (Button) findViewById(R.id.go2losing);
        lose.setOnClickListener(new View.OnClickListener(){
            /**
             * on click of the button
             */
            public void onClick(View view){
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
        });
    }
}
