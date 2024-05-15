/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartyflip.card.dao.CardRepository;
import smartyflip.card.dao.TagRepository;
import smartyflip.card.dto.CardDto;
import smartyflip.card.model.Card;
import smartyflip.card.model.Tag;
import smartyflip.utils.PagedDataResponseDto;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public Collection<String> addTagsToCard(String cardId, List<String> tags) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found"));
        Set<String> existingTags = card.getTags();
        existingTags.addAll(tags);
        card.setTags(existingTags);
        cardRepository.save(card);
        return card.getTags();
    }

    @Override
    public boolean deleteTag(String cardId, String tag) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found"));
        Set<String> existingTags = card.getTags();
        existingTags.remove(tag);
        card.setTags(existingTags);
        cardRepository.save(card);
        return !card.getTags().contains(tag);
    }

    @Override
    public Collection<String> findTagsByCardId(String cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found"));
        return card.getTags();
    }

    @Transactional(readOnly = true)
    @Override
    public PagedDataResponseDto<CardDto> findCardsByTagIds(String tag, PageRequest pageRequest) {
    }

    @Override
    public Collection<String> findAllTags() {
        return tagRepository.findAll().stream()
                .map(Tag::getTagName)
                .collect(Collectors.toList());
    }

//    @Override
//    public boolean deleteTag(String tagId) {
//        return false;
//    }
}
