/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.service;

import smartyflip.decks.dto.DeckDto;
import smartyflip.decks.model.Deck;
import smartyflip.decks.model.Tag;

import java.util.Set;


public interface TagService {

    //    void addTagToDeck(Integer deckId, Integer tagId);
    Iterable<String> addTagToDeck(Integer deckId, String tagName);

    boolean deleteTag(Integer deckId, String tag);

    Iterable<String> findTagsByDeckId(Integer deckId);
}
