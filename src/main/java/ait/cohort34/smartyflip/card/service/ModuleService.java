package ait.cohort34.smartyflip.card.service;

import ait.cohort34.smartyflip.card.dto.DatePeriodDto;
import ait.cohort34.smartyflip.card.dto.ModuleDto;
import ait.cohort34.smartyflip.card.dto.NewModuleDto;
import jakarta.persistence.Table;

import java.util.Set;

public interface ModuleService {
    ModuleDto addModule(NewModuleDto newmoduleDto);
    ModuleDto findModuleById(String moduleId);
    ModuleDto updateModule(NewModuleDto newmoduleDto);
    ModuleDto deleteModule(String moduleId);
    Iterable<ModuleDto> findModulesByAuthor(String authorName);
    Iterable<ModuleDto> findModulesByTags(Set<String> tags);
    Iterable<ModuleDto> findModulesByPeriod(DatePeriodDto datePeriodDto);
    Iterable<ModuleDto> findModulesByName(String moduleName);
}
