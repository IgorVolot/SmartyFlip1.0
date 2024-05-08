/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @Column(name = "tag")
    private String tag;

    @ManyToMany(mappedBy = "decks")
    Set<Deck> decks;

    public Tag(String tag) {
        this.tag = tag.toLowerCase();
    }
}
