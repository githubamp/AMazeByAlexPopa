package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

import edu.wm.cs.cs301.AlexPopa.R;
import edu.wm.cs.cs301.AlexPopa.generation.Order;

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

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             *  following two methods are just needed to override but do noting
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /**
             *  on change of the difficulty seekbar
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //make a log statement
                Log.v("Seekbar", "Difficulty pressed");
                //make a message appear from the bottom of the screen
                Snackbar seek = Snackbar.make(findViewById(android.R.id.content), "Difficulty pressed", 500);
                seek.show();
            }
        });

        Information info = Information.getInformation();

        //spinner that accepts whether someone wants rooms in the maze or not
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        //specifying the choices
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.yesno_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnTouchListener(new View.OnTouchListener() {
            /**
             *  on touch of the yes/no spinner
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //make a log statement
                Log.v("Spinner", "Yes/no pressed");
                //make a message appear from the bottom of the screen
                Snackbar spin1 = Snackbar.make(findViewById(android.R.id.content), "Yes/no pressed", 500);
                spin1.show();
                return false;
            }
        });

        //spinner that accepts which maze type they want to generate
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.generator_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner2.setOnTouchListener(new View.OnTouchListener() {
            /**
             *  on touch of the generator spinner
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //make a log statement
                Log.v("Spinner", "generator pressed");
                //make a message appear from the bottom of the screen
                Snackbar spin2 = Snackbar.make(findViewById(android.R.id.content), "Generator pressed", 500);
                spin2.show();
                return false;
            }
        });

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
                info.setSkill(seekbar.getProgress());
                if(spinner.getSelectedItem().equals("Yes")){
                    info.setRooms(true);
                }else{
                    info.setRooms(false);
                }
                System.out.println("spinner " + spinner2.getSelectedItem());
                if(spinner2.getSelectedItem().equals("DFS")){
                    info.setGen(Order.Builder.DFS);
                }else if(spinner2.getSelectedItem().equals("PRIM")){
                    info.setGen(Order.Builder.Prim);
                }else{
                    info.setGen(Order.Builder.Boruvka);
                }
                Random rnd = new Random();
                info.setSeed(rnd.nextInt(86422));
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
                if(info.getPrevMaze() == null){
                    Snackbar re = Snackbar.make(findViewById(android.R.id.content), "Please play through a maze first", 1000);
                    re.show();
                }else {
                    Intent intentG;
                    if (info.getPrevDriver() instanceof WallFollower || info.getPrevDriver() instanceof Wizard) {
                        intentG = new Intent(view.getContext(), PlayAnimationActivity.class);
                    } else {
                        intentG = new Intent(view.getContext(), PlayManuallyActivity.class);
                    }
                    /*intentG.putExtra("Skill level", seekbar.getProgress());
                    intentG.putExtra("Rooms", (String) spinner.getSelectedItem());
                    intentG.putExtra("Generator", (String) spinner2.getSelectedItem());
                    intentG.putExtra("Seed", Math.random()+100);*/

                    info.setSkill(info.getPrevSkill());
                    info.setSeed(info.getPrevSeed());
                    info.setRooms(info.getPrevRooms());
                    info.setGen(info.getPrevGen());
                    info.setMaze(info.getPrevMaze());
                    info.setRobot((info.getPrevRobot()));
                    info.setDriver(info.getPrevDriver());
                    startActivity(intentG);
                }
            }
        });
    }
}
