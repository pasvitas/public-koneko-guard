package ru.pasvitas.discordbots.konekoguard.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BadWordInfo {
    private String word;
    private Date date;
    private AdderInfo adderInfo;
}
