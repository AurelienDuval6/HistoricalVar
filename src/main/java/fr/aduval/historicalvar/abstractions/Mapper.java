package fr.aduval.historicalvar.abstractions;

/**
 *
 * Definition of how data should be mapped between two different types
 *
 * @param <Source> the source entity
 * @param <Destination> the transformed entity
 */
@FunctionalInterface
public interface Mapper<Source, Destination> {
    Destination transform(Source source);
}
