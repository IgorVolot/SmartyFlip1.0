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
import smartyflip.cards.dto.DatePeriodDto;
import smartyflip.decks.dto.DeckDto;
import smartyflip.decks.dto.NewDeckDto;
import smartyflip.decks.service.DeckService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/decks")
public class DeckController {

    private final DeckService deckService;

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

    @PostMapping("/tags")
    public Iterable<DeckDto> findDecksByTags(@RequestBody Set<String> tags) {
        return deckService.findDecksByTags(tags);
    }

    @PostMapping("/period")
    public Iterable<DeckDto> findDecksByPeriod(DatePeriodDto datePeriodDto) {
        return deckService.findDecksByPeriod(datePeriodDto);
    }

    @GetMapping("/name/{deckName}")
    public Iterable<DeckDto> findDecksByName(@PathVariable String deckName) {
        return deckService.findDecksByName(deckName);
    }

    @GetMapping("/decks/stacks/{stackName}")
    public Iterable<DeckDto> findDecksByStack(String stackName) {
        return deckService.findDecksByStack(stackName);
    }

    @GetMapping("/decks/{isPublic}")
    public Iterable<DeckDto> findDecksByPublic(boolean isPublic) {
        return deckService.findDecksByPublic(isPublic);
    }
}
