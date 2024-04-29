package ait.cohort34.smartyflip.card.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class NewModuleDto {
    Long moduleId;
    String authorName;
    String moduleName;
    LocalDateTime lastUpdate;
    Set<String> tags;
    String stackName;
    boolean isPublic;
    Integer cardsCount;
}
