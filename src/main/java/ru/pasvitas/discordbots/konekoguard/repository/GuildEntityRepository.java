package ru.pasvitas.discordbots.konekoguard.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.pasvitas.discordbots.konekoguard.model.GuildEntity;

@Repository
public interface GuildEntityRepository extends MongoRepository<GuildEntity, String> {
}
