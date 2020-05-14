package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("bruker.fxml"));
        primaryStage.setTitle("SemesterOppgave");
        primaryStage.setScene(new Scene(root,593 , 450)); //593 x ...
        primaryStage.fullScreenProperty();//vise på hele skjermen
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
