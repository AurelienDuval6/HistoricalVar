package fr.aduval.historicalvar.var;

import fr.aduval.historicalvar.helpers.BigDecimalBuilder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class GetValueAtRiskSteps {

    private static final String DATASETS_ROOT_FOLDER = "datasets/";
    private Optional<ValueAtRisk> result;
    private Exception exception;

    private GetValueAtRiskRequest request = GetValueAtRiskRequest.builder().build();
    private int historicalDepth;

    @Given("a PnL history CSV file located at {string}")
    public void aPnLHistoryCSVFileLocatedAt(String path) throws URISyntaxException {
        request = request.toBuilder()
                .path(
                        Paths.get(
                                ClassLoader
                                        .getSystemResource(DATASETS_ROOT_FOLDER + path)
                                        .toURI()
                        )
                )
                .build();
    }

    @Given("a computation date set to ([0-9]{4}-[0-9]{2}-[0-9]{2})")
    public void aComputationDateSetTo(String computationDate) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .toFormatter();

        request = request.toBuilder()
                .endDate(LocalDate.parse(computationDate, formatter))
                .build();
    }

    @Given("an historical depth of ([0-9]+) days")
    public void anHistoricalDepthOfHistoricalDepthDays(int historicalDepth) {
        this.historicalDepth = historicalDepth;
    }

    @Given("a percentile coefficient of ([0-9]+)")
    public void aPercentileCoefficientOf(int coef) {
        request = request.toBuilder()
                .percentile(coef / 100.)
                .build();
    }

    @When("I want to calculate the historical value at risk")
    public void iWantToCalculateTheHistoricalValueAtRisk() {

        request = request.toBuilder()
                .startDate(request.getEndDate().minusDays(historicalDepth))
                .build();

        try {
            result = new GetValueAtRisk().execute(request);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the historical value at risk is ([+-]?[0-9]*[.]?[0-9]+|null)")
    public void theHistoricalValueAtRiskIs(String expectedVar) {
        Optional<ValueAtRisk> expected = "null".equals(expectedVar) ?
                Optional.empty()
                : Optional.of(ValueAtRisk.of(BigDecimalBuilder.of(expectedVar)));
        assertThat(result).isEqualTo(expected);
    }

    @Then("the historical value is rejected with the message {string}")
    public void theHistoricalValueAtRiskIsRejectedWithTheMessage(String message) {
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage())
                .isEqualTo(message);
    }
}
