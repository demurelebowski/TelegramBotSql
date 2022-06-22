package com.demurelebowski.dto;

import com.demurelebowski.service.UserService;

import java.io.IOException;


public class User {
    private final Long timestamp;
    private final Long chatID;
    private String name;
    private String date;
    private String password;
    private boolean authorized;

    public User(String name, long chatID) throws IOException {
        this.chatID = chatID;
        this.timestamp = UserService.getTimeInLong();
        this.name = name;
        this.password = UserService.generateSimplePassword();
        this.date = UserService.getCurrentLocalDateTimeStamp();
        this.authorized = false;
        //insertUserSql(this);

    }


    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) throws IOException {
        this.authorized = authorized;
    }

    @Override
    public String toString() {
        return "Users{" +
                "timestamp=" + timestamp +
                ", name='" + name + '\'' +
                ", ChatID=" + chatID +
                ", Date='" + date + '\'' +
                ", password='" + password + '\'' +
                ", Authorized=" + authorized +
                '}';
    }

    public long getChatID() {
        return chatID;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) throws IOException {
        this.date = date;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getPassword() {
        return password;

    }

    public void setPassword(String password) throws IOException {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IOException {
        this.name = name;
    }


}
