/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartyflip.cards.dao.CardRepository;
import smartyflip.decks.dto.DatePeriodDto;
import smartyflip.decks.dao.DeckRepository;
import smartyflip.decks.dto.DeckDto;
import smartyflip.decks.dto.NewDeckDto;
import smartyflip.decks.model.TagToStringConverter;
import smartyflip.decks.service.exceptions.DeckNotFoundException;
import smartyflip.decks.model.Deck;
import smartyflip.stacks.dao.StackRepository;
import smartyflip.stacks.model.Stack;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {

    private final DeckRepository deckRepository;

    private final CardRepository cardRepository;

    private final StackRepository stackRepository;

    private final ModelMapper modelMapper;


    private Deck findDeckOrThrow(Integer moduleId) {
        return deckRepository.findById(moduleId).orElseThrow(DeckNotFoundException::new);
    }

    @Override
    public DeckDto addDeck(NewDeckDto newDeckDto) {
        // Create a new Deck
        Deck deck = modelMapper.map(newDeckDto, Deck.class);

        // Check if the Stack already exists
        Stack stack = stackRepository.findByStackNameIgnoreCase(newDeckDto.getStackName());
        if ( stack == null ) {
            // If the Stack doesn't exist, create and save a new Stack
            stack = new Stack();
            stack.setStackName(newDeckDto.getStackName());
            stack = stackRepository.save(stack);
        }

        // Update Deck's Stack
        deck.setStack(stack);

        // Save Deck in repository
        deck = deckRepository.save(deck);

        return modelMapper.map(deck, DeckDto.class);
    }

    @Override
    public DeckDto findDeckById(Integer deckId) {
        Deck deck = findDeckOrThrow(deckId);
        return modelMapper.map(deck, DeckDto.class);
    }

    @Transactional
    @Override
    public DeckDto editDeck(Integer deckId, NewDeckDto newDeckDto) {
        Deck deck = findDeckOrThrow(deckId);
        deck.setDeckName(newDeckDto.getDeckName());
        deck.setAuthorName(newDeckDto.getAuthorName());
        changeStackName(newDeckDto, deck);

        deck.setTags(newDeckDto.getTags());
        deck = deckRepository.save(deck);
        return modelMapper.map(deck, DeckDto.class);
    }

    public void changeStackName (NewDeckDto newDeckDto, Deck deck) {
        Stack newStack = stackRepository.findByStackNameIgnoreCase(newDeckDto.getStackName());
        if (newStack == null) {
            newStack = new Stack();
            newStack.setStackName(newDeckDto.getStackName());
            newStack.setStackName(newDeckDto.getStackName());
        }
        deck.setStack(newStack);

        // Set the stackName of Deck.
        deck.setStackName(newStack.getStackName());
    }

    @Transactional
    @Override
    public boolean deleteDeck(Integer deckId) {
        Deck deck = findDeckOrThrow(deckId);
        cardRepository.findAllCardsByDeckDeckId(deckId).forEach(cardRepository::delete);
        deckRepository.deleteById(deckId);
        return !deckRepository.existsById(deckId);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<DeckDto> findDecksByAuthor(String authorName) {
        if (authorName == null) {
            return Collections.emptyList();
        }
        return deckRepository
                .findDecksByAuthorNameIgnoreCase(authorName)
                .map(deck -> modelMapper.map(deck, DeckDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<DeckDto> findDecksByPeriod(DatePeriodDto datePeriodDto) {
        return deckRepository
                .findAllByDateCreatedBetween(datePeriodDto.getDateFrom(), datePeriodDto.getDateTo())
                .map(deck -> modelMapper.map(deck, DeckDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<DeckDto> findDecksByName(String deckName) {
        return deckRepository
                .findDecksByDeckNameIgnoreCase(deckName)
                .map(deck -> modelMapper.map(deck, DeckDto.class))
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    @Override
    public Iterable<DeckDto> findAllDecks() {
        return deckRepository.findAll().stream()
                .map(deck -> modelMapper.map(deck, DeckDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<DeckDto> findDecksByStack(String stackName) {
        return deckRepository
                .findDecksByStackNameIgnoreCase(stackName)
                .map(deck -> modelMapper.map(deck, DeckDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<DeckDto> finAllDecksByTags(Set<String> tag) {
        if ( tag == null || tag.isEmpty() ) {
            return Collections.emptyList();
        }

        TagToStringConverter converter = new TagToStringConverter();
        Set<String> convertedTags = tag.stream().map(converter::convertToDatabaseColumn).collect(Collectors.toSet());

        return deckRepository
                .findAllByTags(convertedTags)
                .map(deck -> modelMapper.map(deck, DeckDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public int cardsAmountByDeckId(Integer deckId) {
        Deck deck = findDeckOrThrow(deckId);
        return deck.cardsAmount();
    }

}
