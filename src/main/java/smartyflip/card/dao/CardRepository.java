/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.dao;

import smartyflip.card.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface CardRepository extends JpaRepository<Card, Long> {

    Stream<Card> findCardsByModuleId(Long moduleId);
}
