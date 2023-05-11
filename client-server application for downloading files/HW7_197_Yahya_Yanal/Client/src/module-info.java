module Client {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    opens sample;
    opens sample.controller;
    opens sample.module;
}