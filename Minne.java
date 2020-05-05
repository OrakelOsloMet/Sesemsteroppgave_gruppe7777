package sample;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Minne {

    private String produsenter;
    private String kapasitet;
    private String hastighet;
    private Double pris;
    private String komponentsnavn;

    Minne(){
        komponentsnavn="Minne";

    }

    public String getKomponentsnavn() {
        return komponentsnavn;
    }

    public void setProdusenter(String navn) throws Unntakk {
        String re= "(((C|c)rosair)|(C(C|c)rucial)|((D|d)ell)|((H|h)yperX))"; //lovelig input for navn av produsentere
        if (navn.isEmpty()) {
            throw new Unntakk("Vennligst, fyll produsenters navn!");
        }

        else if(!(navn).matches(re)){
            throw new Unntakk("Ugyldig navn!");
        }
        this.produsenter= navn;

    }


    public void setKapasitet(String kapasitet) throws Unntakk{
        String re= "((8)|(16)|(32))"; //lovelig input
        if ( kapasitet.isEmpty()){
            throw new Unntakk("Vennligst, fyll kapasitet!");
        }

        else if(!(kapasitet).matches(re)){
            throw new Unntakk("Ugyldig kapasitet!");
        }
        this.kapasitet =kapasitet;

    }

    public void setHastighet(String hastighet) throws Unntakk{
        String re= "((1600)|(2400)|(3200))"; //lovelig input
        if (hastighet.isEmpty()){
            throw new Unntakk("Vennligst, fyll hastighet!");
        }

        else if(!(hastighet).matches(re)){
            throw new Unntakk("Ugyldig hastighet!");
        }
        this.hastighet = hastighet;

    }

    public void setPris(String pris) throws Unntakk  {
        if (pris.isEmpty()){
            throw new Unntakk("Vennligst, fyll pris!");
        }
        //Hvis brukeren ikke  taste inn helttall, for eks bokstaver får en feilmelding
        if(true){
            try {
                Double p = Double.parseDouble(pris);
            } catch (Exception e){
                throw new Unntakk(" Pris må være tall!");
            }
        }

        Double p = Double.parseDouble(pris); //konvertere parametret til double slik at vi kan gjøre opperasjoner på
        if ( p <500 || p>2000){
            throw new Unntakk("Ugyldig pris!");
        }
        this.pris = p;

    }

    public String getProdusenter() {
        return produsenter;
    }

    public String getHastighet() {
        return hastighet;
    }

    public String getKapasitet() {
        return kapasitet;
    }

    public double getPris() {
        return pris;
    }


    @Override
    public String toString() {
        String all_spes= "navn: "+ produsenter+",  hastighet: "+ hastighet+ ",  kapasitet: "+ kapasitet + ",  pris: "+ pris;
        return all_spes;
    }


}
