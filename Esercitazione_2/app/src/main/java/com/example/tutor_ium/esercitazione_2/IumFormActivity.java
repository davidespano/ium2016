package com.example.tutor_ium.esercitazione_2;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.text.SimpleDateFormat;


public class IumFormActivity extends AppCompatActivity {

    Person person;

    EditText nameText, surnameText, birthText;
    Button saveButton;
    TextView errorText;
    boolean isResumed;

    DatePickerFragment datePickerFragment;

    public static final  String PERSON_EXTRA = "com.example.tutor_ium.esercitazione_2.Person";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ium_form);
        person= new Person();

        isResumed = false;
        datePickerFragment = new DatePickerFragment();

        nameText = (EditText) this.findViewById(R.id.attrNome);
        surnameText = (EditText) this.findViewById(R.id.attrCognome);
        birthText = (EditText) this.findViewById(R.id.attrDate);

        errorText = (TextView) this.findViewById((R.id.errorText));
        errorText.setVisibility(View.GONE);

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

        datePickerFragment.setOnDatePickerFragmentChanged(new DatePickerFragment.DatePickerFragmentListener() {
            @Override
            public void onDatePickerFragmentOkButton(android.app.DialogFragment dialog, java.util.Calendar date) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                birthText.setText(format.format(date.getTime()));
            }

            @Override
            public void onDatePickerFragmentCancelButton(android.app.DialogFragment dialog) {}
    });

        //Configurazione Pulsante di Salvataggio
        saveButton = (Button) this.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(checkInput()){
                UpdatePerson();

                Intent showResult = new Intent(IumFormActivity.this, IumFormResultActivity.class);
                showResult.putExtra(PERSON_EXTRA, person);
                startActivity(showResult);
            }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        isResumed = true;
    }

    private boolean checkInput(){
        int errors = 0;

        if(nameText.getText() == null || nameText.getText().length() == 0){
            nameText.setError("Inserire il nome");
            errors++;
        }
        else {
            nameText.setError(null);
        }

        if(surnameText.getText() == null || surnameText.getText().length() == 0){
            surnameText.setError("Inserire il cognome");
            errors++;
        }
        else {
            surnameText.setError(null);
        }
        if(birthText.getText() == null || birthText.getText().length() == 0){
            birthText.setError("Inserire la data di nascita");
            errors++;
        }
        else{
            birthText.setError(null);
        }

        switch (errors){
            case 0:
                errorText.setVisibility(View.GONE);
                errorText.setText("");
                break;
            case 1:
                errorText.setVisibility(View.VISIBLE);
                errorText.setText("Si è verificato un Errore");
                break;
            default:
                errorText.setVisibility(View.VISIBLE);
                errorText.setText("Si sono verificati "+errors+" errori");
                break;
        }

        return errors == 0;
    }

    private void UpdatePerson(){
        this.person.setName(this.nameText.getText().toString());
        this.person.setSurname(this.surnameText.getText().toString());
        this.person.setBirthDate(this.datePickerFragment.getDate());
    }
}
