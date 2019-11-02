package chatbot.hyobin.listener;

import chatbot.hyobin.provider.ContentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class TelegramMessageListener {
    @Value("${telegram.bot.name}")
    private String telegramBotName;
    @Value("${telegram.bot.key}")
    private String telegramBotKey;
    @Autowired
    private ContentProvider contentProvider;

    @PostConstruct
    private void init(){
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        try{
            api.registerBot(new TelegramLongPollingBot() {
                @Override
                public String getBotToken() {
                    return telegramBotKey;
                }

                @Override
                public void onUpdateReceived(Update update) {
                    if(update.hasMessage() && update.getMessage().hasText()){
                        String stringMessage = update.getMessage().getText();
                        SendMessage message = new SendMessage().enableHtml(true);
                        stringMessage = stringMessage.replace("/","");
                        if(stringMessage.contains("신청")){
                            message.setChatId(update.getMessage().getChatId());
                            for(int i=0;i<10000;i++){

                                try {
                                    int result = contentProvider.checkLeast();
                                    if(result==1){
                                        message.setText("신청 쌉가능");
                                        execute(message);
                                    }
                                    else if(result == -1){
                                        message.setText("연결 종료");
                                        execute(message);
                                    }
                                    else if(i%10==0){
                                        message.setText("신청 탐색중");
                                        execute(message);
                                    }
                                    Thread.sleep(60000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                }

                @Override
                public String getBotUsername() {
                    return telegramBotName;
                }
            });
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
