package sample.controller;

import java.io.File;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * Controller class represents the import phone registers scene
 */
public class ImportController {
    private final Validator validator = new Validator();

    @FXML
    private TextField filePathImportField;

    /**
     * an action on browse button
     */
    @FXML
    void browseImportBtnAction() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter;
        extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(filePathImportField.getScene().getWindow());
        if (file != null)
            filePathImportField.setText(file.getAbsolutePath());

    }

    /**
     * an action on cancel button
     */
    @FXML
    void cancelBtnAction() {
        validator.closeStage();
        hideMe();
        SceneBuilder.setScene("/sample/view/main_scene.fxml");
    }

    /**
     * an action on import button
     */
    @FXML
    void importBtnAction() {
        if (validator.checkFileName(filePathImportField.getText())) {
            Parser parser = new Parser(filePathImportField.getText());
            parser.importPhones();
            validator.closeStage();
            hideMe();
            SceneBuilder.setScene("/sample/view/main_scene.fxml");

        }
    }

    /**
     * hide the current scene
     */
    void hideMe() {
        filePathImportField.getScene().getWindow().hide();
    }
}
