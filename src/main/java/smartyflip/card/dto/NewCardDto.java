/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.dto;

import lombok.Getter;
import smartyflip.card.model.Tag;


import java.util.Set;

@Getter
public class NewCardDto {

    Long moduleId;

    String question;

    String answer;

    String level;

    Set<Tag> tags;
}
