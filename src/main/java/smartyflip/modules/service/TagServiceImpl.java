/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import smartyflip.modules.dao.ModuleRepository;
import smartyflip.modules.dao.TagRepository;
import smartyflip.modules.model.Module;
import smartyflip.modules.model.Tag;
import smartyflip.modules.service.exceptions.ModuleNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    final TagRepository tagRepository;

    final ModelMapper modelMapper;

    final ModuleRepository moduleRepository;

    final EntityManager entityManager;

    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper, ModuleRepository moduleRepository, EntityManager entityManager) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
        this.moduleRepository = moduleRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Iterable<String> addTagsToModule(Long moduleId, Iterable<String> tags) {
        Module module = moduleRepository.findByModuleId(moduleId);

        if (module == null) {
            throw new EntityNotFoundException("Module with id: " + moduleId + " not found.");
        }

        Set<Tag> moduleTags = module.getTags();

        for (String tagName : tags) {
            Tag tag = insertIfNotExist(tagName);
            moduleTags.add(tag);
        }

        module.setTags(moduleTags);
        moduleRepository.save(module);

        return moduleTags.stream().map(Tag::getTagName).collect(Collectors.toList());
    }

    @Transactional
    public Tag insertIfNotExist(String tagName) {
        Tag existingTag = tagRepository.findByTagName(tagName);
        if ( existingTag == null ) {
            Tag newTag = new Tag(tagName);
            tagRepository.save(newTag);
            return newTag;
        } else {
            return existingTag;
        }
    }

    @Override
    public boolean deleteTag(Long moduleId, String tag) {
        Optional<Module> optionalModule = moduleRepository.findById(moduleId);
        if ( optionalModule.isPresent() ) {
            Module module = optionalModule.get();
            Set<Tag> tags = module.getTags();
            tags.removeIf(t -> t.getTagName().equals(tag));
            module.setTags(tags);
            moduleRepository.save(module);
            return true;
        }
        return false;
    }

    @Override
    public Iterable<String> findTagsByModuleId(Long moduleId) {
        List<String> tagList = new ArrayList<>();
        Optional<Module> optionalModule = moduleRepository.findById(moduleId);
        if ( optionalModule.isPresent() ) {
            Module module = optionalModule.get();
            module.getTags().forEach(tag -> tagList.add(tag.getTagName()));
        }
        return tagList;
    }

    @Override
    public Iterable<String> findAllTags() {
        return tagRepository.findAll().stream().map(Tag::getTagName).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean deleteTag(long tagId) {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if ( optionalTag.isEmpty() ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found");
        }
        Tag tag = optionalTag.get();
        List<Module> modules = moduleRepository.findAllByTags(Collections.singleton(tag.getTagName())).toList();
        for ( Module module : modules ) {
            Set<Tag> tags = module.getTags();
            tags.removeIf(t -> t.getTagName().equals(tag.getTagName()));
            module.setTags(tags);
            moduleRepository.save(module);
        }
        tagRepository.delete(tag);
        return true;
    }
}
