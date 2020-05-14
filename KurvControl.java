package sample;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.util.Scanner;
import static sample.Dialogs.showErrorDialog;
import static sample.Dialogs.showSuccessDialog;



public class KurvControl  implements Initializable {


    DataCollection collection = new DataCollection();

    @FXML
    TableView<Beholder> tableView;

    @FXML
    TableColumn<Beholder, Double> kolonne_pris;

    int antVarer=0;
    double totalPris=0;
    double forsendelse=0;


    //en metode som leser fra csv filen
    void lesefil() {
        Scanner lese = null;
        try {
            lese = new Scanner(new File("Brukers_data.csv"));
        } catch (FileNotFoundException e) {
            showErrorDialog("Fil ikke funnet!");
            System.exit(1); //drepe kjøringen
        }

        while (lese.hasNextLine()) {
            String[] liste = lese.nextLine().split(",");
            for(String s: liste){
                System.out.println(s);
            }
            if (liste.length != 3) { //dvs. at formatet er ikke riktig
                showErrorDialog("Filformat er uriktig! Det er mulig også at det er en tom linje.");
                continue; //dvs. ikke legge komponenten som har feil format (rikitg format er: kompentsnavn; spesifikasjoner;pris)

            }

            try {
                Double pris = Double.parseDouble(liste[2]);//konvertering er feil.
            } catch (Exception io) {
                showErrorDialog(" Pris er ugyldig!");
                continue; //dvs. ikke legg komponenten som har feil format (rikitg format er: kompentsnavn; spesifikasjoner;pris)
                //vi kunne gjerne bruke System.exit(1);
                //men vi trenger ikke å drepe hele programmet hvis en linje en ikke riktig

            }
            Beholder b = new Beholder(liste[0], liste[1], Double.parseDouble(liste[2]));
            totalPris+=Double.parseDouble(liste[2]); //legge nåværende kompoenentspris til totalpris
            antVarer+=1; //telle antal kompoenenter i filen
            forsendelse+=30.45;


            collection.addElement(b);
        }
        totalPris+=forsendelse;
    }

    @FXML
    private TextField txt_antVarer, txt_forsendelse, txt_totaltPris;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collection.attachTableView(tableView);
        kolonne_pris.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        lesefil();
        txt_antVarer.setText(""+antVarer);
        txt_forsendelse.setText(""+forsendelse);
        txt_totaltPris.setText(""+totalPris);

    }




    //gå bak til bruker sin side
    public void btn_bak(ActionEvent actionEvent) throws IOException {

        Parent bruker_side = FXMLLoader.load(getClass().getResource("bruker.fxml"));
        Scene adminScene = new Scene(bruker_side);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.fullScreenProperty();
        window.show();
    }


    //knapeen bare for utseende
    public void btn_gåTilKasse(ActionEvent actionEvent) {
        showSuccessDialog("Det er bare demo-knapp.");
    }
}
