package com.demurelebowski.dao;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class TelegramCommands {

    public static void executeCommand (String command,    SendMessage message){
        message.setText(command);
    }
    public static void executeNotAuthorized (String command,    SendMessage message){
        message.setText("Not authorized!\nEnter password.\u260E");
    }
    public static void executeAuthorized (String command,    SendMessage message){
        message.setText("You are authorized.");
    }

    public static void sendTextMessage (String text,   SendMessage message){


    }




}
