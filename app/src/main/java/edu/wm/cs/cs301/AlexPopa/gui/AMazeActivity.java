package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import edu.wm.cs.cs301.AlexPopa.R;

/**
 * author @ALEX POPA
 */
public class AMazeActivity extends AppCompatActivity {

    /**
     * when the activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the screen to the activity_main xml
        setContentView(R.layout.activity_main);

        //seekbar that represents difficulty
        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar);

        //spinner that accepts whether someone wants rooms in the maze or not
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        //specifying the choices
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.yesno_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //spinner that accepts which maze type they want to generate
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.generator_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        //button to start the game
        Button explore = (Button) findViewById(R.id.explore);
        explore.setOnClickListener(new View.OnClickListener(){
            /**
             *  on click of the explore button
             */
            public void onClick(View view){
                //make a log statement
                Log.v("Explore", "Explore pressed");
                //make a message appear from the bottom of the screen
                Snackbar ex = Snackbar.make(findViewById(android.R.id.content), "Explore pressed", 1000);
                ex.show();
                Intent intentG = new Intent(view.getContext(), GeneratingActivity.class);
                //send all information to GeneratingActivity (skill level, rooms, generator, and seed)
                intentG.putExtra("Skill level", seekbar.getProgress());
                intentG.putExtra("Rooms", (String) spinner.getSelectedItem());
                intentG.putExtra("Generator", (String) spinner2.getSelectedItem());
                intentG.putExtra("Seed", Math.random()+100);
                //start GeneratingActivity
                startActivity(intentG);
            }
        });

        //button to revisit the previous maze (currently does the exact same thing as explore)
        Button revisit = (Button) findViewById(R.id.revisit);
        revisit.setOnClickListener(new View.OnClickListener(){
            /**
             *  on click of the revisit button
             */
            public void onClick(View view){
                Log.v("Revisit", "Revisit pressed");
                Snackbar re = Snackbar.make(findViewById(android.R.id.content), "Revisit pressed", 1000);
                re.show();
                Intent intentG = new Intent(view.getContext(), GeneratingActivity.class);
                intentG.putExtra("Skill level", seekbar.getProgress());
                intentG.putExtra("Rooms", (String) spinner.getSelectedItem());
                intentG.putExtra("Generator", (String) spinner2.getSelectedItem());
                intentG.putExtra("Seed", Math.random()+100);
                startActivity(intentG);
            }
        });
    }
}
