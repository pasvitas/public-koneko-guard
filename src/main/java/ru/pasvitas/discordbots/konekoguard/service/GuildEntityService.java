package ru.pasvitas.discordbots.konekoguard.service;

import java.util.Optional;
import ru.pasvitas.discordbots.konekoguard.model.GuildEntity;

public interface GuildEntityService {
    Optional<GuildEntity> getGuildEntity(String id);
    void updateGuildEntity(String id, GuildEntity guildEntity);
    void updateGuildEntityFromBan(String id, GuildEntity guildEntity);
}
