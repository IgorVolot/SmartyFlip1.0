///*
// *
// *  *   *******************************************************************
// *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
// *  *   *******************************************************************
// *
// */
//
//package smartyflip.trialModules.dao;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import smartyflip.trialModules.model.TrialModule;
//
//import java.time.LocalDate;
//import java.util.Set;
//import java.util.stream.Stream;
//
//public interface TrialModuleRepository extends JpaRepository<TrialModule, Long> {
//    TrialModule findByModuleId(Long trialModuleId);
//
//    Stream<TrialModule> findAllByDateCreatedBetween(LocalDate dateFrom, LocalDate dateTo);
//
//    Stream<TrialModule> findTrialModulesByAuthorNameIgnoreCase(String authorName);
//
//    Stream<TrialModule> findTrialModulesByModuleNameIgnoreCase(String trialModuleName);
//
//    Stream<TrialModule> findTrialModulesByStackNameIgnoreCase(String stackName);
//
//    @Query("select d from TrialModule d inner join d.tags t where lower(t.tagName) = lower(:tagName)")
//    Stream<TrialModule> findAllByTags(Set<String> tagName);
//}
