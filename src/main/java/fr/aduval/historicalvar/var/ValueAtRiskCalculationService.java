package fr.aduval.historicalvar.var;

import fr.aduval.historicalvar.pnl.Pnl;
import fr.aduval.historicalvar.pnl.PnlData;
import lombok.Builder;
import org.apache.commons.lang3.Range;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Domain service used to calculate an historical value at risk from a range of date
 * and a given percentile coefficient symbolizing the risk margin you want to assume
 *
 */
@Builder
public class ValueAtRiskCalculationService {

    private LocalDate startDate;
    private LocalDate endDate;
    private double percentile;

    /**
     * Calculates the historical value at risk from a dated PnL list
     *
     * @param pnlDataList an unsorted dated PnL list
     * @return the historical value at risk
     */
    public Optional<ValueAtRisk> calculate(List<PnlData> pnlDataList) {
        if(!Range.between(0., 1.).contains(percentile)) return Optional.empty();

        List<Pnl> sortedPnls = pnlDataList.stream()
                .filter(pnlData -> startDate.minusDays(1).isBefore(pnlData.getCreationDate()))
                .filter(pnlData -> endDate.plusDays(1).isAfter(pnlData.getCreationDate()))
                .map(PnlData::getPnl)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        if(sortedPnls.isEmpty()) return Optional.empty();

        int percentileIndex = (int) (sortedPnls.size() * percentile) - 1;
        percentileIndex = Math.max(percentileIndex, 0);

        BigDecimal valueAtRisk = sortedPnls.get(percentileIndex).unwrap();

        return Optional.of(ValueAtRisk.of(valueAtRisk));
    }

}
