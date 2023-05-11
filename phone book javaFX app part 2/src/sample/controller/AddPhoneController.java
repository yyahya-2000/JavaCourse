package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.model.PhoneRegister;
import sample.model.PhoneRegisterDAO;
import sample.model.Validator;

/**
 * Controller class represents the add phone register scene
 */
public class AddPhoneController {

    private final Validator validator = new Validator();
    /*
     * these variables represent the frontend elements
     * */
    @FXML
    private TextField firstNameAddField;

    @FXML
    private TextField secondNameAddField;

    @FXML
    private TextField patronymicAddField;

    @FXML
    private TextField mobilePhoneAddField;

    @FXML
    private TextField homePhoneAddField;

    @FXML
    private TextField addressAddField;

    @FXML
    private DatePicker birthdayAddDate;

    @FXML
    private TextField commentAddField;

    /**
     * an action of save button
     */
    @FXML
    void saveAddBtnAction() {
        var newRegister = createPhoneRegister();
        if (validator.checkRegisterDataValidation(newRegister)) {
            if (validator.isSaved(newRegister)) {
               int id= PhoneRegisterDAO.insertContact(newRegister);
                newRegister.setId(id);
                hideMe();
                SceneBuilder.setScene("/sample/view/main_scene.fxml");
            }
        }
        validator.closeStage();
    }

    /**
     * an action of cancel button
     */
    @FXML
    void cancelAddBtnAction() {
        hideMe();
        SceneBuilder.setScene("/sample/view/main_scene.fxml");
        validator.closeStage();
    }

    /**
     * get the user's input and turn it into phone register object
     *
     * @return phone register object created by user
     */
    private PhoneRegister createPhoneRegister() {
        PhoneRegister phoneRegister = new PhoneRegister();
        phoneRegister.setFirstName(firstNameAddField.getText().trim());
        phoneRegister.setLastName(secondNameAddField.getText().trim());
        phoneRegister.setPatronymic(patronymicAddField.getText() == null ? "" :
                patronymicAddField.getText().trim());
        phoneRegister.setMobilePhone(mobilePhoneAddField.getText() == null ? "" :
                mobilePhoneAddField.getText());
        phoneRegister.setHomePhone(homePhoneAddField.getText() == null ? "" :
                homePhoneAddField.getText());
        phoneRegister.setAddress(addressAddField.getText() == null ? "" :
                addressAddField.getText().trim());
        phoneRegister.setBirthday(birthdayAddDate.getValue() == null ? "" :
                birthdayAddDate.getValue().toString());
        phoneRegister.setComment(commentAddField.getText() == null ? "" :
                commentAddField.getText().trim());
        return phoneRegister;
    }

    /**
     * hide current stage
     */
    private void hideMe() {
        firstNameAddField.getScene().getWindow().hide();
    }
}
