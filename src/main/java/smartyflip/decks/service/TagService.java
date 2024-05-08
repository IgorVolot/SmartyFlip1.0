/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.service;

import smartyflip.decks.model.Tag;


public interface TagService {
    boolean addTag(Integer deckId, String tag);

    boolean deleteTag(Integer deckId, String tag);

    Iterable<Tag> findTagsByDeckId(Integer deckId);

}
