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
import smartyflip.stacks.model.Stack;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "cardId")
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer cardId;

    String question;

    String answer;

    LocalDateTime dateCreated;

    String level;

    Integer deckId;

    String deckName;


    @ManyToOne
    @JoinColumn(name = "deck_deck_id")
    Deck deck;

//    @ManyToOne
//    @JoinColumn(name = "stack_stack_id")
//    Stack stack;


//    public Card(String question, String answer, String level) {
//        this.question = question;
//        this.answer = answer;
//        this.level = level;
//    }

    public void setDateCreated(LocalDateTime now) {
        this.dateCreated = LocalDateTime.from(now);
    }
}
