package ru.pasvitas.discordbots.konekoguard.comands;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;
import ru.pasvitas.discordbots.konekoguard.model.GuildEntity;
import ru.pasvitas.discordbots.konekoguard.service.GuildEntityService;

@RequiredArgsConstructor
@Component
public class UnblockCommand extends AbstractCommand {

	private final GuildEntityService service;

	@Override
	public String getName() {
		return "unblock";
	}

	@Override
	public Permission getPermission() {
		return Permission.BAN_MEMBERS;
	}

	@Override
	public List<OptionData> getOptions() {
		return List.of(new OptionData(OptionType.INTEGER, "wordnum", "Номер слова", true));
	}

	@Override
	public String getDescription() {
		return "Разблокировать строку по ид";
	}

	@Override
	protected void innerProcessCommand(SlashCommandInteractionEvent slashCommandInteractionEvent) {
		Optional<GuildEntity> guildEntityOptional = service.getGuildEntity(slashCommandInteractionEvent.getGuild().getId());
		if (guildEntityOptional.isPresent()) {
			int num = slashCommandInteractionEvent.getOption("wordnum").getAsInt();
			GuildEntity guildEntity = guildEntityOptional.get();
			guildEntity.getBadWordInfoList().remove(num);
			service.updateGuildEntity(slashCommandInteractionEvent.getGuild().getId(), guildEntity);
			slashCommandInteractionEvent
				.reply("Удалено!")
				.queue();
		} else {
			sendUnregisteredCommand(slashCommandInteractionEvent);
		}
	}
}
