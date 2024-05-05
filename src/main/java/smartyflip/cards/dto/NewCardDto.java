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
 * This class represents a Data Transfer Object (DTO) for a new modules.
 * It encapsulates the necessary data fields for a new modules.
 * It provides getters and setters for each data field.
 */
@Getter
public class NewCardDto {

    String question;

    String answer;

    String level;

    Integer moduleId;

    String stackName;

    public String getModuleName() {
        return null;
    }
}
