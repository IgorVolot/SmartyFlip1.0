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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CardController {

    private final CardService cardService;

    @PostMapping("/card")
    public CardDto addCard(@RequestBody NewCardDto newCardDto) {
        return cardService.addCard(newCardDto);
    }

    @GetMapping("/card/{cardId}")
    public CardDto findCardById(@PathVariable Long cardId) {
        return cardService.findCardById(cardId);
    }

    @PutMapping("/card/{cardId}")
    public CardDto editCard(@PathVariable Long cardId, @RequestBody NewCardDto newCardDto) {
        return cardService.editCard(cardId, newCardDto);
    }

    @PutMapping("/card/{cardId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLike(@PathVariable Long cardId) {
        cardService.addLike(cardId);
    }

    @PutMapping("/card/{cardId}/bookmark/{bookmark}")
    public CardDto editBookmark(@PathVariable Long cardId, @PathVariable boolean bookmark, @RequestBody CardDto cardDto) {
        return cardService.editBookmark(cardId, bookmark, cardDto);
    }

    @DeleteMapping("/card/{cardId}")
    public CardDto deleteCard(@PathVariable Long cardId) {
        return cardService.deleteCard(cardId);
    }
}
