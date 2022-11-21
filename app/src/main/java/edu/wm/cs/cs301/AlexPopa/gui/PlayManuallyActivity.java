package edu.wm.cs.cs301.AlexPopa.gui;

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

public class PlayManuallyActivity extends AppCompatActivity {

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_maze);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayUseLogoEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);

        ConstraintLayout parentLayout = (ConstraintLayout) findViewById(R.id.parent);
        MazePanel mazePanel = new MazePanel(this);
        parentLayout.addView(mazePanel, 0);

        Button wall = (Button) findViewById(R.id.Walls);
        wall.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar walls = Snackbar.make(findViewById(android.R.id.content), "Walls pressed", 500);
                walls.show();
                Log.v("Button", "Walls pressed");
            }
        });

        Button fmaze = (Button) findViewById(R.id.Maze);
        fmaze.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar full = Snackbar.make(findViewById(android.R.id.content), "Full maze pressed", 500);
                full.show();
                Log.v("Button", "Full maze pressed");
            }
        });

        Button sol = (Button) findViewById(R.id.Solution);
        sol.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar solut = Snackbar.make(findViewById(android.R.id.content), "Solution pressed", 500);
                solut.show();
                Log.v("Button", "Solution pressed");
            }
        });

        Button small = (Button) findViewById(R.id.button);
        small.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar sma = Snackbar.make(findViewById(android.R.id.content), "Smaller pressed", 500);
                sma.show();
                Log.v("Button", "Smaller pressed");
            }
        });

        Button big = (Button) findViewById(R.id.button2);
        big.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar b = Snackbar.make(findViewById(android.R.id.content), "Bigger pressed", 500);
                b.show();
                Log.v("Button", "Bigger pressed");
            }
        });

        ImageButton up = (ImageButton) findViewById(R.id.Up);
        up.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar u = Snackbar.make(findViewById(android.R.id.content), "Pressed up", 500);
                u.show();
                Log.v("Direction", "Up pressed");
                count++;
            }
        });

        ImageButton down = (ImageButton) findViewById(R.id.Down);
        down.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar d = Snackbar.make(findViewById(android.R.id.content), "Pressed down", 500);
                d.show();
                Log.v("Direction", "Down pressed");
                count++;
            }
        });

        ImageButton left = (ImageButton) findViewById(R.id.Left);
        left.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar l = Snackbar.make(findViewById(android.R.id.content), "Pressed left", 500);
                l.show();
                Log.v("Direction", "Left pressed");
                count++;
            }
        });

        ImageButton right = (ImageButton) findViewById(R.id.Right);
        right.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar r = Snackbar.make(findViewById(android.R.id.content), "Pressed right", 500);
                r.show();
                Log.v("Direction", "Right pressed");
                count++;
            }
        });

        Button jump = (Button) findViewById(R.id.Jump);
        right.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar j = Snackbar.make(findViewById(android.R.id.content), "Pressed jump", 500);
                j.show();
                Log.v("Direction", "Jump pressed");
                count++;
            }
        });

        Button shortcut = (Button) findViewById(R.id.Shortcut);
        shortcut.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar cut = Snackbar.make(findViewById(android.R.id.content), "Pressed shortcut", 500);
                cut.show();
                Log.v("Cheats", "Shortcut pressed");
                Intent intentG = new Intent(view.getContext(), WinningActivity.class);
                intentG.putExtra("Steps taken", count);
                intentG.putExtra("Shortest steps", 500);
                intentG.putExtra("Robot", "n");
                startActivity(intentG);
            }
        });
    }
}
