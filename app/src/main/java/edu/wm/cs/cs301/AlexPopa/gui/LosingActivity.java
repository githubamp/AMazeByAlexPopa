package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.AlexPopa.R;

public class LosingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lose);
        Intent information = getIntent();

        int pathLength = information.getIntExtra("Steps taken", 0);
        int small = information.getIntExtra("Shortest steps", 0);
        int spent = information.getIntExtra("Energy", 0);

        EditText steps = (EditText) findViewById(R.id.steps2);
        EditText shortest = (EditText) findViewById(R.id.shortest2);
        EditText energy = (EditText) findViewById(R.id.energyspent2);

        steps.setText("You traveled " + pathLength + " steps");
        shortest.setText("The shortest path length was " + small + " steps");
        energy.setText("Energy spent: " + spent);
    }
}