package com.example.tutor_ium.esercitazione_3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tutor_IUM on 06/11/2016.
 */

/**
 * La classe rappresenta la serie che verrà utilizzata dalla nostra estensione di View
 * Andremo quindi a creare tutti i metodi e gli attributi per la corretta rappresentazione e gestione
 */
public class Series<T> implements Serializable {
    protected List<T> items;
    protected List<Double> values;

    public Series() {
        this.items = new ArrayList<T>();
        this.values = new ArrayList<Double>();
    }

    /**
     * Permette l'inserimento di nuovi elementi in coda alla serie
     * @param item etichetta alfanumerica del nuovo elemento
     * @param value valore reppresentato
     */
    public void addElement(T item, Double value){
        this.items.add(item);
        this.values.add(value);
    }

    /**
     * Restituisce il nome dell'elemento situato in una determinata posizione
     * @param i indice dell'elemento desiderato
     * @return  etichetta alfanumerica
     */
    public T itemAt(int i) {
        if(i < 0 || i >= this.items.size()) return null;

        return this.items.get(i);
    }

    /**
     * Restituisce il valore dell'elemento situato in una determinata posizione
     * @param i indice dell'elemento desiderato
     * @return valore richiesto
     */
    public Double valueAt (int i){
        if(i < 0 || i >= this.values.size()) return null;

        return this.values.get(i);
    }

    /**
     * Restituisce ila grandezza della serie numerica
     * @return numero di elementi totali
     */
    public int getCount(){
        return this.items.size();
    }

    /**
     * Modifica dell'etichetta e del valore di un determinato elemento
     * @param i indice elemento desiderato
     * @param item nuova etichetta
     * @param value nuovo valore
     * @return esito dell'operazione
     */
    public boolean replaceElement(int i, T item, Double value){
        if(i < 0 || i >= this.items.size()) return false;

        this.items.set(i, item);
        this.values.set(i, value);

        return true;
    }

    /**
     * Restiuisce l'intera lista delle etichette
     * @return lista di etichette
     */
    public Iterable<T> getItems(){
        return this.items;
    }

    /**
     * Restituisce l'intera lista dei valori
     * @return lista di valori
     */
    public Iterable<Double> getValues(){
        return this.values;
    }
}
