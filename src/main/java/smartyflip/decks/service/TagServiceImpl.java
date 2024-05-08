/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartyflip.decks.dao.DeckRepository;
import smartyflip.decks.dao.TagRepository;
import smartyflip.decks.model.Deck;
import smartyflip.decks.model.Tag;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    final TagRepository tagRepository;
    final ModelMapper modelMapper;
    final DeckRepository deckRepository;

    @Override
    public boolean addTag(Integer deckId, String tag) {
        Optional<Deck> optionalDeck = deckRepository.findById(deckId);
        if ( optionalDeck.isPresent() ) {
            Deck deck = optionalDeck.get();
            Set<Tag> tags = deck.getTags();
            Tag newTag = new Tag(tag);
            tags.add(newTag);
            deck.setTags(tags);
            deckRepository.save(deck);
            tagRepository.save(new Tag(tag));
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteTag(Integer deckId, String tag) {
        Optional<Deck> optionalDeck = deckRepository.findById(deckId);
        if ( optionalDeck.isPresent() ) {
            Deck deck = optionalDeck.get();
            Set<Tag> tags = deck.getTags();
            tags.removeIf(t -> t.getTag().equals(tag));
            deck.setTags(tags);
            deckRepository.save(deck);
            return true;
        }
        return false;
    }

    @Override
    public Set<Tag> findTagsByDeckId(Integer deckId) {
        Optional<Deck> optionalDeck = deckRepository.findById(deckId);
        if ( optionalDeck.isPresent() ) {
            Deck deck = optionalDeck.get();
            return deck.getTags();
        }
        return new HashSet<>();
    }
}
