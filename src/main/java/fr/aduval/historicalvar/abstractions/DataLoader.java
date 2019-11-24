package fr.aduval.historicalvar.abstractions;

import java.io.Reader;
import java.util.List;

/**
 * Definition of how external data should be loaded
 *
 * @param <T> Type of the data that will be extracted
 */
@FunctionalInterface
public interface DataLoader<T> {
    List<T> load(Reader reader);
}
