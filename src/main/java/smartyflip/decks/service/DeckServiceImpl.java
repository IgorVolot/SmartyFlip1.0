/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartyflip.cards.dto.DatePeriodDto;
import smartyflip.decks.dao.DeckRepository;
import smartyflip.decks.dto.DeckDto;
import smartyflip.decks.dto.NewDeckDto;
import smartyflip.decks.dto.exceptions.DeckNotFoundException;
import smartyflip.decks.model.Deck;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {
    @NonNull
    final DeckRepository deckRepository;
    @NonNull
    final ModelMapper modelMapper;

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

    @Override
    public DeckDto editDeck(Integer deckId, NewDeckDto newDeckDto) {
        return null;
    }

    @Override
    public DeckDto deleteDeck(Integer deckId) {
        return null;
    }

    @Override
    public Iterable<DeckDto> findDecksByAuthor(String authorName) {
        return null;
    }

    @Override
    public Iterable<DeckDto> findDecksByTags(Set<String> tags) {
        return null;
    }

    @Override
    public Iterable<DeckDto> findDecksByPeriod(DatePeriodDto datePeriodDto) {
        return null;
    }

    @Override
    public Iterable<DeckDto> findDecksByName(String deckName) {
        return null;
    }

    @Override
    public void increaseCardsCount(Integer deckId) {

    }

    @Override
    public Iterable<DeckDto> findAllDecks() {
        return null;
    }

    @Override
    public Iterable<DeckDto> findDecksByStack(String stackName) {
        return null;
    }

    @Override
    public Iterable<DeckDto> findDecksByPublic(boolean isPublic) {
        return null;
    }
}
