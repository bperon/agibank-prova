package br.com.agibank.service;

import static br.com.agibank.constants.Constants.DAT_FILE_EXTENSION;
import static java.lang.String.format;

import br.com.agibank.config.SalesProjectConfiguration;
import br.com.agibank.exception.SalesReportException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportExecutorService {
    private static final Logger LOGGER = LogManager.getLogger(ReportExecutorService.class.getName());

    private SalesProjectConfiguration config;
    private SalesReport salesReport;

    public ReportExecutorService(final SalesProjectConfiguration config, final SalesReport salesReport) {
        this.config = config;
        this.salesReport = salesReport;
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
            salesReport.setFiles(files);

            writeSalesReportDataToFile(outputFile, salesReport);
            LOGGER.debug("Report created in '{}'",  outputFile);
        } catch (SalesReportException e) {
            LOGGER.error("Error when generating report: {}", e.getMessage());
        }
    }

    private static List<File> listFiles(final Path dirPath) throws IOException {
        return Files.walk(dirPath)
                .map(Path::toString)
                .filter(file -> file.endsWith(DAT_FILE_EXTENSION) && !file.endsWith(".done".concat(DAT_FILE_EXTENSION)))
                .map(File::new)
                .collect(Collectors.toList());
    }

    private List<File> getFiles() {
        List<File> files = Collections.emptyList();
        try {
            files = listFiles(config.getInputFolder());
        } catch (IOException e) {
            LOGGER.error("Error when getting files from folder {}: {}", config.getInputFolder(), e.getMessage());
            throw new SalesReportException(e);
        }

        return files;
    }

    private void writeSalesReportDataToFile(final Path outputFile, final SalesReport salesReport) {
        final List<String> outputLines = new ArrayList<>();
        outputLines.add(format("Clients count: %d", salesReport.getClientCount()));
        outputLines.add(format("Salers count: %d", salesReport.getSalerCount()));
        outputLines.add(format("Most expensive sale ID: %d", salesReport.getMaxSaleId()));
        outputLines.add(format("Worst salesman: %s", salesReport.getWorstSalesman()));

        try {
            Files.write(outputFile, outputLines);
        } catch (IOException e) {
            throw new SalesReportException(e);
        }
    }
}
