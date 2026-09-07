package com.patrones.u2;

/**
 * Producto abstracto 2: encabezado y pie de página.
 */
public interface ReportHeaderFooter {
    String renderHeader(String institutionName);
    String renderFooter(int pageNumber);
}
