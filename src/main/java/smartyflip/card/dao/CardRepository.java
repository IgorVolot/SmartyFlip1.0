/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import smartyflip.card.model.Card;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public interface CardRepository extends MongoRepository<Card, String> {

    Stream<Card> findCardsByModuleId(Long moduleId);

    Stream<Card> findAllByTagsInIgnoreCase(Set<String> tags);

    Stream<Card> findAllByTags(Set<String> tags);
}
