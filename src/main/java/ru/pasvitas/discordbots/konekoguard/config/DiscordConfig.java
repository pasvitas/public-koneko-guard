package ru.pasvitas.discordbots.konekoguard.config;

import java.util.List;
import javax.security.auth.login.LoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import ru.pasvitas.discordbots.konekoguard.listener.ChatListener;
import ru.pasvitas.discordbots.konekoguard.service.ChatService;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DiscordConfig {

    @Value("${token}")
    private String token;

    @Value("${activity}")
    private String activity;

    private final ChatListener chatListener;

    @Bean
    public JDA configBot() throws LoginException {
        log.info("Init bot with token: {}", token);
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.disableCache(CacheFlag.ACTIVITY);
        builder.enableIntents(List.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS));
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.playing(activity));
        builder.addEventListeners(chatListener);
        log.info("Init bot with token: {}", token);
        return builder.build();
    }
}
