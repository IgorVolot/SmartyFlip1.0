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
import org.springframework.stereotype.Repository;
import smartyflip.modules.model.Module;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    Module findByModuleId(Long moduleId);

    Stream<Module> findAllByDateCreatedBetween(LocalDate dateFrom, LocalDate dateTo);

    Stream<Module> findModulesByUserNameIgnoreCase(String user);

    Stream<Module> findModulesByModuleNameIgnoreCase(String moduleName);

    Stream<Module> findModulesByStackNameIgnoreCase(String stackName);

    @Query("select m from Module m inner join m.tags t where lower(t.tagName) = lower(:tagName)")
    Stream<Module> findAllByTags(Set<String> tagName);

}
