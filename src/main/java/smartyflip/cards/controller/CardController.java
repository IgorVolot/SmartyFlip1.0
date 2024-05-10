/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.cards.controller;

import smartyflip.cards.dto.CardDto;
import smartyflip.cards.model.Card;
import smartyflip.cards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController {

    final CardService cardService;

    @PostMapping("")
    public Card addCard(@RequestBody CardDto cardDto) {
        return cardService.addCard(cardDto);
    }

    @GetMapping("/{cardId}")
    public CardDto findCardById(@PathVariable Integer cardId) {
        return cardService.findCardById(cardId);
    }

    @PutMapping("/{cardId}")
    public CardDto editCard(@PathVariable Integer cardId, @RequestBody CardDto cardDto) {
        return cardService.editCard(cardId, cardDto);
    }


    @DeleteMapping("/{cardId}")
    public CardDto deleteCard(@PathVariable Integer cardId) {
        return cardService.deleteCard(cardId);
    }

    @GetMapping("/modules/{deckId}")
    public Iterable<CardDto> findCardsByDeckId(@PathVariable Integer deckId) {
        return cardService.findCardsByDeckId(deckId);
    }

}
