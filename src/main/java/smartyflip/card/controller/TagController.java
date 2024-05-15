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
import org.springframework.web.bind.annotation.*;
import smartyflip.card.dto.CardDto;
import smartyflip.card.service.CardService;
import smartyflip.card.service.TagService;
import smartyflip.utils.PagedDataResponseDto;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cards")
public class TagController {

    final CardService cardService;

    final TagService tagService;

    @PatchMapping("/{cardId}/tags")
    public Collection<String> addTagsToCard(@PathVariable String cardId, @RequestBody List<String> tags) {
        return tagService.addTagsToCard(cardId, tags);
    }


    @DeleteMapping("/{cardId}/tags/{tag}")
    public boolean deleteTag(@PathVariable String cardId, @PathVariable String tag) {
        return tagService.deleteTag(cardId, tag);
    }

    @PostMapping("/{cardId}/tags")
    public Collection<String> findTagsByCardId(@PathVariable String cardId) {
        return tagService.findTagsByCardId(cardId);
    }

    @GetMapping("/tags{tag}")
    public PagedDataResponseDto<CardDto> findCardsByTagIds(
            String tag,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page) {
        return tagService.findCardsByTagIds(tag, PageRequest.of(page, size));
    }

//    @DeleteMapping("/tags/{id}")
//    public boolean deleteTag(@PathVariable String id) {
//        return tagService.deleteTag(id);
//    }
}
