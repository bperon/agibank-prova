package br.com.agibank.service;

import static br.com.agibank.constants.Constants.CLIENT_IDENTIFIER;
import static br.com.agibank.constants.Constants.COLUMN_DELIMITER;
import static br.com.agibank.constants.Constants.COMMA;
import static br.com.agibank.constants.Constants.EMPTY;
import static br.com.agibank.constants.Constants.HYPHEN;
import static br.com.agibank.constants.Constants.PRODUCT_SALE_REGEX;
import static br.com.agibank.constants.Constants.SALER_IDENTIFIER;
import static br.com.agibank.constants.Constants.SALE_IDENTIFIER;
import static java.lang.String.format;

import br.com.agibank.exception.SalesReportException;
import br.com.agibank.model.Client;
import br.com.agibank.model.Product;
import br.com.agibank.model.Sale;
import br.com.agibank.model.Saler;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesReportService implements SalesReport {
    private List<File> files = Collections.emptyList();

    @Override
    public void setFiles(final List<File> files) {
        this.files = files;
    }

    @Override
    public int getClientCount() {
        final List<Client> clients = getAllClients(files);
        return clients.size();
    }

    @Override
    public int getSalerCount() {
        final List<Saler> salers = getAllSalers(files);
        return salers.size();
    }

    @Override
    public int getMaxSaleId() {
        Double highestValue = 0.0;
        Integer saleId = 0;

        for (final Sale sale : getAllSales(files)) {
            final Double sum = this.sumAllSaleItems(sale);

            if (sum > highestValue) {
                highestValue = sum;
                saleId = sale.getSaleId();
            }
        }

        return saleId;
    }

    @Override
    public String getWorstSalesman() {
        Double lowestValue = -1.0;
        String saler = EMPTY;

        for (final Sale sale : getAllSales(files)) {
            final Double sum = this.sumAllSaleItems(sale);

            if (lowestValue == -1.0 || sum < lowestValue) {
                lowestValue = sum;
                saler = sale.getSalesmanName();
            }
        }

        return saler;
    }

    private List<String> getRecordsByIdentifier(final List<File> files, final String identifier) {
        final List<String> linesTotal = new ArrayList<>();
        List<String> fileLines;
        for (final File file : files) {
            try {
                fileLines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)
                        .stream()
                        .filter(line -> line.split(COLUMN_DELIMITER)[0].trim().startsWith(identifier))
                        .collect(Collectors.toList());

                linesTotal.addAll(linesTotal.size(), fileLines);
            } catch (IOException e) {
                throw new SalesReportException(e);
            }
        }

        return linesTotal;
    }

    private List<Client> getAllClients(final List<File> files) {
        return getRecordsByIdentifier(files, CLIENT_IDENTIFIER).stream()
                .map(line -> Arrays.asList(line.split(COLUMN_DELIMITER)))
                .map(this::buildClient)
                .collect(Collectors.toList());
    }

    private List<Saler> getAllSalers(final List<File> files) {
        return getRecordsByIdentifier(files, SALER_IDENTIFIER).stream()
                .map(line -> Arrays.asList(line.split(COLUMN_DELIMITER)))
                .map(this::buildSaler)
                .collect(Collectors.toList());
    }

    private List<Sale> getAllSales(final List<File> files) {
        return getRecordsByIdentifier(files, SALE_IDENTIFIER).stream()
                .map(line -> Arrays.asList(line.split(COLUMN_DELIMITER)))
                .map(this::buildSale)
                .collect(Collectors.toList());
    }

    private List<Product> getProducts(final List<String> productsData) {
        return Arrays.stream(productsData.get(0)
                .replaceAll(PRODUCT_SALE_REGEX, EMPTY)
                .split(COMMA))
                .map(this::buildProducts)
                .collect(Collectors.toList());
    }

    private Double sumAllSaleItems(final Sale sale) {
        return sale.getProducts()
                .stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    private Sale buildSale(final List<String> lineData) {
        final int saleId = Integer.parseInt(lineData.get(1));
        final List<Product> products = getProducts(Collections.singletonList(lineData.get(2)));
        String salesmanName = lineData.get(3);

        if (lineData.size() > 4){
            final StringBuilder sb = new StringBuilder();
            for (int i=3; i < lineData.size(); i++) sb.append(lineData.get(i));

            salesmanName = sb.toString();
        }

        return new Sale(saleId, products, salesmanName);
    }

    private Saler buildSaler(final List<String> lineData) {
        if (lineData.size() < 4) {
            throw new SalesReportException(format("Missing data in line %s", lineData));
        }

        final String cpf = lineData.get(1);
        String name;
        Double salary;

        if (lineData.size() == 4) {
            name = lineData.get(2);
            salary = Double.valueOf(lineData.get(3));

        } else {
            final StringBuilder sb = new StringBuilder();
            for (int i=2; i < (lineData.size() - 1); i++) sb.append(lineData.get(i));

            name = sb.toString();
            salary = Double.valueOf(lineData.get(lineData.size() - 1));
        }

        return new Saler(cpf, name, salary);
    }

    private Client buildClient(final List<String> lineData) {
        if (lineData.size() < 4) {
            throw new SalesReportException(format("Missing data in line %s", lineData));
        }

        final String cnpj = lineData.get(1);
        String name;
        String businessArea;

        if (lineData.size() == 4) {
            name = lineData.get(2);
            businessArea = lineData.get(3);

        } else {
            final StringBuilder sb = new StringBuilder();
            for (int i=2; i < (lineData.size() - 1); i++) sb.append(lineData.get(i));

            name = sb.toString();
            businessArea = lineData.get(lineData.size() - 1);
        }

        return new Client(cnpj, name, businessArea);
    }

    private Product buildProducts(final String productData) {
        final List<String> productItems = Arrays.asList(productData.split(HYPHEN));

        return new Product(Integer.parseInt(productItems.get(0)),
                Integer.parseInt(productItems.get(1)),
                Double.parseDouble(productItems.get(2)));
    }
}
