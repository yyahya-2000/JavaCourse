package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/main_scene.fxml"));
        primaryStage.setTitle("File Downloader");
        primaryStage.setScene(new Scene(root, 638, 422));
        primaryStage.setMinWidth(638);
        primaryStage.setMinHeight(422);
        primaryStage.setMaxHeight(422);
        primaryStage.setMaxWidth(638);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
