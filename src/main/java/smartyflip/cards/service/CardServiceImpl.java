/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.cards.service;

import smartyflip.cards.dao.CardRepository;
import smartyflip.cards.dto.CardDto;
import smartyflip.cards.dto.NewCardDto;
import smartyflip.cards.service.exceptions.CardNotFoundException;
import smartyflip.cards.model.Card;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartyflip.decks.dao.DeckRepository;
import smartyflip.decks.service.exceptions.DeckNotFoundException;
import smartyflip.decks.model.Deck;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final DeckRepository deckRepository;

    private final ModelMapper modelMapper;


    @Override
    public CardDto addCard(NewCardDto newCardDto){

        // Validation for required fields
        validateLength(newCardDto.getQuestion(), "Question");
        validateLength(newCardDto.getAnswer(), "Answer");
        validateLength(newCardDto.getLevel(), "Level");

        // Check for the Deck id
        Deck deck = deckRepository.findById(newCardDto.getDeckId()).orElseThrow(DeckNotFoundException::new);

        // A new Card creation
        Card card = modelMapper.map(newCardDto, Card.class);
        card.setDateCreated(LocalDateTime.now());
        card.setDeckName(deck.getDeckName());
        card.setDeck(deck);
        cardRepository.save(card);
        return mapToDto(card);
    }


    @Override
    public CardDto findCardById(Integer cardId) {
        return mapToDto(getCardById(cardId));
    }

    @Override
    public CardDto editCard(Integer cardId, NewCardDto newCardDto) {
        Card card = getCardById(cardId);
        updateCard(newCardDto, card);
        cardRepository.save(card);
        return mapToDto(card);
    }

    @Override
    public CardDto deleteCard(Integer cardId) {
        Card card = getCardById(cardId);
        cardRepository.deleteById(cardId);
        return mapToDto(card);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<CardDto> findCardsByDeckId(Integer deckId){
        if ( deckId == null ) {
            return Collections.emptyList();
        }
        return cardRepository.findCardsByDeckId(deckId)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    private Card getCardById(Integer cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));
    }


    private CardDto mapToDto(Card card) {
        return modelMapper.map(card, CardDto.class);
    }


    private void validateLength(String str, String fieldName) {
        if ( str != null && str.length() > 1500 ) {
            throw new IllegalArgumentException(fieldName + " exceeds the maximum length");
        }
    }

    private void updateCard(NewCardDto newCardDto, Card card) {
        String question = newCardDto.getQuestion();
        if ( question != null ) {
            card.setQuestion(question);
        }

        String answer = newCardDto.getAnswer();
        if ( answer != null ) {
            card.setAnswer(answer);
        }

        String level = newCardDto.getLevel();
        if ( level != null ) {
            card.setLevel(level);
        }

        Integer deckId = newCardDto.getDeckId();
        card.setDeckId(deckId);

        card.setDateCreated(LocalDateTime.now());
    }

}
