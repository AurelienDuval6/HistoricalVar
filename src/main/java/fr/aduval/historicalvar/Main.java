package fr.aduval.historicalvar;

import fr.aduval.historicalvar.args.ArgsParser;
import fr.aduval.historicalvar.args.ProgramArguments;
import fr.aduval.historicalvar.args.ProgramArgumentsValidator;
import fr.aduval.historicalvar.log.LoggerDecorator;
import fr.aduval.historicalvar.var.GetValueAtRisk;
import fr.aduval.historicalvar.var.GetValueAtRiskRequest;
import fr.aduval.historicalvar.var.ValueAtRisk;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        try {
            Main.start(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void start(String[] args) {
        ProgramArguments arguments = new ArgsParser().parse(args);

        if(arguments.showHelp()) return;

        if(arguments.isVerbose()) Main.enableLogging();

        ProgramArgumentsValidator programArgumentsValidator = new ProgramArgumentsValidator();

        programArgumentsValidator.validate(arguments);

        GetValueAtRiskRequest getValueAtRiskRequest = GetValueAtRiskRequest.builder()
                .startDate(arguments.getComputationDate().minusDays(arguments.getHistoricalDepth()))
                .endDate(arguments.getComputationDate())
                .percentile(arguments.getPercentile() / 100.)
                .path(arguments.getFilePath())
                .build();

        Optional<ValueAtRisk> valueAtRisk = new GetValueAtRisk().execute(getValueAtRiskRequest);

        System.out.println(valueAtRisk.orElse(null));
    }

    private static void enableLogging() {
        LoggerDecorator.getInstance().setVerbose(true);
    }

}
