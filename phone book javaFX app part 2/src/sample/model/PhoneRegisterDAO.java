package sample.model;

import sample.utility.DBUtility;

import java.util.List;

/**************************
 * DAO(Data Access Object) CLASS
 * ************************
 */
public class PhoneRegisterDAO {

    /**
     * method deletes contact from db by id
     *
     * @param contactId contact's id, which should be deleted
     */
    public static void deleteContactId(int contactId) {
        //Declare a DELETE statement
        String deleteQuery = "DELETE FROM Contacts WHERE contact_id = " + contactId + "";
        DBUtility.dbExecuteUpdate(deleteQuery);
    }

    /**
     * method inserts contact into db
     *
     * @param phoneRegister new contact
     * @return id added contact
     */
    public static int insertContact(PhoneRegister phoneRegister) {
        String query = "INSERT INTO Contacts(last_name, first_name, "
                + "patronymic, home_phone, mobile_phone, address, birthday, comment) VALUES('" +
                phoneRegister.getLastName() + "', '" + phoneRegister.getFirstName() + "', '" +
                phoneRegister.getPatronymic() + "', '" + phoneRegister.getHomePhone() + "', '" +
                phoneRegister.getMobilePhone() + "', '" + phoneRegister.getAddress() + "', '" +
                phoneRegister.getBirthday() + "', '" + phoneRegister.getComment() + "')";
        return DBUtility.dbExecuteInsert(query);
    }


    /**
     * method updates contact by id
     *
     * @param contactId     contact's id, which should be deleted
     * @param phoneRegister the new values of the contact
     */
    public static void updateContact(int contactId, PhoneRegister phoneRegister) {
        //Declare a UPDATE statement
        String updateQuery = "UPDATE Contacts SET last_name = '" + phoneRegister.getLastName() + "', " +
                "first_name = '" + phoneRegister.getFirstName() + "', " +
                "patronymic = '" + phoneRegister.getPatronymic() + "', " +
                "home_phone = '" + phoneRegister.getHomePhone() + "', " +
                "mobile_phone = '" + phoneRegister.getMobilePhone() + "', " +
                "address = '" + phoneRegister.getAddress() + "', " +
                "birthday = '" + phoneRegister.getBirthday() + "', " +
                "comment = '" + phoneRegister.getComment() + "' WHERE contact_id = " + contactId + "";

        //Execute UPDATE operation
        DBUtility.dbExecuteUpdate(updateQuery);
    }

    /**
     * method for getting all  contacts from db
     *
     * @return list of contacts
     */
    public static List<PhoneRegister> selectContacts() {
        String selectQuery = "select * from Contacts";
        return DBUtility.dbExecuteQuery(selectQuery);
    }


    //**************************************************************************
// SEARCH
//**************************************************************************

    // these boolean variables help to know which search fields
    // we should take it into account, while searching process
    private static boolean includeName, includeLastname, includeHomePhone, includeMobilePhone;
    private static String firstName, lastName, mobilePhone, homePhone;


    /**
     * select the search fields, which we need to take it into account, while searching
     */
    private static void findIncludedParams() {
        includeName = firstName != null &&
                !firstName.trim().isEmpty();
        includeLastname = lastName != null &&
                !lastName.trim().isEmpty();
        includeHomePhone = homePhone != null &&
                !homePhone.trim().isEmpty();
        includeMobilePhone = mobilePhone != null &&
                !mobilePhone.trim().isEmpty();
    }

    /**
     * method for searching into the DB by many params
     *
     * @param firstName   requested first name
     * @param lastName    requested last name
     * @param mobilePhone requested mobile phone
     * @param homePhone   requested home phone
     * @return list of phone registers having the requested params
     */
    public static List<PhoneRegister> searchWithParams(String firstName, String lastName,
                                                       String mobilePhone, String homePhone) {
        PhoneRegisterDAO.firstName = firstName;
        PhoneRegisterDAO.lastName = lastName;
        PhoneRegisterDAO.mobilePhone = mobilePhone;
        PhoneRegisterDAO.homePhone = homePhone;

        findIncludedParams();
        String searchQuery = "SELECT * FROM Contacts WHERE NOT " +
                "((" + includeName + " AND LOWER(first_name) NOT LIKE '" + PhoneRegisterDAO.firstName.toLowerCase() + "%') OR " +
                "(" + includeLastname + " AND LOWER(last_name) NOT LIKE '" + PhoneRegisterDAO.lastName.toLowerCase() + "%') OR " +
                "(" + includeHomePhone + " AND home_phone NOT LIKE '" + PhoneRegisterDAO.homePhone + "%') OR " +
                "(" + includeMobilePhone + " AND mobile_phone NOT LIKE '" + PhoneRegisterDAO.mobilePhone + "%'))";
        return DBUtility.dbExecuteQuery(searchQuery);
    }

}
