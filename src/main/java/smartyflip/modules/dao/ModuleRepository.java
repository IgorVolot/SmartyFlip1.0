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
import smartyflip.accounting.model.UserAccount;
import smartyflip.modules.model.Module;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    Module findByModuleId(Long moduleId);

    Stream<Module> findAllByDateCreatedBetween(LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query("select m from Module m where LOWER(m.userAccount.username) = LOWER(:username)")
    Stream<Module> findModulesByUserAccountIgnoreCase(String username);

    Stream<Module> findModulesByModuleNameIgnoreCase(String moduleName);

    Stream<Module> findModulesByStackNameIgnoreCase(String stackName);

    @Query("select m from Module m inner join m.tags t where lower(t.tag) = lower(:tagName)")
    Stream<Module> findAllByTags(Set<String> tagName);

    Stream<Module> findModulesByUserAccount(UserAccount userAccount);
}
