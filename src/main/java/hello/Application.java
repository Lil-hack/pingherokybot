package hello;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.exceptions.TelegramApiException;


import java.util.Comparator;

import java.util.List;

import static java.lang.Math.toIntExact;

import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageCaption;


import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@EnableScheduling
@SpringBootApplication
public class Application extends TelegramLongPollingBot {
public String URL;
    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(Application.class, args);



        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {

            telegramBotsApi.registerBot(new Application());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Component
    public class ScheduledTasks {


        @Scheduled(cron = "*/10 * * * * *")
        public void reportCurrentTime() {


               SendMessage sendMessage = new SendMessage();
               sendMessage.enableMarkdown(true);
               sendMessage.setChatId("416744346");
               sendMessage.setText("123");
               sendMessageMain(sendMessage);


        }
    }
    @Override
    public String getBotUsername() {
        return "Miit Love";
    }

    @Override
    public String getBotToken() {
        //return "487089461:AAE131pIxM2o0KySxK2jHRvzJeXQK_r7qHI";

        return "765597859:AAFCyuBs6ZC0DWiPsywCEp6OujqCimHPSAo";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();


        if (message != null) {
            if (  message.hasText()) {


                switch (message.getText()) {

                    case "lox":
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.enableMarkdown(true);
                        sendMessage.setChatId(message.getChatId().toString());
                        sendMessage.setText("123 "+message.getChatId().toString());
                        sendMessageMain(sendMessage);
                        URL=message.getChatId().toString();
                         break;


                }
            }
            }
        }
    private void sendMessageMain(SendMessage message)   {

        try {

            execute(message);

        } catch (TelegramApiException e) {
            //e.printStackTrace();
        }
    }
    }


