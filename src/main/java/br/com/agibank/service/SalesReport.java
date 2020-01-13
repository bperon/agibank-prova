package br.com.agibank.service;

import java.io.File;
import java.util.List;

public interface SalesReport {
    int getClientCount();
    int getSalerCount();
    int getMaxSaleId();
    String getWorstSalesman();
    void setFiles(final List<File> files);
}
