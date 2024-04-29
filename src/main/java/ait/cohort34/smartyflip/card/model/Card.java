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
    LocalDateTime lastUpdate;
    String level;
    Integer likes;
    boolean bookmark;
    Long moduleId;

    @ManyToOne
    Module module;
}
