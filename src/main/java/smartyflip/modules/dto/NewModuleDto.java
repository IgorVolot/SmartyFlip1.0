/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class NewModuleDto {

    String moduleName;
    String authorName;
    Set<String> tags;
    String stackName;
    boolean isPublic;

}
