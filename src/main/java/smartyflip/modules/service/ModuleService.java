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

import java.util.Set;

public interface ModuleService {
    ModuleDto addModule(NewModuleDto newModuleDto);

    ModuleDto findModuleById(Long moduleId);

    ModuleDto editModule(Long moduleId, NewModuleDto newModuleDto);

    boolean deleteModule(Long moduleId);

    Iterable<ModuleDto> findModulesByAuthor(String authorName);

    Iterable<ModuleDto> findModulesByPeriod(DatePeriodDto datePeriodDto);

    Iterable<ModuleDto> findModulesByName(String moduleName);

    Iterable<ModuleDto> findAllModules();

    Iterable<ModuleDto> findModulesByStack(String stackName);

    Iterable<ModuleDto> finAllModulesByTags(Set<String> tags);

    int cardsAmountByModuleId(Long moduleId);
}
