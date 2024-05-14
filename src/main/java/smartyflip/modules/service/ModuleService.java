/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service;

import org.springframework.data.domain.PageRequest;
import smartyflip.modules.dto.DatePeriodDto;
import smartyflip.modules.dto.ModuleDto;
import smartyflip.modules.dto.NewModuleDto;
import smartyflip.utils.PagedDataResponseDto;

public interface ModuleService {
    ModuleDto addModule(NewModuleDto newModuleDto, String subscriptionType);

    ModuleDto findModuleById(Long moduleId);

    // FIXME
    ModuleDto editModule(NewModuleDto newModuleDto, Long moduleId, String subscriptionType);

    boolean deleteModule(Long moduleId);

    Iterable<ModuleDto> findModulesByUserName(String userName);

    Iterable<ModuleDto> findModulesByPeriod(DatePeriodDto datePeriodDto);

    Iterable<ModuleDto> findModulesByName(String moduleName);

    PagedDataResponseDto<ModuleDto> findAllModules(PageRequest pageRequest);

    Iterable<ModuleDto> findModulesByStack(String stackName);

    // FIXME
//    Iterable<ModuleDto> finAllModulesByTags(Iterable<String> tags);

    int cardsAmountByModuleId(Long moduleId);
}
