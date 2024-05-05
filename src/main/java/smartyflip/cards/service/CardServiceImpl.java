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
//     * Adds a like to a modules identified by the given cardId.
//     * If the modules with the provided cardId is not found, a CardNotFoundException is thrown.
//     *
//     * @param cardId The ID of the modules to add a like to.
//     * @throws CardNotFoundException If the modules with the given cardId is not found.
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
//     * Edits the bookmark status of a modules with the given cardId. If the modules is found,
//     * the bookmark status is updated based on the provided boolean value. If the bookmark
//     * value provided is true, the modules is bookmarked. If the bookmark value provided is false,
//     * the modules is unbookmarked. The edited modules is saved in the repository and the updated
//     * modules DTO is returned.
//     *
//     * @param cardId   The ID of the modules to be edited.
//     * @param bookmark The new bookmark status of the modules.
//     * @param cardDto  The modules DTO representing the updated modules details.
//     * @return The edited modules DTO.
//     * @throws CardNotFoundException If the modules with the given cardId is not found.
//     */
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
    public Iterable<CardDto> findCardsByModuleId(Integer moduleId) {
        if ( moduleId == null ) {
            return Collections.emptyList();
        }
        try {
            return cardRepository.findAllCardsByModuleId(moduleId)
                    .map(this::mapToDto).collect(Collectors.toList());
        } catch (Exception e) {
            //consider logging the error message for debugging
            throw new RuntimeException("An error occurred while fetching Card Data using Module ID", e);
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

        String moduleName = newCardDto.getModuleName();
        if (moduleName != null) {
            card.setModuleName(moduleName);
        }

//        Integer moduleId = newCardDto.getModuleId();
//        if (moduleId != null) {
//            modules.setModuleId(moduleId);
//        }
//        card.setLikes(0);
        card.setDateCreated(LocalDateTime.now());
    }
}
