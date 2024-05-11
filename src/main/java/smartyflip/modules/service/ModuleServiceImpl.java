/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartyflip.card.dao.CardRepository;
import smartyflip.modules.dto.DatePeriodDto;
import smartyflip.modules.dao.ModuleRepository;
import smartyflip.modules.dto.ModuleDto;
import smartyflip.modules.dto.NewModuleDto;
import smartyflip.modules.model.Module;
import smartyflip.modules.service.util.TagToStringConverter;
import smartyflip.modules.service.exceptions.ModuleNotFoundException;
import smartyflip.modules.service.util.TrialModule;
import smartyflip.stacks.dao.StackRepository;
import smartyflip.stacks.model.Stack;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    private final CardRepository cardRepository;

    private final StackRepository stackRepository;

    private final ModelMapper modelMapper;

//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public Module getModule(Long moduleId, String token) {
//        if (!checkAccess(token)) {
//            throw new SecurityException("Access Denied: Invalid or expired token.");
//        }
//
//        Module module = moduleRepository.findById(moduleId).orElseThrow(() -> new IllegalArgumentException("Module not found"));
//
//        if (module instanceof TrialModule) {
//            TrialModule trialModule = (TrialModule) module;
//            if ( LocalDate.now().isAfter(trialModule.getTrialEndDate())) {
//                throw new RuntimeException("Trial period has expired.");
//            }
//        }
//
//        return module;
//    }
//
//    private boolean checkAccess(String token) {
//        // Assume tokenService.decodeToken returns an object that includes user role
//        DecodedTokenInfo tokenInfo = tokenService.decodeToken(token);
//        return tokenInfo.isValid() && (tokenInfo.getRole().equals("REGISTERED") || (tokenInfo.getRole().equals("GUEST") && module instanceof TrialModule));
//    }


    private Module findModuleOrThrow(Long moduleId) {
        return moduleRepository.findById(moduleId).orElseThrow(ModuleNotFoundException::new);
    }

    @Transactional
    @Override
    public ModuleDto addModule(NewModuleDto newModuleDto) {
        // Create a new Module
        Module module = modelMapper.map(newModuleDto, Module.class);

        // Check if the Stack already exists
        Stack stack = stackRepository.findByStackNameIgnoreCase(newModuleDto.getStackName());
        if ( stack == null ) {
            // If the Stack doesn't exist, create and save a new Stack
            stack = new Stack();
            stack.setStackName(newModuleDto.getStackName().trim().toLowerCase().replaceAll("\\s+", "_"));
            stack = stackRepository.save(stack);
        }

        // Update Module's Stack
        module.setStack(stack);

        // Save Module in repository
        module = moduleRepository.save(module);

        return modelMapper.map(module, ModuleDto.class);
    }

    @Override
    public ModuleDto findModuleById(Long moduleId) {
        Module module = findModuleOrThrow(moduleId);
        return modelMapper.map(module, ModuleDto.class);
    }

    @Transactional
    @Override
    public ModuleDto editModule(Long moduleId, NewModuleDto newModuleDto) {
        Module module = findModuleOrThrow(moduleId);
        module.setModuleName(newModuleDto.getModuleName().trim().toLowerCase().replaceAll("\\s+", "_"));
        module.setAuthorName(newModuleDto.getAuthorName().trim().toLowerCase().replaceAll("\\s+", "_"));
        changeStackName(newModuleDto, module);

        module = moduleRepository.save(module);
        return modelMapper.map(module, ModuleDto.class);
    }

    public void changeStackName (NewModuleDto newModuleDto, Module module) {
        Stack newStack = stackRepository.findByStackNameIgnoreCase(newModuleDto.getStackName());
        if (newStack == null) {
            newStack = new Stack();
            newStack.setStackName(newModuleDto.getStackName().trim().toLowerCase().replaceAll("\\s+", "_"));
//            newStack.setStackName(newModuleDto.getStackName());
        }
        module.setStack(newStack);

        // Set the stackName of Module.
        module.setStackName(newStack.getStackName());
    }

    @Transactional
    @Override
    public boolean deleteModule(Long moduleId) {
        Module module = findModuleOrThrow(moduleId);
        cardRepository.findCardsByModuleId(moduleId).forEach(cardRepository::delete);
        moduleRepository.deleteById(moduleId);
        return !moduleRepository.existsById(moduleId);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<ModuleDto> findModulesByAuthor(String authorName) {
        if (authorName == null) {
            return Collections.emptyList();
        }
        return moduleRepository
                .findModulesByAuthorNameIgnoreCase(authorName)
                .map(module -> modelMapper.map(module, ModuleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<ModuleDto> findModulesByPeriod(DatePeriodDto datePeriodDto) {
        return moduleRepository
                .findAllByDateCreatedBetween(datePeriodDto.getDateFrom(), datePeriodDto.getDateTo())
                .map(module -> modelMapper.map(module, ModuleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<ModuleDto> findModulesByName(String moduleName) {
        return moduleRepository
                .findModulesByModuleNameIgnoreCase(moduleName)
                .map(module -> modelMapper.map(module, ModuleDto.class))
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    @Override
    public Iterable<ModuleDto> findAllModules() {
        return moduleRepository.findAll().stream()
                .map(module -> modelMapper.map(module, ModuleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<ModuleDto> findModulesByStack(String stackName) {
        return moduleRepository
                .findModulesByStackNameIgnoreCase(stackName)
                .map(module -> modelMapper.map(module, ModuleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<ModuleDto> finAllModulesByTags(Set<String> tag) {
        if ( tag == null || tag.isEmpty() ) {
            return Collections.emptyList();
        }

        TagToStringConverter converter = new TagToStringConverter();
        Set<String> convertedTags = tag.stream().map(converter::convertToDatabaseColumn).collect(Collectors.toSet());

        return moduleRepository
                .findAllByTags(convertedTags)
                .map(module -> modelMapper.map(module, ModuleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public int cardsAmountByModuleId(Long moduleId) {
        Module module = findModuleOrThrow(moduleId);
        return module.getCardsAmount();
    }

}
