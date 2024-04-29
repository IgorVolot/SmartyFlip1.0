package ait.cohort34.smartyflip.card.dto;

import lombok.Getter;

@Getter
public class NewCardDto {
    String question;
    String answer;
    String level;
    Long moduleId;
}
