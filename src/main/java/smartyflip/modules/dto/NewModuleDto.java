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

import java.util.Set;

@Getter
public class NewModuleDto {

    String moduleName;
    String authorName;
    String stackName;
    Set<Tag> tags;

}
