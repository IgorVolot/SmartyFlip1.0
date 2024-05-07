/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.tags.service;

import smartyflip.tags.dto.TagDto;
import smartyflip.tags.model.Tag;

import java.util.Set;

public interface TagService {
    TagDto addTag(Integer deckId, TagDto tag);

    boolean deleteTag(Integer deckId, String tag);

    Set<TagDto> findTagsInDeck(Integer deckId);

    Set<Tag> getAllTags();
}
