/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
    Stream<Module> findAllByDateCreatedBetween(LocalDate dateFrom, LocalDate dateTo);
}
