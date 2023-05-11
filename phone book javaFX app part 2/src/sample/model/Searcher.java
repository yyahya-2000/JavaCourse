package sample.model;

import java.util.Optional;

/**
 * Class for searching into PhoneRegisterHolder collection
 */
final class Searcher {
    private String mobilePhone;

    /**
     * method help in filtering registers to get the requested registers
     *
     * @param register contact
     * @return true if the contact has mobile number equal to the requested one
     */
    private boolean filterByPhones(PhoneRegister register) {
        return register.getMobilePhone().equals(mobilePhone);
    }


    /**
     * method creates a full name from given contact
     *
     * @param register given contact
     * @return full name of given contact
     */
    private String getFullName(PhoneRegister register) {
        return register.getFirstName() + register.getPatronymic() + register.getLastName();
    }

    /**
     * @param mobilePh requested mobile number
     * @return contact which have the same mobile phone
     */
    String searchByMobilePhone(String mobilePh) {
        mobilePhone = mobilePh;
        Optional<PhoneRegister> foundRegister = PhoneRegisterHolder.getInstance().getPhoneRegisters()
                .stream().filter(this::filterByPhones).findAny();
        return foundRegister.map(phoneRegister ->
                phoneRegister.getFirstName() + " " +
                        phoneRegister.getPatronymic() + " " +
                        phoneRegister.getLastName()).orElse("");
    }

    /**
     * @param fullName requested full name
     * @return contact with the requested full name
     */
    boolean searchByFullName(String fullName) {
        Optional<PhoneRegister> foundRegister = PhoneRegisterHolder.getInstance().
                getPhoneRegisters().stream().
                filter(register -> getFullName(register).equals(fullName)).findAny();
        return foundRegister.isPresent();
    }


}

