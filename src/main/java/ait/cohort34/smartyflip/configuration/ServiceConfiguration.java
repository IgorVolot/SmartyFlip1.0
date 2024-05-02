/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package ait.cohort34.smartyflip.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ServiceConfiguration is a class that provides configuration for service beans in the application.
 */
@Configuration
public class ServiceConfiguration {
    /**
     * Returns a configured instance of ModelMapper.
     * <p>
     * This method initializes and configures a new instance of ModelMapper with the following settings:
     * - Field matching is enabled
     * - Field access level is set to private
     * - Matching strategy is set to strict
     *
     * @return a configured instance of ModelMapper
     */
    @Bean
    ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}