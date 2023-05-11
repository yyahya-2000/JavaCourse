package sample.controller;

import java.io.*;

import com.opencsv.CSVReader;

/**
 * class represents a parser from an to csv file
 */
final class Parser {
    /**
     * file path
     */
    private final String path;

    //constructor
    public Parser(String path) {
        this.path = path;
    }

    /**
     * method imports registers from PhoneRegisterHolder.phoneRegisters into file(path)
     */
    void importPhones() {
        CSVReader csvReader = null;
        Validator validator = new Validator();
        //fill the List of PhoneRegisterHolder object
        try {
            csvReader = new CSVReader(new FileReader(path), ';', '"', 1);
            //phoneRegister stores the values current line
            String[] phoneRegister;
            PhoneRegister register;
            while ((phoneRegister = csvReader.readNext()) != null) {
                register = new PhoneRegister(phoneRegister[1], phoneRegister[0],
                        phoneRegister[2], phoneRegister[3], phoneRegister[4], phoneRegister[5],
                        phoneRegister[6], phoneRegister[7]);
                if (validator.checkRegisterDataValidation(register))
                    validator.isSaved(register);
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try {
                assert csvReader != null;
                csvReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        validator.closeStage();
    }

    /**
     * method exports registers into PhoneRegisterHolder.phoneRegisters
     */
    void exportPhones() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(path));

            if (PhoneRegisterHolder.getInstance().getPhoneRegisters().size() > 0)
                pw.println("lastName;firstName;patronymic;mobilePhone;" +
                        "homePhone;address;birthday;comment;");

            for (var register : PhoneRegisterHolder.getInstance().getPhoneRegisters())
                pw.println(register);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert pw != null;
            pw.close();
        }
    }
}
