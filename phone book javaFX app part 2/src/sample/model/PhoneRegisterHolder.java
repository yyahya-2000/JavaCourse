package sample.model;

import java.util.ArrayList;
import java.util.List;

/**
 * a singleton class for sharing data between controllers
 */
public final class PhoneRegisterHolder {

    private final List<PhoneRegister> phoneRegisters = new ArrayList<>();
    private int indexRegisterForEdit;
    /**
     * private final static instance of this class, was created by Unique private constructor
     */
    private final static PhoneRegisterHolder INSTANCE = new PhoneRegisterHolder();

    /**
     * private constructor
     */
    private PhoneRegisterHolder() {
    }

    /**
     * @return the already created static instance
     */
    public static PhoneRegisterHolder getInstance() {
        return INSTANCE;
    }

    /**
     * @return all existing phone registers
     */
    public List<PhoneRegister> getPhoneRegisters() {
        return phoneRegisters;
    }

    /**
     * @return phone register for editing
     */
    public PhoneRegister getPhoneRegisterForEdit() {
        return phoneRegisters.get(indexRegisterForEdit);
    }

    /**
     * method adds a new phone register to the existing registers
     *
     * @param phoneRegister new added phone register
     */
    public void addPhoneRegister(PhoneRegister phoneRegister) {
        phoneRegisters.add(phoneRegister);
    }

    public void addAllPhoneRegisters(List<PhoneRegister> phoneRegisters){
        this.phoneRegisters.addAll(phoneRegisters);
    }

    /**
     * method adds a new phone register by index to the existing registers
     *
     * @param index         index where new register should be saved in registers list
     * @param phoneRegister new added phone register
     */
    void addPhoneRegister(int index, PhoneRegister phoneRegister) {
        phoneRegisters.add(index, phoneRegister);
    }

    /**
     * remove a phone register from existing registers
     *
     * @param selectedItem dead phone register
     */
    public void removeRegister(PhoneRegister selectedItem) {
        phoneRegisters.remove(selectedItem);
    }

    /**
     * remove a phone register from existing registers
     *
     * @param index index of register that should be deleted
     */
    void removeRegister(int index) {
        phoneRegisters.remove(index);
    }

    /**
     * get the result of editing and replace it be the old register
     *
     * @param newRegister new register
     */
    public void editRegister(PhoneRegister newRegister) {
        newRegister.setId(getIdRegisterForEdit());
        phoneRegisters.remove(phoneRegisters.get(indexRegisterForEdit));
        phoneRegisters.add(indexRegisterForEdit, newRegister);
    }

    /**
     * @param index index of the phone register, needs edit
     */
    public void setIndexRegisterForEdit(int index) {
        indexRegisterForEdit = index;
    }

    /**
     * @return index of register in registers list, which needs edit
     */
    int getIndexRegisterForEdit() {
        return indexRegisterForEdit;
    }

    public int getIdRegisterForEdit(){
      return phoneRegisters.get(indexRegisterForEdit).getId();
    }
}
