package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Controller class represents the edit phone register scene
 */
public class EditPhoneController {
    private final Validator validator = new Validator();
    /*
     * these variables represent the frontend elements
     * */
    @FXML
    private TextField firstNameEditField;

    @FXML
    private TextField secondNameEditField;

    @FXML
    private TextField patronymicEditField;

    @FXML
    private TextField mobilePhoneEditField;

    @FXML
    private TextField homePhoneEditField;

    @FXML
    private TextField addressEditField;

    @FXML
    private DatePicker birthdayEditField;

    @FXML
    private TextField commentEditField;

    /**
     * an action on save edit button
     */
    @FXML
    void saveEditBtnAction() {
        var editedPhoneRegister = createPhoneRegister();
        if (validator.checkRegisterDataValidation(editedPhoneRegister)) {
            if (validator.isValidForEdit(editedPhoneRegister)) {
                PhoneRegisterHolder.getInstance().editRegister(editedPhoneRegister);
                validator.closeStage();
                hideMe();
                SceneBuilder.setScene("/sample/view/main_scene.fxml");

            }
        }
    }

    /**
     * an action on cancel button
     */
    @FXML
    void cancelEditBtnAction() {
        hideMe();
        SceneBuilder.setScene("/sample/view/main_scene.fxml");
        validator.closeStage();
    }

    @FXML
    void initialize() {
        fillFields();
    }

    /**
     * hide the current scene
     */
    private void hideMe() {
        firstNameEditField.getScene().getWindow().hide();
    }

    /**
     * set phone register data, which needs to be modified into the fields of the current scene
     */
    private void fillFields() {
        firstNameEditField.setText(PhoneRegisterHolder.getInstance().
                getPhoneRegisterForEdit().getFirstName());
        secondNameEditField.setText(PhoneRegisterHolder.getInstance().
                getPhoneRegisterForEdit().getLastName());
        patronymicEditField.setText(PhoneRegisterHolder.getInstance().
                getPhoneRegisterForEdit().getPatronymic());
        mobilePhoneEditField.setText(PhoneRegisterHolder.getInstance().
                getPhoneRegisterForEdit().getMobilePhone());
        homePhoneEditField.setText(PhoneRegisterHolder.getInstance().
                getPhoneRegisterForEdit().getHomePhone());
        addressEditField.setText(PhoneRegisterHolder.getInstance().
                getPhoneRegisterForEdit().getAddress());
        try {
            birthdayEditField.setValue(LocalDate.parse(PhoneRegisterHolder.getInstance().
                    getPhoneRegisterForEdit().getBirthday()));
        } catch (DateTimeParseException ignored) {
        }

        commentEditField.setText(PhoneRegisterHolder.getInstance().
                getPhoneRegisterForEdit().getComment());
    }

    /**
     * get the user's edit and turn it into new phone register object
     *
     * @return phone register object edited by user
     */
    private PhoneRegister createPhoneRegister() {
        PhoneRegister phoneRegister = new PhoneRegister();
        phoneRegister.setFirstName(firstNameEditField.getText().trim());
        phoneRegister.setLastName(secondNameEditField.getText().trim());
        phoneRegister.setPatronymic(patronymicEditField.getText() == null ? "" :
                patronymicEditField.getText().trim());
        phoneRegister.setMobilePhone(mobilePhoneEditField.getText() == null ? "" :
                mobilePhoneEditField.getText());
        phoneRegister.setHomePhone(homePhoneEditField.getText() == null ? "" :
                homePhoneEditField.getText());
        phoneRegister.setAddress(addressEditField.getText() == null ? "" :
                addressEditField.getText().trim());
        phoneRegister.setBirthday(birthdayEditField.getValue() == null ? "" :
                birthdayEditField.getValue().toString());
        phoneRegister.setComment(commentEditField.getText() == null ? "" :
                commentEditField.getText().trim());
        return phoneRegister;
    }
}
