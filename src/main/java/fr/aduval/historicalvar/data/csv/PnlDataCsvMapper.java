package fr.aduval.historicalvar.data.csv;

import fr.aduval.historicalvar.helpers.BigDecimalBuilder;
import fr.aduval.historicalvar.log.LoggerDecorator;
import fr.aduval.historicalvar.pnl.Pnl;
import fr.aduval.historicalvar.pnl.PnlData;
import fr.aduval.historicalvar.pnl.PnlDataMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Builder
public class PnlDataCsvMapper implements CsvRecordMapper<PnlData>, PnlDataMapper<CSVRecord> {

    private DateTimeFormatter dateTimeFormatter;

    @Override
    public PnlData transform(CSVRecord csvRecord) {

        if(csvRecord.size() != 2) {
            LoggerDecorator.getInstance().error(log, "Could not parse CSV record at line " + csvRecord.getRecordNumber());
            throw new IllegalArgumentException("Could not parse CSV record at line " + csvRecord.getRecordNumber());
        }

        LocalDate date = LocalDate.parse(csvRecord.get(0), dateTimeFormatter);
        BigDecimal pnl = BigDecimalBuilder.of(csvRecord.get(1));

        return PnlData.builder()
                .creationDate(date)
                .pnl(Pnl.of(pnl))
                .build();
    }
}
