package com.patrones.u2;

/**
 * Abstract Factory: crea la familia compatible de productos de un formato.
 */
public interface ReportFormatFactory {
    ReportBody createBody();
    ReportHeaderFooter createHeaderFooter();
}
