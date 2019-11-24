package fr.aduval.historicalvar.var;

import fr.aduval.historicalvar.abstractions.QueryRequest;
import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;
import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
public class GetValueAtRiskRequest implements QueryRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private double percentile;
    private Path path;
}
