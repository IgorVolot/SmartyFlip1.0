/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.dto;

import lombok.*;
import smartyflip.decks.dto.DeckDto;
import smartyflip.decks.model.Deck;

import java.util.Set;

@Getter
@EqualsAndHashCode(of = "stackName")
public class StackDto {

    Integer stackId;

    String stackName;

    Set<DeckDto> decks;
}
