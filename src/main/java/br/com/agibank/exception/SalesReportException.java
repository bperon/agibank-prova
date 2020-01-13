package br.com.agibank.exception;

public class SalesReportException extends RuntimeException {
    public SalesReportException(final Throwable cause) {
        super(cause);
    }

    public SalesReportException(final String message) {
        super(message);
    }
}
