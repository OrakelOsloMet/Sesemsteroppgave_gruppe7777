package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LoggInnController {

    @FXML
    private CheckBox adminCheckBox;

    @FXML
    private CheckBox brukerCheckBox;

    @FXML
    private TextField txtfield_brukernavn;

    @FXML
    private TextField txtfield_passord;
    @FXML
    private Label label_feilmelding;
    @FXML
    private Button logInn_ID;
    @FXML
    LoggInnKlasse loggInn_obj;
    @FXML
    void logInn_btn(ActionEvent event) throws IOException {

        loggInn_obj=new LoggInnKlasse ();

        if (adminCheckBox.isSelected()){
            try {
                loggInn_obj.valideringAdminEpost(txtfield_brukernavn.getText());
                loggInn_obj.valideringPassord(txtfield_passord.getText());

                Parent admin_side = FXMLLoader.load(getClass().getResource("sample.fxml"));
                Scene adminScene = new Scene(admin_side);

                //This line gets the Stage information
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                window.setScene(adminScene);
                window.fullScreenProperty();
                window.show();
            } catch (Unntakk u){
                label_feilmelding.setText( u.getMessage());
            }
        }

        if (brukerCheckBox.isSelected()){
                try {

                    Parent bruker_side = FXMLLoader.load(getClass().getResource("bruker.fxml"));
                    Scene adminScene = new Scene(bruker_side);

                    //This line gets the Stage information
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                    window.setScene(adminScene);
                    window.fullScreenProperty();
                    window.show();
                    loggInn_obj.valideringBrukerEpost(txtfield_brukernavn.getText());
                    loggInn_obj.valideringPassord(txtfield_passord.getText());

                    Parent admin_side = FXMLLoader.load(getClass().getResource("sample.fxml"));
                    Scene brukerScene = new Scene(admin_side);

                    //This line gets the Stage information
                    Stage window_bruker = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setTitle("bruker side");
                    window.setScene(adminScene);
                    window_bruker.show();
                } catch (Unntakk u){
                    label_feilmelding.setText( u.getMessage());
                }

        }

    }
    public void adminCheckBox_onAction(ActionEvent event) {


        if (adminCheckBox.isSelected()){

            txtfield_brukernavn.setPromptText("example123@admin.oslomet.no");
            txtfield_passord.setPromptText("eks. @dmiN777");

            brukerCheckBox.setDisable(true); //deaktivere kunde checkBox fordi det ikke er tillat å velge begge checkBox-er
            logInn_ID.setDisable(false);
            txtfield_passord.setDisable(false);
            txtfield_brukernavn.setDisable(false);


        }

        //hvis adminCheckBox ble igjen deaktviert da programmet sette alt til difult.
        if (!adminCheckBox.isSelected()){
            txtfield_brukernavn.setPromptText("");
            txtfield_passord.setPromptText("");
            logInn_ID.setDisable(true);
            txtfield_passord.setDisable(true);
            txtfield_brukernavn.setDisable(true);
            brukerCheckBox.setDisable(false); //deaktivere brukercheckbox slik at man kan velge mellom bruker og admin
        }

    }

    //hvis  brukerCheckBox ble igjen deaktviert da programmet sette alt til difult.
    public void brukerCheckBox_onAction(ActionEvent event) {


        if (brukerCheckBox.isSelected()){

            txtfield_brukernavn.setPromptText("example123@bruker.oslomet.no");
            txtfield_passord.setPromptText("eks. Bruker1&3");

            adminCheckBox.setDisable(true); //deaktivere kunde checkBox fordi det ikke er tillat å velge begge checkBox-er
            logInn_ID.setDisable(false);
            txtfield_passord.setDisable(false);
            txtfield_brukernavn.setDisable(false);


        }

        //hvis adminCheckBox ble igjen deaktviert da programmet sette alt til difult. Dvs. tomme prompt text ..
        if (!brukerCheckBox.isSelected()){
            txtfield_brukernavn.setPromptText("");
            txtfield_passord.setPromptText("");

            logInn_ID.setDisable(true);
            txtfield_passord.setDisable(true);
            txtfield_brukernavn.setDisable(true);
            adminCheckBox.setDisable(false); //deaktivere brukercheckbox slik at man kan velge mellom.
        }

    }


    //txtfield_passord.setPromptText("example123@bruker.oslomet.no");

}