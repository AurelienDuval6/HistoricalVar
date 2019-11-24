package fr.aduval.historicalvar.data.csv;

import fr.aduval.historicalvar.abstractions.Mapper;
import org.apache.commons.csv.CSVRecord;

public interface CsvRecordMapper<T> extends Mapper<CSVRecord, T> {
}
