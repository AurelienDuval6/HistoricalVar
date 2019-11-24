package fr.aduval.historicalvar.var;

import fr.aduval.historicalvar.abstractions.NominalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ValueAtRisk extends NominalType<BigDecimal> {
    protected ValueAtRisk(BigDecimal value) {
        super(value);
    }

    public static ValueAtRisk of(BigDecimal value) {
        return Optional.ofNullable(value)
                .map(ValueAtRisk::new)
                .orElse(null);
    }
}
