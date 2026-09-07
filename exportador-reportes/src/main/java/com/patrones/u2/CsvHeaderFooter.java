package com.patrones.u2;

public class CsvHeaderFooter implements ReportHeaderFooter {
    @Override
    public String renderHeader(String institutionName) {
        return "[CSV:encabezado] " + institutionName + " - Acta de Calificaciones";
    }

    @Override
    public String renderFooter(int pageNumber) {
        return "[CSV:pie] Registro " + pageNumber + " — exportación para data warehouse";
    }
}
