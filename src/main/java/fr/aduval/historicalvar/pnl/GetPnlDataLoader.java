package fr.aduval.historicalvar.pnl;

import fr.aduval.historicalvar.abstractions.DataLoader;
import fr.aduval.historicalvar.abstractions.Query;
import fr.aduval.historicalvar.data.csv.CsvDataLoader;
import fr.aduval.historicalvar.data.csv.PnlDataCsvMapper;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class GetPnlDataLoader implements Query<DataLoader<PnlData>, GetPnlDataLoaderQueryRequest> {

    @Override
    public DataLoader<PnlData> execute(GetPnlDataLoaderQueryRequest request) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern(request.getDatePattern())
                .toFormatter();
        PnlDataCsvMapper mapper = PnlDataCsvMapper.builder()
                .dateTimeFormatter(dateTimeFormatter)
                .build();

        return CsvDataLoader.<PnlData>builder()
                .mapper(mapper)
                .escapeChar(request.getEscapeChar())
                .build();
    }

}
