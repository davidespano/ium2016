package com.example.tutor_ium.esercitazione_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class IumFormResultActivity extends AppCompatActivity {
    Person person;

    TextView nameText, surNameText, birthdateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ium_form_result);

        nameText = (TextView) findViewById(R.id.attrNomeRes);
        surNameText = (TextView) findViewById(R.id.attrCognomeRes);
        birthdateText = (TextView) findViewById(R.id.attrDateRes);

        Intent intent = getIntent();
        Serializable obj =  intent.getSerializableExtra(
                IumFormActivity.PERSON_EXTRA);

        if(obj instanceof Person) {
            person = (Person) obj;
        }
        else{
            person = new Person();
        }

        nameText.setText(person.getName());
        surNameText.setText(person.getSurname());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        birthdateText.setText(format.format(
                person.getBirthDate().getTime()));
    }
}
