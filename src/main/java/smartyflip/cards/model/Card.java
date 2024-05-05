/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.cards.model;

import jakarta.persistence.*;
import lombok.*;
import smartyflip.decks.model.Deck;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "cardId")
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardId;

    @Setter
    private String question;

    @Setter
    private String answer;

    private LocalDateTime dateCreated;

    @Setter
    private String level;

//    /**
//     * This variable represents the number of likes a decks has received.
//     * It is an instance of the Integer class, which is a wrapper class for the primitive type int.
//     * The value of this variable can be null, indicating that the decks has not received any likes yet.
//     * To access or modify the value of this variable, use the appropriate getters and setters methods provided by the Card class.
//     *
//     * Example usage:
//     *
//     * Card decks = new Card();
//     * decks.setLikes(10); // Set the number of likes to 10
//     * int likes = decks.getLikes(); // Get the number of likes
//     *
//     * Note: The Card class is defined in the same package, so you can directly access and modify the likes variable without any additional imports.
//     */
//    @Setter
//    private Integer likes;
//
//    /**
//     * The bookmark variable represents whether a decks has been bookmarked or not.
//     *
//     * If the value of bookmark is true, it means that the decks has been bookmarked.
//     * If the value of bookmark is false, it means that the decks has not been bookmarked.
//     */
//    @Setter
//    private boolean bookmark;

//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer deckId;

    @Setter
    private String deckName;

    @Setter
    private String stackName;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;



    public Card(String question, String answer, String level, String deckName, String stackName) {
        this.question = question;
        this.answer = answer;
        this.level = level;
        this.deckName = deckName;
        this.stackName = stackName;
//        this.likes = 0;
//        this.bookmark = false;
    }

    public void setDateCreated(LocalDateTime now) {
        this.dateCreated = LocalDateTime.from(now);
    }


//    public void addLike() {
//        if ( likes == null ) {
//            likes = 0;
//        }
//        likes++;
//    }
}
