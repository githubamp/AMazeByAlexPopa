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
