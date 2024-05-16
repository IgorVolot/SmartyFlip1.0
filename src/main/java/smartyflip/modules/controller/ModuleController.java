/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import smartyflip.modules.dto.DatePeriodDto;
import smartyflip.modules.dto.ModuleDto;
import smartyflip.modules.dto.NewModuleDto;
import smartyflip.modules.service.ModuleService;
import smartyflip.modules.service.TagService;
import smartyflip.stacks.service.StackService;
import smartyflip.utils.PagedDataResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/modules")
public class ModuleController {

    final ModuleService moduleService;

    final StackService stackService;

    final TagService tagService;

    @PostMapping("/{userType}")
    public ModuleDto addModule(@RequestBody NewModuleDto newModuleDto, @PathVariable String userType) {
        return moduleService.addModule(newModuleDto, userType);
    }

    @GetMapping("/{moduleId}")
    public ModuleDto findModuleById(@PathVariable Long moduleId) {
        return moduleService.findModuleById(moduleId);
    }

    @PutMapping("/{moduleId}")
    public ModuleDto editModule(@RequestBody NewModuleDto newModuleDto, @PathVariable Long moduleId) {
        return moduleService.editModule(newModuleDto, moduleId);
    }


    @DeleteMapping("/{moduleId}")
    public ModuleDto deleteModule(@PathVariable Long moduleId) {
        return moduleService.deleteModule(moduleId);
    }


    @GetMapping("/user/{userId}")
    public Iterable<ModuleDto> findModulesByUserId(@PathVariable Integer userId){
        return moduleService.findModulesByUserId(userId);
    }

    @PostMapping("/period")
    public Iterable<ModuleDto> findModulesByPeriod(@RequestBody DatePeriodDto datePeriodDto) {
        return moduleService.findModulesByPeriod(datePeriodDto);
    }

    @GetMapping("/name/{moduleName}")
    public Iterable<ModuleDto> findModulesByName(@PathVariable String moduleName) {
        return moduleService.findModulesByName(moduleName);
    }


//    @GetMapping("/tags")
//    public Iterable<ModuleDto> findAllModulesByTags(@RequestBody Iterable<String> tagNames) {
//        return moduleService.finAllModulesByTags(tagNames);
//    }

    @GetMapping("/stacks/{stackName}")
    public Iterable<ModuleDto> findModulesByStack(@PathVariable String stackName) {
        return moduleService.findModulesByStack(stackName);
    }

    @GetMapping
    public PagedDataResponseDto<ModuleDto> findAllModules(
            @RequestParam(required = false, defaultValue = "6", name = "size") Integer size,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page
    ) {
        return moduleService.findAllModules(PageRequest.of(page, size));
    }

    @GetMapping("/tags")
    public Iterable<String> findAllTags() {
        return tagService.findAllTags();
    }

    @GetMapping("/{moduleId}/cards/amount")
    public int cardsCountByModuleId(@PathVariable Long moduleId) {
        return moduleService.cardsAmountByModuleId(moduleId);
    }
}
