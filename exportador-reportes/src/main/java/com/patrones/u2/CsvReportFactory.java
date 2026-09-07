package com.patrones.u2;

/**
 * Ejemplo de extensión: se puede registrar CSV sin modificar ReportFactoryRegistry.
 */
public class CsvReportFactory implements ReportFormatFactory {
    @Override
    public ReportBody createBody() {
        return new CsvReportBody();
    }

    @Override
    public ReportHeaderFooter createHeaderFooter() {
        return new CsvHeaderFooter();
    }
}
