/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service;

import smartyflip.cards.dto.DatePeriodDto;
import smartyflip.modules.dto.ModuleDto;
import smartyflip.modules.dto.NewModuleDto;

import java.util.Set;

public interface ModuleService {
    ModuleDto addModule(NewModuleDto newmoduleDto);

    ModuleDto findModuleById(Integer moduleId);

    ModuleDto editModule(Integer moduleId, NewModuleDto newmoduleDto);

    ModuleDto deleteModule(Integer moduleId);

    Iterable<ModuleDto> findModulesByAuthor(String authorName);

    Iterable<ModuleDto> findModulesByTags(Set<String> tags);

    Iterable<ModuleDto> findModulesByPeriod(DatePeriodDto datePeriodDto);

    Iterable<ModuleDto> findModulesByName(String moduleName);

    void increaseCardsCount(Integer moduleId);

    Iterable<ModuleDto> findAllModules();

    Iterable<ModuleDto> findModulesByStack(String stackName);

    Iterable<ModuleDto> findModulesByPublic(boolean isPublic);
}
