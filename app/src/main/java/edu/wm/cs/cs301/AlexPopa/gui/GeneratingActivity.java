package edu.wm.cs.cs301.AlexPopa.gui;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.AlexPopa.R;

public class GeneratingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generating);
        ProgressBar loading = (ProgressBar) findViewById(R.id.loading);
        new Thread(new Runnable(){
            public void run(){
                while(loading.getProgress() != loading.getMax()){
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
            }
        }).start();

        /*Thread load = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(loading.getProgress() != loading.getMax()){
                        //Thread.sleep(5);
                        loading.post(new Runnable() {
                            @Override
                            public void run() {
                                loading.incrementProgressBy(1);
                            }
                        });
                    }
                }catch(Exception E){

                }
            }
        });
        load.run();*/
    }
}
