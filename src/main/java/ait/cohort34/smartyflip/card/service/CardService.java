/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package ait.cohort34.smartyflip.card.service;

import ait.cohort34.smartyflip.card.dto.CardDto;
import ait.cohort34.smartyflip.card.dto.NewCardDto;

/**
 * A service interface for managing cards.
 */
public interface CardService {

    /**
     * Adds a new card to the system.
     *
     * @param newCardDto the NewCardDto object containing the data for the new card
     * @return the created CardDto object representing the added card
     */
    CardDto addCard(NewCardDto newCardDto);

    /**
     * Retrieves a card by its ID.
     *
     * @param cardId the ID of the card to retrieve
     * @return the CardDto object representing the retrieved card
     */
    CardDto findCardById(Integer cardId);

    /**
     * Adds a like to the specified card.
     *
     * @param cardId the ID of the card to add a like to
     */
    void addLike(Integer cardId);

    /**
     * Edits a card in the system.
     *
     * @param cardId      the ID of the card to be edited
     * @param newCardDto  the NewCardDto object containing the updated data for the card
     * @return the updated CardDto object representing the edited card
     */
    CardDto editCard(Integer cardId, NewCardDto newCardDto);

    /**
     * Edits the bookmark status of a card.
     *
     * @param cardId    the ID of the card to be edited
     * @param bookmark  the new bookmark status for the card
     * @param cardDto   the CardDto object containing the updated data for the card
     * @return the updated CardDto object representing the edited card
     */
    CardDto editBookmark(Integer cardId, boolean bookmark, CardDto cardDto);

    /**
     * Deletes a card from the system.
     *
     * @param cardId the ID of the card to be deleted
     * @return the CardDto object representing the deleted card
     */
    CardDto deleteCard(Integer cardId);
}