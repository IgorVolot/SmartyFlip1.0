/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.cards.controller;

import smartyflip.cards.dto.CardDto;
import smartyflip.cards.dto.NewCardDto;
import smartyflip.cards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a controller that handles HTTP requests related to the Card entity.
 * It provides endpoints for adding, retrieving, editing, and deleting cards.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController {

    final CardService cardService;

    @PostMapping("")
    public CardDto addCard(@RequestBody NewCardDto newCardDto) {
        return cardService.addCard(newCardDto);
    }

    @GetMapping("/{cardId}")
    public CardDto findCardById(@PathVariable Integer cardId) {
        return cardService.findCardById(cardId);
    }

    @PutMapping("/{cardId}")
    public CardDto editCard(@PathVariable Integer cardId, @RequestBody NewCardDto newCardDto) {
        return cardService.editCard(cardId, newCardDto);
    }

//    /**
//     * Adds a like to the specified decks.
//     *
//     * @param cardId the ID of the decks to add a like to
//     */
//    @PutMapping("/{cardId}/like")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void addLike(@PathVariable Integer cardId) {
//        cardService.addLike(cardId);
//    }
//
//    /**
//     * Edits the bookmark status of a decks.
//     *
//     * @param cardId    the ID of the decks to be edited
//     * @param bookmark  the new bookmark status for the decks
//     * @param cardDto   the CardDto object containing the updated data for the decks
//     * @return the updated CardDto object representing the edited decks
//     */
//    @PutMapping("/{cardId}/bookmark/{bookmark}")
//    public CardDto editBookmark(@PathVariable Integer cardId, @PathVariable boolean bookmark, @RequestBody CardDto cardDto) {
//        return cardService.editBookmark(cardId, bookmark, cardDto);
//    }

    @DeleteMapping("/{cardId}")
    public CardDto deleteCard(@PathVariable Integer cardId) {
        return cardService.deleteCard(cardId);
    }

    @GetMapping("/modules/{deckId}")
    public Iterable<CardDto> findCardsByDeckId(@PathVariable Integer deckId) {
        return cardService.findCardsByDeckId(deckId);
    }

}
