package chatbot.hyobin.listener;

import chatbot.hyobin.contentSelector.ContentSelector;
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
    private ContentSelector contentSelector;
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
                        message.setChatId(update.getMessage().getChatId());
                        message.setText(contentSelector.selectContent(stringMessage));
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
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
