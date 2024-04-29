package ait.cohort34.smartyflip.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    Long cardId;
    String question;
    String answer;
    LocalDateTime lastUpdate;
    String level;
    Long moduleId;
    Integer likes;
    boolean bookmark;
}
