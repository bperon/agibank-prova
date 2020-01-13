package br.com.agibank.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import br.com.agibank.exception.SalesReportException;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SalesReportServiceTest {
    private final String file1Path = this.buildFilePath("in/file1.dat");
    private final String file2Path = this.buildFilePath("in/file2.dat");

    private SalesReportService salesReport;

    @Before
    public void setUp() {
        salesReport = new SalesReportService();

        final List<File> files = new ArrayList<>();
        files.add(new File(file1Path));
        files.add(new File(file2Path));

        salesReport.setFiles(files);
    }

    @Test
    public void shouldGetClientCount() {
        final int clientCount = salesReport.getClientCount();
        assertTrue(clientCount != 0);
    }

    @Test
    public void shouldGetSalersCount() {
        final int salerCount = salesReport.getSalerCount();
        assertTrue(salerCount != 0);
    }

    @Test
    public void shouldGetMaxSaleId() {
        final int maxSaleId = salesReport.getMaxSaleId();
        assertTrue(maxSaleId != 0);
    }

    @Test
    public void shouldGetWorstSalesman() {
        final String worstSalesman = salesReport.getWorstSalesman();
        assertNotNull(worstSalesman);
    }

    @Test(expected = SalesReportException.class)
    public void shouldThrowExceptionWithMissingDataFromClient() {
        final String invalidFilePath = this.buildFilePath("in/missing_data_file.dat");
        final List<File> files = new ArrayList<>();
        files.add(new File(invalidFilePath));

        salesReport.setFiles(files);
        salesReport.getClientCount();
    }

    @Test(expected = SalesReportException.class)
    public void shouldThrowExceptionWithMissingDataFromSaler() {
        final String invalidFilePath = this.buildFilePath("in/missing_data_file.dat");
        final List<File> files = new ArrayList<>();
        files.add(new File(invalidFilePath));

        salesReport.setFiles(files);
        salesReport.getSalerCount();
    }

    private String buildFilePath(final String fileName) {
        return Paths.get("src","test", "resources", fileName).toAbsolutePath().toString();
    }
}
