package br.com.agibank.config;

import static br.com.agibank.constants.Constants.USER_HOME_PROPERTY_KEY;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class SalesProjectConfiguration {
    private static final String HOME_PATH = System.getProperty(USER_HOME_PROPERTY_KEY);
    private static final String OUTPUT_FOLDER_RELATIVE_PATH = Paths.get("data", "out").toString();
    private static final String INPUT_FOLDER_RELATIVE_PATH = Paths.get("data", "in").toString();

    @Value("${REPORT_FILENAME:report.done.dat}")
    private String reportFilename;

    @Value("${NUMBER_OF_THREADS:10}")
    private String numberOfThreads;

    public String getReportFilename() {
        return reportFilename;
    }

    public String getNumberOfThreads() {
        return numberOfThreads;
    }

    public Path getOutputFile() {
        return Paths.get(HOME_PATH,OUTPUT_FOLDER_RELATIVE_PATH, reportFilename);
    }

    public Path getInputFolder() {
        return Paths.get(HOME_PATH, INPUT_FOLDER_RELATIVE_PATH);
    }
}
