package com.example.tutor_ium.esercitazione_2;

import java.io.Serializable;

import java.util.Calendar;

/**
 * Created by Tutor_IUM on 17/10/2016.
 */

public class Person implements Serializable {
    private String name;
    private String surname;

    private Calendar birthDate;


    public Person(){
        this.setName("");
        this.setSurname("");
    }

    public Person(String name, String surname){
        this.setName(name);
        this.setSurname(surname);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }
}
