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
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Integer> {
    Stream<Deck> findAllByDateCreatedBetween(LocalDateTime dateFrom, LocalDateTime dateTo);

    Stream<Deck> findDecksByAuthorNameIgnoreCase(String authorName);

    @Query("select d from Deck d where :tags member of d.tags")
    Stream<Deck> findDecksByTagsIgnoreCase(@Param("tags") String tags);

    Stream<Deck> findDecksByDateCreatedBetween(LocalDateTime dateFrom, LocalDateTime dateTo);

    Stream<Deck> findDecksByDeckNameIgnoreCase(String deckName);

    Stream<Deck> findDecksByStackNameIgnoreCase(String stackName);

    @Query("select d from Deck d where d.isPublic = ?1")
    Stream<Deck> findDecksByPublic(boolean isPublic);
}
