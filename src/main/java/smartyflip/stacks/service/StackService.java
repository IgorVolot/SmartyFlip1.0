/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.service;

import smartyflip.stacks.dto.StackDto;
import smartyflip.stacks.model.Stack;

import java.util.List;

public interface StackService {

    StackDto addStack(StackDto stackDto);

    boolean removeStack(String stackName);

    StackDto findStackByName(String stackName);

    StackDto editStack(String stackName, StackDto stackDto);

    List<Stack> getAllStacks();

    int getStackDecksCount(String stackName);
}
