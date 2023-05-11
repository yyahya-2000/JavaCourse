package sample.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

final class Searcher {
    // these boolean variables help to know which search fields
    // we should take it into account, while searching process
    private boolean includeName, includeLastname, includeHomePhone, includeMobilePhone;
    private String firstName, lastName, mobilePhone, homePhone;

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
     * check if the register meets the user request
     *
     * @param register register to check
     * @return true, if the register meets user's request, false otherwise
     */
    private boolean isRequested(PhoneRegister register) {
        return !((includeName && !register.getFirstName().equalsIgnoreCase(firstName)) ||
                (includeLastname && !register.getLastName().equalsIgnoreCase(lastName)) ||
                (includeHomePhone && !register.getHomePhone().equals(homePhone)) ||
                (includeMobilePhone && !register.getMobilePhone().equals(mobilePhone)));
    }

    /**
     * select the search fields, which we need to take it into account, while searching
     */
    private void findIncludedParams() {
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

    /**
     * method helps in search process
     *
     * @param firstName   requested first name
     * @param lastName    requested last name
     * @param mobilePhone requested mobile phone
     * @param homePhone   requested home phone
     * @return list of phone registers having the requested params
     */
    List<PhoneRegister> searchWithParams(String firstName, String lastName,
                                         String mobilePhone, String homePhone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        findIncludedParams();
        return PhoneRegisterHolder.getInstance().
                getPhoneRegisters().stream().filter(this::isRequested).
                collect(Collectors.toList());
    }
}

