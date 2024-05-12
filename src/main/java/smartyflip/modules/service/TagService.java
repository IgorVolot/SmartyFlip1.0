/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service;


public interface TagService {

    // FIXME
//    Iterable<String> addTagToModule(Long moduleId, String tagName);

    boolean deleteTag(Long moduleId, String tag);

    Iterable<String> findTagsByModuleId(Long moduleId);

    Iterable<String> findAllTags();
}
