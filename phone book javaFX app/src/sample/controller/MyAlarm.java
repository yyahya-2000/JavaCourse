package sample.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

final class MyAlarm {
    /**
     * @return true is user confirmed exiting process, false otherwise
     */
    static boolean exitAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit Confirmation");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * alert user when he want to edit, and he didn't select any register first
     */
    static void notSelectedItemAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Selected Item");
        alert.setHeaderText(null);
        alert.setContentText("Please select an item from the table first!");
        alert.showAndWait();
    }

    /**
     * program Info
     */
    static void AboutAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Program Author and Purpose");
        alert.setContentText("\nYanal Yahya, Software Engineer, group 197.\n\n" +
                "This desktop application was created like a phone book to save the contacts" +
                "(it could be a large amount of contacts)");
        alert.showAndWait();
    }
}
