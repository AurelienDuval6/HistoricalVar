package fr.aduval.historicalvar.data.csv;

import fr.aduval.historicalvar.helpers.BigDecimalBuilder;
import fr.aduval.historicalvar.pnl.Pnl;
import fr.aduval.historicalvar.pnl.PnlData;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PnlDataCsvMapperTest {

    @Test
    public void shouldReturnPnlData() {
        String date = "2019-03-04";
        String pnl = "-8451321.5451";
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .toFormatter();

        PnlDataCsvMapper pnlDataCsvMapper = PnlDataCsvMapper.builder()
                .dateTimeFormatter(formatter)
                .build();

        CSVRecord csvRecord = Mockito.mock(CSVRecord.class);

        Mockito.doReturn(date).when(csvRecord).get(0);
        Mockito.doReturn(pnl).when(csvRecord).get(1);
        Mockito.doReturn(2).when(csvRecord).size();

        PnlData pnlData = pnlDataCsvMapper.transform(csvRecord);

        PnlData expectedPnlData = PnlData.builder()
                .pnl(Pnl.of(BigDecimalBuilder.of(pnl)))
                .creationDate(LocalDate.parse(date, formatter))
                .build();
        assertThat(pnlData).isEqualTo(expectedPnlData);
    }

    @Test
    public void shouldThrowWhenCsvRecordSizeIsGreaterThan2() {
        PnlDataCsvMapper pnlDataCsvMapper = PnlDataCsvMapper.builder().build();

        CSVRecord csvRecord = Mockito.mock(CSVRecord.class);
        Mockito.doReturn(3).when(csvRecord).size();

        assertThatThrownBy(() -> pnlDataCsvMapper.transform(csvRecord))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowWhenCsvRecordSizeIsSmallerThan2() {
        PnlDataCsvMapper pnlDataCsvMapper = PnlDataCsvMapper.builder().build();

        CSVRecord csvRecord = Mockito.mock(CSVRecord.class);
        Mockito.doReturn(1).when(csvRecord).size();

        assertThatThrownBy(() -> pnlDataCsvMapper.transform(csvRecord))
                .isInstanceOf(IllegalArgumentException.class);
    }

}