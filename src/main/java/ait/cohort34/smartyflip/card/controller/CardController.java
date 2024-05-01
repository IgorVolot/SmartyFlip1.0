/*
 *   *******************************************************************
 *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *   *******************************************************************
 */

package ait.cohort34.smartyflip.card.controller;

import ait.cohort34.smartyflip.card.dto.CardDto;
import ait.cohort34.smartyflip.card.dto.NewCardDto;
import ait.cohort34.smartyflip.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a controller that handles HTTP requests related to the Card entity.
 * It provides endpoints for adding, retrieving, editing, and deleting cards.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CardController {

    /**
     * This variable represents an instance of the CardService interface.
     * It is used to perform operations related to the Card entity, such as adding, retrieving, editing, and deleting cards.
     *
     * Example usage:
     *
     * // Adding a new card
     * NewCardDto newCardDto = new NewCardDto();
     * newCardDto.setQuestion("What is the capital of France?");
     * newCardDto.setAnswer("Paris");
     * newCardDto.setLevel("Beginner");
     * newCardDto.setModuleId(1);
     * newCardDto.setStackName("Geography");
     * CardDto createdCard = cardService.addCard(newCardDto);
     *
     * // Retrieving a card by its ID
     * Integer cardId = 1;
     * CardDto retrievedCard = cardService.findCardById(cardId);
     *
     * // Editing a card
     * Integer cardId = 1;
     * NewCardDto updatedCardDto = new NewCardDto();
     * updatedCardDto.setQuestion("What is the capital of Germany?");
     * updatedCardDto.setAnswer("Berlin");
     * updatedCardDto.setLevel("Intermediate");
     * CardDto updatedCard = cardService.editCard(cardId, updatedCardDto);
     *
     * // Adding a like to a card
     * Integer cardId = 1;
     * cardService.addLike(cardId);
     *
     * // Editing the bookmark status of a card
     * Integer cardId = 1;
     * boolean bookmark = true;
     * CardDto cardDto = new CardDto();
     * CardDto updatedCard = cardService.editBookmark(cardId, bookmark, cardDto);
     *
     * // Deleting a card
     * Integer cardId = 1;
     * CardDto deletedCard = cardService.deleteCard(cardId);
     */
    private final CardService cardService;

    /**
     * Adds a new card to the system.
     *
     * @param newCardDto the NewCardDto object containing the data for the new card
     * @return the created CardDto object representing the added card
     */
    @PostMapping("/card")
    public CardDto addCard(@RequestBody NewCardDto newCardDto) {
        return cardService.addCard(newCardDto);
    }

    /**
     * Retrieves a card by its ID.
     *
     * @param cardId the ID of the card to retrieve
     * @return the CardDto object representing the retrieved card
     */
    @GetMapping("/card/{cardId}")
    public CardDto findCardById(@PathVariable Integer cardId) {
        return cardService.findCardById(cardId);
    }

    /**
     * Edits a card in the system.
     *
     * @param cardId the ID of the card to be edited
     * @param newCardDto the NewCardDto object containing the updated data for the card
     * @return the updated CardDto object representing the edited card
     */
    @PutMapping("/card/{cardId}")
    public CardDto editCard(@PathVariable Integer cardId, @RequestBody NewCardDto newCardDto) {
        return cardService.editCard(cardId, newCardDto);
    }

    /**
     * Adds a like to the specified card.
     *
     * @param cardId the ID of the card to add a like to
     */
    @PutMapping("/card/{cardId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLike(@PathVariable Integer cardId) {
        cardService.addLike(cardId);
    }

    /**
     * Edits the bookmark status of a card.
     *
     * @param cardId    the ID of the card to be edited
     * @param bookmark  the new bookmark status for the card
     * @param cardDto   the CardDto object containing the updated data for the card
     * @return the updated CardDto object representing the edited card
     */
    @PutMapping("/card/{cardId}/bookmark/{bookmark}")
    public CardDto editBookmark(@PathVariable Integer cardId, @PathVariable boolean bookmark, @RequestBody CardDto cardDto) {
        return cardService.editBookmark(cardId, bookmark, cardDto);
    }

    /**
     * Deletes a card from the system.
     *
     * @param cardId the ID of the card to be deleted
     * @return the CardDto object representing the deleted card
     */
    @DeleteMapping("/card/{cardId}")
    public CardDto deleteCard(@PathVariable Integer cardId) {
        return cardService.deleteCard(cardId);
    }
}
