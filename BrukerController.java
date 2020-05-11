package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import static sample.Dialogs.showErrorDialog;


public class BrukerController  implements Initializable {


    DataCollection collection = new DataCollection();

    @FXML
    TableView <Beholder> tableView;

    @FXML
    TableColumn<Beholder, Double> kolonne_pris;


    @Override
    public void initialize (URL location, ResourceBundle resources) {
        collection.attachTableView(tableView);
        kolonne_pris.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter()));

    }


    Boolean ertrykket=false; //får vite om knappen er trykket to ganger
    @FXML
    void lasteDataFraFil(ActionEvent event)  {
        //for å unngå henter samme data to ganger hvis man trykket to ganger på knappen
        if(ertrykket==true){

            return;
        }
        ertrykket=true;
        Scanner lese = null;
        try {
            lese = new Scanner(new File("kompoenters_data.bin"));
        }catch (FileNotFoundException e) {
                showErrorDialog("Fil ikke funnet!");
                System.exit(1); //drepe kjøringen
         }

        //lese fra binære filen
        while(lese.hasNextLine()){
        String [] liste=lese.nextLine().split(";");

            if(liste.length!=3 ){ //dvs. at formatet er ikke riktig
                       showErrorDialog("Filformat er ikke riktig!");
                       continue; //dvs. ikke legge komponenten som har feil format (rikitg format er: kompentsnavn; spesifikasjoner;pris)
                       //jeg kunne gjerne bruke System.exit(1);
                       //men vi trenger ikke å drepe hele programmet hvis en linje en ikke riktig

            }

            try{
                Double pris= Double.parseDouble(liste[2]);//konvertering er feil.
             }catch (Exception io){
                showErrorDialog(" Pris er ugyldig!");
                continue; //dvs. ikke legge komponenten som har feil format (rikitg format er: kompentsnavn; spesifikasjoner;pris)
                //jeg kunne gjerne bruke System.exit(1);
                //men vi trenger ikke å drepe hele programmet hvis en linje en ikke riktig

            }
            Beholder b= new Beholder(liste[0], liste[1], Double.parseDouble(liste[2]));
            collection.addElement(b);


    }
        //disable buttom


    }





    void btn_fjern_rad_fraTV(ActionEvent event){
        /*
        ObservableList<Beholder> valgte_k, alle_k;
        alle_k = tableView.getItems();

        //this gives us the rows that were selected
        valgte_k = tableView.getSelectionModel().getSelectedItems();

        //loop over the selected rows and remove the Person objects from the table
        for (Beholder element : valgte_k)
        {
            alle_k.remove(element);
        }


        final Menu menu1 = new Menu("File");
        final Menu menu2 = new Menu("Options");
        final Menu menu3 = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2, menu3);
         */}

    public void LeggeTilKurve(ActionEvent actionEvent) {

        String filnavn= "Brukers_data.csv";
        PrintWriter pw = null;
        FileWriter fw= null; //den lar oss skrive tileksisterende fil og unngår å overskrive info som ble lagt fra gør til fil
        try {

            fw = new FileWriter(filnavn, true);//hvis jeg setter parameter til false , kommer den til å override info
            pw = new PrintWriter(fw);

        }catch (IOException e) {
            showErrorDialog("Input er ikke riktig!");  //eller "Det som failer er: "+e.getMessage()
            System.exit(1); //drepe kjøringen
        }
        //NullPointerException hvis bruker trykker på knappen uten å velge minst et element
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Beholder b = tableView.getSelectionModel().getSelectedItem();
        System.out.println(b.getK_pris());

    }
}

