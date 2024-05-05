/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class NewDeckDto {

    String deckName;
    String authorName;
    Set<String> tags;
    String stackName;
    boolean isPublic;

}
