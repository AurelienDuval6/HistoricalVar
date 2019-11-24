package fr.aduval.historicalvar.args;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProgramArgumentsValidatorTest {

    @Test
    public void shouldAcceptCorrectProgramArgument() {
        Path path = Mockito.mock(Path.class);
        File file = Mockito.mock(File.class);
        ProgramArguments programArguments = ProgramArguments.builder()
                .computationDate(LocalDate.MAX)
                .historicalDepth(1)
                .percentile(75)
                .filePath(path)
                .build();

        Mockito.doReturn(file).when(path).toFile();
        Mockito.doReturn(true).when(file).exists();

        ProgramArgumentsValidator programArgumentsValidator = new ProgramArgumentsValidator();

        programArgumentsValidator.validate(programArguments);
    }

    @Test
    public void shouldAcceptProgramArgumentWithZeroPercentileCoefficient() {
        Path path = Mockito.mock(Path.class);
        File file = Mockito.mock(File.class);
        ProgramArguments programArguments = ProgramArguments.builder()
                .computationDate(LocalDate.MAX)
                .historicalDepth(1)
                .percentile(0)
                .filePath(path)
                .build();

        Mockito.doReturn(file).when(path).toFile();
        Mockito.doReturn(true).when(file).exists();

        ProgramArgumentsValidator programArgumentsValidator = new ProgramArgumentsValidator();

        programArgumentsValidator.validate(programArguments);
    }

    @Test
    public void shouldAcceptProgramArgumentWith100PercentileCoefficient() {
        Path path = Mockito.mock(Path.class);
        File file = Mockito.mock(File.class);
        ProgramArguments programArguments = ProgramArguments.builder()
                .computationDate(LocalDate.MAX)
                .historicalDepth(1)
                .percentile(100)
                .filePath(path)
                .build();

        Mockito.doReturn(file).when(path).toFile();
        Mockito.doReturn(true).when(file).exists();

        ProgramArgumentsValidator programArgumentsValidator = new ProgramArgumentsValidator();

        programArgumentsValidator.validate(programArguments);
    }

    @Test
    public void shouldThrowWhenProgramArgumentHasNegativeHistoricalDepth() {
        Path path = Mockito.mock(Path.class);
        File file = Mockito.mock(File.class);
        ProgramArguments programArguments = ProgramArguments.builder()
                .computationDate(LocalDate.MAX)
                .historicalDepth(-1)
                .percentile(75)
                .filePath(path)
                .build();

        Mockito.doReturn(file).when(path).toFile();
        Mockito.doReturn(true).when(file).exists();

        ProgramArgumentsValidator programArgumentsValidator = new ProgramArgumentsValidator();

        assertThatThrownBy(() -> programArgumentsValidator.validate(programArguments))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowWhenProgramArgumentHasNegativePercentileCoef() {
        Path path = Mockito.mock(Path.class);
        File file = Mockito.mock(File.class);
        ProgramArguments programArguments = ProgramArguments.builder()
                .computationDate(LocalDate.MAX)
                .historicalDepth(1)
                .percentile(-1)
                .filePath(path)
                .build();

        Mockito.doReturn(file).when(path).toFile();
        Mockito.doReturn(true).when(file).exists();

        ProgramArgumentsValidator programArgumentsValidator = new ProgramArgumentsValidator();

        assertThatThrownBy(() -> programArgumentsValidator.validate(programArguments))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowWhenProgramArgumentHasPercentileCoefGreaterThan100() {
        Path path = Mockito.mock(Path.class);
        File file = Mockito.mock(File.class);
        ProgramArguments programArguments = ProgramArguments.builder()
                .computationDate(LocalDate.MAX)
                .historicalDepth(1)
                .percentile(101)
                .filePath(path)
                .build();

        Mockito.doReturn(file).when(path).toFile();
        Mockito.doReturn(true).when(file).exists();

        ProgramArgumentsValidator programArgumentsValidator = new ProgramArgumentsValidator();

        assertThatThrownBy(() -> programArgumentsValidator.validate(programArguments))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowWhenProgramArgumentHasNullPath() {
        ProgramArguments programArguments = ProgramArguments.builder()
                .computationDate(LocalDate.MAX)
                .historicalDepth(1)
                .percentile(100)
                .filePath(null)
                .build();

        ProgramArgumentsValidator programArgumentsValidator = new ProgramArgumentsValidator();

        assertThatThrownBy(() -> programArgumentsValidator.validate(programArguments))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowWhenProgramArgumentHasAnNonExistingFile() {
        Path path = Mockito.mock(Path.class);
        File file = Mockito.mock(File.class);
        ProgramArguments programArguments = ProgramArguments.builder()
                .computationDate(LocalDate.MAX)
                .historicalDepth(1)
                .percentile(100)
                .filePath(path)
                .build();

        Mockito.doReturn(file).when(path).toFile();
        Mockito.doReturn(false).when(file).exists();

        ProgramArgumentsValidator programArgumentsValidator = new ProgramArgumentsValidator();

        assertThatThrownBy(() -> programArgumentsValidator.validate(programArguments))
                .isInstanceOf(IllegalArgumentException.class);
    }


}