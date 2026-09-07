package com.patrones.u2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ExportConfigTest {

    @Test
    void usaValoresPorDefecto() {
        ExportConfig config = new ExportConfig.Builder("pdf").build();

        assertEquals("pdf", config.getFormat());
        assertEquals("A4", config.getPageSize());
        assertEquals("PORTRAIT", config.getOrientation());
        assertEquals("es-CO", config.getLocale());
        assertTrue(config.isIncludeLogo());
        assertFalse(config.isCompress());
        assertEquals(40, config.getMaxRowsPerPage());
    }

    @Test
    void validaCompresionSinRuta() {
        assertThrows(
                IllegalStateException.class,
                () -> new ExportConfig.Builder("pdf")
                        .compress(true)
                        .build());
    }

    @Test
    void validaFilasPorPagina() {
        assertThrows(
                IllegalStateException.class,
                () -> new ExportConfig.Builder("pdf")
                        .maxRowsPerPage(0)
                        .build());
    }

    @Test
    void aceptaConfiguracionPersonalizada() {
        ExportConfig config = new ExportConfig.Builder("excel")
                .outputPath("salida/reporte.xlsx")
                .pageSize("LETTER")
                .orientation("LANDSCAPE")
                .locale("en-US")
                .watermarkText("BORRADOR")
                .includeLogo(false)
                .compress(true)
                .maxRowsPerPage(25)
                .build();

        assertEquals("excel", config.getFormat());
        assertEquals("salida/reporte.xlsx", config.getOutputPath());
        assertEquals("LETTER", config.getPageSize());
        assertEquals("LANDSCAPE", config.getOrientation());
        assertEquals("en-US", config.getLocale());
        assertEquals("BORRADOR", config.getWatermarkText());
        assertFalse(config.isIncludeLogo());
        assertTrue(config.isCompress());
        assertEquals(25, config.getMaxRowsPerPage());
    }
}
