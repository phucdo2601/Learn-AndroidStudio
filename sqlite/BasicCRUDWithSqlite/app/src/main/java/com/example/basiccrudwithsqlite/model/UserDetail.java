package com.example.basiccrudwithsqlite.model;

public class UserDetail {
    private String name;
    private String contact;
    private String dob;

    public UserDetail(String name, String contact, String dob) {
        this.name = name;
        this.contact = contact;
        this.dob = dob;
    }

    public UserDetail() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
