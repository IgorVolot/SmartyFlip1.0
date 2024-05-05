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
import smartyflip.cards.dto.DatePeriodDto;
import smartyflip.modules.dto.ModuleDto;
import smartyflip.modules.dto.NewModuleDto;
import smartyflip.modules.service.ModuleService;
import smartyflip.modules.service.ModuleService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/modules")
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping("")
    public ModuleDto addModule(@RequestBody NewModuleDto newModuleDto) {
        return moduleService.addModule(newModuleDto);
    }

    @GetMapping("/{moduleId}")
    public ModuleDto findModuleById(@PathVariable Integer moduleId) {
        return moduleService.findModuleById(moduleId);
    }

    @PutMapping("/{moduleId}")
    public ModuleDto editModule(@PathVariable Integer moduleId, @RequestBody NewModuleDto newModuleDto) {
        return moduleService.editModule(moduleId, newModuleDto);
    }

    @DeleteMapping("/{moduleId}")
    public ModuleDto deleteModule(@PathVariable Integer moduleId) {
        return moduleService.deleteModule(moduleId);
    }

    @GetMapping("/author/{user}")
    public Iterable<ModuleDto> findModulesByAuthor(@PathVariable String user) {
        return moduleService.findModulesByAuthor(user);
    }

    @PostMapping("/tags")
    public Iterable<ModuleDto> findModulesByTags(@RequestBody Set<String> tags) {
        return moduleService.findModulesByTags(tags);
    }

    @PostMapping("/period")
    public Iterable<ModuleDto> findModulesByPeriod(DatePeriodDto datePeriodDto) {
        return moduleService.findModulesByPeriod(datePeriodDto);
    }

    @GetMapping("/name/{moduleName}")
    public Iterable<ModuleDto> findModulesByName(@PathVariable String moduleName) {
        return moduleService.findModulesByName(moduleName);
    }

    @GetMapping("/modules/stacks/{stackName}")
    public Iterable<ModuleDto> findModulesByStack(String stackName) {
        return moduleService.findModulesByStack(stackName);
    }

    @GetMapping("/modules/{isPublic}")
    public Iterable<ModuleDto> findModulesByPublic(boolean isPublic) {
        return moduleService.findModulesByPublic(isPublic);
    }
}
