/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.cards.service;

import smartyflip.cards.dto.CardDto;
import smartyflip.cards.model.Card;

public interface CardService {

    Card addCard(CardDto CardDto);

    CardDto findCardById(Integer cardId);

    CardDto editCard(Integer cardId, CardDto CardDto);

    CardDto deleteCard(Integer cardId);

    Iterable<CardDto> findCardsByDeckId(Integer deckId);
}
