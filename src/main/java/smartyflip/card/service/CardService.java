/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.service;

import smartyflip.card.dto.CardDto;
import smartyflip.card.dto.NewCardDto;

public interface CardService {

    CardDto addCard(NewCardDto newCardDto);

    CardDto findCardById(Long cardId);

    CardDto editCard(Long cardId, NewCardDto newCardDto);

    CardDto deleteCard(Long cardId);

    Iterable<CardDto> findCardsByModuleId(Long moduleId);
}
