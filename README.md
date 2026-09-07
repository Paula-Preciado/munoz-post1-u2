# Post-contenido — Unidad 2: Patrones Creacionales

## Descripción

Repositorio del post-contenido de la Unidad 2 de Patrones de Diseño de Software — Sexto Semestre. Un único proyecto Maven (`exportador-reportes/`) que resuelve la exportación de reportes académicos en múltiples formatos (Parte 1) y se extiende con configuración compleja y evaluación de Singleton (Parte 2).

## Cómo ejecutar

Desde `exportador-reportes/`:

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.patrones.u2.Main"
```

Para ejecutar las pruebas:

```bash
mvn test
```

## Decisiones de diseño

### Decisión 1 — Factory Method vs. Abstract Factory (Parte 1)

**Patrón elegido: Abstract Factory.**

El problema no crea un único producto aislado: cada exportación necesita una familia de dos productos relacionados, el cuerpo del reporte y el encabezado/pie de página. Ambos deben pertenecer al mismo formato para evitar combinaciones inconsistentes, como un cuerpo Excel con un encabezado PDF.

1. **¿Uno o varios productos relacionados?** Se crean dos productos relacionados que deben mantenerse coherentes dentro del mismo formato, por lo que Abstract Factory representa mejor el problema.
2. **¿Qué ocurre al agregar CSV?** Hay que agregar una familia completa de productos CSV: cuerpo y encabezado/pie. Esto encaja con una fábrica abstracta por familia.
3. **¿Cuál es el riesgo principal?** El riesgo real es mezclar piezas de familias diferentes y producir un documento incoherente. Abstract Factory reduce ese riesgo porque una fábrica concreta crea ambos productos del mismo formato.

**Por qué se descarta Factory Method:** Factory Method sería más adecuado si el sistema modelara un solo producto que cambia por formato. Aquí se separaron explícitamente dos productos relacionados, por lo que una fábrica con dos métodos hace visible y protege mejor la compatibilidad entre ellos.

### Decisión 2 — Mecanismo de extensibilidad de formatos (Parte 1)

**Opción elegida: registro dinámico con `Map<String, Supplier<ReportFormatFactory>>`.**

El registro permite resolver una fábrica a partir de un identificador sin mantener un `switch` o una cadena de `if/else`. Para agregar un nuevo formato, se registra un nuevo `Supplier`; el método `resolve()` no necesita modificarse.

**Por qué se descarta `switch/if-else`:** cada formato nuevo obligaría a editar el mismo método de resolución, aumentando el acoplamiento y violando el principio abierto/cerrado (OCP) planteado en el enunciado.

### Decisión 3 — Builder vs. constructor telescópico vs. setters (Parte 2)

**Patrón elegido: Builder.**

`ExportConfig` tiene un parámetro obligatorio (`format`) y ocho opcionales. Builder permite configurar solo los valores necesarios mediante métodos encadenables y concentra la validación en `build()` antes de crear el objeto final.

**Por qué se descarta el constructor de 9 parámetros:** obliga a recordar el orden de muchos argumentos y varios son del mismo tipo, por lo que es fácil intercambiarlos accidentalmente.

**Por qué se descartan constructores sobrecargados:** con ocho parámetros opcionales crecería rápidamente el número de combinaciones y aparecerían muchos constructores casi iguales.

**Por qué se descartan setters sueltos:** permitirían dejar el objeto a medio configurar y dificultarían garantizar reglas de consistencia. Con Builder, `build()` funciona como punto único de validación.

### Decisión 4 — ¿ReportFactoryRegistry necesita ser Singleton? (Parte 2)

**Conclusión: NO conviene convertirlo en Singleton clásico.**

- **Identidad de objeto:** no se necesita pasar el registro como objeto, inyectarlo por constructor ni sustituirlo por un mock en este proyecto. El servicio usa directamente sus operaciones estáticas.
- **Inicialización costosa:** no existe una inicialización costosa; el registro solo contiene tres entradas y se inicializa mediante un bloque `static`.
- **Fuente única de verdad:** el `Map` estático ya representa un registro compartido dentro de la JVM. Un Singleton clásico agregaría `getInstance()` y una instancia sin resolver una necesidad adicional.
- **Escenarios futuros:** si el sistema evolucionara a un escenario multi-institución con registros independientes, un Singleton sería una restricción porque impediría tener registros separados.

Por estas razones, `ReportFactoryRegistry` se mantiene como clase utilitaria final con constructor privado y estado estático, no como Singleton clásico.

## Herramientas utilizadas

- Java 17
- Apache Maven
- VS Code
- Git
- GitHub

## Conclusiones

El proyecto muestra que elegir un patrón no consiste solamente en reconocer su nombre, sino en analizar la estructura real del problema. Abstract Factory resulta adecuado porque mantiene juntos el cuerpo y el encabezado/pie del mismo formato, mientras que Builder facilita construir una configuración con muchos parámetros opcionales y validar su estado. También se comprobó que Singleton no debe aplicarse por costumbre: en este caso el estado estático ya resuelve la necesidad de un registro compartido. En conjunto, el ejercicio ayuda a relacionar cada patrón con el problema que realmente resuelve.