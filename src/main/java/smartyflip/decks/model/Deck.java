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
    @GeneratedValue(strategy=GenerationType.AUTO)
    Integer deckId;

    @Column(name = "deckName", nullable = false)
    String deckName;

    String authorName;

    LocalDate dateCreated = LocalDate.now();

    String stackName;

    int cardsAmount;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "deck_tag",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();


    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Card> cards;

    public Deck(Integer deckId) {
        this.deckId = deckId;
    }

    @ManyToOne
    @JoinColumn(name = "stack_stack_id")
    Stack stack;


    public int cardsAmount() {
        cardsAmount = cards.size();
        return cardsAmount;
    }
}
