package fr.aduval.historicalvar.args;

import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;
import java.time.LocalDate;

@Builder
public class ProgramArguments {
    @Getter
    private LocalDate computationDate;
    @Getter
    private long historicalDepth;
    @Getter
    private long percentile;
    @Getter
    private boolean verbose;
    @Getter
    private Path filePath;
    private boolean showHelp;

    public boolean showHelp() {
        return showHelp;
    }
}
