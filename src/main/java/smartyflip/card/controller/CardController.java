/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.controller;

import smartyflip.card.dto.CardDto;
import smartyflip.card.dto.NewCardDto;
import smartyflip.card.service.CardService;
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
    public CardDto findCardById(@PathVariable Long cardId) {
        return cardService.findCardById(cardId);
    }

    @PutMapping("/{cardId}")
    public CardDto editCard(@PathVariable Long cardId, @RequestBody NewCardDto newCardDto) {
        return cardService.editCard(cardId, newCardDto);
    }

    @DeleteMapping("/{cardId}")
    public CardDto deleteCard(@PathVariable Long cardId) {
        return cardService.deleteCard(cardId);
    }

    @GetMapping("/modules/{moduleId}")
    public Iterable<CardDto> findCardsByModuleId(@PathVariable Long moduleId) {
        return cardService.findCardsByModuleId(moduleId);
    }

}
