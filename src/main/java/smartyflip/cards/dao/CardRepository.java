/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.cards.dao;

import smartyflip.cards.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

/**
 * This interface represents a repository for managing Card objects.
 */
public interface CardRepository extends JpaRepository<Card, Integer> {

    Stream<Card> findAllCardsByDeckId(Integer deckId);

}
