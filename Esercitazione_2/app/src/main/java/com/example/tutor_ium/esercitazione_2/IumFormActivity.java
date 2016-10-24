package com.example.tutor_ium.esercitazione_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IumFormActivity extends AppCompatActivity {

    Person person;

    EditText nameText, surnameText, birthText;
    Button saveButton;
    boolean isResumed;

    DatePickerFragment datePickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ium_form);

        isResumed = false;
        datePickerFragment = new DatePickerFragment();

        nameText = (EditText) this.findViewById(R.id.attrNome);
        surnameText = (EditText) this.findViewById(R.id.attrCognome);
        birthText = (EditText) this.findViewById(R.id.attrDate);

        //Configurazione Eventi Dialog Calendar
        birthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });

        birthText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    datePickerFragment.show(getFragmentManager(), "datePicker");
                }
            }
        });

        //Configurazione Pulsante di Salvataggio
        saveButton = (Button) this.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameText.getText() == null || nameText.getText().length() == 0){
                    nameText.setError("Inserire il nome");
                }
                else {
                    nameText.setError(null);
                }

                if(surnameText.getText() == null || surnameText.getText().length() == 0){
                    surnameText.setError("Inserire il cognome");
                }
                else {
                    surnameText.setError(null);
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        isResumed = true;
    }
}
