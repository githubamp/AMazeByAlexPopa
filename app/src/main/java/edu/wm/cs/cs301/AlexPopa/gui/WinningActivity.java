package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.AlexPopa.R;

public class WinningActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win);
        Intent information = getIntent();

        int pathLength = information.getIntExtra("Steps taken", 0);
        int small = information.getIntExtra("Shortest steps", 0);
        int spent = information.getIntExtra("Energy", 0);

        String rob = information.getStringExtra("Robot");

        EditText steps = (EditText) findViewById(R.id.Steps);
        EditText shortest = (EditText) findViewById(R.id.shortest);
        EditText energy = (EditText) findViewById(R.id.energyspent);

        steps.setText("You traveled " + pathLength + " steps");
        shortest.setText("The shortest path length was " + small + " steps");


        if(rob.equals("y")){
            energy.setVisibility(View.VISIBLE);
            energy.setText("Energy spent: " + spent);
        }else{
            energy.setVisibility(View.INVISIBLE);
        }
    }
}
