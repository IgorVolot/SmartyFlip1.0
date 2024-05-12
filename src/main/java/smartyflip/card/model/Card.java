/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.model;

import jakarta.persistence.*;
import lombok.*;
import smartyflip.card.model.enums.Level;
import smartyflip.modules.model.Module;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cardId;

    String question;

    String answer;

    LocalDateTime dateCreated;

    @Enumerated(EnumType.STRING)
    private Level level;

    Long moduleId;

    String moduleName;

    @ManyToOne
    @JoinColumn(name = "module_module_id")
    Module module;

    public long modulesAmount() {
        if(module != null) {
            return module.getCardsAmount();
        } else {
            // Default value
            return 0;
        }
    }
}
