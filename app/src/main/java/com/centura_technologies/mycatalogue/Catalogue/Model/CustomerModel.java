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
    String UserName;
    String ShippingAddress;
    String Landmark;
    String City;
    String State;
    String PinCode;
    boolean IsActive;


    public CustomerModel(){
        Id="";
        Name="";
        Gender="";
        DOB="";
        Phone="";
        Email="";
        UserName="";
        ShippingAddress="";
        Landmark="";
        City="";
        State="";
        PinCode="";
        IsActive=false;
    }

    public CustomerModel(String name){
        Id= UUID.randomUUID().toString();
        Name="Customer"+name;
        Gender="Male";
        DOB="";
        Phone="123456781"+name;
        Email="email"+name+"@gmail.com";
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }
}
