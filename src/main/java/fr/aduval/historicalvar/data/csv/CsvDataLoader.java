package fr.aduval.historicalvar.data.csv;

import fr.aduval.historicalvar.abstractions.DataLoader;
import fr.aduval.historicalvar.log.LoggerDecorator;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Builder
public class CsvDataLoader<T> implements DataLoader<T> {

    public static final String FAILED_TO_LOAD_CSV_DATA_ERROR_MESSAGE = "Failed to load CSV data : ";
    private CsvRecordMapper<T> mapper;
    private char escapeChar;

    @Override
    public List<T> load(Reader reader) {
        try {
            return processRecords(reader);
        } catch (Exception e) {
            LoggerDecorator.getInstance().error(log, FAILED_TO_LOAD_CSV_DATA_ERROR_MESSAGE + e.getMessage(), e);
            throw new IllegalStateException(FAILED_TO_LOAD_CSV_DATA_ERROR_MESSAGE + e.getMessage(), e);
        }
    }

    private List<T> processRecords(Reader reader) throws IOException {
        CSVFormat csvFormat = CSVFormat.newFormat(escapeChar);
        CSVParser records = csvFormat.parse(reader);

        return records.getRecords().stream()
                .map(mapper::transform)
                .collect(Collectors.toList());
    }

}
