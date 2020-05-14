package sample;

import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;

import static sample.Dialogs.showErrorDialog;
import static sample.Dialogs.showSuccessDialog;

public class Controller  implements Initializable {


    //Prossessor sine fx-objekter
    @FXML
    private TextField txt_type, txt_antallcore, txt_antallthreads, txt_pris;

    @FXML
    private Label lbl_feilmelding; //feilmelding til prossessor


    @FXML
    private TextField txt_søkfelt;//filtering


    ArrayList<Beholder> data_til_Fil= new  ArrayList<Beholder>();//hjelpe-array til fil-

    @FXML
    private ProgressIndicator indikator_pros;
    @FXML
    private Button btn_add_prosessor;
    @FXML
    private Button btn_add_minne;
    @FXML
    private Button btn_fjern_rad_fraTV;

    @FXML
    private Button btn_lagre_til_fil;

    @FXML
    private Button Sortering_pris;

    //.........Harddisk........................
    @FXML
    private Button btn_add_hd;

    @FXML
    private TextField txt_produsenter_hd;

    @FXML
    private TextField txt_kapasitet_hd;

    @FXML
    private TextField txt_type_hd;

    @FXML
    private TextField txt_pris_hd;

    @FXML
    private Label lbl_feilmelding_hd;

    @FXML
    void btn_add_hd(ActionEvent event) {

        Harddisk obj = new Harddisk();
        try{

            obj.setProdusenter(txt_produsenter_hd.getText());
            obj.setKapasitet (txt_kapasitet_hd.getText());
            obj.setType(txt_type_hd.getText() );
            obj.setPris(txt_pris_hd.getText());

            Beholder b=new Beholder(obj.getKomponentsnavn(), obj.toString(), obj.getPris());

            collection.addElement(b);

            lbl_feilmelding_hd.setTextFill(Color.web("#1c9b23"));// gi beskjed med grønn text at man har satt alt info riktig

            lbl_feilmelding_hd.setText("Du har lagt et nytt element til TabelView!");
            data_til_Fil.add(b);

            //reset minne etter info er lagret
            txt_produsenter_hd.setText("");
            txt_type_hd.setText("");
            txt_kapasitet_hd.setText("");
            txt_pris_hd.setText("");


        } catch (Unntakk u){
            lbl_feilmelding_hd.setTextFill(Color.web("#d62828")); // vise feilmelding med rød farge
            lbl_feilmelding_hd.setText(u.getMessage());
        }

    }




// ............ hjelpe-metoder til tråd-lsøning.......................................................
    Beholder temp=null;
    private void deakivere_fx_objekter(){
        tableView.setEditable(false);
        btn_fjern_rad_fraTV.setDisable(true);
        txt_søkfelt.setDisable(true);
        btn_add_hd.setDisable(true);
        btn_add_minne.setDisable(true);
        btn_add_prosessor.setDisable(true);
        Sortering_pris.setDisable(true);
        btn_lagre_til_fil.setDisable(true);
    }//en annen måte for å deaktivere all objekter er bruk av StackPane

    private void akivere_fx_objekter(){
        tableView.setEditable(true);
        btn_fjern_rad_fraTV.setDisable(false);
        txt_søkfelt.setDisable(false);
        btn_add_hd.setDisable(false);
        btn_add_minne.setDisable(false);
        btn_add_prosessor.setDisable(false);
        Sortering_pris.setDisable(false);
        btn_lagre_til_fil.setDisable(false);
    }

    private void traadUtført(WorkerStateEvent e) {

        lbl_feilmelding.setText("Hurra, ett nytt element til tabellen! Du kan legge flere.");//obs. du kan gjerne legge et nytt.
        lbl_feilmelding.setTextFill(Color.web("#1c9b23"));//bytte farge til grønn.
        akivere_fx_objekter();

        if(temp!=null)
            collection.addElement(temp); //etter at tråden blir ferdig, legger vi elementet til tableViewet
    }

    private void traadAvsluttet(WorkerStateEvent event) {
        lbl_feilmelding.setTextFill(Color.web("#d62828"));//rød farge
        lbl_feilmelding.setText("Avviket sier: " + event.getSource().getException().getMessage());
        akivere_fx_objekter();

    }


//...................................................................................................

    @FXML
    void btn_add_prosessor(ActionEvent event) throws Unntakk{
        try{

              Prosessor pros=new Prosessor (); //klasse-instans
              pros.setType(txt_type.getText());
              pros.setAntallCore(txt_antallcore.getText());
              pros.setAntallThreads(txt_antallthreads.getText());
              pros.setPris(txt_pris.getText());
              temp=new Beholder(pros.getKomponentsnavn(), pros.toString(), pros.getPris());
        //.........................tråd-løsning.....................................................
        //Obs. vi har flere metoder som laster inn data på superbrukerens side men vi valgte å
        // implementere Thread bare i denne metoden slik at det blir lettere for deg å lese koden vår.

            Oppdrag oppdrag=new Oppdrag();
            lbl_feilmelding.setText("vent litt..");
            deakivere_fx_objekter();
            indikator_pros.progressProperty().bind(oppdrag.progressProperty());

            oppdrag.setOnSucceeded(this::traadUtført);
            oppdrag.setOnFailed(this::traadAvsluttet);

            new Thread(oppdrag).start();//lage en traad og kjøre sin metode start()
            deakivere_fx_objekter();//deaktivere alle knapper og ikke la tableview kunne redigeres før tråden er ferdig.
        //.........................................................................................

            data_til_Fil.add(temp); //legge til en arraylist som videre vil bruke for å skrive til fil
            lbl_feilmelding.setTextFill(Color.web("#1c9b23"));// gi beskjed med grønn text at man har satt alt info riktiglbl_feilmelding.setText("Du har lagt et nytt element til TabelView!");//......lagre info............

        //reset prosessor etter info er lagret
           txt_type.setText("");
           txt_antallcore.setText("");
           txt_antallthreads.setText("");
           txt_pris.setText("");

        } catch (Unntakk e){
           lbl_feilmelding.setTextFill(Color.web("#d62828")); // vise feilmelding med rød farge
           lbl_feilmelding.setText(e.getMessage());
        }

    }


//......................................................................................................................


//Minne
    @FXML
    private TextField txt_produsenter, txt_kapasitet, txt_hastighet, txt_pris_minne;

    @FXML
    private Label lbl_feilmelding_minne;


    @FXML
    void btn_add_minne(ActionEvent event) {

            Minne obj = new Minne();

            try{

            obj.setProdusenter(txt_produsenter.getText());
            obj.setHastighet (txt_hastighet.getText() );
            obj.setKapasitet (txt_kapasitet.getText());
            obj.setPris(txt_pris_minne.getText());
            Beholder b=new Beholder(obj.getKomponentsnavn(), obj.toString(), obj.getPris());
            collection.addElement(b);
            lbl_feilmelding_minne.setTextFill(Color.web("#1c9b23"));// gi beskjed med grønn text at man har satt alt info riktig
            lbl_feilmelding_minne.setText("Du har lagt et nytt element til TabelView!");
            data_til_Fil.add(b);

            //reset minne etter info er lagret
             txt_produsenter.setText("");
             txt_hastighet.setText("");
             txt_kapasitet.setText("");
             txt_pris_minne.setText("");


            } catch (Unntakk u){
                lbl_feilmelding_minne.setTextFill(Color.web("#d62828")); // vise feilmelding med rød farge
                lbl_feilmelding_minne.setText(u.getMessage());
            }

    }
//.................................tableView.....................................................................................


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


    }
    //filterering
    @FXML
    public void txt_søkfelt(ActionEvent actionEvent) {

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


    // fjerne elementer fra tabellen (du må velge en' rad først)
    @FXML
    void btn_fjern_rad_fraTV(ActionEvent event){
        //hvis brukeren trykker på knappen mens han/hun ikke har valgt en rad/element fra tabellen
        if(tableView.getSelectionModel().isEmpty())
            showErrorDialog("velg en rad først!");

        tableView.setItems(obs);
        tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem() );
    }


    //........redigering på kolonner...........................................
    public void RedigerKnavn(TableColumn.CellEditEvent<Beholder,String> event) {
        event.getRowValue().setKnavn(event.getNewValue());
    }

    public void RedigerSpes(TableColumn.CellEditEvent<Beholder,String> event) {
        event.getRowValue().setSpes(event.getNewValue());
    }

    public void RedigerPris(TableColumn.CellEditEvent<Beholder,Double> event) {
        event.getRowValue().setK_pris(event.getNewValue());
    }
    //........................................................................


    //sortering basert på pris fra lavest priser til høyste
    public void Sortering_pris(ActionEvent actionEvent) {
        tableView.getSortOrder().add(kolonne_pris);
    }


    //når man trykker på knappen lagres alle elementer i tabellen til en fil
    @FXML
    void btn_lagre_til_fil(ActionEvent event) {

        if (collection.liste.size()==0){
            showErrorDialog("TableViewet er tomt!");
            return;
        }

        String filnavn= "kompoenters_data.bin";
        //File fil=new File(filnavn);
        //if(fil.)
        PrintWriter pw = null;
        FileWriter fw= null; //den lar oss skrive tileksisterende fil og unngår å overskrive info som ble lagt fra gør til fil
        try {

            fw= new FileWriter(filnavn, true);//hvis jeg setter parameter til false , kommer den til å override info
            pw = new PrintWriter(fw);

        } catch (FileNotFoundException e) {
            showErrorDialog("Fil ikke funnet!");
            System.exit(1); //drepe kjøringen
        }catch (IOException e) {
            showErrorDialog("Input er ikke riktig!");  //eller "Det som failer er: "+e.getMessage()
            System.exit(1); //drepe kjøringen
        }



        for(int i=0; i< data_til_Fil.size(); i++){ //skrive alle komponenter (som er på tableView) til filen
            //System.out.println(data_til_Fil.get(i).getKnavn());
            pw.println(data_til_Fil.get(i).getKnavn()+"," + data_til_Fil.get(i).getSpes()+","+data_til_Fil.get(i).getK_pris());


        }


        showSuccessDialog("Du har lagret  komponentene riktig til fil!");
        pw.close();
        collection.liste.clear();// nullsitte tableviewet slik at jeg unngå å legge til elementene som jeg har allerede lagt til filen
        tableView.refresh();


    }


}

