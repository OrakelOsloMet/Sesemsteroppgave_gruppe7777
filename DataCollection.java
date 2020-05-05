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

}