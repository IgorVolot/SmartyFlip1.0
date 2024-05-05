/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.cards.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * A simple data transfer object representing a date period.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DatePeriodDto {
    LocalDate dateFrom;
    LocalDate dateTo;
}
