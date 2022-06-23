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

            SendMessage message = new SendMessage() ;// Create a message object
            message.setChatId(String.valueOf(chat_id));

            SendPhoto messagePhoto = new SendPhoto();
            messagePhoto.setCaption(message_text);
            messagePhoto.setChatId(String.valueOf(chat_id));

            InputFile fileP = new InputFile().setMedia(new File("E:\\тел.png"));
            messagePhoto.setPhoto(fileP);
            try {
                User currentUser = SqlUsers.getUserSQL(chat_id);
                if (currentUser != null) {
                   if (currentUser.isAuthorized()){
                    TelegramCommands.executeCommand(message_text,message);}
                   else{
                       if (message_text.equals(currentUser.getPassword())){
                           currentUser.setAuthorized(true);
                           SqlUsers.updateUserSql(currentUser);
                           TelegramCommands.executeAuthorized(message_text,message);
                       }
                       else{
                           TelegramCommands.executeNotAuthorized(message_text,message);
                       }
                   }
                }
                else{
                    SqlUsers.insertUserSql(new User(update.getMessage().getFrom()));
                    TelegramCommands.executeNotAuthorized(message_text,message);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            try {
                execute(messagePhoto); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
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
}
