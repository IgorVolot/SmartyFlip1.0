/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long cardId;

    String question;

    String answer;

    String level;

    Long moduleId;

    String moduleName;

    LocalDateTime dateCreated;
}