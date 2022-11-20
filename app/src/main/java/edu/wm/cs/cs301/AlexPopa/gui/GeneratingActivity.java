package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import edu.wm.cs.cs301.AlexPopa.R;

public class GeneratingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generating);
        Context context = this;
        ProgressBar loading = (ProgressBar) findViewById(R.id.loading);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayUseLogoEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);
        Spinner spinner = (Spinner) findViewById(R.id.Driver);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.driver_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Spinner spinner2 = (Spinner) findViewById(R.id.Sensors);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.configuration_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        Thread load = new Thread(new Runnable(){
            boolean shown = false;
            public void run(){
                while(loading.getProgress() != loading.getMax()){
                    if(!spinner.getSelectedItem().equals("Select:") && shown == false) {
                        Snackbar wait = Snackbar.make(findViewById(android.R.id.content), "Please wait for the maze to finish generating", 2000);
                        wait.show();
                        shown = true;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loading.post(new Runnable() {
                        @Override
                        public void run() {
                            loading.incrementProgressBy(1);
                        }
                    });
                }
                if(spinner.getSelectedItem().equals("Select:")) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please select a driver", 2000);
                    snackbar.show();

                }
                boolean done = false;
                while(loading.getProgress() == loading.getMax() && done == false){
                    if(spinner.getSelectedItem().equals("Manual")){
                        done = true;
                        Intent intentG = new Intent(context, PlayManuallyActivity.class);
                        startActivity(intentG);
                    }else if (spinner.getSelectedItem().equals("Wall Follower") || (spinner.getSelectedItem().equals("Wizard"))){
                        done = true;
                        Intent intentG = new Intent(context, PlayAnimationActivity.class);
                        intentG.putExtra("Driver", (String) spinner.getSelectedItem());
                        intentG.putExtra("Sensors", (String) spinner2.getSelectedItem());
                        startActivity(intentG);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        load.start();
    }
}
