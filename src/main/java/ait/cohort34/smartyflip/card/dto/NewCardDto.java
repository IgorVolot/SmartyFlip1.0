/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package ait.cohort34.smartyflip.card.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;

@Getter
public class NewCardDto {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cardId;
    private String question;
    private String answer;
    private String level;
    private Long moduleId;
}
