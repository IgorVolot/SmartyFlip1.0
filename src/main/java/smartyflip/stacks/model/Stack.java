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
    private Integer stackId;

    @Column(name = "stack_name")
    private String stackName;

    private int decksCount;

    @OneToMany(mappedBy="stack", cascade = CascadeType.ALL, orphanRemoval=true)
    Set<Deck> decks;

    public Stack(String stackName, int decksCount) {
        this.stackName = stackName;
        this.decksCount = decksCount;
    }

    public int decksCount() {
        return decks.size();
    }
}