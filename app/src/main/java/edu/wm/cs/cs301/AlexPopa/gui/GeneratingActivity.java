package edu.wm.cs.cs301.AlexPopa.gui;

import static edu.wm.cs.cs301.AlexPopa.generation.Order.Builder.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import edu.wm.cs.cs301.AlexPopa.R;
import edu.wm.cs.cs301.AlexPopa.generation.DefaultOrder;
import edu.wm.cs.cs301.AlexPopa.generation.MazeFactory;
import edu.wm.cs.cs301.AlexPopa.generation.Order;

/**
 * author @ALEX POPA
 */
public class GeneratingActivity extends AppCompatActivity {

    /**
     * when the activity is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the screen to the generating xml
        setContentView(R.layout.generating);
        //store the current context for later use when it cannot be easily accessed
        Context context = this;

        //progressbar for how much of the maze has generated
        ProgressBar loading = (ProgressBar) findViewById(R.id.loading);

        //spinner to allow the user to choose what driver they would like for the maze
        Spinner spinner = (Spinner) findViewById(R.id.Driver);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.driver_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnTouchListener(new View.OnTouchListener() {
            /**
             *  on touch of the driver spinner
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //make a log statement
                Log.v("Spinner", "Driver pressed");
                //make a message appear from the bottom of the screen
                Snackbar drive = Snackbar.make(findViewById(android.R.id.content), "Driver pressed", 500);
                drive.show();
                return false;
            }
        });

        //spinner to allow the user to choose what sensor configuration they would like for the robot (useless if not used)
        Spinner spinner2 = (Spinner) findViewById(R.id.Sensors);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.configuration_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner2.setOnTouchListener(new View.OnTouchListener() {
            /**
             *  on touch of the configuration spinner
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //make a log statement
                Log.v("Spinner", "Configuration pressed");
                //make a message appear from the bottom of the screen
                Snackbar config = Snackbar.make(findViewById(android.R.id.content), "Configuration pressed", 500);
                config.show();
                return false;
            }
        });

        //start a thread to represent the maze generating
        Thread load = new Thread(new Runnable(){
            //variable to keep track if a Snackbar has shown (don't want to show multiple of the same message)
            boolean shown = false;

            /**
             * run the thread to load the progressbar
             */
            public void run(){
                //while the progress bar isn't full
                while(loading.getProgress() != loading.getMax()){
                    //if someone has chosen a driver but the bar is still loading
                    if(!spinner.getSelectedItem().equals("Select:") && shown == false) {
                        //make a pop up message
                        Snackbar wait = Snackbar.make(findViewById(android.R.id.content), "Please wait for the maze to finish generating", 2000);
                        wait.show();
                        //makes sure the message doesn't show up again
                        shown = true;
                    }
                    try {
                        //represents the maze generating by making the bar load by 1% every 50 milliseconds
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Thread build = factory.getBuild();

                    /*loading.post(new Runnable() {
                        @Override
                        /**
                         * increment the progress bar by 1
                         */
                       /* public void run() {
                            loading.incrementProgressBy(1);
                        }
                    });*/
                }
                //if the bar is finished loading and the user hasn't chosen a driver yet
                if(spinner.getSelectedItem().equals("Select:")) {
                    //make a pop up message asking to pick a driver
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please select a driver", 2000);
                    snackbar.show();
                }
                //boolean value to keep track of whether the user has chosen a driver and the progress bar is full
                boolean done = false;
                //while the progress bar is full, keep checking if the user has picked a driver
                while(loading.getProgress() == loading.getMax() && done == false){
                    //if manual is chosen
                    if(spinner.getSelectedItem().equals("Manual")){
                        done = true;
                        //make an intent to go to PlayManuallyActivity and start it
                        Intent intentG = new Intent(context, PlayManuallyActivity.class);
                        startActivity(intentG);
                    //if a robot driver was chosen
                    }else if (spinner.getSelectedItem().equals("Wall Follower") || (spinner.getSelectedItem().equals("Wizard"))){
                        done = true;
                        //make an intent to go to PlayAnimationActivity and start it
                        Intent intentG = new Intent(context, PlayAnimationActivity.class);
                        //pass along information regarding the driver and sensors used to PlayAnimationActivity
                        intentG.putExtra("Driver", (String) spinner.getSelectedItem());
                        intentG.putExtra("Sensors", (String) spinner2.getSelectedItem());
                        startActivity(intentG);
                    }
                    try {
                        //thread sleeps as to not cause a loop that keeps going without pause
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //begin the thread
        load.start();
    }

    public void update(){
        Information info = Information.getInformation();
        MazeFactory factory = new MazeFactory();
        Order order = new DefaultOrder(info.getSkill(), info.getGen(), info.getRooms(), info.getSeed());
        factory.order(order);


    }
}
