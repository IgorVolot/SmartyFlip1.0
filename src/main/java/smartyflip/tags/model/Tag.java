/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.tags.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import smartyflip.decks.model.Deck;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    private String tag;

    public Tag(String tag) {
        this.tag = tag.toLowerCase();
    }

    @ManyToMany(mappedBy = "tags")
    private List<Deck> decks = new ArrayList<>();

}
