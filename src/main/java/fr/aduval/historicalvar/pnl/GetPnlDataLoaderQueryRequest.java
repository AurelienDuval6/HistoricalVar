package fr.aduval.historicalvar.pnl;

import fr.aduval.historicalvar.abstractions.QueryRequest;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetPnlDataLoaderQueryRequest implements QueryRequest {

    private char escapeChar;
    private String datePattern;

}
