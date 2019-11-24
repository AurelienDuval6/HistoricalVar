package fr.aduval.historicalvar.abstractions;

/**
 * Definition of how an object should be validated
 * @param <T> The object you need to validate
 */
@FunctionalInterface
public interface Validator<T> {

    void validate(T object);

}
