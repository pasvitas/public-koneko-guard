package ru.pasvitas.discordbots.konekoguard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.pasvitas.discordbots.konekoguard.comands.AbstractCommand;
import ru.pasvitas.discordbots.konekoguard.model.BadWordInfo;
import ru.pasvitas.discordbots.konekoguard.model.GuildEntity;


@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    private final GuildEntityService guildEntityService;

    private final Map<String, AbstractCommand> commandMap = new HashMap<>();

    private final List<AbstractCommand> commands;

    @Autowired
    public ChatServiceImpl(GuildEntityService guildEntityService, List<AbstractCommand> commands) {
        this.guildEntityService = guildEntityService;
        this.commands = commands;
    }

    @Override
    public void registerCommand(JDA jda) {
        commands.forEach(
            command -> jda
                .upsertCommand(command.getName(), command.getDescription())
                .addOptions(command.getOptions())
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(command.getPermission()))
                .queue(
                    message -> commandMap.put(command.getName(), command)
                )
        );
    }

    @Override
    public void processChatMessage(MessageReceivedEvent guildMessageReceivedEvent) {
        if (guildMessageReceivedEvent.getAuthor().getId().equals(guildMessageReceivedEvent.getJDA().getSelfUser().getId())) {
            return;
        }
        Optional<GuildEntity> guildEntityOptional = guildEntityService.getGuildEntity(guildMessageReceivedEvent.getGuild().getId());
        String rawMessage = guildMessageReceivedEvent.getMessage().getContentRaw();
        if (guildEntityOptional.isPresent()) {
            GuildEntity guildEntity = guildEntityOptional.get();
            List<String> words = getBadWordsForGuild(guildEntity);
            boolean checkResult = checkMatcher(words, rawMessage);
            if (checkResult) {
                if (guildMessageReceivedEvent.getMember() != null) {
                    String banId = UUID.randomUUID().toString();
                    guildEntity.setCounter(guildEntity.getCounter() + 1);
                    guildMessageReceivedEvent
                            //.getChannel().sendMessage(guildMessageReceivedEvent.getAuthor().getAsMention() + "Типа бан тебя")
                            .getMember().ban(1, TimeUnit.DAYS)
                            .queue(success -> {
                                guildMessageReceivedEvent
                                        .getChannel()
                                        .sendMessage("Скам-бот уничтожен. Уничтожено тут всего: " + guildEntity.getCounter())
                                        .queue(successMessage -> {
                                            try {
                                                Thread.sleep(30000L);
                                                successMessage.delete().queue();
                                            } catch (InterruptedException interruptedException) {
                                                interruptedException.printStackTrace();
                                            }
                                        });
                            });
                    TextChannel textChannel = guildMessageReceivedEvent.getGuild().getTextChannelById(guildEntity.getLogChannel());
                    if (textChannel != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder
                                .append("**Бан скамбота**")
                                .append("\nБан ID: ").append(banId)
                                .append("\nПользователь: ").append(guildMessageReceivedEvent.getAuthor().getAsMention())
                                .append("\nСообщение: ").append(rawMessage);
                        textChannel.sendMessage(stringBuilder).queue();
                    }
                    guildEntityService.updateGuildEntityFromBan(guildEntity.getId(), guildEntity);
                }
            }
        }
    }

    @Override
    public void processSlash(SlashCommandInteractionEvent event) {
        commandMap.get(event.getName()).processCommand(event);
    }

    @Cacheable("badWordsForGuild")
    public List<String> getBadWordsForGuild(GuildEntity guildEntity) {
        return guildEntity.getBadWordInfoList().stream().map(
                BadWordInfo::getWord
        ).collect(Collectors.toList());
    }

    private boolean checkMatcher(List<String> phrases, String message) {
        return phrases.stream().anyMatch(phrase -> message.toLowerCase().contains(phrase.toLowerCase()));
    }
}
