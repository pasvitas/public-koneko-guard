package ru.pasvitas.discordbots.konekoguard.listener;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import ru.pasvitas.discordbots.konekoguard.service.ChatService;

@RequiredArgsConstructor
@Component
public class ChatListener extends ListenerAdapter {

    private final ChatService chatService;

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        chatService.processChatMessage(event);
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        chatService.processSlash(event);
    }
}
