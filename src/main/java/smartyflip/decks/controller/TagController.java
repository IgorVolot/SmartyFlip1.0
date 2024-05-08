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
import smartyflip.decks.service.DeckService;
import smartyflip.decks.service.TagService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/decks/{deckId}/tags")
public class TagController {

    final DeckService deckService;

    final TagService tagService;

    @PatchMapping("/{tag}")
    public boolean addTag(@PathVariable Integer deckId, @PathVariable String tag) {
        return tagService.addTag(deckId, tag);
    }


    @GetMapping("/{tag}")
    public boolean deleteTag(@PathVariable Integer deckId, @PathVariable String tag) {
        return tagService.deleteTag(deckId, tag);
    }

}
