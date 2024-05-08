/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.model;

import jakarta.persistence.*;
import lombok.*;
import smartyflip.decks.model.Deck;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "stackId")
@Entity
@Table(name = "stack")
public class Stack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer stackId;

    @Column(name = "stack_name")
    String stackName;

    int decksCount;

    @OneToMany(mappedBy="stack", cascade = CascadeType.ALL, orphanRemoval=true)
    Set<Deck> decks;


    public Stack(String stackName, int decksCount) {
        this.stackName = stackName;
        this.decksCount = decksCount;
    }

    public int decksCount() {
        return decks.size();
    }

    public void addDeck(Deck deck) {
        if(deck==null){
            throw new IllegalArgumentException("Deck cannot be null");
        }
        this.decks.add(deck);
        deck.setStack(this);
    }

    public void removeDeck(Deck deck) {
        this.decks.remove(deck);
        deck.setStack(null);
    }
}