/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import smartyflip.modules.model.Module;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;


public interface ModuleRepository extends JpaRepository<Module, Long> {

    Module findByModuleId(Long moduleId);

    Stream<Module> findAllByDateCreatedBetween(LocalDate dateFrom, LocalDate dateTo);

    Stream<Module> findModulesByAuthorNameIgnoreCase(String authorName);

    Stream<Module> findModulesByModuleNameIgnoreCase(String moduleName);

    Stream<Module> findModulesByStackNameIgnoreCase(String stackName);

    @Query("select d from Module d inner join d.tags t where lower(t.tagName) = lower(:tagName)")
    Stream<Module> findAllByTags(Set<String> tagName);
}
