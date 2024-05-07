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
import smartyflip.tags.model.Tag;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "deckId")
@Entity
@Table(name = "deck")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer deckId;
    @Setter
    private String deckName;

    @Setter
    private String authorName;

    LocalDate dateCreated = LocalDate.now();

    @Setter
    private String stackName;

    Integer cardsAmount;

//    boolean bookmark;

    @ManyToMany
    @JoinTable(name = "deck_tags",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();


//    @ElementCollection
//    @Column(name = "tag")
//    @Setter
//    Set<String> tags = new HashSet<>();

//    @ManyToOne
//    @JoinColumn(name="stack_id")
//    private Stack stack;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> cards = new HashSet<>();

    public Deck(String deckName, String authorName, String stackName) {
        this.deckName = deckName;
        this.authorName = authorName;
        this.stackName = stackName;
    }

    @ManyToOne
    @JoinColumn(name = "stack_stack_name")
    Stack stack;

    public int cardsCount() {

        return cards.size();
    }

//    public boolean removeTag(String tag) {
//        return tags.remove(tag);
//    }
//
//    public boolean addTag(String tag) {
//        return tags.add(tag);
//    }
}
