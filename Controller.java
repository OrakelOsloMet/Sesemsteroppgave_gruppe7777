package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;


public class Controller  implements Initializable {

    @FXML
    private TextField txt_type;

    @FXML
    private TextField txt_antallcore;

    @FXML
    private TextField txt_antallthreads;

    @FXML
    private TextField txt_pris; //pris til prossessor

    @FXML
    private Label lbl_feilmelding; //feilmelding til prossessor



    @FXML
    private TextField txt_søkfelt;


    @FXML
    void btn_add_prosessor(ActionEvent event) throws Unntakk{
        try{
         Prosessor pros=new Prosessor (); //klasse-instans


        pros.setType(txt_type.getText());

        pros.setAntallCore(txt_antallcore.getText());

        pros.setAntallThreads(txt_antallthreads.getText());

        pros.setPris(txt_pris.getText());

        Beholder b=new Beholder(pros.getKomponentsnavn(), pros.toString(), pros.getPris());

        collection.addElement(b);

        lbl_feilmelding.setTextFill(Color.web("#1c9b23"));// gi beskjed med grønn text at man har satt alt info riktiglbl_feilmelding.setText("Du har lagt et nytt element til TabelView!");//......lagre info............
        lbl_feilmelding.setText("\"Du har lagt et nytt element til TabelView!\"");

        //reset prosessor etter info er lagret
         txt_type.setText("");
         txt_antallcore.setText("");
         txt_antallthreads.setText("");
            txt_pris.setText("");
         //reset minne

        } catch (Unntakk e){
            lbl_feilmelding.setTextFill(Color.web("#d62828")); // vise feilmelding med rød farge
            lbl_feilmelding.setText(e.getMessage());
        }

    }

//......................................................................................................................



//Minne
    @FXML
    private TextField txt_produsenter;

    @FXML
    private TextField txt_kapasitet;

    @FXML
    private TextField txt_hastighet;

    @FXML
    private Label lbl_feilmelding_minne;

    @FXML
    private TextField txt_pris_minne;
    Minne obj;



    @FXML
    void btn_add_minne(ActionEvent event) {

         obj = new Minne();
            try{

            obj.setProdusenter(txt_produsenter.getText());
            obj.setHastighet (txt_hastighet.getText() );
            obj.setKapasitet (txt_kapasitet.getText());
            obj.setPris(txt_pris_minne.getText());
            Beholder b=new Beholder(obj.getKomponentsnavn(), obj.toString(), obj.getPris());
            collection.addElement(b);
                lbl_feilmelding_minne.setTextFill(Color.web("#1c9b23"));// gi beskjed med grønn text at man har satt alt info riktig
                lbl_feilmelding_minne.setText("Du har lagt et nytt element til TabelView!");
            //......lagre info............

            //reset minne etter info er lagret
                txt_produsenter.setText("");
                txt_hastighet.setText("");
                txt_kapasitet.setText("");
                txt_pris_minne.setText("");


            } catch (Unntakk u){
                lbl_feilmelding_minne.setTextFill(Color.web("#d62828")); // vise feilmelding med rød farge
                lbl_feilmelding_minne.setText(u.getMessage());
            }





        //....info til fil (automatisk)
        //...en kanpp for hente info
        //.................leggetabelview...................



    }
//......................................................................................................................


    @FXML
    TableView <Beholder> tableView;

    DataCollection collection = new DataCollection();

    ObservableList<Beholder> obs;

    @FXML
    TableColumn<Beholder, Double> kolonne_pris;
    @Override

    public void initialize (URL location, ResourceBundle resources) {

        collection.attachTableView(tableView);
        kolonne_pris.setCellFactory(TextFieldTableCell.forTableColumn(  new javafx.util.converter.DoubleStringConverter()));
        obs = tableView.getItems();


        //filterering
        ObservableList<Beholder> obslist = collection.liste;
        FilteredList<Beholder> filteredData = new FilteredList<>(obslist, b -> true);
        txt_søkfelt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(beholder -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (beholder.getKnavn().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (beholder.getSpes().toLowerCase().contains(lowerCaseFilter))
                    return true;

                return false;
            });
        });

        SortedList<Beholder> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }


    // fjerne valgte rad fra tabellen
    @FXML
    void btn_fjern_rad_fraTV(ActionEvent event){
        tableView.setItems(obs);
        tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem() );
    }


    //redigering på kolonner
    public void RedigerKnavn(TableColumn.CellEditEvent<Beholder,String> event) {
        event.getRowValue().setKnavn(event.getNewValue());
    }
    public void RedigerSpes(TableColumn.CellEditEvent<Beholder,String> event) {
        event.getRowValue().setSpes(event.getNewValue());
    }

    public void RedigerPris(TableColumn.CellEditEvent<Beholder,Double> event) {
        event.getRowValue().setK_pris(event.getNewValue());
    }

    @FXML
    void btn_lagre_til_fil(ActionEvent event) {
        FileChooser saver = new FileChooser();

    }


}

/*
  public void btnLagre(ActionEvent event) throws IOException {
        FileChooser saver = new FileChooser();
        Stage mainStage = new Stage();
        saver.setTitle("Lagere In i fil");
        saver.getExtensionFilters().addAll(new ExtensionFilter[]{new ExtensionFilter("Text Filer", new String[]{"*.txt"})});
        File selectedFile = saver.showSaveDialog(mainStage);
        if (selectedFile != null) {
            String paths = selectedFile.getAbsolutePath();
            Path path = Paths.get(paths);
            LagringTxt.skriveInn(this.register.liste, path);
        }
 */