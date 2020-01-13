package br.com.agibank.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import br.com.agibank.config.SalesProjectConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(MockitoJUnitRunner.class)
public class ReportExecutorTest {
    private static final String RESOURCE_DIR = Paths.get("src","test","resources").toString();
    private static final String INPUT_DIR = Paths.get(RESOURCE_DIR,"in").toString();
    private static final String OUTPUT_DIR = Paths.get(RESOURCE_DIR,"out").toString();
    private static final String INVALID_FILES_DIR = Paths.get(RESOURCE_DIR, "invalid_files_dir").toString();

    @InjectMocks
    private ReportExecutorService reportExecutorService;

    @Mock
    private SalesProjectConfiguration configuration;

    @Mock
    private SalesReport salesReport;

    private Path outputFilePath;

    @Before
    public void setUp() {
        outputFilePath = Paths.get(OUTPUT_DIR, "report.done.dat").toAbsolutePath();
    }

    @Test
    public void shouldProcessReport() {
        when(configuration.getInputFolder()).thenReturn(Paths.get(INPUT_DIR));
        when(salesReport.getClientCount()).thenReturn(Integer.valueOf(randomNumeric(1)));
        when(salesReport.getMaxSaleId()).thenReturn(Integer.valueOf(randomNumeric(2)));
        when(salesReport.getSalerCount()).thenReturn(Integer.valueOf(randomNumeric(1)));
        when(salesReport.getWorstSalesman()).thenReturn(randomAlphabetic(10));
        when(configuration.getOutputFile()).thenReturn(outputFilePath);

        reportExecutorService.execute();

        verify(salesReport).getClientCount();
        verify(salesReport).getSalerCount();
        verify(salesReport).getMaxSaleId();
        verify(salesReport).getWorstSalesman();
        verify(configuration).getOutputFile();
    }

    @Test
    public void shouldNotProcessReportWithEmptyFolder() {
        when(configuration.getInputFolder()).thenReturn(Paths.get(INVALID_FILES_DIR));

        reportExecutorService.execute();

        verifyNoInteractions(salesReport);
        verify(configuration, never()).getOutputFile();
    }
}
