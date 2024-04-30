/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package ait.cohort34.smartyflip.card.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "cardId")
@Entity
@Table(name = "card")
public class Card {

    @Id
    Long cardId;
    String question;
    String answer;
    LocalDateTime lastUpdate = LocalDateTime.now();
    String level;
    Integer likes;
    boolean bookmark;
    Long moduleId;
}
