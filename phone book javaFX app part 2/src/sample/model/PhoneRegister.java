package sample.model;

import java.util.Objects;

/**
 * class represent a phone register object
 */
public final class PhoneRegister {


    /* object fields */
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String mobilePhone;
    private String homePhone;
    private String address;
    private String birthday;
    private String comment;

    public PhoneRegister(String firstName, String lastName, String patronymic,
                         String mobilePhone, String homePhone, String address,
                         String birthday, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.address = address;
        this.birthday = birthday;
        this.comment = comment;
    }

    public PhoneRegister() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /* list of getters and setters of the object variables */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * toString method
     *
     * @return string helps in turning phone register
     * object into a valid string for exporting to csv file
     */
    @Override
    public String toString() {
        return lastName + ';' + firstName + ';' + patronymic + ';' +
                mobilePhone + ';' + homePhone + ';' + address + ';' +
                birthday + ";" + comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneRegister)) return false;
        PhoneRegister that = (PhoneRegister) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                Objects.equals(getPatronymic(), that.getPatronymic()) &&
                Objects.equals(getMobilePhone(), that.getMobilePhone()) &&
                Objects.equals(getHomePhone(), that.getHomePhone()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getBirthday(), that.getBirthday()) &&
                Objects.equals(getComment(), that.getComment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(),
                getPatronymic(), getMobilePhone(), getHomePhone(),
                getAddress(), getBirthday(), getComment());
    }
}
