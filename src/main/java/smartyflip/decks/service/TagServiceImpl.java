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
import org.springframework.transaction.annotation.Transactional;
import smartyflip.decks.dao.DeckRepository;
import smartyflip.decks.dao.TagRepository;
import smartyflip.decks.model.Deck;
import smartyflip.decks.model.Tag;
import smartyflip.decks.service.exceptions.DeckNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    final TagRepository tagRepository;
    final ModelMapper modelMapper;
    final DeckRepository deckRepository;

    @Transactional
    public Iterable<String> addTagToDeck(Integer deckId, String tagName) {
        Deck deck = deckRepository.findById(deckId).orElseThrow(DeckNotFoundException::new);
        Tag tag = new Tag(tagName);
        if ( deck.getTags().stream().anyMatch(t -> t.getTagName().equals(tag.getTagName())) ) {
            throw new IllegalArgumentException("Tag already exists in deck");
        }
        deck.getTags().add(tag);
        tagRepository.save(tag);
        deckRepository.save(deck);
        return deck.getTags().stream().map(Tag::getTagName).collect(Collectors.toList());
    }

//    @Transactional
//    public void addTagToDeck(Integer deckId, String tagName) {
//        Deck deck = deckRepository.findById(deckId).orElseThrow(DeckNotFoundException::new);
//        Tag tag = new Tag(tagName);
//        if (deck.getTags().contains(tag)) {
//            throw new IllegalArgumentException("Tag already exists in deck");
//        }
//        deck.getTags().add(tag);
//        tagRepository.save(tag);
//        deckRepository.save(deck);
//    }

//    @Transactional
//    public void addTagToDeck(Integer deckId, Integer tagId) {
//        Deck deck = deckRepository.findById(deckId).orElseThrow(DeckNotFoundException::new);
//        Tag tag = tagRepository.findById(tagId).orElseThrow();
//
//        deck.getTags().add(tag);
//        tag.getDecks().add(deck);
//
//        deckRepository.save(deck);
//        tagRepository.save(tag);
//    }


    @Override
    public boolean deleteTag(Integer deckId, String tag) {
        Optional<Deck> optionalDeck = deckRepository.findById(deckId);
        if ( optionalDeck.isPresent() ) {
            Deck deck = optionalDeck.get();
            Set<Tag> tags = deck.getTags();
            tags.removeIf(t -> t.getTagName().equals(tag));
            deck.setTags(tags);
            deckRepository.save(deck);
            return true;
        }
        return false;
    }

    @Override
    public Iterable<String> findTagsByDeckId(Integer deckId) {
        List<String> tagList = new ArrayList<>();
        Optional<Deck> optionalDeck = deckRepository.findById(deckId);
        if ( optionalDeck.isPresent() ) {
            Deck deck = optionalDeck.get();
            deck.getTags().forEach(tag -> tagList.add(tag.getTagName()));
        }
        return tagList;
    }
}
