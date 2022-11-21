package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import edu.wm.cs.cs301.AlexPopa.R;

public class PlayAnimationActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_maze);

        Button wall = (Button) findViewById(R.id.walls);
        wall.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar walls = Snackbar.make(findViewById(android.R.id.content), "Walls pressed", 500);
                walls.show();
                Log.v("Button", "Walls pressed");
            }
        });

        Button fmaze = (Button) findViewById(R.id.fullmaze);
        fmaze.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar full = Snackbar.make(findViewById(android.R.id.content), "Full maze pressed", 500);
                full.show();
                Log.v("Button", "Full maze pressed");
            }
        });

        Button sol = (Button) findViewById(R.id.solution);
        sol.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar solut = Snackbar.make(findViewById(android.R.id.content), "Solution pressed", 500);
                solut.show();
                Log.v("Button", "Solution pressed");
            }
        });

        Button small = (Button) findViewById(R.id.smaller);
        small.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar sma = Snackbar.make(findViewById(android.R.id.content), "Smaller pressed", 500);
                sma.show();
                Log.v("Button", "Smaller pressed");
            }
        });

        Button big = (Button) findViewById(R.id.bigger);
        big.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar b = Snackbar.make(findViewById(android.R.id.content), "Bigger pressed", 500);
                b.show();
                Log.v("Button", "Bigger pressed");
            }
        });

        Button sp = (Button) findViewById(R.id.start);
        sp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar st = Snackbar.make(findViewById(android.R.id.content), "Start/pause pressed", 500);
                st.show();
                Log.v("Button", "Start/pause pressed");
            }
        });

        Button win = (Button) findViewById(R.id.go2winning);
        win.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar won = Snackbar.make(findViewById(android.R.id.content), "Pressed win", 500);
                won.show();
                Log.v("Cheats", "Win pressed");
                Intent intentG = new Intent(view.getContext(), WinningActivity.class);
                intentG.putExtra("Steps taken", 500);
                intentG.putExtra("Shortest steps", 500);
                intentG.putExtra("Energy", 1000);
                intentG.putExtra("Robot", "y");
                startActivity(intentG);
            }
        });

        Button lose = (Button) findViewById(R.id.go2losing);
        lose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Snackbar lost = Snackbar.make(findViewById(android.R.id.content), "Pressed lose", 500);
                lost.show();
                Log.v("Cheats", "Lose pressed");
                Intent intentG = new Intent(view.getContext(), LosingActivity.class);
                intentG.putExtra("Steps taken", 500);
                intentG.putExtra("Shortest steps", 500);
                intentG.putExtra("Energy", 3500);
                intentG.putExtra("Robot", "y");
                startActivity(intentG);
            }
        });
    }
}
