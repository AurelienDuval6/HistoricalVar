package fr.aduval.historicalvar.pnl;

import fr.aduval.historicalvar.abstractions.NominalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Profit and Losses : The balance of a portfolio for a given range of time
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class Pnl extends NominalType<BigDecimal> implements Comparable<Pnl> {
    protected Pnl(BigDecimal value) {
        super(value);
    }

    public static Pnl of(BigDecimal value) {
        return Optional.ofNullable(value)
                .map(Pnl::new)
                .orElse(null);
    }

    @Override
    public int compareTo(Pnl other) {
        return value.compareTo(other.value);
    }
}
