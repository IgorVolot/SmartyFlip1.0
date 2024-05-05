/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ModuleDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer moduleId;

    private String moduleName;

    private String authorName;

    private LocalDateTime dateCreated;

    private String stackName;

    private boolean isPublic;

    private int cardsCount;

    @Singular
    Set<String> tags;

//    public void increaseCardsCount() {
//        cardsCount++;
//    }
}
