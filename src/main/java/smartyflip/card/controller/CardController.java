/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import smartyflip.accounting.dto.UserResponseDto;
import smartyflip.card.dto.CardDto;
import smartyflip.card.dto.NewCardDto;
import smartyflip.card.service.CardService;
import smartyflip.utils.PagedDataResponseDto;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController {

    final CardService cardService;

    @PostMapping("")
    public CardDto addCard(@RequestBody NewCardDto newCardDto) {
        return cardService.addCard(newCardDto);
    }

    @GetMapping("/{id}")
    public CardDto findCardById(@PathVariable String id) {
        return cardService.findCardById(id);
    }

    @PutMapping("/{id}")
    public CardDto editCard(@PathVariable String id, @RequestBody NewCardDto newCardDto) {
        return cardService.editCard(id, newCardDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCard(@PathVariable String id) {
        return cardService.deleteCard(id);
    }

    @PutMapping("/{id}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLike(@PathVariable String id) {
        cardService.addLike(id);
    }

    @PutMapping("/{id}/dislike")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addDislike(@PathVariable String id) {
        cardService.addDislike(id);
    }

    @GetMapping("/modules/{moduleId}")
    public Iterable<CardDto> findCardsByModuleId(@PathVariable Long moduleId) {
        return cardService.findCardsByModuleId(moduleId);
    }

    @GetMapping
    public PagedDataResponseDto<CardDto> findAllCards(
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page
    ) {
        return cardService.findAllCards(PageRequest.of(page, size));
    }

}
