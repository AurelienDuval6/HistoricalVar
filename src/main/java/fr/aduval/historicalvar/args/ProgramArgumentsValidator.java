package fr.aduval.historicalvar.args;

import fr.aduval.historicalvar.abstractions.Validator;
import fr.aduval.historicalvar.log.LoggerDecorator;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
public class ProgramArgumentsValidator implements Validator<ProgramArguments> {

    public static final String GIVEN_PERCENTILE_COEFFICIENT_OUT_OF_BOUNDS_ERROR_MESSAGE = "Given percentile coefficient is out of bounds [0;100]";
    public static final String NEGATIVE_HISTORICAL_DEPTH_ERROR_MESSAGE = "Given historical depth is negative";
    public static final String FILE_NOT_FOUND_ERROR_MESSAGE = "Input file does not exist";

    @Override
    public void validate(ProgramArguments programArguments) {

        Optional<File> inputFile = Optional.ofNullable(programArguments.getFilePath())
                .map(Path::toFile);

        if(!inputFile.map(File::exists).orElse(false)) {
            LoggerDecorator.getInstance().error(log, FILE_NOT_FOUND_ERROR_MESSAGE);
            throw new IllegalArgumentException(FILE_NOT_FOUND_ERROR_MESSAGE);
        }

        else if(programArguments.getHistoricalDepth() < 0) {
            LoggerDecorator.getInstance().error(log, NEGATIVE_HISTORICAL_DEPTH_ERROR_MESSAGE);
            throw new IllegalArgumentException(NEGATIVE_HISTORICAL_DEPTH_ERROR_MESSAGE);
        }

        else if (programArguments.getPercentile() < 0
                || programArguments.getPercentile() > 100) {
            LoggerDecorator.getInstance().error(log, GIVEN_PERCENTILE_COEFFICIENT_OUT_OF_BOUNDS_ERROR_MESSAGE);
            throw new IllegalArgumentException(GIVEN_PERCENTILE_COEFFICIENT_OUT_OF_BOUNDS_ERROR_MESSAGE);
        }

    }
}
