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

/**
 * A service interface for managing cards.
 */
public interface CardService {

    /**
     * Adds a new modules to the system.
     *
     * @param newCardDto the NewCardDto object containing the data for the new modules
     * @return the created CardDto object representing the added modules
     */
    CardDto addCard(NewCardDto newCardDto);

    /**
     * Retrieves a modules by its ID.
     *
     * @param cardId the ID of the modules to retrieve
     * @return the CardDto object representing the retrieved modules
     */
    CardDto findCardById(Integer cardId);

//    /**
//     * Adds a like to the specified modules.
//     *
//     * @param cardId the ID of the modules to add a like to
//     */
//    void addLike(Integer cardId);

    /**
     * Edits a modules in the system.
     *
     * @param cardId      the ID of the modules to be edited
     * @param newCardDto  the NewCardDto object containing the updated data for the modules
     * @return the updated CardDto object representing the edited modules
     */
    CardDto editCard(Integer cardId, NewCardDto newCardDto);

//    /**
//     * Edits the bookmark status of a modules.
//     *
//     * @param cardId    the ID of the modules to be edited
//     * @param bookmark  the new bookmark status for the modules
//     * @param cardDto   the CardDto object containing the updated data for the modules
//     * @return the updated CardDto object representing the edited modules
//     */
//    CardDto editBookmark(Integer cardId, boolean bookmark, CardDto cardDto);

    /**
     * Deletes a modules from the system.
     *
     * @param cardId the ID of the modules to be deleted
     * @return the CardDto object representing the deleted modules
     */
    CardDto deleteCard(Integer cardId);

    /**
     * Retrieves cards by their associated module ID.
     * <p>
     * This method retrieves all the cards that belong to a specific module identified
     * by the provided module ID. The cards are returned as an Iterable collection of CardDto objects.
     *
     * @param moduleId the ID of the module for which to retrieve the cards
     * @return an Iterable collection of CardDto objects representing the retrieved cards
     */
    Iterable<CardDto> findCardsByModuleId(Integer moduleId);
}
