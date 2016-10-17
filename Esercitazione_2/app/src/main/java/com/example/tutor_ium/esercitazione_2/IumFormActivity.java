package com.example.tutor_ium.esercitazione_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IumFormActivity extends AppCompatActivity {

    Person person;

    EditText nameText, surnameText;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ium_form);

        nameText = (EditText) this.findViewById(R.id.attrNome);
        surnameText = (EditText) this.findViewById(R.id.attrCognome);

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
}
