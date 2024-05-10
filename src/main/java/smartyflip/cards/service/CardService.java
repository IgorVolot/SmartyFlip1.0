/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.cards.service;

import smartyflip.cards.dto.CardDto;
import smartyflip.cards.dto.NewCardDto;

public interface CardService {

    CardDto addCard(NewCardDto newCardDto);

    CardDto findCardById(Integer cardId);

    CardDto editCard(Integer cardId, NewCardDto newCardDto);

    CardDto deleteCard(Integer cardId);

    Iterable<CardDto> findCardsByDeckId(Integer deckId);
}
