/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package ait.cohort34.smartyflip.card.dao;

import ait.cohort34.smartyflip.card.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface represents a repository for managing Card objects.
 */
public interface CardRepository extends JpaRepository<Card, Integer> {

}
