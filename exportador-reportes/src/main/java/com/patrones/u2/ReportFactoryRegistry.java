package com.patrones.u2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Registro central de fábricas. No es Singleton clásico:
 * utiliza estado estático y no necesita identidad de objeto.
 */
public final class ReportFactoryRegistry {
    private static final Map<String, Supplier<ReportFormatFactory>> REGISTRY = new HashMap<>();

    static {
        REGISTRY.put("pdf", PdfReportFactory::new);
        REGISTRY.put("excel", ExcelReportFactory::new);
        REGISTRY.put("html", HtmlReportFactory::new);
    }

    private ReportFactoryRegistry() {
        // Clase utilitaria no instanciable.
    }

    public static void register(String format, Supplier<ReportFormatFactory> factory) {
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("El formato no puede estar vacío");
        }
        if (factory == null) {
            throw new IllegalArgumentException("La fábrica no puede ser null");
        }
        REGISTRY.put(format.toLowerCase(), factory);
    }

    public static ReportFormatFactory resolve(String format) {
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("Formato de reporte no puede estar vacío");
        }

        Supplier<ReportFormatFactory> factory = REGISTRY.get(format.toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException(
                    "Formato de reporte no registrado: " + format
                            + ". Formatos disponibles: " + REGISTRY.keySet());
        }
        return factory.get();
    }
}
