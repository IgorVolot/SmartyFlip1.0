/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.service;

import smartyflip.decks.dto.DatePeriodDto;
import smartyflip.decks.dto.DeckDto;
import smartyflip.decks.dto.NewDeckDto;

import java.util.Set;

public interface DeckService {
    DeckDto addDeck(NewDeckDto newDeckDto);

    DeckDto findDeckById(Integer deckId);

    DeckDto editDeck(Integer deckId, NewDeckDto newDeckDto);

    boolean deleteDeck(Integer deckId);

    Iterable<DeckDto> findDecksByAuthor(String authorName);

    Iterable<DeckDto> findDecksByPeriod(DatePeriodDto datePeriodDto);

    Iterable<DeckDto> findDecksByName(String deckName);

    int cardsCount(Integer deckId);

    Iterable<DeckDto> findAllDecks();

    Iterable<DeckDto> findDecksByStack(String stackName);
}
