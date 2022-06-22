package com.demurelebowski.dao;

import com.demurelebowski.dto.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class MyAmazingBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            Long chat_id = update.getMessage().getChatId();

            SendMessage message = new SendMessage() ;// Create a message object object
            message.setChatId(String.valueOf(chat_id));

            try {
                User currentUser = SqlUsers.getUserSQL(chat_id);
                if (currentUser != null) {
                   if (currentUser.isAuthorized()){
                    message.setText(message_text);}
                   else{
                       if (message_text.equals(currentUser.getPassword())){
                           currentUser.setAuthorized(true);
                           SqlUsers.updateUserSql(currentUser);
                           message.setText(message_text);
                       }
                       else{
                       message.setText("Not authorized!\nEnter password.\u260E");
                       }
                   }
                }
                else{
                    SqlUsers.insertUserSql(new User(update.getMessage().getFrom()));
                    message.setText("Not authorized!\nEnter password.\u260E");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                execute(message); // Sending our message object to user
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
