package fr.aduval.historicalvar.var;

import fr.aduval.historicalvar.abstractions.DataLoader;
import fr.aduval.historicalvar.abstractions.Query;
import fr.aduval.historicalvar.data.file.FileDataLoader;
import fr.aduval.historicalvar.pnl.GetPnlDataLoader;
import fr.aduval.historicalvar.pnl.GetPnlDataLoaderQueryRequest;
import fr.aduval.historicalvar.pnl.PnlData;

import java.util.List;
import java.util.Optional;

/**
 * Calculates the historical value at risk from a CSV dataset
 *
 * @see ValueAtRiskCalculationService
 */
public class GetValueAtRisk implements Query<Optional<ValueAtRisk>, GetValueAtRiskRequest> {

    @Override
    public Optional<ValueAtRisk> execute(GetValueAtRiskRequest riskRequest) {
        GetPnlDataLoaderQueryRequest pnlDataLoaderQueryRequest = GetPnlDataLoaderQueryRequest.builder()
                .datePattern("yyyy-MM-dd")
                .escapeChar(';')
                .build();
        DataLoader<PnlData> pnlDataDataLoader = new GetPnlDataLoader().execute(pnlDataLoaderQueryRequest);


        FileDataLoader<PnlData> pnlDataFileDataLoader = FileDataLoader.<PnlData>builder()
                .loader(pnlDataDataLoader)
                .build();

        List<PnlData> pnlDataList = pnlDataFileDataLoader.load(riskRequest.getPath());

        ValueAtRiskCalculationService valueAtRiskCalculationService =
                ValueAtRiskCalculationService.builder()
                        .startDate(riskRequest.getStartDate())
                        .endDate(riskRequest.getEndDate())
                        .percentile(riskRequest.getPercentile())
                        .build();

        return valueAtRiskCalculationService.calculate(pnlDataList);
    }
}
