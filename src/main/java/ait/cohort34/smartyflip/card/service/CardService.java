package ait.cohort34.smartyflip.card.service;

import ait.cohort34.smartyflip.card.dto.CardDto;
import ait.cohort34.smartyflip.card.dto.NewCardDto;

public interface CardService {
    CardDto addCard(NewCardDto newCardDto);
    CardDto findCardById(Long cardId);
    void addLike (Long cardId);
    CardDto editCard(NewCardDto newCardDto);
    void editBookmark(Long cardId, boolean bookmark);
    CardDto deleteCard(Long cardId);
}
