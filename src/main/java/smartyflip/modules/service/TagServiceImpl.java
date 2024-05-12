/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import smartyflip.modules.dao.ModuleRepository;
import smartyflip.modules.dao.TagRepository;
import smartyflip.modules.model.Module;
import smartyflip.modules.model.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    final TagRepository tagRepository;
    final ModelMapper modelMapper;
    final ModuleRepository moduleRepository;

    // FIXME
//    @Transactional
//    public Iterable<String> addTagToModule(Long moduleId, String tagName) {
//        Module module = moduleRepository.findById(moduleId).orElseThrow(ModuleNotFoundException::new);
//        Tag tag = new Tag(tagName.trim().toLowerCase().replaceAll("\\s+", "_"));
//        try {
//            if ( !module.getTags().contains(tag) ) {
//                module.getTags().add(tag);
//                tagRepository.save(tag);
//                moduleRepository.save(module);
//            }
//        } catch (Exception ex) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag already exists in this module", ex);
//        }
//        return module.getTags().stream().map(Tag::getTagName).collect(Collectors.toList());
//    }

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
}
