package ru.pasvitas.discordbots.konekoguard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GuildInfo {
    private String guildId;
    private String guildName;
    private String registerId;
    private String registerName;
}
