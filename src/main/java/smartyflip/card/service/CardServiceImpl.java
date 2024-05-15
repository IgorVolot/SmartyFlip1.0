/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

    package smartyflip.card.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartyflip.card.dao.CardRepository;
import smartyflip.card.dto.CardDto;
import smartyflip.card.dto.NewCardDto;
import smartyflip.card.model.Card;
import smartyflip.card.model.enums.Level;
import smartyflip.card.service.exceptions.CardNotFoundException;
import smartyflip.card.service.exceptions.PayloadRequiredException;
import smartyflip.card.service.exceptions.PayloadTooLargeException;
import smartyflip.card.service.logging.CardLogger;
import smartyflip.modules.dao.ModuleRepository;
import smartyflip.modules.model.Module;
import smartyflip.modules.service.exceptions.ModuleNotFoundException;
import smartyflip.utils.PagedDataResponseDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Card findCardOrThrow(String id) {
        return cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
    }


    @Override
    public CardDto addCard(NewCardDto newCardDto) {
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
        int cardCount = module.getCardsAmount() + 1;
        module.setCardsAmount(cardCount);
        moduleRepository.save(module);
        cardRepository.save(card);
        return mapToDto(card);
    }


    @Override
    public CardDto findCardById(String cardId) {
        return mapToDto(getCardById(cardId));
    }

    @Override
    public CardDto editCard(String cardId, NewCardDto newCardDto) {
        Card card = getCardById(cardId);
        updateCard(newCardDto, card);
        cardRepository.save(card);
        return mapToDto(card);
    }

    @Override
    public boolean deleteCard(String cardId) {
        Card card = card = getCardById(cardId);
        if ( card == null || card.getId().isEmpty() ) {
            return false;
        }
        Module module = moduleRepository.findById(card.getModuleId()).orElseThrow(ModuleNotFoundException::new);
        cardRepository.deleteById(cardId);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<CardDto> findCardsByModuleId(Long moduleId) {
        if ( moduleId == null ) {
            return Collections.emptyList();
        }
        return cardRepository.findCardsByModuleId(moduleId)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PagedDataResponseDto<CardDto> findAllCards(PageRequest pageRequest) {
        Page<Card> cardPage = cardRepository.findAll(pageRequest);
        PagedDataResponseDto<CardDto> pagedDataResponseDto = new PagedDataResponseDto<>();
        pagedDataResponseDto.setData(cardPage.getContent().stream()
                .map((Card card) -> modelMapper.map(card, CardDto.class))
                .collect(Collectors.toList()));
        pagedDataResponseDto.setTotalElements(cardPage.getTotalElements());
        pagedDataResponseDto.setTotalPages(cardPage.getTotalPages());
        return pagedDataResponseDto;
    }

    @CardLogger
    @Override
    public void addLike(String id) {
        Card card = findCardOrThrow(id);
        card.addLike();
        cardRepository.save(card);
    }

    @CardLogger
    @Override
    public void addDislike(String id) {
        Card card = findCardOrThrow(id);
        card.addDislike();
        cardRepository.save(card);
    }


    @Override
    public Iterable<CardDto> findCardByTags(Set<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return Collections.emptyList();
        }
        return cardRepository
                .findAllByTagsInIgnoreCase(tags)
                .map(c -> modelMapper.map(c, CardDto.class))
                .collect(Collectors.toList());
    }



    private Card getCardById(String cardId) {
        return cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);
    }


    private CardDto mapToDto(Card card) {
        return modelMapper.map(card, CardDto.class);
    }


    private void validateLength(String str, String fieldName) {

        if ( str.isEmpty() ) {
            throw new PayloadRequiredException();
        }
        if ( str.length() > 2500 ) {
            throw new PayloadTooLargeException();
        }
    }

    private void updateCard(NewCardDto newCardDto, Card card) {
        String question = newCardDto.getQuestion();
        validateLength(question, "Question");
        card.setQuestion(question);

        String answer = newCardDto.getAnswer();
        validateLength(answer, "Answer");
        card.setAnswer(answer);

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
    }

}
