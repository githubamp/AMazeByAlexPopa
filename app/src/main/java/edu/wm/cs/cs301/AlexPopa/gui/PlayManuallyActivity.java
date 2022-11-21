package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

        ImageButton up = (ImageButton) findViewById(R.id.Up);
        up.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                count++;
            }
        });
        ImageButton down = (ImageButton) findViewById(R.id.Down);
        down.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                count++;
            }
        });
        ImageButton left = (ImageButton) findViewById(R.id.Left);
        left.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                count++;
            }
        });
        ImageButton right = (ImageButton) findViewById(R.id.Right);
        right.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                count++;
            }
        });

        Button shortcut = (Button) findViewById(R.id.Shortcut);
        shortcut.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intentG = new Intent(view.getContext(), WinningActivity.class);
                intentG.putExtra("Steps taken", count);
                startActivity(intentG);
            }
        });
    }
}
