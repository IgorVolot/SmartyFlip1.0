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
     * Represents the ID of a decks.
     *
     * <p>
     * The cardId variable is used to uniquely identify a decks. It is annotated with
     * {@code @Id} and {@code @GeneratedValue} to indicate that it is the primary key of the entity.
     * The {@code @GeneratedValue} annotation specifies the strategy for generating the ID values,
     * in this case, it is set to {@code GenerationType.AUTO}.
     * </p>
     *
     * <p>
     * Example usage:
     * </p>
     * <pre>{@code
     *   CardDto decks = new CardDto();
     *   decks.setCardId(1);
     * }</pre>
     *
     * @see Id
     * @see GeneratedValue
     * @see GenerationType
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer cardId;

    String question;

    String answer;

    LocalDateTime dateCreated;

    String level;

//    /**
//     * The `likes` variable represents the number of likes a decks has received.
//     *
//     * It is of type `Integer`.
//     *
//     * This variable is declared and used within the `CardDto` class, which is a Data Transfer Object (DTO) for a Card.
//     *
//     * To access or modify the value of the `likes` variable, you can use the appropriate getters and setters provided by the `CardDto` class.
//     *
//     * Example usage:
//     * ```java
//     * CardDto decks = new CardDto();
//     * decks.setLikes(5);
//     * Integer likes = decks.getLikes();
//     * ```
//     */
//    Integer likes;
//
//    /**
//     * Represents a bookmark status for a particular decks.
//     *
//     * The bookmark variable is a boolean field that indicates whether a decks has been bookmarked or not.
//     * A value of true indicates that the decks has been bookmarked, while a value of false indicates that it has not.
//     *
//     * This field is part of the CardDto class, which represents a Data Transfer Object (DTO) for a decks.
//     * The CardDto class encapsulates all the necessary data fields for a decks and provides getters and setters for each field.
//     *
//     * Example usage:
//     *
//     * CardDto decks = new CardDto();
//     * decks.setBookmark(true);
//     * boolean bookmarked = decks.isBookmark();
//     *
//     * In this example, a new CardDto object is created and assigned to the decks variable.
//     * The setBookmark() method is then used to set the bookmark status of the decks to true.
//     * Finally, the isBookmark() method is used to retrieve the bookmark status of the decks, which is assigned to the bookmarked variable.
//     */
//    boolean bookmark;

    Integer deckId;

    String deckName;

    String stackName;
}
