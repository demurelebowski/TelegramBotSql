package com.demurelebowski.dao;

import com.demurelebowski.dto.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

public class MyAmazingBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            Long chat_id = update.getMessage().getChatId();

            try {
                User currentUser = SqlUsers.getUserSQL(chat_id);
                if (currentUser != null) {
                   if (currentUser.isAuthorized()){
                    executeCommand(chat_id,message_text);
                   }
                   else{
                       if (message_text.equals(currentUser.getPassword())){
                           currentUser.setAuthorized(true);
                           SqlUsers.updateUserSql(currentUser);
                           executeAuthorized(chat_id);
                       }
                       else{
                           executeNotAuthorized(chat_id);
                       }
                   }
                }
                else{
                    SqlUsers.insertUserSql(new User(update.getMessage().getFrom()));
                    executeNotAuthorized(chat_id);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "MyAmazingBot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "840069339:AAEHayYtft9gzEUxZu27_pDz38ugAdQXd3c";
    }
    public void executeCommand (Long chat_id, String text) {

        SendTextMessage(chat_id, text);
        if ("photo".equals(text)){
            SendPhotoMessage(chat_id,text,"E:\\тел.png");
        }

    }
    public void executeNotAuthorized (Long chat_id) {

        SendTextMessage(chat_id,"Not authorized! Enter password.");

    }
    public void executeAuthorized (Long chat_id) {

        SendTextMessage(chat_id,"You are authorized.");

    }
    public void SendTextMessage (Long chat_id, String text) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chat_id));
        message.setText(text);
        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void SendPhotoMessage (Long chat_id, String text, String PathFile) {

        SendPhoto messagePhoto = new SendPhoto();
        messagePhoto.setCaption(text);
        messagePhoto.setChatId(String.valueOf(chat_id));

        InputFile fileP = new InputFile().setMedia(new File(PathFile));
        messagePhoto.setPhoto(fileP);

        try {
            execute(messagePhoto); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
