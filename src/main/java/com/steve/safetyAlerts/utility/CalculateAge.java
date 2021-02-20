package com.steve.safetyAlerts.utility;

import org.apache.logging.log4j.LogManager;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class CalculateAge {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(CalculateAge.class);


    public static int personAge(String birthdate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, dateTimeFormatter);
        LocalDate currentDate = LocalDate.now();
        logger.info("CalculateAge: age retourner");
        return Period.between(birthDate, currentDate).getYears();
    }
}
