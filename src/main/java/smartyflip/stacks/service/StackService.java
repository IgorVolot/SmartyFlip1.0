/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.service;

import smartyflip.stacks.dto.StackDto;

public interface StackService {

    StackDto addStack(StackDto stackDto);

//    boolean removeStack(String stackName);

    boolean removeStack(Long stackId);

//    StackDto findStackByName(String stackName);

    StackDto findStackById(Long stackId);

//    StackDto editStack(String stackName, StackDto stackDto);

    StackDto editStack(Long stackId, StackDto stackDto);

    Iterable<String> getAllStacks();

    int getModulesAmount(String stackName);
}
