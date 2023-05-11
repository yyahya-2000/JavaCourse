package sample.module;

import javafx.scene.control.Alert;

public final class MyAlarm {
    /**
     * alert user when he want to edit, and he didn't select any register first
     */
    public static void notSelectedItemAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Selected Item");
        alert.setHeaderText(null);
        alert.setContentText("Please select an item from the table first!");
        alert.showAndWait();
    }

    public static void wrongAdress() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Unknown Host");
        alert.setHeaderText(null);
        alert.setContentText("Please enter the exact address of the server!");
        alert.showAndWait();
    }

    public static void wrongPort() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wrong Port");
        alert.setHeaderText(null);
        alert.setContentText("Please enter the exact port of the server!");
        alert.showAndWait();
    }
}
