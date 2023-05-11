package sample.model;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PhoneRegisterDAOTest {

    private static int idAddedContact;
    @BeforeAll
    static void beforeAll() {
        idAddedContact =  PhoneRegisterDAO.insertContact(new PhoneRegister("test_",
                "test_", "test_", "7-977-777-3333",
                "7-457-777-3333", "Moscow", "2000-1-1", "test_"));

    }


    @Test
    @Order(1)
    void insertContact() {
        assertNotEquals(-1, idAddedContact);
    }


    @Test
    @Order(2)
    void selectContacts() {
        List<PhoneRegister> phoneRegisters = PhoneRegisterDAO.selectContacts();
        assertNotEquals(0, phoneRegisters.size());
    }

    @Test
    @Order(3)
    void searchWithParams() {
        List<PhoneRegister> phoneRegisters = PhoneRegisterDAO.searchWithParams("test_",
                "test_", "", "");
        assertNotEquals(0, phoneRegisters.size());
    }

    @Test
    @Order(4)
    void updateContactId() {
        PhoneRegisterDAO.updateContact(idAddedContact,new PhoneRegister("test_updated",
                "test_updated", "test_updated", "7-977-777-3333",
                "7-457-777-3333", "Moscow_updated", "2000-1-1", "test_updated"));
        List<PhoneRegister> phoneRegisters = PhoneRegisterDAO.searchWithParams("test_updated",
                "test_updated", "7-977-777-3333", "7-457-777-3333");
        assertNotEquals(0, phoneRegisters.size());
    }

    @Test
    @Order(5)
    void deleteContactId() {
        PhoneRegisterDAO.deleteContactId(idAddedContact);
        List<PhoneRegister> phoneRegisters = PhoneRegisterDAO.searchWithParams("test_updated",
                "test_updated", "7-977-777-3333", "7-457-777-3333");
        assertEquals(0, phoneRegisters.size());
    }
}