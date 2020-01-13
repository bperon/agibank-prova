package br.com.agibank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A Spring Boot Application responsible for listening to HOMEPATH/data/in folder for incoming .dat files, processing the
 * content for all files found in folder and generating/updating a report file in HOMEPATH/data/out with the following data:
 * - The number of clients
 * - The number of salesman
 * - The sale ID of the most expensive sale
 * - The worst salesman
 *
 * The {@link java.nio.file.WatchService} API was used for monitoring directory changes and
 * {@link java.util.concurrent.ExecutorService} API was used for handling concurrency.
 *
 * If {@code NUMBER_OF_THREADS} environment variable is not provided, a default value of 10 will be set for the number
 * of threads in Executor thread pool.
 *
 * If {@code REPORT_FILENAME} environment variable is not provided, a file named 'report.done.dat' will be added in
 * HOMEPATH/data/out folder as the report file.
 *
 */
@SpringBootApplication
public class SalesReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalesReportApplication.class, args);
    }
}
