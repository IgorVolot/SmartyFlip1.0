/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import smartyflip.modules.service.ModuleService;
import smartyflip.modules.service.TagService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/modules/{moduleId}/tags")
public class TagController {

    final ModuleService moduleService;

    final TagService tagService;

    @PatchMapping("/{tagName}")
    public Iterable<String> addTagToModule(@PathVariable Long moduleId, @PathVariable String tagName) {
        return tagService.addTagToModule(moduleId, tagName);
    }


    @DeleteMapping("/{tag}")
    public boolean deleteTag(@PathVariable Long moduleId, @PathVariable String tag) {
        return tagService.deleteTag(moduleId, tag);
    }

    @GetMapping
    public Iterable<String> findTagsByModuleId(@PathVariable Long moduleId) {
        return tagService.findTagsByModuleId(moduleId);
    }
}
