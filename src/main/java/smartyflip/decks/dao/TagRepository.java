/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import smartyflip.decks.model.Deck;
import smartyflip.decks.model.Tag;

import java.util.stream.Stream;

public interface TagRepository extends JpaRepository<Tag, Integer> {

}
