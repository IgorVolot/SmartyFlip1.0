/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.trialModules.service;

import smartyflip.modules.dto.DatePeriodDto;
import smartyflip.trialModules.dto.NewTrialModuleDto;
import smartyflip.trialModules.dto.TrialModuleDto;

import java.util.Set;

public interface TrialModuleService {
    TrialModuleDto addTrialModule(NewTrialModuleDto newTrialModuleDto);

    TrialModuleDto findTrialModuleById(Long trialModuleId);

    TrialModuleDto editTrialModule(Long trialModuleId, NewTrialModuleDto newTrialModuleDto);

    boolean deleteTrialModule(Long trialModuleId);

    Iterable<TrialModuleDto> findTrialModulesByName(String trialModuleName);

    Iterable<TrialModuleDto> findTrialModulesByAuthor(String authorName);

    Iterable<TrialModuleDto> findTrialModulesByPeriod(DatePeriodDto datePeriodDto);

    Iterable<TrialModuleDto> finAllTrialModulesByTags(Set<String> tags);

    Iterable<TrialModuleDto> findTrialModulesByStack(String stackName);

    Iterable<TrialModuleDto> findAllTrialModules();
}
