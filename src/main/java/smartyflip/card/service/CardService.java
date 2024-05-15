/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.service;

import org.springframework.data.domain.PageRequest;
import smartyflip.card.dto.CardDto;
import smartyflip.card.dto.NewCardDto;
import smartyflip.utils.PagedDataResponseDto;

import java.util.Set;

public interface CardService {

    CardDto addCard(NewCardDto newCardDto);

    CardDto findCardById(String cardId);

    CardDto editCard(String cardId, NewCardDto newCardDto);

    boolean deleteCard(String cardId);

    Iterable<CardDto> findCardsByModuleId(Long moduleId);

    PagedDataResponseDto<CardDto> findAllCards(PageRequest pageRequest);

    Iterable<CardDto> findCardByTags(Set<String> tag);


    void addLike(String id);

    void addDislike(String id);
}
