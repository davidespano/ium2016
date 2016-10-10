package com.example.tutor_ium.esercitazione_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
/* Attributi */
    static final int MinValue = 1;
    static final int MaxValue = 100;
    int valueModel = 50;

    // riferimento foglie
    Button sommaButton, sottrazioneButton;
    SeekBar seekBar;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inizializzazione foglie
        seekBar = (SeekBar) findViewById(R.id.sliderInput);
        text = (EditText) findViewById(R.id.input);
        sommaButton = (Button) findViewById(R.id.incremento);
        sottrazioneButton = (Button) findViewById(R.id.decremento);

        text.setText(""+valueModel);

        sommaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("" + (++valueModel));
            }
        });

        sottrazioneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                text.setText("" + (--valueModel));
            }
        });
    }
}
