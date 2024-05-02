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

/**
 * Implementation of the CardService interface that provides methods to add, find, edit, delete, and perform operations on cards.
 */
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    /**
     * Represents a repository for the Card entity.
     * The repository extends the JpaRepository interface for performing CRUD operations.
     */
    @NonNull
    final CardRepository cardRepository;
    /**
     * The model mapper used for mapping between different object types.
     */
    @NonNull
    final ModelMapper modelMapper;

    /**
     * Adds a new card to the system.
     *
     * @param newCardDto The object representing the new card to be added.
     * @return The created card DTO.
     */
    @Override
    public CardDto addCard(NewCardDto newCardDto) {
        validateLength(newCardDto.getQuestion(), "Question");
        validateLength(newCardDto.getAnswer(), "Answer");
        validateLength(newCardDto.getLevel(), "Level");

        Card card = modelMapper.map(newCardDto, Card.class);
        card.setLastUpdate(LocalDateTime.now());
        cardRepository.save(card);
        return mapToDto(card);
    }

    /**
     * Finds a card by its ID.
     *
     * @param cardId The ID of the card to be found.
     * @return The card DTO corresponding to the ID.
     */
    @Override
    public CardDto findCardById(Integer cardId) {
        return mapToDto(getCardById(cardId));
    }

    /**
     * Adds a like to a card identified by the given ID. If the card is not found, a CardNotFoundException is thrown.
     * If the card already has likes, the number of likes is incremented. Otherwise, the likes are set to 1.
     *
     * @param cardId The ID of the card to add a like to.
     * @throws CardNotFoundException If the card with the given ID is not found.
     */
    @Override
    public void addLike(Integer cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));

        Integer currentLikes = card.getLikes();
        if ( currentLikes == null ) {
            card.setLikes(1); // The case when the likes are null, setting it to first like
        } else {
            card.setLikes(currentLikes + 1); // Otherwise incrementing the likes
        }
        cardRepository.save(card);
    }

    /**
     * Edits a card with the given cardId using the provided newCardDto.
     *
     * @param cardId     The ID of the card to be edited.
     * @param newCardDto The object representing the new card details.
     * @return The edited card DTO.
     * @throws CardNotFoundException If the card with the given cardId is not found.
     */
    @Override
    public CardDto editCard(Integer cardId, NewCardDto newCardDto) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));
        String question = newCardDto.getQuestion();
        if ( question != null ) {
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
        Integer moduleId = newCardDto.getModuleId();
        if ( moduleId != null ) {
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


    /**
     * Edits the bookmark status of a card with the given cardId. If the card is found,
     * the bookmark status is updated based on the provided boolean value. If the bookmark
     * value provided is true, the card is bookmarked. If the bookmark value provided is false,
     * the card is unbookmarked. The edited card is saved in the repository and the updated
     * card DTO is returned.
     *
     * @param cardId     The ID of the card to be edited.
     * @param bookmark   The new bookmark status of the card.
     * @param cardDto    The card DTO representing the updated card details.
     * @return The edited card DTO.
     * @throws CardNotFoundException If the card with the given cardId is not found.
     */
    @Override
    public CardDto editBookmark(Integer cardId, boolean bookmark, CardDto cardDto) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));

        if ( !cardDto.isBookmark() ) {
            card.setBookmark(false);
        }
        if ( cardDto.isBookmark() ) {
            card.setBookmark(true);
        }
        cardRepository.save(card);
        return modelMapper.map(card, CardDto.class);
    }

    /**
     * Deletes a card with the given cardId from the repository.
     *
     * @param cardId The ID of the card to be deleted.
     * @return The DTO of the deleted card.
     * @throws CardNotFoundException If the card with the given cardId is not found.
     */
    @Override
    public CardDto deleteCard(Integer cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));
        cardRepository.deleteById(cardId);
        return modelMapper.map(card, CardDto.class);
    }

    /**
     * Retrieves a card from the repository based on the given card ID.
     *
     * @param cardId The ID of the card to retrieve.
     * @return The retrieved card.
     * @throws CardNotFoundException If the card with the specified ID is not found.
     */
    private Card getCardById(Integer cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));
    }

    /**
     * Maps a Card object to a CardDto object using ModelMapper.
     *
     * @param card The Card object to be mapped.
     * @return The mapped CardDto object.
     */
    private CardDto mapToDto(Card card) {
        return modelMapper.map(card, CardDto.class);
    }

    /**
     * Validates the length of a string.
     * If the string is not null and its length is greater than 1500 characters, an IllegalArgumentException is thrown.
     *
     * @param str        The string to be validated.
     * @param fieldName  The name of the field associated with the string.
     * @throws IllegalArgumentException If the string length exceeds the maximum length.
     */
    private void validateLength(String str, String fieldName) {
        if (str != null && str.length() > 1500) {
            throw new IllegalArgumentException(fieldName + " exceeds the maximum length");
        }
    }
}
