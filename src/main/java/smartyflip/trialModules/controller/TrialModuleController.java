///*
// *
// *  *   *******************************************************************
// *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
// *  *   *******************************************************************
// *
// */
//
//package smartyflip.trialModules.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import smartyflip.modules.dto.DatePeriodDto;
//import smartyflip.modules.service.TagService;
//import smartyflip.stacks.service.StackService;
//import smartyflip.trialModules.dto.NewTrialModuleDto;
//import smartyflip.trialModules.dto.TrialModuleDto;
//import smartyflip.trialModules.service.TrialModuleService;
//
//import java.util.Set;
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/trialModules/trial")
//public class TrialModuleController {
//
//    final TrialModuleService trialModuleService;
//
//    final StackService stackService;
//
//    final TagService tagService;
//
//    @PostMapping
//    public TrialModuleDto addTrialModule(@RequestBody NewTrialModuleDto newTrialModuleDto) {
//        return trialModuleService.addTrialModule(newTrialModuleDto);
//    }
//
//    @GetMapping("/{trialModuleId}")
//    public TrialModuleDto findTrialModuleById(@PathVariable Long trialModuleId) {
//        return trialModuleService.findTrialModuleById(trialModuleId);
//    }
//
//    @PutMapping("/{trialModuleId}")
//    public TrialModuleDto editTrialModule(@PathVariable Long trialModuleId, @RequestBody NewTrialModuleDto newTrialModuleDto) {
//        return trialModuleService.editTrialModule(trialModuleId, newTrialModuleDto);
//    }
//
//    @DeleteMapping("/{trialModuleId}")
//    public boolean deleteTrialModule(@PathVariable Long trialModuleId) {
//        return trialModuleService.deleteTrialModule(trialModuleId);
//    }
//
//    @GetMapping("/author/{user}")
//    public Iterable<TrialModuleDto> findTrialModulesByAuthor(@PathVariable String user) {
//        return trialModuleService.findTrialModulesByAuthor(user);
//    }
//
//    @GetMapping("/period")
//    public Iterable<TrialModuleDto> findTrialModulesByPeriod(@RequestBody DatePeriodDto datePeriodDto) {
//        return trialModuleService.findTrialModulesByPeriod(datePeriodDto);
//    }
//
//    @GetMapping("/name/{trialModuleName}")
//    public Iterable<TrialModuleDto> findTrialModulesByName(@PathVariable String trialModuleName) {
//        return trialModuleService.findTrialModulesByName(trialModuleName);
//    }
//
//    @GetMapping("/tags")
//    public Iterable<TrialModuleDto> finAllTrialModulesByTags(@RequestBody Set<String> tags){
//        return trialModuleService.finAllTrialModulesByTags(tags);
//    }
//
//    @GetMapping("/stacks/{stackName}")
//    public Iterable<TrialModuleDto> findTrialModulesByStack(@PathVariable String stackName) {
//        return trialModuleService.findTrialModulesByStack(stackName);
//    }
//
//    @GetMapping
//    public Iterable<TrialModuleDto> findAllTrialModules() {
//        return trialModuleService.findAllTrialModules();
//    }
//}
