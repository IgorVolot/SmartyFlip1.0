/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */
package smartyflip.modules.model;

import jakarta.persistence.*;
import lombok.*;
import smartyflip.card.model.Card;
import smartyflip.stacks.model.Stack;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "moduleId")
@Entity
@Table(name = "module")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long moduleId;

    @Column(name = "moduleName", nullable = false)
    String moduleName;

    String authorName;

    LocalDate dateCreated = LocalDate.now();

    String stackName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stack_id")
    Stack stack;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "module_tag",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Card> cards;

    // Utility method to compute the amount of cards
    public int getCardsAmount() {
        return cards.size();
    }

}
