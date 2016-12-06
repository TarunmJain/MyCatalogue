package com.centura_technologies.mycatalogue.Order.Model;

/**
 * Created by Centura on 15-10-2016.
 */
public class SalesmanModel {
    public String Id;
    public String Name;
    public String Username;
    public String Role;
    public String Phone;
    public String Email;
    public String ImageUrl;

    public SalesmanModel() {
        Id = "";
        Name = "";
        Username = "";
        Role = "";
        Phone = "";
        Email = "";
        ImageUrl = "";
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

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
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

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
