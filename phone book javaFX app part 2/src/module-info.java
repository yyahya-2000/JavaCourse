module HW.Yahya.Yanal {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires opencsv;
    requires java.sql;
    requires org.apache.derby.tools;

    opens sample;
    opens sample.controller;
    opens sample.model;
}