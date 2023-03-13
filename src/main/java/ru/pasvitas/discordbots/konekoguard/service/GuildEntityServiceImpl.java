package ru.pasvitas.discordbots.konekoguard.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.pasvitas.discordbots.konekoguard.model.GuildEntity;
import ru.pasvitas.discordbots.konekoguard.repository.GuildEntityRepository;

@RequiredArgsConstructor
@Service
public class GuildEntityServiceImpl implements GuildEntityService {

    private final GuildEntityRepository guildEntityRepository;

    @Cacheable("guildEntity")
    @Override
    public Optional<GuildEntity> getGuildEntity(String id) {
        return guildEntityRepository.findById(id);
    }

    @CacheEvict(value = { "guildEntity", "badWordsForGuild" }, key = "#id")
    @Override
    public void updateGuildEntity(String id, GuildEntity guildEntity) {
        guildEntityRepository.save(guildEntity);
    }

    @CacheEvict(value = "guildEntity", key = "#id")
    @Override
    public void updateGuildEntityFromBan(String id, GuildEntity guildEntity) {
        guildEntityRepository.save(guildEntity);
    }
}
