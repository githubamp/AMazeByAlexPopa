package edu.wm.cs.cs301.AlexPopa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.AlexPopa.R;

public class AMazeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.yesno_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.generator_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        Button explore = (Button) findViewById(R.id.explore);
        explore.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intentG = new Intent(view.getContext(), GeneratingActivity.class);
                intentG.putExtra("Skill level", seekbar.getProgress());
                intentG.putExtra("Rooms", (String) spinner.getSelectedItem());
                intentG.putExtra("Generator", (String) spinner2.getSelectedItem());
                startActivity(intentG);
            }
        });
    }
}