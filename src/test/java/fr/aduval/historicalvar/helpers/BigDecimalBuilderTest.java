package fr.aduval.historicalvar.helpers;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BigDecimalBuilderTest {

    @Test
    public void shouldBuildBigDecimalFromString() {
        String largeNumber = "78965128485564654655.878515";
        BigDecimal bigDecimal = new BigDecimal(largeNumber);

        assertThat(BigDecimalBuilder.of(largeNumber))
                .isEqualTo(bigDecimal);
    }

    @Test
    public void shouldThrowWhenGivenNumberHasIncorrectFormat() {
        String largeNumber = "78965E128485564654655.878515";

        assertThatThrownBy(() -> BigDecimalBuilder.of(largeNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowNullPointerWhenGivenNumberIsNull() {

        assertThatThrownBy(() -> BigDecimalBuilder.of(null))
                .isInstanceOf(NullPointerException.class);
    }

}