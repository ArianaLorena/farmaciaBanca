package Banca;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
 
public class Client extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Client!");
        primaryStage.setScene(new Scene(root, 334, 424));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}