package com.demurelebowski.dto;

import com.demurelebowski.service.UserService;

import java.io.IOException;


public class User {


    private String name;
    private final Long chatID;
    private String date;
    private String password;
    private final Long timestamp;

    public User(String name, Long chatID, String date, String password, Long timestamp, boolean authorized) {
        this.name = name;
        this.chatID = chatID;
        this.date = date;
        this.password = password;
        this.timestamp = timestamp;
        this.authorized = authorized;
    }

    private boolean authorized;

    public User(String name, long chatID) {
        this.chatID = chatID;
        this.timestamp = UserService.getTimeInLong();
        this.name = name;
        this.password = UserService.generateSimplePassword();
        this.date = UserService.getCurrentLocalDateTimeStamp();
        this.authorized = false;
        //insertUserSql(this);

    }
    public User(org.telegram.telegrambots.meta.api.objects.User user) throws IOException {
        this.chatID = user.getId();
        this.timestamp = UserService.getTimeInLong();
        this.name = user.getFirstName() + " " + user.getLastName() +". "+user.getUserName();
        this.password = UserService.generateSimplePassword();
        this.date = UserService.getCurrentLocalDateTimeStamp();
        this.authorized = false;

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

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getPassword() {
        return password;

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
