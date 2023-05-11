package sample.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import sample.model.*;
import sample.utility.DBUtility;

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
    private TableColumn<?, ?> iDCol;


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
        //import data from db
        PhoneRegisterHolder.getInstance().addAllPhoneRegisters(PhoneRegisterDAO.selectContacts());
    }

    /**
     * this method will work when user want to close the program,
     * so the existing data will be exported into data.csv file
     */
    public static void shutDown() {
        // new Parser("./src/sample/res/data.csv").exportPhones();
        DBUtility.closeApacheDerbyJDBC();
    }

    @FXML
    void initialize() {
        initializeColumns();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(FXCollections.observableArrayList(PhoneRegisterHolder.
                getInstance().getPhoneRegisters()));
        tableView.refresh();
        initializeSearchingProcess();
    }

    private void initializeSearchingProcess() {
        ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
            tableView.setItems(FXCollections.observableArrayList(
                    //set the data got from DB into the table
                    PhoneRegisterDAO.searchWithParams(searchFirstnameField.getText(),
                            searchLastnameField.getText(), searchMobilePhoneField.getText(),
                            SearchHomePhoneField.getText())));
            tableView.refresh();
        });
        searchMobilePhoneField.textProperty().addListener(listener);
        searchFirstnameField.textProperty().addListener(listener);
        searchLastnameField.textProperty().addListener(listener);
        SearchHomePhoneField.textProperty().addListener(listener);
    }

    /**
     * initializing table columns
     */
    private void initializeColumns() {
        iDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
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
            PhoneRegister selectedPhone = tableView.getSelectionModel().getSelectedItem();
            PhoneRegisterHolder.getInstance().removeRegister(selectedPhone);
            tableView.setItems(FXCollections.observableArrayList(
                    PhoneRegisterHolder.getInstance().getPhoneRegisters()));

            PhoneRegisterDAO.deleteContactId(selectedPhone.getId());
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
     * hide current scene
     */
    private void hideMe() {
        searchFirstnameField.getScene().getWindow().hide();
    }

}
