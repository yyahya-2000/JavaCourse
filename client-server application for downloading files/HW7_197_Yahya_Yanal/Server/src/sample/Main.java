package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.Controller;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("view/main_scene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Folder Share");
        assert root != null;
        primaryStage.setScene(new Scene(root, 538, 480));
        primaryStage.setMinWidth(538);
        primaryStage.setMinHeight(480);
        primaryStage.setMaxHeight(480);
        primaryStage.setMaxWidth(538);
        primaryStage.setOnCloseRequest(e -> Controller.shutDown());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
