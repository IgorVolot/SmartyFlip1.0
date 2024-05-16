/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smartyflip.stacks.model.Stack;

@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {
    Stack findByStackNameIgnoreCase(String stackName);
}
