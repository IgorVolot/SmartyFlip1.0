/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dto;

import lombok.Getter;
import smartyflip.modules.model.Tag;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class NewModuleDto {

    String moduleName;
    String userName;
    String stackName;
    Set<Tag> tags;

    public Collection<Long> getTagIds() {
        return tags.stream()
                .map(Tag::getTagId)
                .collect(Collectors.toList());
    }

    public Collection<String> getTagNames() {
        return tags.stream()
                .map(Tag::getTagName)
                .collect(Collectors.toList());
    }
}
