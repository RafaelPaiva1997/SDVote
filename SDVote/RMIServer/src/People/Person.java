package People;

import java.util.Date;

/**
 * Created by Yevgen on 20/10/2017.
 * This class describes possible voters
 */

public abstract class Person {
    String faculty;
    String department;
    String name;
    int phoneNumber;
    int identityNumber;
    Date validUntill;
    String password;

    Person(String faculty, String department, String name, int phoneNumber, int identityNumber, Date validUntill, String password){
        this.faculty = faculty;
        this.department = department;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.identityNumber = identityNumber;
        this.validUntill = validUntill;
        this.password = password;

    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(int identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Date getValidUntill() {
        return validUntill;
    }

    public void setValidUntill(Date validUntill) {
        this.validUntill = validUntill;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
