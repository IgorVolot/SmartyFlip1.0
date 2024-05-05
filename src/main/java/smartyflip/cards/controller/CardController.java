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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a controller that handles HTTP requests related to the Card entity.
 * It provides endpoints for adding, retrieving, editing, and deleting cards.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService cardService;

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
//     * Adds a like to the specified modules.
//     *
//     * @param cardId the ID of the modules to add a like to
//     */
//    @PutMapping("/{cardId}/like")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void addLike(@PathVariable Integer cardId) {
//        cardService.addLike(cardId);
//    }
//
//    /**
//     * Edits the bookmark status of a modules.
//     *
//     * @param cardId    the ID of the modules to be edited
//     * @param bookmark  the new bookmark status for the modules
//     * @param cardDto   the CardDto object containing the updated data for the modules
//     * @return the updated CardDto object representing the edited modules
//     */
//    @PutMapping("/{cardId}/bookmark/{bookmark}")
//    public CardDto editBookmark(@PathVariable Integer cardId, @PathVariable boolean bookmark, @RequestBody CardDto cardDto) {
//        return cardService.editBookmark(cardId, bookmark, cardDto);
//    }

    @DeleteMapping("/{cardId}")
    public CardDto deleteCard(@PathVariable Integer cardId) {
        return cardService.deleteCard(cardId);
    }

    @GetMapping("/modules/{moduleId}")
    public Iterable<CardDto> findCardsByModuleId(@PathVariable Integer moduleId) {
        return cardService.findCardsByModuleId(moduleId);
    }

}
