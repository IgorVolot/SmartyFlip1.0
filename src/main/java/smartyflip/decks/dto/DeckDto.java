/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.dto;

import jakarta.persistence.*;
import lombok.*;
import smartyflip.cards.dto.CardDto;
import smartyflip.cards.model.Card;
import smartyflip.decks.model.Tag;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DeckDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer deckId;

    String deckName;

    String authorName;

    LocalDate dateCreated;

    Integer stackId;

    String stackName;

    @Singular
    Set<String> tags;

    Set<CardDto> cards;

    int cardsAmount;

}
