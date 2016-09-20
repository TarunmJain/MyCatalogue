package com.centura_technologies.mycatalogue.Leads.Model;

/**
 * Created by Centura User1 on 10-08-2016.
 */
public class LeadsModel {
    private String Id;
    private String LeadOwner;
    private String CompanyName;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Phone;
    private String Mobile;
    private String Website;
    private String LeadSource;
    private String LeadStatus;
    private String ContactName;
    private String ContactPhoneNumber;
    private String ContactEmail;
    private String Address;
    private String City;
    private String State;
    private String ZipCode;
    private String Country;
    private String Description;

    public LeadsModel(){
        Id="";
        LeadOwner="";
        CompanyName="";
        FirstName="";
        LastName="";
        Email="";
        Phone="";
        Mobile="";
        Website="";
        LeadSource="";
        LeadStatus="";
        ContactName="";
        ContactPhoneNumber="";
        ContactEmail="";
        Address="";
        City="";
        State="";
        ZipCode="";
        Country="";
        Description="";
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLeadOwner() {
        return LeadOwner;
    }

    public void setLeadOwner(String leadOwner) {
        LeadOwner = leadOwner;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getLeadSource() {
        return LeadSource;
    }

    public void setLeadSource(String leadSource) {
        LeadSource = leadSource;
    }

    public String getLeadStatus() {
        return LeadStatus;
    }

    public void setLeadStatus(String leadStatus) {
        LeadStatus = leadStatus;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactPhoneNumber() {
        return ContactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        ContactPhoneNumber = contactPhoneNumber;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public void setContactEmail(String contactEmail) {
        ContactEmail = contactEmail;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
