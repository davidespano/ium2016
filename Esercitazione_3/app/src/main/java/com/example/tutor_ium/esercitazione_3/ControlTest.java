package com.example.tutor_ium.esercitazione_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ControlTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_test);

        Series<String> s = new Series();
        for(int i = 0; i<6; i++){
            s.addElement("elemento " +i, (float) (Math.random() *1000));
        }

        LineGraph linegraph = (LineGraph) findViewById(R.id.linegraph);
        linegraph.setSeries(s);
    }
}
