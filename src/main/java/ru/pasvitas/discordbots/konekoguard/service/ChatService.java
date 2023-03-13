package ru.pasvitas.discordbots.konekoguard.service;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ChatService {
    void registerCommand(JDA jda);
    void processChatMessage(MessageReceivedEvent guildMessageReceivedEvent);
    void processSlash(SlashCommandInteractionEvent event);
}
