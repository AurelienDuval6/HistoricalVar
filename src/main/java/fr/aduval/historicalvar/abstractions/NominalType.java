package fr.aduval.historicalvar.abstractions;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Optional;

/**
 * A nominal type is a user friendly wrapper that prevents its user to
 * match two irrelevant types together, as it is in the real world.
 *
 * Example:
 *      In the real world, we cannot add 2 apples and 3 potatoes.
 *      However, both of them are numbers.
 *      As a Java program only sees 2 numbers, the addition is possible between
 *      apples and potatoes.
 *
 * @param <T> The wrapped type
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public abstract class NominalType<T> {

    protected T value;

    public T unwrap() {
        return value;
    }

    public String toString() {
        return Optional.ofNullable(value)
                .map(T::toString)
                .orElse(null);
    }

}
