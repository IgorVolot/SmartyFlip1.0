/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartyflip.modules.model.Module;
import smartyflip.stacks.dao.StackRepository;
import smartyflip.stacks.dto.StackDto;
import smartyflip.stacks.model.Stack;
import smartyflip.stacks.service.exceptions.StackAlreadyExistException;
import smartyflip.stacks.service.exceptions.StackNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StackServiceImpl implements StackService {

    final StackRepository stackRepository;

    final ModelMapper modelMapper;

    public StackServiceImpl(StackRepository stackRepository, ModelMapper modelMapper) {
        this.stackRepository = stackRepository;
        this.modelMapper = modelMapper;
    }

    private Stack findStackOrThrow(String stackName) {
        Stack existingStack = stackRepository.findByStackNameIgnoreCase(stackName);
        if(existingStack == null) {
            throw new StackNotFoundException();
        }
        return existingStack;
    }

    @Override
    public StackDto addStack(StackDto stackDto) {
        if (stackDto == null) {
            throw new IllegalArgumentException("StackDto cannot be null");
        }

        Stack stackExists = stackRepository
                .findByStackNameIgnoreCase(stackDto.getStackName()
                        .trim().toLowerCase().replaceAll("\\s+", "_"));

        if (stackExists != null) {
            throw new StackAlreadyExistException("Stack already exists with this name.");
        }
        Stack stack = modelMapper.map(stackDto, Stack.class);
        stackRepository.save(stack);

        return modelMapper.map(stack, StackDto.class);
    }

//    @Override
//    public boolean removeStack(String stackName) {
//        Stack stack = findStackOrThrow(stackName);
//        stackRepository.delete(stack);
//        return true;
//    }

    @Transactional
    @Override
    public boolean removeStack(Long stackId) {
        Stack stack = stackRepository.findById(stackId)
                .orElseThrow(StackNotFoundException::new);
        stackRepository.delete(stack);
        return true;
    }

//    @Override
//    public StackDto findStackByName(String stackName) {
//        Stack stack = findStackOrThrow(stackName);
//        return modelMapper.map(stack, StackDto.class);
//    }

    @Override
    public StackDto findStackById(Long stackId) {
        Stack stack = stackRepository.findById(stackId)
                .orElseThrow(StackNotFoundException::new);
        return modelMapper.map(stack, StackDto.class);
    }

    // TODO Next method stopped and changed to another EditStack by stackId
//    @Override
//    public StackDto editStack(String stackName, StackDto stackDto) {
//        Stack existingStack = findStackOrThrow(stackName);
//
//        Stack stackExists = stackRepository
//                .findByStackNameIgnoreCase(stackDto.getStackName()
//                        .trim().toLowerCase().replaceAll("\\s+", "_"));
//
//        if ( stackExists != null && !stackExists.getStackId().equals(existingStack.getStackId()) ) {
//            throw new StackAlreadyExistException("Stack already exists with this name.");
//        }
//
//        // Preserve the id of the existing entity
//        long existingId = existingStack.getStackId();
//        // Preserve the modules
//        Set<Module> existingModules = existingStack.getModules();
//        // Update the existingStack with StackDto's values
//        modelMapper.map(stackDto, existingStack);
//        // Replace the existing stack id and modules
//        existingStack.setStackId(existingId);
//        if ( existingModules != null ) {
//            existingStack.setModules(existingModules);
//        }
//        stackRepository.save(existingStack);
//        return modelMapper.map(existingStack, StackDto.class);
//    }

    @Override
    public StackDto editStack(Long stackId, StackDto stackDto) {
        Stack existingStack = stackRepository.findById(stackId)
                .orElseThrow(StackNotFoundException::new);

        Stack stackExists = stackRepository
                .findByStackNameIgnoreCase(stackDto.getStackName()
                        .trim().toLowerCase().replaceAll("\\s+", "_"));

        if ( stackExists != null && !stackExists.getStackId().equals(existingStack.getStackId()) ) {
            throw new StackAlreadyExistException("Stack already exists with this name.");
        }

        // Preserve the id of the existing entity
        long existingId = existingStack.getStackId();
        // Preserve the modules
        Set<Module> existingModules = existingStack.getModules();
        // Update the existingStack with StackDto's values
        modelMapper.map(stackDto, existingStack);
        // Replace the existing stack id and modules
        existingStack.setStackId(existingId);
        if ( existingModules != null ) {
            existingStack.setModules(existingModules);
        }
        stackRepository.save(existingStack);
        return modelMapper.map(existingStack, StackDto.class);
    }

    @Override
    public Iterable<String> getAllStacks() {
        return stackRepository
                .findAll()
                .stream()
                .map(Stack::getStackName)
                .collect(Collectors.toList());
    }

    @Override
    public int getModulesAmount(String stackName) {
        Stack stack = findStackOrThrow(stackName);
        return stack.getModulesAmount();
    }

}

