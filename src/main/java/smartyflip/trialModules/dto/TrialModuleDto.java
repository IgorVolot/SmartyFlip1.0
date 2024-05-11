/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.trialModules.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import smartyflip.card.dto.CardDto;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TrialModuleDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long trialModuleId;

    String trialModuleName;

    String authorName;

    LocalDate dateCreated;

    String stackName;

    @Singular
    Set<String> tags;

    Set<CardDto> cards;

    int cardsAmount;
}
