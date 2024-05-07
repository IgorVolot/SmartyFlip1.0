/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartyflip.decks.model.Deck;
import smartyflip.stacks.dao.StackRepository;
import smartyflip.stacks.dto.StackDto;
import smartyflip.stacks.model.Stack;
import smartyflip.stacks.service.exceptions.AlreadyExistException;
import smartyflip.stacks.service.exceptions.StackNotFoundException;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StackServiceImpl implements StackService {


    final StackRepository stackRepository;

    final ModelMapper modelMapper;

    @Override
    public StackDto addStack(StackDto stackDto) {
        Stack stack = modelMapper.map(stackDto, Stack.class);
        Stack existingStack = stackRepository.findByStackNameIgnoreCase(stackDto.getStackName());

        if(existingStack != null) {
            throw new AlreadyExistException("Stack already exists with this name.");
        } else {
            stackRepository.save(stack);
            return modelMapper.map(stack, StackDto.class);
        }
    }

    @Override
    public boolean removeStack(String stackName) {
        Stack stack = stackRepository.findByStackNameIgnoreCase(stackName);
        if ( stack == null ) {
            throw new StackNotFoundException("Stack with name " + stackName + " not found");
        }
        stackRepository.delete(stack);
        return true;
    }

    @Override
    public StackDto findStackByName(String stackName) {
        Stack stack = stackRepository
                .findByStackNameIgnoreCase(stackName);
        if ( stack == null ) {
            throw new StackNotFoundException("Stack with name " + stackName + " not found");
        }
        return modelMapper.map(stack, StackDto.class);
    }

    @Override
    public StackDto editStack(String stackName, StackDto stackDto) {
        Stack existingStack = stackRepository.findByStackNameIgnoreCase(stackName);
        if(existingStack != null) {
            // Preserve the id of the existing entity
            Integer existingId = existingStack.getStackId();

            // Preserve the decks
            Set<Deck> existingDecks = existingStack.getDecks();

            // Update the existingStack with StackDto's values
            modelMapper.map(stackDto, existingStack);

            // Replace the existing stack id and decks
            existingStack.setStackId(existingId);
            existingStack.setDecks(existingDecks);

            stackRepository.save(existingStack);
            return modelMapper.map(existingStack, StackDto.class);
        } else {
            throw new StackNotFoundException("Stack does not exist with this name.");
        }
    }

    @Override
    public List<Stack> getAllStacks() {
        return stackRepository
                .findAll()
                .stream().toList();
    }
}
