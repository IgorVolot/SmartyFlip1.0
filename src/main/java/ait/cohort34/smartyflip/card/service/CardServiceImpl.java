/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package ait.cohort34.smartyflip.card.service;

import ait.cohort34.smartyflip.card.dao.CardRepository;
import ait.cohort34.smartyflip.card.dto.CardDto;
import ait.cohort34.smartyflip.card.dto.NewCardDto;
import ait.cohort34.smartyflip.card.dto.exceptions.CardNotFoundException;
import ait.cohort34.smartyflip.card.model.Card;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    @NonNull final CardRepository cardRepository;
    @NonNull final ModelMapper modelMapper;

    @Override
    public CardDto addCard(NewCardDto newCardDto) {
        if (newCardDto == null) {
            throw new IllegalArgumentException("NewCardDto cannot be null");
        }
        Card card = modelMapper.map(newCardDto, Card.class);
        cardRepository.save(card);
        return modelMapper.map(card, CardDto.class);
    }

    @Override
    public CardDto findCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .map(card -> modelMapper.map(card, CardDto.class))
                .orElse(null);
    }

    @Override
    public void addLike(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));

        Integer currentLikes = card.getLikes();
        if (currentLikes == null) {
            card.setLikes(1); // The case when the likes is null, setting it to first like
        } else {
            card.setLikes(currentLikes + 1); // Otherwise incrementing the likes
        }
        cardRepository.save(card);
    }

    @Override
    public CardDto editCard(Long cardId, NewCardDto newCardDto) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));
        String question = newCardDto.getQuestion();
        if (question != null) {
            card.setQuestion(newCardDto.getQuestion());
        }
        String answer = newCardDto.getAnswer();
        if ( answer != null ) {
            card.setAnswer(newCardDto.getAnswer());
        }
        String level = newCardDto.getLevel();
        if ( level != null ) {
            card.setLevel(newCardDto.getLevel());
        }
        Long moduleId = newCardDto.getModuleId();
        if (moduleId != null) {
            card.setModuleId(newCardDto.getModuleId());
        }
        Integer currentLikes = card.getLikes();
        if ( currentLikes != null ) {
            card.setLikes(0);
        }
        LocalDateTime now = LocalDateTime.now();
        card.setLastUpdate(now);
        cardRepository.save(card);
        return modelMapper.map(card, CardDto.class);
    }


    @Override
    public CardDto editBookmark(Long cardId, boolean bookmark, CardDto cardDto) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));

        if ( !cardDto.isBookmark() ){
            card.setBookmark(false);
        }
        if ( cardDto.isBookmark()){
            card.setBookmark(true);
        }
        cardRepository.save(card);
        return modelMapper.map(card, CardDto.class);
    }

    @Override
    public CardDto deleteCard(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));
        cardRepository.deleteById(cardId);
        return modelMapper.map(card, CardDto.class);
    }

}
