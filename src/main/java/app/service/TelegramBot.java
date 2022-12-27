package app.service;


import app.config.BotConfig;
import app.model.Anime;

import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.objects.Privacy;

import java.io.IOException;
import java.util.List;

import static app.api.SikimoriBuild.getRequest;
import static org.telegram.abilitybots.api.objects.Flag.TEXT;
import static org.telegram.abilitybots.api.objects.Locality.ALL;


@Component
public class TelegramBot extends AbilityBot {

    final BotConfig config;

    TelegramBot(BotConfig config)
    {
        super(config.getBotToken(), config.getBotName());
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

    @Override
    public long creatorId() {
        return -1;
    }



    public Ability startCommand()
    {
        return Ability.builder().name("start")
                .locality(ALL).privacy(Privacy.PUBLIC)
                .action(ctx->silent.send("Бот для рассылки выхода аниме", ctx.chatId())).build();
    }

    public Ability searchCommand()
    {
        return Ability.builder().name("search")
                .locality(ALL).privacy(Privacy.PUBLIC)
                .flag(TEXT).input(0)
                .action((MessageContext ctx)->{
                    if(ctx.firstArg().equals(ctx.secondArg())){
                        try {
                            List<Anime> anime = getRequest().getAnimeSearch(ctx.firstArg()).execute().body();
                            System.out.println(ctx.firstArg() +" "+ctx.secondArg());
                            for (Anime value : anime) {
                                silent.send(value.toString(), ctx.chatId());
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).build();
    }


}
