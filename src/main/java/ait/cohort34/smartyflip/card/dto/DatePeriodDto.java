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
    /**
     * The start date of a date period.
     *
     * <p>
     * This variable represents the start date of a date period. It is of type {@link LocalDate}.
     * The value of this variable is useful for defining the beginning of a date range or date interval.
     * </p>
     *
     * @see DatePeriodDto
     * @since 1.0.0
     */
    LocalDate dateFrom;
    /**
     * Represents the end date of a date period.
     */
    LocalDate dateTo;
}
