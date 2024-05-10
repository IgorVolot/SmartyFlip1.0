/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.configuration;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;


@Configuration
public class ServiceConfiguration {
    @Bean
    ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        try {
            Class<?> tagClass = Class.forName("smartyflip.decks.model.Tag");
            Converter converter = new AbstractConverter<Object, String>() {
                @Override
                protected String convert(Object source) {
                    try {
                        Method getTagName = source.getClass().getMethod("getTagName");
                        return (String) getTagName.invoke(source);
                    } catch (Exception e) {
                        // Handle reflection exceptions
                        return null;
                    }
                }
            };
            modelMapper.addConverter(converter, tagClass, String.class);
        } catch (ClassNotFoundException e) {
            // Handle the case where the Tag class isn't found
        }
        return modelMapper;
    }
}