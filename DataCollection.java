package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class DataCollection {

    public ObservableList<Beholder> liste = FXCollections.observableArrayList();

    // å linke en TableView med listen og
    public void attachTableView(TableView tv) {
        tv.setItems(liste);
    }

    //en metode for å legge til nye objekter:
    public void addElement(Beholder obj) {
        liste.add(obj);
    }

    public ObservableList<Beholder> hentCollectionList(){
        return liste;
    }
    public void hent(){
        for(int i=0; i< liste.size(); i++){
            System.out.println( liste.get(i).toString());
        }


    }

}