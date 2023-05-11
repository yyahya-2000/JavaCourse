package sample.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class SearcherTest {
   Searcher searcher = new Searcher();

   @BeforeAll
   static void setUp() {
       PhoneRegisterHolder.getInstance().getPhoneRegisters().clear();
          PhoneRegister phoneRegister;
       for (int i = 0; i < 50; i++) {
           phoneRegister = new PhoneRegister("firstName" + i, "lastName" + i,
                   "patronymic" + i, "7-888-888-88" + (i < 10 ? "0" : "") + i, "7-888-888-888" + i % 10,
                   "address" + i, ("188" + i % 10) +
                   "-1-1", "comment" + i);
           PhoneRegisterHolder.getInstance().addPhoneRegister(phoneRegister);
       }
   }

   @Test
   void searchByMobilePhone() {

       assertNotEquals("", searcher.searchByMobilePhone("7-888-888-8820"));
       assertEquals("", searcher.searchByMobilePhone("7-888-888-9999"));
   }

   @Test
   void searchByFullName() {
       assertTrue(searcher.searchByFullName("firstName20patronymic20lastName20"));
       assertFalse(searcher.searchByFullName("firstName21patronymic20lastName23"));
   }


}