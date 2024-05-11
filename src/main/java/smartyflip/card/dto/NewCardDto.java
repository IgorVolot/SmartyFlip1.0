/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.dto;

import lombok.Getter;

@Getter
public class NewCardDto {

    String question;

    String answer;

    String level;

    Long moduleId;
}
