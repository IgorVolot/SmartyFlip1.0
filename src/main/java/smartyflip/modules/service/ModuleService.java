/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service;

import smartyflip.modules.dto.DatePeriodDto;
import smartyflip.modules.dto.ModuleDto;
import smartyflip.modules.dto.NewModuleDto;

public interface ModuleService {
    ModuleDto addModule(NewModuleDto newModuleDto, String subscriptionType);

    ModuleDto findModuleById(Long moduleId);

    // FIXME
//    ModuleDto editModule(NewModuleDto newModuleDto, Long moduleId,String subscriptionType);

    boolean deleteModule(Long moduleId);

    Iterable<ModuleDto> findModulesByAuthor(String authorName);

    Iterable<ModuleDto> findModulesByPeriod(DatePeriodDto datePeriodDto);

    Iterable<ModuleDto> findModulesByName(String moduleName);

    Iterable<ModuleDto> findAllModules();

    Iterable<ModuleDto> findModulesByStack(String stackName);

    // FIXME
//    Iterable<ModuleDto> finAllModulesByTags(Set<String> tags);

    int cardsAmountByModuleId(Long moduleId);
}

