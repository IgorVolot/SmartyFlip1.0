/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package ait.cohort34.smartyflip.card.service;

import ait.cohort34.smartyflip.card.dto.CardDto;
import ait.cohort34.smartyflip.card.dto.NewCardDto;

public interface CardService {

    CardDto addCard(NewCardDto newCardDto);

    CardDto findCardById(Long cardId);

    void addLike(Long cardId);

    CardDto editCard(Long cardId, NewCardDto newCardDto);

    CardDto editBookmark(Long cardId, boolean bookmark, CardDto cardDto);

    CardDto deleteCard(Long cardId);
}
