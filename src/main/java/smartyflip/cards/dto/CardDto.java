/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.cards.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CardDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer cardId;

    String question;

    String answer;

    String level;

    Integer deckId;

    String deckName;

//    Integer stackId;
//
//    String stackName;

    LocalDateTime dateCreated;
}
