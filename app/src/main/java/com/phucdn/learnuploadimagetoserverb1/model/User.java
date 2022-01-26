package com.phucdn.learnuploadimagetoserverb1.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    private int accountId;
    private String username;
    private String password;
    @SerializedName("avt")
    private String avtatar;
    private String createAt;
    private String updateAt;

    public User() {
    }

    public User(int accountId, String username, String password, String avtatar) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.avtatar = avtatar;
    }

    public User(int accountId, String username, String password, String avtatar, String createAt, String updateAt) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.avtatar = avtatar;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvtatar() {
        return avtatar;
    }

    public void setAvtatar(String avtatar) {
        this.avtatar = avtatar;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
