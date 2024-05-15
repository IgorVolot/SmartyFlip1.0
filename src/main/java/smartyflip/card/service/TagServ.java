///*
// *
// *  *   *******************************************************************
// *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
// *  *   *******************************************************************
// *
// */
//
//package smartyflip.card.service;
//
//import jakarta.persistence.EntityManager;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.server.ResponseStatusException;
//import smartyflip.card.dao.CardRepository;
//import smartyflip.card.model.Card;
//import smartyflip.card.dao.TagRepository;
//import smartyflip.card.model.Tag;
//import smartyflip.card.service.exceptions.CardNotFoundException;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class TagServiceImpl implements TagService {
//
//    final TagRepository tagRepository;
//
//    final CardRepository cardRepository;
//
//    final ModelMapper modelMapper;
//
////    final ModuleRepository moduleRepository;
//
//    final EntityManager entityManager;
//
//    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper, CardRepository cardRepository, EntityManager entityManager) {
//        this.tagRepository = tagRepository;
//        this.modelMapper = modelMapper;
//        this.cardRepository = cardRepository;
//        this.entityManager = entityManager;
//    }
//
//    @Transactional
//    @Override
//    public Iterable<String> addTagsToCard(String cardId, Iterable<String> tags) {
//        Card card = cardRepository.findById(cardId);
//
//        if (card == null) {
//            throw new CardNotFoundException();
//        }
//
//        Set<String> cardTags = card.getTagIds();
//
//        for (String tagName : tags) {
//            Tag tag = insertIfNotExist(tagName);
//            cardTags.add(String.valueOf(tag));
//        }
//
//        card.setTagIds(cardTags);
//        cardRepository.save(card);
//
//        return cardTags.stream().map(Tag::getTagName).collect(Collectors.toList());
//    }
//
//    @Transactional
//    public Tag insertIfNotExist(String tagName) {
//        Tag existingTag = tagRepository.findByTagName(tagName);
//        if ( existingTag == null ) {
//            Tag newTag = new Tag(tagName);
//            tagRepository.save(newTag);
//            return newTag;
//        } else {
//            return existingTag;
//        }
//    }
//
//    @Override
//    public boolean deleteTag(String cardId, String tag) {
//        Optional<Card> optionalCard = cardRepository.findById(cardId);
//        if ( optionalCard.isPresent() ) {
//            Card card = optionalCard.get();
//            Set<String> tags = card.getTagIds();
//            tags.removeIf(t -> t.getTagName().equals(tag));
//            card.setTagIds(tags);
//            cardRepository.save(card);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public Iterable<String> findTagsByCardId(String cardId) {
//        List<String> tagList = new ArrayList<>();
//        Optional<Card> optionalCard = cardRepository.findById(cardId);
//        if ( optionalCard.isPresent() ) {
//            Card card = optionalCard.get();
//            Card.getTagIds().forEach(tag -> tagList.add(tag.getTagName()));
//        }
//        return tagList;
//    }
//
//    @Override
//    public Iterable<String> findAllTags() {
//        return tagRepository.findAll().stream().map(Tag::getTagName).collect(Collectors.toList());
//    }
//
//    @Transactional
//    @Override
//    public boolean deleteTag(String tagId) {
//        Optional<Tag> optionalTag = tagRepository.findById(tagId);
//        if ( optionalTag.isEmpty() ) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found");
//        }
//        Tag tag = optionalTag.get();
//        List<Card> cards = cardRepository.findAllByTags(Collections.singleton(tag.getTagName())).toList();
//        for ( Card card : cards ) {
//            Set<Tag> tags = card.getTagIds();
//            tags.removeIf(t -> t.getTagName().equals(tag.getTagName()));
//            card.setTagIds(tags);
//            cardRepository.save(card);
//        }
//        tagRepository.delete(tag);
//        return true;
//    }
//}
