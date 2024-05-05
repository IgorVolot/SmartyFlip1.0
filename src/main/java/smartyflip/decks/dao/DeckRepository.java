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

import java.time.LocalDate;
import java.util.stream.Stream;

public interface DeckRepository extends JpaRepository<Deck, Integer> {
    Stream<Deck> findAllByDateCreatedBetween(LocalDate dateFrom, LocalDate dateTo);
}
