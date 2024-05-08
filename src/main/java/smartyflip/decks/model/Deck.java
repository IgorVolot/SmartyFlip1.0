/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */
package smartyflip.decks.model;

import jakarta.persistence.*;
import lombok.*;
import smartyflip.cards.model.Card;
import smartyflip.stacks.model.Stack;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "deckId")
@Entity
@Table(name = "deck")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "deckId")
    Integer deckId;

    @Column(name = "deckName")
    String deckName;
    @Column(name = "userName")
    String authorName;

    @Column(name = "dateCreated")
    LocalDate dateCreated;

    @Column(name = "stackName")
    String stackName;

    @Column(name = "totalCards")
    int cardsAmount;

    @ManyToMany(mappedBy = "decks", cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "deck_tags",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "tags"))
    Set<Tag> tags;


    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Card> cards;

    public Deck(String deckName, String authorName, String stackName) {
        this.deckName = deckName;
        this.authorName = authorName;
        this.stackName = stackName;
    }

    @ManyToOne
    @JoinColumn(name = "stack_stack_name")
    Stack stack;

    public int cardsAmount() {
        cardsAmount = cards.size();
        return cardsAmount;
    }

    public void addCard(Card card) {
        if(card==null){
            throw new IllegalArgumentException("Card cannot be null");
        }
        this.cards.add(card);
        card.setDeck(this);
    }

    public void removeDeck(Card card) {
        this.cards.remove(card);
        card.setDeck(null);
    }
}
