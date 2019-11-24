package fr.aduval.historicalvar.helpers;

import fr.aduval.historicalvar.log.LoggerDecorator;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * A user-friendly BigDecimal builder that displays a more expressive message
 */
@Slf4j
public class BigDecimalBuilder {

    public static BigDecimal of(String number) {
        try {
            return new BigDecimal(number);
        } catch(NumberFormatException e) {
            LoggerDecorator.getInstance().error(log, "Cannot convert '" + number + "' to a number");
            throw new IllegalArgumentException("Cannot convert '" + number + "' to a number");
        }
    }

}
