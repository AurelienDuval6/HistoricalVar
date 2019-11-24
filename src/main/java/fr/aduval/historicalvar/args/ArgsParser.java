package fr.aduval.historicalvar.args;

import fr.aduval.historicalvar.log.LoggerDecorator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Definition of the command line arguments of our program
 *
 * @see fr.aduval.historicalvar.var.GetValueAtRisk for more informations
 */
@Slf4j
public class ArgsParser {

    public Options getOptions() {
        final Option computationDateOption = Option.builder("c")
                .longOpt("computation-date") //
                .desc("The date (YYYY-MM-DD) which will serve as base for VAR computation")
                .hasArg(true)
                .argName("computation-date")
                .required(true)
                .build();

        final Option beforeOption = Option.builder("d")
                .longOpt("historical-depth")
                .desc("Number of days before the computation date that will be used to build the history")
                .type(Number.class)
                .hasArg(true)
                .argName("historical-depth")
                .required(true)
                .build();

        final Option percentileOption = Option.builder("p")
                .longOpt("percentile")
                .desc("Define n value to adjust the potential loss chance (number between 1 and 100)")
                .argName("percentile")
                .type(Number.class)
                .hasArg(true)
                .required(true)
                .build();

        final Option fileOption = Option.builder("f")
                .longOpt("file")
                .desc("Defines path to an input file")
                .argName("file")
                .hasArg(true)
                .required(true)
                .build();

        final Option verboseOption = Option.builder("v")
                .longOpt("verbose")
                .desc("Activates the verbose mode")
                .argName("verbose")
                .hasArg(false)
                .required(false)
                .build();

        final Option helpOption = Option.builder("h")
                .longOpt("help")
                .desc("Displays help")
                .argName("help")
                .hasArg(false)
                .required(false)
                .build();

        final Options options = new Options();

        options.addOption(computationDateOption);
        options.addOption(beforeOption);
        options.addOption(percentileOption);
        options.addOption(verboseOption);
        options.addOption(fileOption);
        options.addOption(helpOption);

        return options;
    }

    public ProgramArguments parse(String[] args) {
        try {
            return parseArguments(args);
        } catch (ParseException e) {
            LoggerDecorator.getInstance().error(log, "Failed to parse command line : " + e.getMessage(), e);
            throw new IllegalArgumentException("Failed to parse command line : " + e.getMessage(), e);
        }
    }

    public ProgramArguments parseArguments(String[] args) throws ParseException {
        final CommandLineParser parser = new DefaultParser();
        final CommandLine line = parser.parse(getOptions(), args);

        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .toFormatter();

        if(line.hasOption("help")) {
            showHelpMessage();

            return ProgramArguments.builder()
                    .showHelp(true)
                    .build();
        }

        return ProgramArguments.builder()
                .computationDate(LocalDate.parse(line.getOptionValue("computation-date"), dateTimeFormatter))
                .historicalDepth((Long)line.getParsedOptionValue("historical-depth"))
                .percentile((Long) line.getParsedOptionValue("percentile"))
                .verbose(line.hasOption("verbose"))
                .filePath(Paths.get(line.getOptionValue("file")))
                .build();
    }

    private void showHelpMessage() {
        HelpFormatter formatter = new HelpFormatter();

        final PrintWriter writer = new PrintWriter(System.out);
        formatter.printUsage(writer,256,"HistoricalVar", getOptions());
        writer.flush();
    }

}
