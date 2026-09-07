package com.patrones.u2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ReportFactoryRegistryTest {

    @Test
    void resuelveFormatosRegistrados() {
        assertTrue(ReportFactoryRegistry.resolve("pdf") instanceof PdfReportFactory);
        assertTrue(ReportFactoryRegistry.resolve("excel") instanceof ExcelReportFactory);
        assertTrue(ReportFactoryRegistry.resolve("html") instanceof HtmlReportFactory);
    }

    @Test
    void permiteRegistrarUnNuevoFormato() {
        ReportFactoryRegistry.register("test", TestReportFactory::new);
        assertTrue(ReportFactoryRegistry.resolve("test") instanceof TestReportFactory);
    }

    @Test
    void rechazaFormatoNoRegistrado() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> ReportFactoryRegistry.resolve("no-existe"));
        assertTrue(ex.getMessage().contains("no-existe"));
    }

    static class TestReportFactory implements ReportFormatFactory {
        @Override
        public ReportBody createBody() {
            return records -> "[TEST:cuerpo]";
        }

        @Override
        public ReportHeaderFooter createHeaderFooter() {
            return new ReportHeaderFooter() {
                @Override
                public String renderHeader(String institutionName) {
                    return "[TEST:encabezado] " + institutionName;
                }

                @Override
                public String renderFooter(int pageNumber) {
                    return "[TEST:pie] " + pageNumber;
                }
            };
        }
    }
}
