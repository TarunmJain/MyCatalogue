package com.centura_technologies.mycatalogue.Catalogue.Model;

/**
 * Created by Centura User1 on 23-08-2016.
 */
public class CustomerModel {
    String Name;
    String Gender;
    String DOB;
    String Phone;
    String Email;
    String Address;

    public CustomerModel(){
        Name="";
        Gender="";
        DOB="";
        Phone="";
        Email="";
        Address="";
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
