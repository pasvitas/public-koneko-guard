package ru.pasvitas.discordbots.konekoguard.config;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.pasvitas.discordbots.konekoguard.service.ChatService;

@Component
@RequiredArgsConstructor
public class RegisterConfig {

	private final JDA jda;
	private final ChatService chatService;

	@EventListener(ApplicationStartedEvent.class)
	public void register() {
		chatService.registerCommand(jda);
	}

}
