package app;

import com.beust.jcommander.Parameter;

public class Args {

    @Parameter(names = {"-input", "-in", "-i"},
            description = "Input file path",
            required = true)
    private String inputFilePath;

    public String getInputFilePath() {
        return inputFilePath;
    }
}
