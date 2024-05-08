/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smartyflip.decks.model.Deck;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

//@Repository
public interface DeckRepository extends JpaRepository<Deck, Integer> {
    Stream<Deck> findAllByDateCreatedBetween(LocalDate dateFrom, LocalDate dateTo);

    Stream<Deck> findDecksByAuthorNameIgnoreCase(String authorName);

    Stream<Deck> findDeckByTag(String tag);

    Stream<Deck> findDecksByDeckNameIgnoreCase(String deckName);

    Stream<Deck> findAllByStackNameIgnoreCase(String stackName);
}