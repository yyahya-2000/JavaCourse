package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.Controller;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller.setLastVersionData();
        Parent root = FXMLLoader.load(getClass().getResource("view/main_scene.fxml"));
        primaryStage.setMinHeight(498);
        primaryStage.setMinWidth(750);
        primaryStage.setTitle("Phone Book");
        primaryStage.setOnCloseRequest(e -> Controller.shutDown());
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
