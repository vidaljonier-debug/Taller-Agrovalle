# Sprint 1 — Planning

## Sprint Goal
Construir la base funcional del flujo productor-catálogo de AgroValle Connect: registrar al agricultor, registrar su finca con municipio y permitir consultar ofertas por municipio. El filtro por categoría se reserva para un Sprint posterior, cuando existan cosechas/ofertas publicadas y categorizadas.

**Capacidad comprometida: 10 Story Points.**

## Historias seleccionadas

| ID | Historia | Prioridad | SP |
|---|---|---|---:|
| HU-01 | Registro de Agricultores | Must | 5 |
| HU-06 | Registro de Finca | Must | 2 |
| HU-07 | Búsqueda por Municipio | Must | 3 |
| **Total** | | | **10** |

> Las estimaciones de este Sprint deben validarse con Planning Poker. HU-06 queda en 2 SP para ajustarse a la capacidad de 10 SP definida para el Sprint.

## Justificación del alcance

HU-04 — Filtro de Categorías no se incluye en Sprint 1 porque requiere ofertas/cosechas publicadas y categorizadas para producir resultados reales. Se planifica para Sprint 2 junto con la publicación de productos.

HU-05 — Contacto Directo tampoco se incluye en Sprint 1 porque depende de una oferta publicada y un productor asociado.

## Descomposición técnica

### HU-01 — Registro de Agricultores
1. Crear DTO de registro.
2. Validar nombre, municipio y cédula.
3. Crear entidad y `AgricultorRepository`.
4. Implementar `AgricultorService`.
5. Exponer endpoint `POST /api/v1/agricultores`.
6. Traducir escenarios BDD a pruebas automatizadas JUnit 5.

### HU-06 — Registro de Finca
1. Crear DTO de finca.
2. Validar nombre y municipio.
3. Crear entidad y `FincaRepository`.
4. Implementar `FincaService`.
5. Exponer endpoint `POST /api/v1/fincas`.
6. Traducir escenarios BDD a pruebas automatizadas JUnit 5.

### HU-07 — Búsqueda por Municipio
1. Crear entidad `Oferta` para la consulta del catálogo.
2. Crear método `findByMunicipioIgnoreCase` en `OfertaRepository`.
3. Implementar `CatalogoService`.
4. Exponer `GET /api/v1/ofertas?municipio={municipio}`.
5. Validar municipio y respuesta vacía.
6. Traducir escenarios BDD a pruebas automatizadas JUnit 5.

## BDD → JUnit 5

### HU-01
**Given:** datos de agricultor válidos.  
**When:** se ejecuta el registro.  
**Then:** se guarda el agricultor y el servicio devuelve la entidad persistida.

### HU-06
**Given:** un agricultor registrado y datos de finca válidos.  
**When:** se registra la finca.  
**Then:** la finca queda asociada al agricultor y se guarda.

### HU-07
**Given:** existen ofertas de Dagua y Palmira.  
**When:** se consulta Dagua.  
**Then:** el servicio solicita al repositorio únicamente las ofertas de Dagua.

## ISO/IEC 25010 aplicado al Sprint

- Adecuación funcional: cada HU conserva su criterio Given-When-Then.
- Mantenibilidad: arquitectura Controller → Service → Repository → Entity.
- Fiabilidad: validaciones y manejo de resultados vacíos.
- Seguridad: secretos mediante variables de entorno; las credenciales no se almacenan en código.
- Testabilidad: JUnit 5 y pruebas unitarias sobre los servicios.
- Compatibilidad: Java 17 y Spring Boot 3.3.x.

## Dependencias y riesgos

- HU-06 depende de HU-01 para identificar al agricultor.
- HU-07 depende de que existan ofertas con municipio registrado.
- HU-04 queda fuera del Sprint 1 por dependencia de publicación/categorización de cosechas.
