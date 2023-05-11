package sample.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class represents the dashboard scene
 */
public class Controller {
    /*
     * these variables represent the frontend elements
     * */
    @FXML
    private TableView<PhoneRegister> tableView;

    @FXML
    private TableColumn<?, ?> lastNameCol;

    @FXML
    private TableColumn<?, ?> firstNameCol;

    @FXML
    private TableColumn<?, ?> patronymicCol;

    @FXML
    private TableColumn<?, ?> homePhoneCol;

    @FXML
    private TableColumn<?, ?> mobilePhoneCol;

    @FXML
    private TableColumn<?, ?> addressCol;

    @FXML
    private TableColumn<?, ?> birthdayCol;

    @FXML
    private TableColumn<?, ?> CommentCol;

    @FXML
    private TextField searchLastnameField;

    @FXML
    private TextField searchFirstnameField;

    @FXML
    private TextField SearchHomePhoneField;
    @FXML
    private TextField searchMobilePhoneField;

    /**
     * parsing the last version of data from local csv file data.csv
     * into PhoneRegisterHolder.phoneRegisters
     */
    public static void setLastVersionData() {
        Parser parser = new Parser("./src/sample/res/data.csv");
        parser.importPhones();
    }

    /**
     * this method will work when user want to close the program,
     * so the existing data will be exported into data.csv file
     */
    public static void shutDown() {
        new Parser("./src/sample/res/data.csv").exportPhones();
    }

    @FXML
    void initialize() {
        initializeColumns();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(FXCollections.observableArrayList(PhoneRegisterHolder.
                getInstance().getPhoneRegisters()));
        tableView.refresh();
    }

    /**
     * initializing table columns
     */
    private void initializeColumns() {
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patronymicCol.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        mobilePhoneCol.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));
        homePhoneCol.setCellValueFactory(new PropertyValueFactory<>("homePhone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        CommentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
    }

    /**
     * an action on exit menu item
     */
    @FXML
    void exit() {
        if (MyAlarm.exitAlert()) {
            Controller.shutDown();
            Platform.exit();
            System.exit(0);
        }
    }

    /**
     * an action on add phone register menu item
     */
    @FXML
    void addPhoneRegister() {
        hideMe();
        SceneBuilder.setScene("/sample/view/add_phone_scene.fxml");
    }

    /**
     * an action on edit phone register menu item
     */
    @FXML
    void editPhoneRegister() {
        if (tableView.getSelectionModel().getSelectedIndex() > -1) {
            PhoneRegisterHolder.getInstance().setIndexRegisterForEdit(
                    PhoneRegisterHolder.getInstance().getPhoneRegisters().
                            indexOf(tableView.getSelectionModel().getSelectedItem()));
            hideMe();
            SceneBuilder.setScene("/sample/view/edit_phone_scene.fxml");
        } else {
            MyAlarm.notSelectedItemAlert();
        }

    }

    /**
     * an action on delete phone register menu item
     */
    @FXML
    void deleteRegisterAction() {
        if (tableView.getSelectionModel().getSelectedIndex() > -1) {
            PhoneRegisterHolder.getInstance().removeRegister(
                    tableView.getSelectionModel().getSelectedItem());
            tableView.setItems(FXCollections.observableArrayList(
                    PhoneRegisterHolder.getInstance().getPhoneRegisters()));
        } else {
            MyAlarm.notSelectedItemAlert();
        }
    }

    /**
     * an action on export phone registers menu item
     */
    @FXML
    void exportPhones() {
        hideMe();
        SceneBuilder.setScene("/sample/view/export_scene.fxml");
    }

    /**
     * an action on import phone register menu item
     */
    @FXML
    void importPhones() {
        hideMe();
        SceneBuilder.setScene("/sample/view/import_scene.fxml");
    }

    /**
     * an action on about menu item
     */
    @FXML
    void showAboutWindow() {
        MyAlarm.AboutAlert();
    }

    /**
     * an action on search button
     */
    @FXML
    void searchBtnAction() {
        tableView.setItems(FXCollections.observableArrayList(
                (new Searcher()).searchWithParams(searchFirstnameField.getText(),
                        searchLastnameField.getText(), searchMobilePhoneField.getText(),
                        SearchHomePhoneField.getText())));
        tableView.refresh();
    }

    /**
     * hide current scene
     */
    private void hideMe() {
        searchFirstnameField.getScene().getWindow().hide();
    }

}
