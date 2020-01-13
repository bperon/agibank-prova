package br.com.agibank.executor;

import static br.com.agibank.constants.Constants.DAT_FILE_EXTENSION;
import static java.lang.String.format;

import br.com.agibank.config.SalesProjectConfiguration;
import br.com.agibank.exception.SalesReportException;
import br.com.agibank.service.SalesReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalesReportExecutor {
    private static final Logger LOGGER = LogManager.getLogger(SalesReportExecutor.class.getName());

    private SalesProjectConfiguration config;
    private SalesReport salesReportService;

    public SalesReportExecutor(final SalesProjectConfiguration config, final SalesReport salesReportService) {
        this.config = config;
        this.salesReportService = salesReportService;
    }

    public void execute() {
        try {
            final List<File> files = getFiles();
            if (files.isEmpty()) {
                LOGGER.info("There are no files in '{}'.", config.getInputFolder());
                return;
            }

            LOGGER.debug("Total files: {}.", files.size());
            final Path outputFile = config.getOutputFile();
            salesReportService.setFiles(files);

            writeSalesReportDataToFile(outputFile);
            LOGGER.debug("Report created in '{}'",  outputFile);
        } catch (SalesReportException e) {
            LOGGER.error("Error when generating report: {}", e.getMessage());
        }
    }

    private List<File> getFiles() {
        List<File> files;

        try {
            files = listFiles(config.getInputFolder());
        } catch (IOException e) {
            LOGGER.error("Error when getting files from folder {}: {}", config.getInputFolder(), e.getMessage());
            throw new SalesReportException(e);
        }

        return files;
    }

    private static List<File> listFiles(final Path dirPath) throws IOException {
        return Files.walk(dirPath)
                .map(Path::toString)
                .filter(file -> file.endsWith(DAT_FILE_EXTENSION) && !file.endsWith(".done".concat(DAT_FILE_EXTENSION)))
                .map(File::new)
                .collect(Collectors.toList());
    }

    private void writeSalesReportDataToFile(final Path outputFile) {
        final List<String> outputLines = new ArrayList<>();
        outputLines.add(format("Clients count: %d", salesReportService.getClientCount()));
        outputLines.add(format("Salers count: %d", salesReportService.getSalerCount()));
        outputLines.add(format("Most expensive sale ID: %d", salesReportService.getMaxSaleId()));
        outputLines.add(format("Worst salesman: %s", salesReportService.getWorstSalesman()));

        try {
            Files.write(outputFile, outputLines);
        } catch (IOException e) {
            throw new SalesReportException(e);
        }
    }
}
