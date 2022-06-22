package com.demurelebowski;


import com.demurelebowski.dao.MyAmazingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Run {

    public static void main(String[] args) throws TelegramApiException {

                // Initialize Api Context
                //ApiContextInitializer.init();


        // Instantiate Telegram Bots API
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

                // Register our bot
                try {
                    botsApi.registerBot(new MyAmazingBot());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }


