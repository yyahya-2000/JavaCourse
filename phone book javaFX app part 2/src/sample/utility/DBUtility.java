package sample.utility;

import sample.model.PhoneRegister;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtility {
    private final static String connectionURL = "jdbc:derby:phoneBook;create=true";
    private static final String connectionCloseURL = "jdbc:derby:;shutdown=true";
    private static Connection conn = null;
    private static ResultSet resultSet = null;
    private static final String createString = "CREATE TABLE Contacts"
            + " (contact_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + " last_name VARCHAR(32) NOT NULL, "
            + " first_name VARCHAR(32) NOT NULL, "
            + " patronymic VARCHAR(32), "
            + " home_phone VARCHAR(14), "
            + " mobile_phone VARCHAR(14), "
            + " address VARCHAR(255),"
            + " birthday VARCHAR(14), "
            + " comment VARCHAR(255)) ";

    private static Statement s;

    public static void dbConnect() {
        try {

            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            conn = DriverManager.getConnection(connectionURL);
            s = conn.createStatement();
            if (!doesTableExists("Contacts", conn)) {
                System.out.println(" . . . . creating table Contacts");
                s.execute(createString);
            }

        } catch (SQLException  throwable) {
            throwable.printStackTrace();
        }
    }

    private static boolean doesTableExists(String tableName, Connection conn)
            throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet result = meta.getTables(null, null, tableName.toUpperCase(), null);
        return result.next();
    }

    public static void closeApacheDerbyJDBC() {
        try {
            DriverManager.getConnection(connectionCloseURL);
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("XJ015"))
                System.out.println("Database shut down normally");
            else
                ex.printStackTrace();
        }

    }

    public static void dbDisconnect() {
        try {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (s != null) {
                //Close statement
                s.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("XJ015"))
                System.out.println("Database shut down normally");
            else
                ex.printStackTrace();
        }
    }

    public static void dbExecuteUpdate(String query) {
        try {
            dbConnect();

            //Run executeUpdate operation with given sql statement
            s.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Problem occurred at executeUpdate operation : " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            //Close connection
            dbDisconnect();
        }
    }

    public static int dbExecuteInsert(String query) {
        int generatedKey = -1;
        try {
            dbConnect();

            PreparedStatement ps = conn.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) generatedKey = rs.getInt(1);

        } catch (SQLException ex) {
            System.out.println("Problem occurred at executeUpdate operation : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            //Close connection
            dbDisconnect();
        }
        return generatedKey;
    }


    public static List<PhoneRegister> dbExecuteQuery(String queryStmt) {
        List<PhoneRegister> phoneRegisters = null;
        try {

            dbConnect();

            s = conn.createStatement();

            //Execute select (query) operation
            resultSet = s.executeQuery(queryStmt);
            phoneRegisters = getContactsCollection();

        } catch (SQLException ex) {
            System.out.println("Problem occurred at executeQuery operation : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            //Close connection
            dbDisconnect();
        }
        //Return CachedRowSet
        return phoneRegisters;
    }

    private static List<PhoneRegister> getContactsCollection() {
        List<PhoneRegister> contacts = new ArrayList<>();
        PhoneRegister phoneRegister;
        try {
            while (resultSet.next()) {
                phoneRegister = new PhoneRegister();
                phoneRegister.setId(resultSet.getInt("contact_id"));
                phoneRegister.setLastName(resultSet.getString("last_name"));
                phoneRegister.setFirstName(resultSet.getString("first_name"));
                phoneRegister.setPatronymic(resultSet.getString("patronymic"));
                phoneRegister.setHomePhone(resultSet.getString("home_phone"));
                phoneRegister.setMobilePhone(resultSet.getString("mobile_phone"));
                phoneRegister.setAddress(resultSet.getString("address"));
                phoneRegister.setBirthday(resultSet.getString("birthday"));
                phoneRegister.setComment(resultSet.getString("comment"));
                contacts.add(phoneRegister);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return contacts;
    }

}
