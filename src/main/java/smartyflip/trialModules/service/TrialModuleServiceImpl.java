/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.trialModules.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartyflip.card.dao.CardRepository;
import smartyflip.modules.dto.DatePeriodDto;
import smartyflip.modules.model.TagToStringConverter;
import smartyflip.modules.service.exceptions.ModuleNotFoundException;
import smartyflip.stacks.model.Stack;
import smartyflip.trialModules.dto.TrialModuleDto;
import smartyflip.trialModules.dto.NewTrialModuleDto;
import smartyflip.stacks.dao.StackRepository;
import smartyflip.trialModules.dao.TrialModuleRepository;
import smartyflip.trialModules.model.TrialModule;


import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrialModuleServiceImpl implements TrialModuleService {

    private final TrialModuleRepository trialModuleRepository;

    private final CardRepository cardRepository;

    private final StackRepository stackRepository;

    private final ModelMapper modelMapper;

    private TrialModule findTrialModuleOrThrow(Long trialModuleId) {
        return trialModuleRepository.findById(trialModuleId).orElseThrow(ModuleNotFoundException::new);
    }

    @Transactional
    @Override
    public TrialModuleDto addTrialModule(NewTrialModuleDto newTrialModuleDto) {
        // Create a new TrialModule
        TrialModule trialModule = modelMapper.map(newTrialModuleDto, TrialModule.class);

        // Check if the Stack already exists
        Stack stack = stackRepository.findByStackNameIgnoreCase(newTrialModuleDto.getStackName());
        if ( stack == null ) {
            // If the Stack doesn't exist, create and save a new Stack
            stack = new Stack();
            stack.setStackName(newTrialModuleDto.getStackName().trim().toLowerCase().replaceAll("\\s+", "_"));
            stack = stackRepository.save(stack);
        }

        // Update TrialModule's Stack
        trialModule.setStack(stack);

        // Save TrialModule in repository
        trialModule = trialModuleRepository.save(trialModule);

        return modelMapper.map(trialModule, TrialModuleDto.class);
    }

    @Override
    public TrialModuleDto findTrialModuleById(Long trialModuleId) {
        TrialModule trialModule = findTrialModuleOrThrow(trialModuleId);
        return modelMapper.map(trialModule, TrialModuleDto.class);
    }

    @Transactional
    @Override
    public TrialModuleDto editTrialModule(Long trialModuleId, NewTrialModuleDto newTrialModuleDto) {
        TrialModule trialModule = findTrialModuleOrThrow(trialModuleId);
        trialModule.setTrialModuleName(newTrialModuleDto.getTrialModuleName().trim().toLowerCase().replaceAll("\\s+", "_"));
        trialModule.setAuthorName(newTrialModuleDto.getAuthorName().trim().toLowerCase().replaceAll("\\s+", "_"));
        changeStackName(newTrialModuleDto, trialModule);

        trialModule = trialModuleRepository.save(trialModule);
        return modelMapper.map(trialModule, TrialModuleDto.class);
    }

    public void changeStackName (NewTrialModuleDto newTrialModuleDto, TrialModule trialModule) {
        Stack newStack = stackRepository.findByStackNameIgnoreCase(newTrialModuleDto.getStackName());
        if (newStack == null) {
            newStack = new Stack();
            newStack.setStackName(newTrialModuleDto.getStackName().trim().toLowerCase().replaceAll("\\s+", "_"));
//            newStack.setStackName(newTrialModuleDto.getStackName());
        }
        trialModule.setStack(newStack);

        // Set the stackName of TrialModule.
        trialModule.setStackName(newStack.getStackName());
    }

    @Transactional
    @Override
    public boolean deleteTrialModule(Long trialModuleId) {
        TrialModule trialModule = findTrialModuleOrThrow(trialModuleId);
        cardRepository.findCardsByModuleId(trialModuleId).forEach(cardRepository::delete);
        trialModuleRepository.deleteById(trialModuleId);
        return !trialModuleRepository.existsById(trialModuleId);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<TrialModuleDto> findTrialModulesByName(String trialModuleName) {
        return trialModuleRepository
                .findTrialModulesByModuleNameIgnoreCase(trialModuleName)
                .map(m -> modelMapper.map(m, TrialModuleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<TrialModuleDto> findTrialModulesByAuthor(String authorName) {
        if (authorName == null) {
            return Collections.emptyList();
        }
        return trialModuleRepository
                .findTrialModulesByAuthorNameIgnoreCase(authorName)
                .map(module -> modelMapper.map(module, TrialModuleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<TrialModuleDto> findTrialModulesByPeriod(DatePeriodDto datePeriodDto) {
        return trialModuleRepository
                .findAllByDateCreatedBetween(datePeriodDto.getDateFrom(), datePeriodDto.getDateTo())
                .map(module -> modelMapper.map(module, TrialModuleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<TrialModuleDto> finAllTrialModulesByTags(Set<String> tags) {
        if ( tags == null || tags.isEmpty() ) {
            return Collections.emptyList();
        }

        TagToStringConverter converter = new TagToStringConverter();
        Set<String> convertedTags = tags.stream().map(converter::convertToDatabaseColumn).collect(Collectors.toSet());

        return trialModuleRepository
                .findAllByTags(convertedTags)
                .map(module -> modelMapper.map(module, TrialModuleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<TrialModuleDto> findTrialModulesByStack(String stackName) {
        return trialModuleRepository
                .findTrialModulesByStackNameIgnoreCase(stackName)
                .map(module -> modelMapper.map(module, TrialModuleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<TrialModuleDto> findAllTrialModules() {
        return trialModuleRepository.findAll().stream()
                .map(module -> modelMapper.map(module, TrialModuleDto.class))
                .collect(Collectors.toList());
    }
}
