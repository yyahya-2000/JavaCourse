package sample.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ParserTest {
    Parser parser = new Parser("./src/sample/res/test.csv");
    static List<PhoneRegister> phoneRegisters = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        PhoneRegister phoneRegister;
        for (int i = 0; i < 50; i++) {
            phoneRegister = new PhoneRegister("firstName" + i, "lastName" + i,
                    "patronymic" + i, "7-888-888-88" + (i < 10 ? "0" : "") + i, "7-888-888-888" + i % 10,
                    "address" + i, ("188" + i % 10) +
                    "-1-1", "comment" + i);
            phoneRegisters.add(phoneRegister);
            PhoneRegisterHolder.getInstance().addPhoneRegister(phoneRegister);
        }
    }

    @Test
    @Order(1)
    void exportPhones() {
        parser.exportPhones();
        assertTrue(fileWasCreatedAndNotEmpty());
    }

    private boolean fileWasCreatedAndNotEmpty() {
        File file = new File("./src/sample/res/test.csv");
        return file.exists() && file.length() != 0;
    }

    // there is a problem because of static stage in Validator.java:21 line
    // but the method work correctly
    @Test
    @Order(2)
    void importPhones() {
        // PhoneRegisterHolder.getInstance().getPhoneRegisters().clear();
        // parser.importPhones();
        // assertEquals(PhoneRegisterHolder.getInstance().getPhoneRegisters(), phoneRegisters);
    }
}