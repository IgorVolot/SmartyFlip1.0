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
import smartyflip.modules.dto.DatePeriodDto;
import smartyflip.modules.dto.ModuleDto;
import smartyflip.modules.dto.NewModuleDto;
import smartyflip.modules.service.ModuleService;
import smartyflip.modules.service.TagService;
import smartyflip.stacks.service.StackService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/modules")
public class ModuleController {

    final ModuleService moduleService;

    final StackService stackService;

    final TagService tagService;

    @PostMapping
    public ModuleDto addModule(@RequestBody NewModuleDto newModuleDto) {
        return moduleService.addModule(newModuleDto);
    }

    @GetMapping("/{moduleId}")
    public ModuleDto findModuleById(@PathVariable Long moduleId) {
        return moduleService.findModuleById(moduleId);
    }

    @PutMapping("/{moduleId}")
    public ModuleDto editModule(@PathVariable Long moduleId, @RequestBody NewModuleDto newModuleDto) {
        return moduleService.editModule(moduleId, newModuleDto);
    }

    @DeleteMapping("/{moduleId}")
    public boolean deleteModule(@PathVariable Long moduleId) {
        return moduleService.deleteModule(moduleId);
    }

    @GetMapping("/author/{user}")
    public Iterable<ModuleDto> findModulesByAuthor(@PathVariable String user) {
        return moduleService.findModulesByAuthor(user);
    }

    @PostMapping("/period")
    public Iterable<ModuleDto> findModulesByPeriod(@RequestBody DatePeriodDto datePeriodDto) {
        return moduleService.findModulesByPeriod(datePeriodDto);
    }

    @GetMapping("/name/{moduleName}")
    public Iterable<ModuleDto> findModulesByName(@PathVariable String moduleName) {
        return moduleService.findModulesByName(moduleName);
    }

    @GetMapping("/tags")
    public Iterable<ModuleDto> finAllModulesByTags(@RequestBody Set<String> tags){
        return moduleService.finAllModulesByTags(tags);
    }

    @GetMapping("/stacks/{stackName}")
    public Iterable<ModuleDto> findModulesByStack(@PathVariable String stackName) {
        return moduleService.findModulesByStack(stackName);
    }

    @GetMapping
    public Iterable<ModuleDto> findAllModules() {
        return moduleService.findAllModules();
    }

    @GetMapping("/{moduleId}/cards/amount")
    public int cardsCountByModuleId(@PathVariable Long moduleId) {
        return moduleService.cardsAmountByModuleId(moduleId);
    }
}
