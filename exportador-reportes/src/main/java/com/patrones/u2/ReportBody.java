package com.patrones.u2;

import java.util.List;

/**
 * Producto abstracto 1: cuerpo del reporte.
 */
public interface ReportBody {
    String render(List<GradeRecord> records);
}
