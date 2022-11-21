package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.AlexPopa.R;

/**
 * author @ALEX POPA
 */
public class LosingActivity extends AppCompatActivity {

    /**
     * when the activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the screen to the lose xml
        setContentView(R.layout.lose);
        //get the information from the intent that started this activity
        Intent information = getIntent();

        //receive the information from the intent and make a variable for each appropriate value (pathlength, shortest path, and energy spent)
        int pathLength = information.getIntExtra("Steps taken", 0);
        int small = information.getIntExtra("Shortest steps", 0);
        int spent = information.getIntExtra("Energy", 0);

        //make variables for the text on the xml so that it can be edited
        EditText steps = (EditText) findViewById(R.id.steps2);
        EditText shortest = (EditText) findViewById(R.id.shortest2);
        EditText energy = (EditText) findViewById(R.id.energyspent2);

        //add the intent information to the text
        steps.setText("You traveled " + pathLength + " steps");
        shortest.setText("The shortest path length was " + small + " steps");
        energy.setText("Energy spent: " + spent);
    }
}