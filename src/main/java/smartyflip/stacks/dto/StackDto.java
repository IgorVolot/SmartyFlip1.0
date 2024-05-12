/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import smartyflip.modules.dto.ModuleDto;

import java.util.Set;

@Getter
@EqualsAndHashCode(of = "stackName")
public class StackDto {

    Long stackId;

    String stackName;

    Set<ModuleDto> modules;
}
