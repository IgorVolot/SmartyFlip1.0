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
    final CardService cardService;

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
        return cardService.editCard(newCardDto);
    }

    @PutMapping("/card/{cardId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLike(@PathVariable Long cardId) {
        cardService.addLike(cardId);
    }

    @PatchMapping("/card/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editBookmark(@PathVariable Long cardId, @PathVariable boolean bookmark) {
        cardService.editBookmark(cardId, bookmark);
    }

    @DeleteMapping("/card/{cardId}")
    public CardDto deleteCard(@PathVariable Long cardId) {
        return cardService.deleteCard(cardId);
    }
}
