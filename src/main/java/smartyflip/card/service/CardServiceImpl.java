/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartyflip.card.dao.CardRepository;
import smartyflip.card.dto.CardDto;
import smartyflip.card.dto.NewCardDto;
import smartyflip.card.model.Card;
import smartyflip.card.model.enums.Level;
import smartyflip.card.service.exceptions.CardNotFoundException;
import smartyflip.modules.dao.ModuleRepository;
import smartyflip.modules.model.Module;
import smartyflip.modules.service.exceptions.ModuleNotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final ModuleRepository moduleRepository;

    private final ModelMapper modelMapper;


    @Override
    public CardDto addCard(NewCardDto newCardDto){

        // Validation for required fields
        validateLength(newCardDto.getQuestion(), "Question");
        validateLength(newCardDto.getAnswer(), "Answer");
        validateLength(newCardDto.getLevel(), "Level");

        // Check for the Module id
        Module module = moduleRepository.findById(newCardDto.getModuleId()).orElseThrow(ModuleNotFoundException::new);

        // A new Card creation
        Card card = modelMapper.map(newCardDto, Card.class);
        card.setLevel(Level.fromString(newCardDto.getLevel()));
        card.setDateCreated(LocalDateTime.now());
        card.setModuleName(module.getModuleName().trim().toLowerCase().replaceAll("\\s+", "_"));
        card.setModule(module);
        cardRepository.save(card);
        return mapToDto(card);
    }


    @Override
    public CardDto findCardById(Long cardId) {
        return mapToDto(getCardById(cardId));
    }

    @Override
    public CardDto editCard(Long cardId, NewCardDto newCardDto) {
        Card card = getCardById(cardId);
        updateCard(newCardDto, card);
        cardRepository.save(card);
        return mapToDto(card);
    }

    @Override
    public CardDto deleteCard(Long cardId) {
        Card card = getCardById(cardId);
        cardRepository.deleteById(cardId);
        return mapToDto(card);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<CardDto> findCardsByModuleId(Long moduleId){
        if ( moduleId == null ) {
            return Collections.emptyList();
        }
        return cardRepository.findCardsByModuleId(moduleId)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    private Card getCardById(Long cardId) {
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

        String levelStr = newCardDto.getLevel();
        if ( levelStr != null ) {
            Level level = Level.valueOf(levelStr.toUpperCase());
            card.setLevel(level);
        }

        Long moduleId = newCardDto.getModuleId();
        card.setModuleId(moduleId);

        Optional<Module> moduleOpt = moduleRepository.findById(moduleId);
        if ( moduleOpt.isPresent() ) {
            String moduleName = moduleOpt.get().getModuleName().trim().toLowerCase().replaceAll("\\s+", "_");
            card.setModuleName(moduleName);
        }

        card.setDateCreated(LocalDateTime.now());
    }

}
