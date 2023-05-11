package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class represent a unique method for building scenes
 */
final class SceneBuilder {

    /**
     * creat new stage with new scene
     *
     * @param path path of scene file
     */
    static void setScene(String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneBuilder.class.getResource(path));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = getStageWithSuitableSize(path);

        if (path.endsWith("main_scene.fxml"))
            stage.setOnCloseRequest(e -> Controller.shutDown());
        else
            stage.setOnCloseRequest(e -> setScene("/sample/view/main_scene.fxml"));

        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * sets the min size of each window
     */
    private static Stage getStageWithSuitableSize(String path) {
        Stage stage = new Stage();
        if (path.endsWith("main_scene.fxml")) {
            stage.setMinHeight(510);
            stage.setMinWidth(750);
        } else if (path.endsWith("export_scene.fxml")) {
            stage.setMinHeight(355);
            stage.setMinWidth(371);
        } else if (path.endsWith("import_scene.fxml")) {
            stage.setMinHeight(300);
            stage.setMinWidth(372);
        } else if (path.endsWith("add_phone_scene.fxml")) {
            stage.setMinHeight(612);
            stage.setMinWidth(356);
        } else if (path.endsWith("edit_phone_scene.fxml")) {
            stage.setMinHeight(600);
            stage.setMinWidth(512);
        }
        return stage;
    }
}
