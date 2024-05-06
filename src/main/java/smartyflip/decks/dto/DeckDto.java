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
    private Integer deckId;

    private String deckName;

    private String authorName;

    private LocalDate dateCreated;

    private String stackName;

    private int cardsCount;

    @Singular
    Set<String> tags;
}
