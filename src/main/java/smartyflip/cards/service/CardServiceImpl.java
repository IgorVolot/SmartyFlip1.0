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
import smartyflip.cards.dto.exceptions.CardNotFoundException;
import smartyflip.cards.model.Card;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Implementation of the CardService interface that provides methods to add, find, edit, delete, and perform operations on cards.
 */
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    @NonNull
    final CardRepository cardRepository;

    @NonNull
    final ModelMapper modelMapper;


    @Override
    public CardDto addCard(NewCardDto newCardDto) {
        validateLength(newCardDto.getQuestion(), "Question");
        validateLength(newCardDto.getAnswer(), "Answer");
        validateLength(newCardDto.getLevel(), "Level");

        Card card = modelMapper.map(newCardDto, Card.class);
        card.setDateCreated(LocalDateTime.now());
//        card.setLikes(0);
        cardRepository.save(card);
        return mapToDto(card);
    }


    @Override
    public CardDto findCardById(Integer cardId) {
        return mapToDto(getCardById(cardId));
    }


//    /**
//     * Adds a like to a decks identified by the given cardId.
//     * If the decks with the provided cardId is not found, a CardNotFoundException is thrown.
//     *
//     * @param cardId The ID of the decks to add a like to.
//     * @throws CardNotFoundException If the decks with the given cardId is not found.
//     */
//    @Override
//    public void addLike(Integer cardId) {
//        Card card = getCardById(cardId);
//        card.setLikes(card.getLikes() + 1);
//        cardRepository.save(card);
//    }


    @Override
    public CardDto editCard(Integer cardId, NewCardDto newCardDto) {
        Card card = getCardById(cardId);
        updateCard(newCardDto, card);
        cardRepository.save(card);
        return mapToDto(card);
    }


//    /**
//    @Override
//    public CardDto editBookmark(Integer cardId, boolean bookmark, CardDto cardDto) {
//        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));
//
//        if ( !cardDto.isBookmark() ) {
//            card.setBookmark(false);
//        }
//        if ( cardDto.isBookmark() ) {
//            card.setBookmark(true);
//        }
//        cardRepository.save(card);
//        return modelMapper.map(card, CardDto.class);
//    }


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

    private void updateCard(NewCardDto newCardDto, Card card) {
        String question = newCardDto.getQuestion();
        if (question != null) {
            card.setQuestion(question);
        }

        String answer = newCardDto.getAnswer();
        if (answer != null) {
            card.setAnswer(answer);
        }

        String level = newCardDto.getLevel();
        if (level != null) {
            card.setLevel(level);
        }

        String deckName = newCardDto.getDeckName();
        if (deckName != null) {
            card.setDeckName(deckName);
        }

//        Integer moduleId = newCardDto.getModuleId();
//        if (moduleId != null) {
//            decks.setModuleId(moduleId);
//        }
//        card.setLikes(0);
        card.setDateCreated(LocalDateTime.now());
    }
}
