/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartyflip.cards.dto.DatePeriodDto;
import smartyflip.modules.dao.ModuleRepository;
import smartyflip.modules.dto.ModuleDto;
import smartyflip.modules.dto.NewModuleDto;
import smartyflip.modules.dto.exceptions.ModuleNotFoundException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {
    @NonNull
    final ModuleRepository moduleRepository;
    @NonNull
    final ModelMapper modelMapper;

    private Module findModuleOrThrow(Integer moduleId) {
        return moduleRepository.findById(moduleId).orElseThrow(ModuleNotFoundException::new);
    }

    @Override
    public ModuleDto addModule(NewModuleDto newModuleDto) {
        Module module = modelMapper.map(newModuleDto, Module.class);
        module = moduleRepository.save(module);
        return modelMapper.map(module, ModuleDto.class);
    }

    @Override
    public ModuleDto findModuleById(Integer moduleId) {
        Module module = findModuleOrThrow(moduleId);
        return modelMapper.map(module, ModuleDto.class);
    }

    @Override
    public ModuleDto editModule(Integer moduleId, NewModuleDto newModuleDto) {
        return null;
    }

    @Override
    public ModuleDto deleteModule(Integer moduleId) {
        return null;
    }

    @Override
    public Iterable<ModuleDto> findModulesByAuthor(String authorName) {
        return null;
    }

    @Override
    public Iterable<ModuleDto> findModulesByTags(Set<String> tags) {
        return null;
    }

    @Override
    public Iterable<ModuleDto> findModulesByPeriod(DatePeriodDto datePeriodDto) {
        return null;
    }

    @Override
    public Iterable<ModuleDto> findModulesByName(String moduleName) {
        return null;
    }

    @Override
    public void increaseCardsCount(Integer moduleId) {

    }

    @Override
    public Iterable<ModuleDto> findAllModules() {
        return null;
    }

    @Override
    public Iterable<ModuleDto> findModulesByStack(String stackName) {
        return null;
    }

    @Override
    public Iterable<ModuleDto> findModulesByPublic(boolean isPublic) {
        return null;
    }
}
