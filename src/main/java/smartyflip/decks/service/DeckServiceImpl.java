/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.service;


import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartyflip.cards.dao.CardRepository;
import smartyflip.decks.dto.DatePeriodDto;
import smartyflip.decks.dao.DeckRepository;
import smartyflip.decks.dto.DeckDto;
import smartyflip.decks.dto.NewDeckDto;
import smartyflip.decks.dto.exceptions.DeckNotFoundException;
import smartyflip.decks.model.Deck;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeckServiceImpl implements DeckService {

    @NonNull
    @Autowired
    final DeckRepository deckRepository;

    @NonNull
    final CardRepository cardRepository;
    @NonNull
    final ModelMapper modelMapper;

    public DeckServiceImpl(DeckRepository deckRepository, CardRepository cardRepository, ModelMapper modelMapper) {
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private Deck findDeckOrThrow(Integer moduleId) {
        return deckRepository.findById(moduleId).orElseThrow(DeckNotFoundException::new);
    }

    @Override
    public DeckDto addDeck(NewDeckDto newDeckDto) {
        Deck deck = modelMapper.map(newDeckDto, Deck.class);
        deck = deckRepository.save(deck);
        return modelMapper.map(deck, DeckDto.class);
    }

    @Override
    public DeckDto findDeckById(Integer deckIdId) {
        Deck deck = findDeckOrThrow(deckIdId);
        return modelMapper.map(deck, DeckDto.class);
    }

    @Transactional
    @Override
    public DeckDto editDeck(Integer deckId, NewDeckDto newDeckDto) {
        Deck deck = findDeckOrThrow(deckId);
        deck.setDeckName(newDeckDto.getDeckName());
        deck.setAuthorName(newDeckDto.getAuthorName());
        deck.setStackName(newDeckDto.getStackName());
        deck.setTags(newDeckDto.getTags());
        deck.setPublic(newDeckDto.isPublic());
        deck = deckRepository.save(deck);
        return modelMapper.map(deck, DeckDto.class);
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
    public Iterable<DeckDto> findDecksByTags(Set<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> lowerCaseTags = tags.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
        return lowerCaseTags.stream()
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

    @Transactional
    @Override
    public int cardsCount(Integer deckId) {
        Deck deck = findDeckOrThrow(deckId);
        return deck.getCardsAmount();
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
    public Iterable<DeckDto> findDecksByPublic(boolean isPublic) {
        return deckRepository
                .findDecksByPublic(isPublic)
                .map(deck -> modelMapper.map(deck, DeckDto.class))
                .collect(Collectors.toList());
    }
}
