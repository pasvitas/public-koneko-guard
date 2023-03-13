package ru.pasvitas.discordbots.konekoguard.comands;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import ru.pasvitas.discordbots.konekoguard.model.AdderInfo;
import ru.pasvitas.discordbots.konekoguard.model.BadWordInfo;
import ru.pasvitas.discordbots.konekoguard.model.GuildEntity;
import ru.pasvitas.discordbots.konekoguard.service.GuildEntityService;

@RequiredArgsConstructor
@Component
public class BlockCommand extends AbstractCommand {

    private final GuildEntityService service;

    @Override
    public String getName() {
        return "block";
    }

    @Override
    public Permission getPermission() {
        return Permission.BAN_MEMBERS;
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(new OptionData(OptionType.STRING, "word", "Cлово для бана", true));
    }

    @Override
    public String getDescription() {
        return "Заблокировать строку";
    }

    @Override
    protected void innerProcessCommand(SlashCommandInteractionEvent slashCommandInteractionEvent) {
        Optional<GuildEntity> guildEntityOptional = service.getGuildEntity(slashCommandInteractionEvent.getGuild().getId());
        if (guildEntityOptional.isPresent()) {
            String word = slashCommandInteractionEvent.getOption("word").getAsString();
            GuildEntity guildEntity = guildEntityOptional.get();
            guildEntity.getBadWordInfoList().add(new BadWordInfo(
                    word,
                    new Date(),
                    new AdderInfo(
                            slashCommandInteractionEvent.getUser().getId(),
                            slashCommandInteractionEvent.getUser().getAsTag(),
                            slashCommandInteractionEvent.getUser().getName()
                    )
            ));
            service.updateGuildEntity(slashCommandInteractionEvent.getGuild().getId(), guildEntity);
            slashCommandInteractionEvent
                .reply("Добавлено!")
                .queue();
        }
        else {
            sendUnregisteredCommand(slashCommandInteractionEvent);
        }
    }
}
