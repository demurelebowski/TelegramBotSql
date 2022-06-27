package com.demurelebowski.dao;

import com.demurelebowski.dto.Calendar;
import com.demurelebowski.dto.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyAmazingBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            Long chat_id = update.getMessage().getChatId();
            Long From_id = update.getMessage().getFrom().getId();
            int message_id = update.getMessage().getMessageId();

            try {
                User currentUser = SqlUsers.getUserSQL(From_id);
                if (currentUser != null) {
                    if (currentUser.isAuthorized()) {
                        executeCommand(From_id, message_text);
                    } else {
                        if (message_text.equals(currentUser.getPassword())) {
                            currentUser.setAuthorized(true);
                            SqlUsers.updateUserSql(currentUser);
                            executeAuthorized(From_id);
                        } else {
                            executeNotAuthorized(From_id);
                        }
                    }
                } else {
                    SqlUsers.insertUserSql(new User(update.getMessage().getFrom()));
                    executeNotAuthorized(From_id);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } else if (update.hasCallbackQuery()) {

            CallbackQuery callbackQuery = update.getCallbackQuery();
            Long chat_id = callbackQuery.getMessage().getChatId();
            String callBackData = callbackQuery.getData();
            String callbackQueryId = callbackQuery.getId();
            int message_id = callbackQuery.getMessage().getMessageId();

            try {
                User currentUser = SqlUsers.getUserSQL(chat_id);
                if (currentUser != null) {
                    if (currentUser.isAuthorized()) {
                        executeCallbackQuery(chat_id, callBackData, message_id, callbackQueryId);

                    }
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

      /*
        String appConfigPath = "src\\main\\resources\\resources.properties";
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "840069339:AAEHayYtft9gzEUxZu27_pDz38ugAdQXd3c";
    }

    public void executeCommand(Long chat_id, String text) {

        if ("/setappointment".equals(text)) {
            // SendTextMessage(chat_id, "Choose date", Calendar.inKeysPeriod(null, "cal_datech_"));
            sendCalendar(chat_id, "Choose date", null);
        }
        /*
        if ("photo".equals(text)){
            SendPhotoMessage(chat_id,text,"E:\\тел.png");
        }
*/
    }

    public void executeCallbackQuery(Long chat_id, String data, int message_id, String callbackQueryId) {

        if (data.contains("cal_date_")) {
            sendCalendar(chat_id, data, message_id);
        }
        if (data.contains("cal_datech_")) {
            writeAppointment(chat_id, data, message_id);
        }
        answerCallbackQuery(callbackQueryId);
    }

    public void sendCalendar(Long chat_id, String data, Integer message_id) {

        String text = "Choose a date";
        String callBackText = "cal_datech_";

        if (message_id != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(data.replace("cal_date_", ""), formatter);
            editTextMessage(chat_id, text, message_id, Calendar.inKeysPeriod(localDate, callBackText));
        } else {
            sendTextMessage(chat_id, text, Calendar.inKeysPeriod(null, callBackText));
        }

    }

    public void writeAppointment(Long chat_id, String data, Integer message_id) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(data.replace("cal_datech_", ""), formatter);
        sendTextMessage(chat_id, data, null);

    }

    public void answerCallbackQuery(String callbackQueryId) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackQueryId);
        try {
            execute(answer); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeNotAuthorized(Long chat_id) {

        sendTextMessage(chat_id, "Not authorized! Enter password.", null);

    }

    public void executeAuthorized(Long chat_id) {

        sendTextMessage(chat_id, "You are authorized.", null);

    }

    public void sendTextMessage(Long chat_id, String text, InlineKeyboardMarkup inlineKeyboard) {

        SendMessage message = new SendMessage();

        message.setChatId(String.valueOf(chat_id));
        message.setText(text);
        message.setReplyMarkup(inlineKeyboard);

        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void editTextMessage(Long chat_id, String text, int message_id, InlineKeyboardMarkup inlineKeyboard) {

        EditMessageText message = new EditMessageText();

        message.setChatId(String.valueOf(chat_id));
        message.setText(text);
        message.setReplyMarkup(inlineKeyboard);
        message.setMessageId(message_id);
        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendPhotoMessage(Long chat_id, String text, String PathFile) {

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
