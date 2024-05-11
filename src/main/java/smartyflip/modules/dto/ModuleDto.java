/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dto;

import jakarta.persistence.*;
import lombok.*;
import smartyflip.card.dto.CardDto;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ModuleDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long moduleId;

    String moduleName;

    String authorName;

    LocalDate dateCreated;

    String stackName;

    @Singular
    Set<String> tags;

    Set<CardDto> cards;

    int cardsAmount;

}
