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
import smartyflip.utils.PagedDataResponseDto;

import java.util.Collection;
import java.util.List;

public interface TagService {

    Collection<String> addTagsToCard(String cardId, List<String> tags);

    boolean deleteTag(String cardId, String tag);

    Collection<String> findTagsByCardId(String cardId);

    //    Iterable<CardDto> findCardsByTagIds(String tag);
    PagedDataResponseDto<CardDto> findCardsByTagIds(String tag, PageRequest pageRequest);

    Collection<String> findAllTags();

//    boolean deleteTag(String tagId);
}
