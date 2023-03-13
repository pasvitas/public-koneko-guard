package ru.pasvitas.discordbots.konekoguard.comands;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import ru.pasvitas.discordbots.konekoguard.model.GuildEntity;
import ru.pasvitas.discordbots.konekoguard.model.GuildInfo;
import ru.pasvitas.discordbots.konekoguard.service.GuildEntityService;

@RequiredArgsConstructor
@Component
public class RegisterGuildCommand extends AbstractCommand {

    private final GuildEntityService service;

    @Override
    public String getName() {
        return "init";
    }

    @Override
    public Permission getPermission() {
        return Permission.ADMINISTRATOR;
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
            new OptionData(OptionType.CHANNEL, "logсhannel", "Канал для логов", true)
        );
    }

    @Override
    public String getDescription() {
        return "Инициализация гильдии";
    }


    @Override
    protected void innerProcessCommand(SlashCommandInteractionEvent slashCommandInteractionEvent) {
        if (slashCommandInteractionEvent.getOption("logChannel") != null) {
            TextChannel channel = slashCommandInteractionEvent.getOption("logсhannel").getAsChannel().asTextChannel();
            GuildEntity guildEntity = new GuildEntity(
                slashCommandInteractionEvent.getGuild().getId(),
                0,
                channel.getId(),
                new GuildInfo(
                    slashCommandInteractionEvent.getGuild().getId(),
                    slashCommandInteractionEvent.getGuild().getName(),
                    slashCommandInteractionEvent.getUser().getId(),
                    slashCommandInteractionEvent.getUser().getName()
                ),
                new ArrayList<>(),
                new ArrayList<>()
            );
            service.updateGuildEntity(guildEntity.getId(), guildEntity);
        }
        else {
            slashCommandInteractionEvent
                .reply("Нужно указать канал для логов")
                .queue();
        }
    }
}
