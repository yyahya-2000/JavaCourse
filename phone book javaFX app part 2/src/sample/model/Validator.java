package sample.model;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * a class represents a validator for user input
 */
public final class Validator {
    private final static String DATE_FORMAT = "yyyy-mm-dd";
    private final static Stage OWNER = new Stage(StageStyle.TRANSPARENT);
    private final static List<String> BLOCKED_CHARACTERS =
            Arrays.asList("<", ">", ":", "\"", "/", "\\", "|", "?", "*");

    /**
     * @param text a string
     * @return true if string doesn't empty or contain ";", false otherwise
     */
    private boolean isNotValidString(String text) {
        return !(!text.trim().isEmpty() & !text.contains(";"));
    }

    /**
     * @param number can be any string
     * @return true if the number has this format: +X-XXX-XXX-XXXX, false otherwise
     */
    private boolean isNotValidPhone(String number) {
        String regex = "\\d(-\\d{3}){2}-\\d{4}"; // +X-XXX-XXX-XXXX
        return !number.matches(regex);
    }

    /**
     * @param birthdayDate datePicker
     * @return true if this datePicker has a value can be turned into DateFormat,
     * false otherwise
     */
    private boolean isDateValid(String birthdayDate) {
        //it's not a necessary field
        if (birthdayDate.equals(""))
            return true;
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(birthdayDate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * a method notifies user, if he entered wrong input
     *
     * @param pTitle   notify window title
     * @param pMessage notify window message
     */
     void notifier(String pTitle, String pMessage) {
        Platform.runLater(() -> {
                    StackPane root = new StackPane();
                    root.setStyle("-fx-background-color: TRANSPARENT");
                    Scene scene = new Scene(root, 1, 1);
                    scene.setFill(Color.TRANSPARENT);
                    OWNER.setScene(scene);
                    OWNER.setWidth(1);
                    OWNER.setHeight(1);
                    OWNER.toBack();
                    OWNER.show();
                    Notifications.create().title(pTitle).text(pMessage).showWarning();
                }
        );
    }

    /**
     * check user's input if it meet the conditions
     *
     * @param newPhoneRegister new phone register
     * @return true if the new phone register meet all the conditions, false otherwise
     */
    public boolean checkRegisterDataValidation(PhoneRegister newPhoneRegister) {
        if (isNotValidString(newPhoneRegister.getFirstName())) {
            notifier("Incorrect Firstname Input",
                    "The firstname should not be blank or empty," +
                            " or contain a semicolon(';')!");
            return false;
        }
        if (isNotValidString(newPhoneRegister.getLastName())) {
            notifier("Incorrect Lastname Input",
                    "The lastname should not be blank or empty," +
                            " or contain a semicolon(';')!");
            return false;
        }
        if (!newPhoneRegister.getPatronymic().equals("") && newPhoneRegister.getPatronymic().contains(";")) {
            notifier("Incorrect Patronymic Input",
                    "The Patronymic should not contain a semicolon(';')!");
            return false;
        }
        if (!newPhoneRegister.getMobilePhone().equals("") && isNotValidPhone(newPhoneRegister.getMobilePhone())) {
            notifier("Incorrect Mobile Phone Input",
                    "The correct style is: X-XXX-XXX-XXXX!");
            return false;
        }
        if (!newPhoneRegister.getHomePhone().equals("") && isNotValidPhone(newPhoneRegister.getHomePhone())) {
            notifier("Incorrect Home Phone Input",
                    "The correct style is: X-XXX-XXX-XXXX!");
            return false;
        }
        if (newPhoneRegister.getMobilePhone().equals("") && newPhoneRegister.getHomePhone().equals("")) {
            notifier("Incorrect Phone Input",
                    "At least one phone number(Home, Mobile) must exist!");
            return false;
        }
        if (!newPhoneRegister.getAddress().equals("") && newPhoneRegister.getAddress().contains(";")) {
            notifier("Incorrect Address Input",
                    "The address should not contain a semicolon(';')!");
            return false;
        }
        if (!isDateValid(newPhoneRegister.getBirthday())) {
            notifier("Incorrect Birth Date Input",
                    "Please enter the birth date in any correct format!");
            return false;
        }
        if (!newPhoneRegister.getComment().equals("") && newPhoneRegister.getComment().contains(";")) {
            notifier("Incorrect Comment Input",
                    "The comment should not contain a semicolon(';')!");
            return false;
        }
        return true;
    }

    /**
     * @param dir  directory name
     * @param name file name
     * @return true if the both string not null, empty and doesn't contain blocked characters,
     * false otherwise
     */
    public boolean checkFileNameAndDir(String dir, String name) {
        if (dir == null || dir.trim().isEmpty()) {
            notifier("Incorrect Directory Format",
                    "The Directory shouldn't be empty");
            return false;
        }
        if (name == null || name.trim().isEmpty() || containsBlockedCharacter(name)) {
            notifier("Incorrect Name Format",
                    "The Name shouldn't be empty or contains any of this characters:" +
                            "\n'<', '>', ':', '\"', '/', '\\', '|', '?', '*'");
            return false;
        }
        if (!name.endsWith(".csv")) {
            notifier("Incorrect Name Format",
                    "The only allowed file format is .csv, " +
                            "so please add it to the end of the file name!");
            return false;
        }
        return true;
    }

    /**
     * check if a string contains any of the blocked characters
     *
     * @param st can be any string
     * @return true if the string doesn't contain any of the blocked characters,
     * false otherwise
     */
    private boolean containsBlockedCharacter(String st) {
        boolean found = false;
        for (String item : BLOCKED_CHARACTERS) {
            if (st.contains(item)) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * check the validity of filename
     *
     * @param name any file name
     * @return true if the file name isn't null or empty, false otherwise
     */
    public boolean checkFileName(String name) {
        if (name == null || name.trim().isEmpty()) {
            notifier("Not Choosing File",
                    "Please select a file first!");
            return false;
        }
        return true;
    }

    /**
     * close notify stage
     */
    public void closeStage() {
        OWNER.close();
    }

    /**
     * checks the validity of saving given contact into existing contacts
     *
     * @param newRegister given new contact
     * @return true if the contact have unique full name and mobile number, false otherwise
     */
    public boolean isSaved(PhoneRegister newRegister) {
        Searcher searcher = new Searcher();
        // int id;
        var temp = PhoneRegisterHolder.getInstance().getPhoneRegisters().contains(newRegister);
        if (!temp && searcher.searchByFullName(newRegister.getFirstName() + newRegister.getPatronymic() + newRegister.getLastName())) {
            notifier("Exist Full Name", "This full name (" +
                    newRegister.getFirstName() + " " + newRegister.getPatronymic() +
                    " " + newRegister.getLastName() + ") is already exist!");
            return false;
        }

        String fullName;
        if (!temp && !(fullName = searcher.searchByMobilePhone(newRegister.getMobilePhone())).equals("")) {
            notifier("Exist Mobile Phone", "This mobile phone(" +
                    newRegister.getMobilePhone() + ") is already owned by:\n" + fullName);
            return false;
        }

        if (!temp)
            PhoneRegisterHolder.getInstance().addPhoneRegister(newRegister);
        return true;
    }

    /**
     * checks the validity of saving edited contact into existing contacts
     *
     * @param editedPhoneRegister edited contact
     * @return true if the edited contact have unique full name and mobile number, false otherwise
     */
    public boolean isValidForEdit(PhoneRegister editedPhoneRegister) {
        boolean result = true;
        Searcher searcher = new Searcher();
        Validator validator = new Validator();
        int index = PhoneRegisterHolder.getInstance().getIndexRegisterForEdit();
        PhoneRegister originalPhoneRegister = PhoneRegisterHolder.getInstance().getPhoneRegisters().get(index);
        PhoneRegisterHolder.getInstance().removeRegister(index);
        String fullName;
        if (searcher.searchByFullName(editedPhoneRegister.getFirstName() +
                editedPhoneRegister.getPatronymic() + editedPhoneRegister.getLastName())) {
            validator.notifier("Exist Full Name", "This full name is already exist!");
            validator.closeStage();
            result = false;
        } else if (!(fullName = searcher.searchByMobilePhone(editedPhoneRegister.getMobilePhone())).equals("")) {
            validator.notifier("Exist Mobile Phone", "This mobile phone is already owned by:\n" + fullName);
            validator.closeStage();
            result = false;
        }

        PhoneRegisterHolder.getInstance().addPhoneRegister(index, originalPhoneRegister);
        return result;
    }
}
