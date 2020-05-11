package sample;

import java.io.*;

import static sample.Dialogs.showErrorDialog;

public class Skriv_til_fil {
    Skriv_til_fil(){

    }

    static void skriv_til(String filnavn){

        PrintWriter pw = null;
        FileWriter fw= null; //den lar oss skrive tileksisterende fil og unngår å overskrive info som ble lagt fra gør til fil
        try {

            fw= new FileWriter(filnavn, true);//hvis jeg setter parameter til false , kommer den til å override info
            pw = new PrintWriter(fw);

        } catch (FileNotFoundException e) {
       showErrorDialog("Fil ikke funnet!");
       System.exit(1); //drepe kjøringen
        }catch (IOException e) {
            showErrorDialog("IOException");  //eller "Det som failer er: "+e.getMessage()
            System.exit(1); //drepe kjøringen
        }
    }
}