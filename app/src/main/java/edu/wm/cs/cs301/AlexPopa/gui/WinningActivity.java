package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.AlexPopa.R;

/**
 * author @ALEX POPA
 */
public class WinningActivity  extends AppCompatActivity {

    /**
     * when the activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the screen to the win xml
        setContentView(R.layout.win);
        //get the information from the intent that started this activity
        Intent information = getIntent();

        final Vibrator vib =  (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
        //vib.cancel();

        //receive the information from the intent and make a variable for each appropriate value (pathlength, shortest path, and energy spent)
        int pathLength = information.getIntExtra("Steps taken", 0);
        int small = information.getIntExtra("Shortest steps", 0);
        int spent = information.getIntExtra("Energy", 0);

        //check if a robot was used instead of manual
        String rob = information.getStringExtra("Robot");

        //make variables for the text on the xml so that it can be edited
        EditText steps = (EditText) findViewById(R.id.Steps);
        EditText shortest = (EditText) findViewById(R.id.shortest);
        EditText energy = (EditText) findViewById(R.id.energyspent);

        //add the intent information to the text
        steps.setText("You traveled " + pathLength + " steps");
        shortest.setText("The shortest path length was " + small + " steps");

        //if a robot was used, make the energy text variable and include the energy that was spent
        if(rob.equals("y")){
            energy.setVisibility(View.VISIBLE);
            energy.setText("Energy spent: " + spent);
        }else{
            //if it was manual, make the energy text invisible and don't edit it
            energy.setVisibility(View.INVISIBLE);
        }
    }
}
