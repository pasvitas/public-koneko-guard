package ru.pasvitas.discordbots.konekoguard.comands;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import ru.pasvitas.discordbots.konekoguard.model.GuildEntity;
import ru.pasvitas.discordbots.konekoguard.service.GuildEntityService;

@Component
@RequiredArgsConstructor
public class BlockListCommand extends AbstractCommand {

    private final GuildEntityService guildEntityService;

    @Override
    public String getName() {
        return "blocklist";
    }

    @Override
    public Permission getPermission() {
        return Permission.BAN_MEMBERS;
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of();
    }

    @Override
    public String getDescription() {
        return "Показать блок-лист";
    }

    @Override
    protected void innerProcessCommand(SlashCommandInteractionEvent slashCommandInteractionEvent) {
        Optional<GuildEntity> guildEntityOptional = guildEntityService.getGuildEntity(slashCommandInteractionEvent.getGuild().getId());
        if (guildEntityOptional.isPresent()) {
            GuildEntity guildEntity = guildEntityOptional.get();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Забаненные слова и выражения:");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < guildEntity.getBadWordInfoList().size(); i++) {
                stringBuilder.append("**").append(i).append("**").append(" ").append(guildEntity.getBadWordInfoList().get(i).getWord()).append("\n");
            }
            embedBuilder.addField("В бане:", stringBuilder.toString(), false);
            slashCommandInteractionEvent.replyEmbeds(embedBuilder.build()).queue();
        }
        else {
            sendUnregisteredCommand(slashCommandInteractionEvent);
        }
    }
}
