package hello;

import java.util.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
    public  String URL_API="https://beatmaker.tv/mp3/Mp3/ValidateCode";

    public static String CID="d256c775-88b6-47b9-a313-958228f49485";
public int schet=0;
    public  static boolean start=true;
    public  static List<String> idList=new ArrayList<>();

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



        @Scheduled(fixedDelay = 120000)
        public void reportCurrentTime() {
        if(start==true) {
            schet = schet + 1;

            try {

                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_API);
                System.out.println(start);
                System.out.println(idList.size());
                for (String item : idList) {
                    System.out.println(item);
                    Formatter f = new Formatter();
                    String requestJson = f.format("code=%s&beatId=%s&isBeat=true&isFreeBeat=false",CID,item).toString();
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                    HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
                    RestTemplate restTemplate = new RestTemplate();

                    String result =
                            restTemplate.exchange(
                                    builder.toUriString(), HttpMethod.POST, entity, String.class).getBody();
                    try {
                        Random rand = new Random();
                        Thread.sleep(rand.nextInt(5000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            } catch (HttpServerErrorException e) {

                SendMessage sendMessage = new SendMessage();
                sendMessage.enableMarkdown(true);
                sendMessage.setChatId("416744346");
                sendMessage.setText(e.toString());
                sendMessageMain(sendMessage);
            }


            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId("416744346");
            sendMessage.setText(Integer.toString(schet)+" len:"+ String.valueOf(idList.size()));
            sendMessageMain(sendMessage);

        }
            try {
                Random rand = new Random();
                Thread.sleep(rand.nextInt(60000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                        sendMessage.setText(schet+"lox"+message.getChatId().toString());
                        sendMessageMain(sendMessage);
                        URL=message.getChatId().toString();
                         break;

                    case "start":
                        start=true;
                        break;
                    case "stop":
                        start=false;
                        break;
                    case "cid":
                        SendMessage sendMessage2 = new SendMessage();
                        sendMessage2.enableMarkdown(true);
                        sendMessage2.setChatId(message.getChatId().toString());
                        sendMessage2.setText(CID);
                        sendMessageMain(sendMessage2);
                        URL=message.getChatId().toString();
                        break;
                    case "clean":
                        idList.clear();
                        break;
                    default:
            if(message.getText().length()==36)
            {
                CID=message.getText();
            }
            else
                {

                    if(message.getText().length()>10)
                    {
                        System.out.println(message.getText());
                        String[] subStr;
                        String delimeter = "\n"; // Разделитель
                        subStr = message.getText().split(delimeter); // Разделения строки str с помощью метода split()
                        // Вывод результата на экран
                        for (int i = 0; i < subStr.length; i++) {
                            idList.add(subStr[i]);
                        }
                    }
                }


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


