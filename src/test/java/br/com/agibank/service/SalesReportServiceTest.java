package br.com.agibank.service;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SalesReportServiceTest {
    private final String fileTest1FilePath = this.buildFilePath("in/file1.dat");
    private final String fileTest2FilePath = this.buildFilePath("in/file2.dat");

    private SalesReportService salesReport;

    @Before
    public void setUp() {
        salesReport = new SalesReportService();
        final List<File> files = new ArrayList<>();
        files.add(new File(fileTest1FilePath));
        files.add(new File(fileTest2FilePath));
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

    private String buildFilePath(final String fileName) {
        return Paths.get("src","test", "resources", fileName).toAbsolutePath().toString();
    }
}
