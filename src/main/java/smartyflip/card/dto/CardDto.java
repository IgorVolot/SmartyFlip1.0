/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CardDto {

    private String id;

    private String question;

    private String answer;

    private String level;

    private Long moduleId;

    private String moduleName;

    private LocalDateTime dateCreated;

    @Singular
    private Set<String> tags;

    private Integer likes;

    private Integer dislike;
}
