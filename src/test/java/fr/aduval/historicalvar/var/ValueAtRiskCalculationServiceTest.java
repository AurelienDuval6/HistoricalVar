package fr.aduval.historicalvar.var;

import fr.aduval.historicalvar.pnl.Pnl;
import fr.aduval.historicalvar.pnl.PnlData;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueAtRiskCalculationServiceTest {

    @Test
    public void shouldReturnEmptyValueWhenPercentileIsGreaterThan1() {
        ValueAtRiskCalculationService valueAtRiskCalculationService = ValueAtRiskCalculationService.builder()
                .endDate(LocalDate.MAX.minusDays(1))
                .startDate(LocalDate.MAX.minusDays(8))
                .percentile(1.2)
                .build();
        List<PnlData> pnlDataList = new ArrayList<>();

        Optional<ValueAtRisk> result = valueAtRiskCalculationService.calculate(pnlDataList);

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldReturnEmptyValueWhenPercentileIsNegative() {
        ValueAtRiskCalculationService valueAtRiskCalculationService = ValueAtRiskCalculationService.builder()
                .endDate(LocalDate.MAX.minusDays(1))
                .startDate(LocalDate.MAX.minusDays(8))
                .percentile(-1)
                .build();
        List<PnlData> pnlDataList = new ArrayList<>();

        Optional<ValueAtRisk> result = valueAtRiskCalculationService.calculate(pnlDataList);

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldReturnEmptyValueWhenGivenListIsEmpty() {
        ValueAtRiskCalculationService valueAtRiskCalculationService = ValueAtRiskCalculationService.builder()
                .endDate(LocalDate.MAX.minusDays(1))
                .startDate(LocalDate.MAX.minusDays(8))
                .percentile(0.75)
                .build();
        List<PnlData> pnlDataList = new ArrayList<>();

        Optional<ValueAtRisk> result = valueAtRiskCalculationService.calculate(pnlDataList);

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldReturnPnl() {
        ValueAtRiskCalculationService valueAtRiskCalculationService = ValueAtRiskCalculationService.builder()
                .endDate(LocalDate.MAX.minusDays(1))
                .startDate(LocalDate.MAX.minusDays(8))
                .percentile(0.75)
                .build();
        PnlData pnlData = PnlData.builder()
                .creationDate(LocalDate.MAX.minusDays(5))
                .pnl(Pnl.of(BigDecimal.TEN))
                .build();

        List<PnlData> pnlDataList = Collections.singletonList(pnlData);

        Optional<ValueAtRisk> result = valueAtRiskCalculationService.calculate(pnlDataList);

        assertThat(result).contains(ValueAtRisk.of(pnlData.getPnl().unwrap()));
    }

}