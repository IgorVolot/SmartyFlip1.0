package ait.cohort34.smartyflip.card.dao;

import ait.cohort34.smartyflip.card.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
