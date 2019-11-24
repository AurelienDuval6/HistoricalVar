package fr.aduval.historicalvar.data.file;

import fr.aduval.historicalvar.abstractions.DataLoader;
import fr.aduval.historicalvar.log.LoggerDecorator;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Builder
public class FileDataLoader<T> {

    private DataLoader<T> loader;

    public List<T> load(Path path) {
        Reader reader;
        try {
            reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            LoggerDecorator.getInstance().error(log, "Failed to load file", e);
            throw new IllegalArgumentException("Failed to load file", e);
        }

        return loader.load(reader);
    }

}
