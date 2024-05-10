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

    @GetMapping("/{stackName}")
    public StackDto findStackByName(@PathVariable String stackName) {
        return stackService.findStackByName(stackName);
    }

    @PutMapping("/{stackName}")
    public StackDto editStack(@PathVariable String stackName, @RequestBody StackDto stackDto) {
        return stackService.editStack(stackName, stackDto);
    }

    @DeleteMapping("/{stackName}")
    public boolean removeStack(@PathVariable String stackName) {
        return stackService.removeStack(stackName);
    }

    @GetMapping("")
    public Iterable<String> getAllStacks() {
        return stackService.getAllStacks();
    }
}
