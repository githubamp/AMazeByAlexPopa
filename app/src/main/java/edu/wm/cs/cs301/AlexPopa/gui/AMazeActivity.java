package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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

    //titles for storing information
    public static final String PREFS = "prefs";
    public static final String ROOMS = "Yes";
    public static final String GEN = "DFS";
    public static final String SKILL = "0";
    public static final String SEED = "86422";

    private int diff;
    private boolean ro;
    private String generate;
    private int seed;

    public SeekBar seekbar;
    public Spinner spinner;
    public Spinner spinner2;

    /**
     * saves data for revisiting
     */
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(spinner.getSelectedItem().equals("Yes")){
            editor.putBoolean(ROOMS, true);
        }else{
            editor.putBoolean(ROOMS, false);
        }
        editor.putString(GEN, (String) spinner2.getSelectedItem());
        editor.putInt(SKILL, seekbar.getProgress());
        editor.putInt(SEED, seed);
        editor.apply();
        Log.v("Data", "Data saved");
    }

    /**
     *  loads data for revisiting
     */
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        ro = sharedPreferences.getBoolean(ROOMS, true);
        generate = sharedPreferences.getString(GEN, "");
        diff = sharedPreferences.getInt(SKILL, 0);
        seed = sharedPreferences.getInt(SEED, 86422);
        Log.v("Data", "Data loaded");
    }

    /**
     * when the activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the screen to the activity_main xml
        setContentView(R.layout.activity_main);

        //seekbar that represents difficulty
        seekbar = (SeekBar) findViewById(R.id.seekBar);

        //start music and make it loop
        MediaPlayer music = MediaPlayer.create(this, R.raw.hkintro);
        music.start();
        music.setLooping(true);

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
        spinner = (Spinner) findViewById(R.id.spinner);
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
                /*Snackbar spin1 = Snackbar.make(findViewById(android.R.id.content), "Yes/no pressed", 500);
                spin1.show();*/
                return false;
            }
        });

        //spinner that accepts which maze type they want to generate
        spinner2 = (Spinner) findViewById(R.id.spinner2);
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
                //stop music
                music.stop();
                music.release();
                Intent intentG = new Intent(view.getContext(), GeneratingActivity.class);
                //send all information to GeneratingActivity (skill level, rooms, generator, and seed)
                intentG.putExtra("Skill level", seekbar.getProgress());
                intentG.putExtra("Rooms", (String) spinner.getSelectedItem());
                intentG.putExtra("Generator", (String) spinner2.getSelectedItem());
                intentG.putExtra("Seed", Math.random()+100);
                //set all information for generating
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
                seed = rnd.nextInt(86422);
                //save this information
                saveData();
                info.setSeed(seed);
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
                if (generate == null) {
                    Snackbar re = Snackbar.make(findViewById(android.R.id.content), "Please play through a maze first", 1000);
                    re.show();
                }
                Intent intentG;
                intentG = new Intent(view.getContext(), GeneratingActivity.class);
                //stop the music
                music.stop();
                music.release();
                //load information
                loadData();
                //set all info for generating
                info.setSkill(diff);
                info.setSeed(seed);
                info.setRooms(ro);
                if(generate.equals("DFS")){
                    info.setGen(Order.Builder.DFS);
                }else if(generate.equals("PRIM")){
                    info.setGen(Order.Builder.Prim);
                }else{
                    info.setGen(Order.Builder.Boruvka);
                }
                info.setMaze(info.getPrevMaze());
                //start generating screen
                startActivity(intentG);
            }
        });
    }
}
