package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Controller class represents the export phone registers scene
 */
public class ExportController {
    private final Validator validator = new Validator();
    /*
     * these variables represent the frontend elements
     * */
    @FXML
    private TextField filePathExportField;
    @FXML
    private TextField fileNameExportField;

    /**
     * an action on export button
     */
    @FXML
    void exportPhones() {
        if (validator.checkFileNameAndDir(filePathExportField.getText(),
                fileNameExportField.getText())) {
            validator.closeStage();
            Parser parser = new Parser(filePathExportField.getText() + "\\"
                    + fileNameExportField.getText());
            parser.exportPhones();
            hideMe();
            SceneBuilder.setScene("/sample/view/main_scene.fxml");
        }
    }

    /**
     * an action on browse button
     */
    @FXML
    void choiceDirectoryBtnAction() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) filePathExportField.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        if (file != null) {
            filePathExportField.setText(file.getAbsolutePath());
        }
    }

    /**
     * an action on cancel button
     */
    @FXML
    void cancelExportingAction() {
        validator.closeStage();
        hideMe();
        SceneBuilder.setScene("/sample/view/main_scene.fxml");
    }

    /**
     * hide the current scene
     */
    private void hideMe() {
        filePathExportField.getScene().getWindow().hide();
    }
}
