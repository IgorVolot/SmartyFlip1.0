/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.cards.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartyflip.cards.dao.CardRepository;
import smartyflip.cards.dto.CardDto;
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
    public Card addCard(CardDto cardDto) {

        // Validation for required fields
        validateLength(cardDto.getQuestion(), "Question");
        validateLength(cardDto.getAnswer(), "Answer");
        validateLength(cardDto.getLevel(), "Level");

        // Check for the Deck id
        Deck deck = deckRepository.findById(cardDto.getDeckId())
                .orElseThrow(DeckNotFoundException::new);

        // A new Card creation
        Card newCard = modelMapper.map(cardDto, Card.class);
        newCard.setDateCreated(LocalDateTime.now());
        newCard.setDeck(deck);
//        newCard.setDeckName(deck.getDeckName());
        return cardRepository.save(newCard);
    }


    @Override
    public CardDto findCardById(Integer cardId) {
        return mapToDto(getCardById(cardId));
    }

    @Override
    public CardDto editCard(Integer cardId, CardDto cardDto) {
        Card card = getCardById(cardId);
        updateCard(cardDto, card);
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
    public Iterable<CardDto> findCardsByDeckId(Integer deckId) {
        if ( deckId == null ) {
            return Collections.emptyList();
        }
        try {
            return cardRepository.findAllCardsByDeckDeckId(deckId)
                    .map(this::mapToDto).collect(Collectors.toList());
        } catch (Exception e) {
            //consider logging the error message for debugging
            throw new RuntimeException("An error occurred while fetching Card Data using Deck ID", e);
        }
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

    private void updateCard(CardDto cardDto, Card card) {
        String question = cardDto.getQuestion();
        if ( question != null ) {
            card.setQuestion(question);
        }

        String answer = cardDto.getAnswer();
        if ( answer != null ) {
            card.setAnswer(answer);
        }

        String level = cardDto.getLevel();
        if ( level != null ) {
            card.setLevel(level);
        }

        String deckName = cardDto.getDeckName();
        if ( deckName != null ) {
            card.setDeckName(deckName);
        }
//        card.setDateCreated(LocalDateTime.now());
    }

}
