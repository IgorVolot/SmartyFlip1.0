/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import smartyflip.decks.dto.DatePeriodDto;
import smartyflip.decks.dto.DeckDto;
import smartyflip.decks.dto.NewDeckDto;
import smartyflip.decks.service.DeckService;
import smartyflip.decks.service.TagService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/decks")
public class DeckController {

    final DeckService deckService;

    final TagService tagService;

    @PostMapping("")
    public DeckDto addDeck(@RequestBody NewDeckDto newDeckDto) {
        return deckService.addDeck(newDeckDto);
    }

    @GetMapping("/{deckId}")
    public DeckDto findDeckById(@PathVariable Integer deckId) {
        return deckService.findDeckById(deckId);
    }

    @PutMapping("/{deckId}")
    public DeckDto editDeck(@PathVariable Integer deckId, @RequestBody NewDeckDto newDeckDto) {
        return deckService.editDeck(deckId, newDeckDto);
    }

    @DeleteMapping("/{deckId}")
    public boolean deleteDeck(@PathVariable Integer deckId) {
        return deckService.deleteDeck(deckId);
    }

    @GetMapping("/author/{user}")
    public Iterable<DeckDto> findDecksByAuthor(@PathVariable String user) {
        return deckService.findDecksByAuthor(user);
    }

    @PostMapping("/period")
    public Iterable<DeckDto> findDecksByPeriod(@RequestBody DatePeriodDto datePeriodDto) {
        return deckService.findDecksByPeriod(datePeriodDto);
    }

    @GetMapping("/name/{deckName}")
    public Iterable<DeckDto> findDecksByName(@PathVariable String deckName) {
        return deckService.findDecksByName(deckName);
    }

    @GetMapping("/stacks/{stackName}")
    public Iterable<DeckDto> findDecksByStack(@PathVariable String stackName) {
        return deckService.findDecksByStack(stackName);
    }

    @GetMapping("")
    public Iterable<DeckDto> findAllDecks() {
        return deckService.findAllDecks();
    }

    @GetMapping("/{deckId}/cards/amount")
    public int cardsCountByDeckId(@PathVariable Integer deckId) {
        return deckService.cardsAmountByDeckId(deckId);
    }
}
