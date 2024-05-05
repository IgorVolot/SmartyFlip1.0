/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.cards.dto;

import lombok.Getter;

/**
 * This class represents a Data Transfer Object (DTO) for a new decks.
 * It encapsulates the necessary data fields for a new decks.
 * It provides getters and setters for each data field.
 */
@Getter
public class NewCardDto {

    String question;

    String answer;

    String level;

//    Integer deckId;

    String deckName;

    String stackName;

    public String getModuleName() {
        return null;
    }
}
