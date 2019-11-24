package fr.aduval.historicalvar.pnl;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * PnL data for a given day
 *
 * @see Pnl
 */
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class PnlData {

    private LocalDate creationDate;
    private Pnl pnl;

}
