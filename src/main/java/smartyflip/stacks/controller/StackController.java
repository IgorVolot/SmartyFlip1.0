/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import smartyflip.stacks.dto.StackDto;
import smartyflip.stacks.service.StackService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stacks")
public class StackController {
    final StackService stackService;

    @PostMapping("")
    public StackDto addStack(@RequestBody StackDto stackDto) {
        return stackService.addStack(stackDto);
    }


    @GetMapping("/{stackId}")
    public StackDto findStackById(@PathVariable("stackId") Long stackId) {
        return stackService.findStackById(stackId);
    }

    @PutMapping("/{stackId}")
    public StackDto editStack(@PathVariable String stackId, @RequestBody StackDto stackDto) {
        return stackService.editStack(Long.parseLong(stackId), stackDto);
    }

    @DeleteMapping("/{stackId}")
    public StackDto deleteStack(@PathVariable Long stackId) {
        return stackService.deleteStack(stackId);
    }

    @GetMapping("")
    public Iterable<String> getAllStacks() {
        return stackService.getAllStacks();
    }

    @GetMapping("/{stackName}/modulesAmount")
    public int getModulesAmount(@PathVariable String stackName) {
        return stackService.getModulesAmount(stackName);
    }
}
