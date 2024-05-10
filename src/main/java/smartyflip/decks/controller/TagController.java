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
import smartyflip.decks.dto.DeckDto;
import smartyflip.decks.model.Tag;
import smartyflip.decks.service.DeckService;
import smartyflip.decks.service.TagService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/decks/{deckId}/tags")
public class TagController {

    final DeckService deckService;

    final TagService tagService;

    @PatchMapping("/{tagName}")
    public Iterable<String> addTagToDeck(@PathVariable Integer deckId, @PathVariable String tagName) {
        return tagService.addTagToDeck(deckId, tagName);
    }


    @DeleteMapping("/{tag}")
    public boolean deleteTag(@PathVariable Integer deckId, @PathVariable String tag) {
        return tagService.deleteTag(deckId, tag);
    }

    @GetMapping
    public Iterable<String> findTagsByDeckId(@PathVariable Integer deckId) {
        return tagService.findTagsByDeckId(deckId);
    }
}
