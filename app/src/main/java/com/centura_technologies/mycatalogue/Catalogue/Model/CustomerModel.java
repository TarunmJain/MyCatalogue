package com.centura_technologies.mycatalogue.Catalogue.Model;

import java.util.UUID;

/**
 * Created by Centura User1 on 23-08-2016.
 */
public class CustomerModel {
    String Id;
    String Name;
    String Gender;
    String DOB;
    String Phone;
    String Email;
    String Address;

    public CustomerModel(){
        Id="";
        Name="";
        Gender="";
        DOB="";
        Phone="";
        Email="";
        Address="";
    }

    public CustomerModel(String name){
        Id= UUID.randomUUID().toString();
        Name="Customer"+name;
        Gender="Male";
        DOB="";
        Phone="123456781"+name;
        Email="email"+name+"@gmail.com";
        Address="";
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
