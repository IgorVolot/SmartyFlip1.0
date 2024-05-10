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

    @DeleteMapping("/{cardId}")
    public CardDto deleteCard(@PathVariable Integer cardId) {
        return cardService.deleteCard(cardId);
    }

    @GetMapping("/decks/{deckId}")
    public Iterable<CardDto> findCardsByDeckId(@PathVariable Integer deckId) {
        return cardService.findCardsByDeckId(deckId);
    }

}
