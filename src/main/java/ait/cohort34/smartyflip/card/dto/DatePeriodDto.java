/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package ait.cohort34.smartyflip.card.dto;

import lombok.Getter;

import java.time.LocalDate;

/**
 * A simple data transfer object representing a date period.
 */
@Getter
public class DatePeriodDto {
    LocalDate dateFrom;
    LocalDate dateTo;
}
