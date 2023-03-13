package ru.pasvitas.discordbots.konekoguard.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GuildEntity {

    @Id
    @MongoId
    private String id;

    private int counter;

    private String logChannel;

    private GuildInfo guildInfo;

    private List<String> modelRoles;

    private List<BadWordInfo> badWordInfoList;

}
