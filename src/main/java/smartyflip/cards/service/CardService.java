/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.cards.service;

import smartyflip.cards.dto.CardDto;
import smartyflip.cards.dto.NewCardDto;

public interface CardService {

    CardDto addCard(NewCardDto newCardDto);

    CardDto findCardById(Integer cardId);

//    /**
//     * Adds a like to the specified decks.
//     *
//     * @param cardId the ID of the decks to add a like to
//     */
//    void addLike(Integer cardId);

    CardDto editCard(Integer cardId, NewCardDto newCardDto);

//    /**
//     * Edits the bookmark status of a decks.
//     *
//     * @param cardId    the ID of the decks to be edited
//     * @param bookmark  the new bookmark status for the decks
//     * @param cardDto   the CardDto object containing the updated data for the decks
//     * @return the updated CardDto object representing the edited decks
//     */
//    CardDto editBookmark(Integer cardId, boolean bookmark, CardDto cardDto);

    CardDto deleteCard(Integer cardId);

    Iterable<CardDto> findCardsByDeckId(Integer deckId);
}
