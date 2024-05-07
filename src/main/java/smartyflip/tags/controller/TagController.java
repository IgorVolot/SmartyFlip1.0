/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.tags.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import smartyflip.decks.service.DeckService;
import smartyflip.tags.dto.TagDto;
import smartyflip.tags.model.Tag;
import smartyflip.tags.service.TagService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/decks/tags")
public class TagController {

    private final TagService tagService;

    private final DeckService deckService;

    @PatchMapping("")
    public TagDto addTag(@PathVariable Integer deckId, @RequestBody TagDto tagDto) {
        return tagService.addTag(deckId, tagDto);
    }

    @GetMapping("/{tag}")
    public boolean deleteTag(@PathVariable Integer deckId, @PathVariable String tag) {
        return tagService.deleteTag(deckId, tag);
    }

    @GetMapping("")
    public Set<Tag> getAllTags() {
        return tagService.getAllTags();
    }
}
