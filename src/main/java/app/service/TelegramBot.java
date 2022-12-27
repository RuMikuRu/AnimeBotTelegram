package app.service;

import app.api.ShikimoriApi;
import app.config.BotConfig;
import app.model.Anime;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Arrays;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    TelegramBot(BotConfig config)
    {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText){
                case "/start":
                    startCommandReceive(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/anime":
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://shikimori.one/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ShikimoriApi shikimoriApi = retrofit.create(ShikimoriApi.class);
                    List<Anime> animeList = shikimoriApi.getAnime().execute().body();
                    sendMessageAnimeList(chatId, animeList);
                default: sendMessage(chatId, "Sorry not command");
            }
        }
    }

    private void sendMessageAnimeList(long chatId, List<Anime> animeList) throws TelegramApiException {
        SendMessage message = new SendMessage();
        for(int i = 0;i< animeList.size();i++)
        {
            message.setChatId(String.valueOf(chatId));
            message.setText(animeList.get(i).toString());
            execute(message);
        }
    }

    private void startCommandReceive(long chatId, String firstName) throws TelegramApiException {
        String answer = "Hi, " + firstName;

        sendMessage(chatId, String.valueOf(chatId));
    }

    private void sendMessage(long chatId, String textToSend) throws TelegramApiException {
        SendMessage message = new SendMessage();

        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try{
            execute(message);
        }catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
