/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package ait.cohort34.smartyflip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The SmartyFlipApplication class is the main class for running the SmartyFlip application.
 * It is annotated with @SpringBootApplication to indicate that it is a Spring Boot application.
 */
@SpringBootApplication
public class SmartyFlipApplication {

    /**
     * The main method is the entry point for the SmartyFlip application. It runs the SpringApplication by calling
     * the SpringApplication.run() method.
     *
     * @param args the command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(SmartyFlipApplication.class, args);
    }

}
