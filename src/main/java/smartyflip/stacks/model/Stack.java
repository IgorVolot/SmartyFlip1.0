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

    @Column
    String stackName;

    int decksAmount;

    @OneToMany(mappedBy = "stack", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Deck> decks;

    public Stack(String stackName, int decksAmount) {
        this.stackName = stackName;
        this.decksAmount = decksAmount;
    }

    public int decksAmount() {
        return decks.size();
    }
}