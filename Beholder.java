package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

//hjelpeklasse for å fylle TabelView med elementer fra ulike type klasser-objekter
public class Beholder{
    SimpleStringProperty knavn, spes;
    SimpleDoubleProperty k_pris;
    Beholder(String Knavn, String spes, Double k_pris){

    this.knavn=new SimpleStringProperty (Knavn);
    this.spes= new SimpleStringProperty (spes);
    this.k_pris= new SimpleDoubleProperty(k_pris);
    }

    public String getKnavn() {
        return knavn.getValue();
    }

    public String getSpes() {
        return spes.getValue();
    }

    public double getK_pris() {
        return k_pris.get();
    }

    public void setKnavn(String knavn) {
        this.knavn.set(knavn);
    }

    public void setSpes(String spes) {
        this.spes.set(spes);
    }

    public void setK_pris(double k_pris) { this.k_pris.set(k_pris); }




}
