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
        card.setLikes(card.getLikes() + 1);
        cardRepository.save(card);

    }

    @Override
    public CardDto editCard(NewCardDto newCardDto) {
        Card card = cardRepository.findById(newCardDto.getCardId()).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));
        card.setQuestion(newCardDto.getQuestion());
        card.setAnswer(newCardDto.getAnswer());
        card.setLevel(newCardDto.getLevel());
        card.setModuleId(newCardDto.getModuleId());
        card.setLastUpdate(LocalDateTime.now());
        card.setLikes(0);
        card.setBookmark(false);
        cardRepository.save(card);
        return modelMapper.map(card, CardDto.class);
    }

    @Override
    public void editBookmark(Long cardId, boolean bookmark) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));
        card.setBookmark(bookmark);
        cardRepository.save(card);
    }

    @Override
    public CardDto deleteCard(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found"));
        cardRepository.deleteById(cardId);
        return modelMapper.map(card, CardDto.class);
    }

}
