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
import smartyflip.stacks.model.Stack;

import java.util.stream.Stream;

public interface CardRepository extends JpaRepository<Card, Integer> {

    Stream<Card> findAllCardsByDeckDeckId(Integer deckId);

    Stream<Card> findCardsByDeckId(Integer deckId);
}
