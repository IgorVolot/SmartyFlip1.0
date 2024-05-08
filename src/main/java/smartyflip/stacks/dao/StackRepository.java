/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import smartyflip.stacks.model.Stack;

public interface StackRepository extends JpaRepository<Stack, Integer> {
    Stack findByStackNameIgnoreCase(String stackName);
}
