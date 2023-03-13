package ru.pasvitas.discordbots.konekoguard.comands;

import java.util.List;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class AbstractCommand {

    public abstract String getName();
    public abstract Permission getPermission();
    public abstract List<OptionData> getOptions();
    public abstract String getDescription();

    protected abstract void innerProcessCommand(SlashCommandInteractionEvent slashCommandInteractionEvent);

    protected final void sendUnregisteredCommand(SlashCommandInteractionEvent slashCommandInteractionEvent) {
        slashCommandInteractionEvent
            .reply(
                "Сервер не зарегестрирован администратором!"
            ).queue();
    }

    public final void processCommand(SlashCommandInteractionEvent slashCommandInteractionEvent) {
        innerProcessCommand(slashCommandInteractionEvent);
    }
}
