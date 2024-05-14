/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import smartyflip.card.dto.CardDto;

import java.time.LocalDate;
import java.util.HashSet;
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

    String userName;

    LocalDate dateCreated;

    String stackName;

    @Builder.Default
    Set<String> tags = new HashSet<>();

    @Builder.Default
    Set<CardDto> cards = new HashSet<>();

//    int cardsAmount;

}
