package ru.pasvitas.discordbots.konekoguard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdderInfo {
    private String userId;
    private String discordTag;
    private String userName;
}
