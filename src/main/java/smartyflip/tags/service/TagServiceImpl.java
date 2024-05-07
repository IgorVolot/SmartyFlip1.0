/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.tags.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartyflip.decks.dao.DeckRepository;
import smartyflip.tags.dao.TagRepository;
import smartyflip.tags.dto.TagDto;
import smartyflip.tags.model.Tag;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    final TagRepository tagRepository;
    @Autowired
    private DeckRepository deckRepository;

    final ModelMapper modelMapper;

    @Override
    public TagDto addTag(Integer deckId, TagDto tag) {
        if ( tagRepository.existsById(tag.getTag()) ) {
            throw new IllegalArgumentException("Tag already exists");
        }
        Tag newTag = modelMapper.map(tag, Tag.class);
        tagRepository.save(newTag);
        return modelMapper.map(newTag, TagDto.class);
    }

    @Override
    public boolean deleteTag(Integer deckId, String tag) {
        if( !tagRepository.existsById(tag) ) {
            return false;
        }
        tagRepository.deleteById(tag);
        return true;
    }

    @Override
    public Set<TagDto> findTagsInDeck(Integer deckId) {
        return Set.of();
    }

    @Override
    public Set<Tag> getAllTags() {
        return new HashSet<>(tagRepository.findAll());
    }
}
