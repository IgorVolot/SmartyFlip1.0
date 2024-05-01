/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package ait.cohort34.smartyflip.card.dto;

import lombok.Getter;

/**
 * This class represents a Data Transfer Object (DTO) for a new card.
 * It encapsulates the necessary data fields for a new card.
 * It provides getters and setters for each data field.
 */
@Getter
public class NewCardDto {

    /**
     * Represents a question.
     *
     * This variable is used to store a question for a card in the context of a Data Transfer Object (DTO) for a new card.
     * It belongs to the NewCardDto class and encapsulates the necessary data field for a question.
     * This variable can be accessed by using getter and setter methods defined in the NewCardDto class.
     */
    String question;

    /**
     * Represents the answer for a card in the context of a Data Transfer Object (DTO) for a new card.
     *
     * This variable belongs to the NewCardDto class and is used to store the answer for a card.
     * It can be accessed and modified using getter and setter methods defined in the NewCardDto class.
     */
    String answer;

    /**
     * The level of a card.
     *
     * The level indicates the difficulty or proficiency level of a card. It can be used to track the progress or skill level of a user with regard to a specific card.
     * The level is represented as a String.
     *
     * The possible values for the level are not defined in this variable, but are specific to the usage context.
     * The level can be set and retrieved using the appropriate getter and setter methods.
     */
    String level;

    /**
     * The moduleId variable represents the unique identifier of a module.
     * It is used as a data field in the NewCardDto class.
     *
     * Example Usage:
     *
     * NewCardDto newCardDto = new NewCardDto();
     * newCardDto.setModuleId(12345);
     *
     * int moduleId = newCardDto.getModuleId();
     * System.out.println(moduleId); // Output: 12345
     */
    Integer moduleId;

    /**
     * The stackName variable represents the name of a card stack or deck.
     * It is used as a data field in the NewCardDto class.
     *
     * Example Usage:
     *
     * NewCardDto newCardDto = new NewCardDto();
     * newCardDto.setStackName("Java Basics");
     *
     * System.out.println(newCardDto.getStackName()); // Output: Java Basics
     */
    String stackName;
}
