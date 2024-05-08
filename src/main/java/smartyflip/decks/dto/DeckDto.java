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
import smartyflip.decks.model.Tag;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

//@AllArgsConstructor
//@NoArgsConstructor
@Getter
public class DeckDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer deckId;

    String deckName;

    String authorName;

    LocalDate dateCreated;

    String stackName;

    int cardsCount;

    Set<Tag> tags;

}
