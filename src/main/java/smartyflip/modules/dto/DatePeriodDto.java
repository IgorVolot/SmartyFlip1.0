/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DatePeriodDto {

    LocalDate dateFrom;

    LocalDate dateTo;
}
