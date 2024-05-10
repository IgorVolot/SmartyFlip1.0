/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.dao;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import smartyflip.decks.model.Deck;

import java.time.LocalDate;


public interface DeckRepository extends JpaRepository<Deck, Integer> {

    Deck findByDeckId(Integer deckId);

    Stream<Deck> findAllByDateCreatedBetween(LocalDate dateFrom, LocalDate dateTo);

    Stream<Deck> findDecksByAuthorNameIgnoreCase(String authorName);

    Stream<Deck> findDecksByDeckNameIgnoreCase(String deckName);

    Stream<Deck> findDecksByStackNameIgnoreCase(String stackName);

    @Query("select d from Deck d inner join d.tags t where lower(t.tagName) = lower(:tagName)")
    Stream<Deck> findAllByTags(Set<String> tagName);
}
