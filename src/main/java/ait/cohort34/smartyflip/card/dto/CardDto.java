/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package ait.cohort34.smartyflip.card.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * This class represents a Data Transfer Object (DTO) for a Card.
 * It encapsulates the necessary data fields for a Card.
 * It provides getters and setters for each data field.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CardDto {
    /**
     * Represents the ID of a card.
     *
     * <p>
     * The cardId variable is used to uniquely identify a card. It is annotated with
     * {@code @Id} and {@code @GeneratedValue} to indicate that it is the primary key of the entity.
     * The {@code @GeneratedValue} annotation specifies the strategy for generating the ID values,
     * in this case, it is set to {@code GenerationType.AUTO}.
     * </p>
     *
     * <p>
     * Example usage:
     * </p>
     * <pre>{@code
     *   CardDto card = new CardDto();
     *   card.setCardId(1);
     * }</pre>
     *
     * /@see javax.persistence.Id
     * /@see javax.persistence.GeneratedValue
     * /@see javax.persistence.GenerationType
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer cardId;

    /**
     * Represents a question string.
     *
     * <p>
     * The question string is a field of the {@link CardDto} class.
     * It encapsulates the question associated with a card.
     * </p>
     *
     * <p>
     * Example usage:
     * </p>
     * <pre>
     * CardDto card = new CardDto();
     * card.setQuestion(question);
     * </pre>
     * </p>
     */
    String question;

    /**
     * The answer variable represents the answer to a specific question.
     * It is a String type variable.
     * <p>
     * The answer variable is a field in the CardDto class, which is a Data Transfer Object (DTO) for a Card.
     * The CardDto class encapsulates the necessary data fields for a Card and provides getters and setters for each data field.
     * <p>
     * Note that this documentation only describes the variable itself, not how it is used or manipulated within the CardDto class.
     */
    String answer;

    /**
     * Represents the last update time of a CardDto object.
     * <p>
     * The last update time indicates the date and time when the CardDto object was last modified.
     * It is represented using the LocalDateTime class provided by the Java API.
     */
    LocalDateTime lastUpdate;

    /**
     * The level variable represents the difficulty level of a card in a flashcard application.
     * <p>
     * It is a String that can hold one of the following values:
     *  - "easy": representing an easy level difficulty
     *  - "medium": representing a medium level difficulty
     *  - "hard": representing a hard level difficulty
     * <p>
     * This variable is used in the CardDto class to indicate the difficulty level of a flashcard.
     */
    String level;

    /**
     * The `likes` variable represents the number of likes a card has received.
     * <p>
     * It is of type `Integer`.
     * <p>
     * This variable is declared and used within the `CardDto` class, which is a Data Transfer Object (DTO) for a Card.
     * <p>
     * To access or modify the value of the `likes` variable, you can use the appropriate getters and setters provided by the `CardDto` class.
     * <p>
     * Example usage:
     * ```java
     * CardDto card = new CardDto();
     * card.setLikes(5);
     * Integer likes = card.getLikes();
     * ```
     */
    Integer likes;

    /**
     * Represents a bookmark status for a particular card.
     * <p>
     * The bookmark variable is a boolean field that indicates whether a card has been bookmarked or not.
     * A value of true indicates that the card has been bookmarked, while a value of false indicates that it has not.
     * <p>
     * This field is part of the CardDto class, which represents a Data Transfer Object (DTO) for a card.
     * The CardDto class encapsulates all the necessary data fields for a card and provides getters and setters for each field.
     * <p>
     * Example usage:
     * <p>
     * CardDto card = new CardDto();
     * card.setBookmark(true);
     * boolean bookmarked = card.isBookmark();
     * <p>
     * In this example, a new CardDto object is created and assigned to the card variable.
     * The setBookmark() method is then used to set the bookmark status of the card to true.
     * Finally, the isBookmark() method is used to retrieve the bookmark status of the card, which is assigned to the bookmarked variable.
     */
    boolean bookmark;

    /**
     * The `moduleId` variable represents the ID of a module in the application.
     * It is an `Integer` type.
     * <p>
     * This variable is used in the `CardDto` class to identify the module to which a card belongs.
     * It is an important identifier for managing and organizing the cards within the application.
     * <p>
     * Example usage:
     * <p>
     * // Creating a new card with a specific module ID
     * CardDto card = new CardDto();
     * card.setModuleId(1);
     * <p>
     * // Getting the module ID of a card
     * Integer moduleId = card.getModuleId();
     * <p>
     * // Checking if a card belongs to a specific module
     * boolean belongsToModule = moduleId.equals(1);
     * <p>
     * // Updating the module ID of a card
     * card.setModuleId(2);
     * <p>
     * // Deleting a card with a specific module ID
     * repository.deleteByModuleId(1);
     * <p>
     * Note: The `moduleId` can be used in conjunction with other properties to perform operations
     * related to the module to which a card belongs, such as filtering, sorting, or retrieving.
     */
    Integer moduleId;

    /**
     * The stackName variable represents the name of a stack in an application.
     * It is a string that identifies a specific stack.
     * <p>
     * The stackName variable is a property of the CardDto class, which is a Data Transfer Object (DTO) for a Card.
     * The CardDto class encapsulates the necessary data fields for a Card and provides getters and setters for each data field.
     * <p>
     * Example usage:
     * <p>
     * CardDto card = new CardDto();
     * card.setStackName("Java Basics");
     * String stackName = card.getStackName();
     * <p>
     * Note: The value of stackName should be set and retrieved using the corresponding getter and setter methods of the CardDto class.
     */
    String stackName;
}
