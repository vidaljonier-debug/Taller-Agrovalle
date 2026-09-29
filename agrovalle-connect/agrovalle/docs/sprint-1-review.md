# Sprint 1 — Review

## Objetivo revisado
Presentar la base funcional del flujo productor-catálogo, manteniendo una capacidad de 10 Story Points.

## Funcionalidades demostradas

### HU-01 — Registro de Agricultores
- Endpoint definido: `POST /api/v1/agricultores`.
- Validación de campos obligatorios.
- Persistencia delegada a `AgricultorRepository`.

### HU-06 — Registro de Finca
- Endpoint definido: `POST /api/v1/fincas`.
- Validación del municipio y asociación con el agricultor.
- Persistencia delegada a `FincaRepository`.

### HU-07 — Búsqueda por Municipio
- Endpoint definido: `GET /api/v1/ofertas?municipio={municipio}`.
- Consulta mediante `findByMunicipioIgnoreCase`.
- Devuelve únicamente ofertas del municipio solicitado.

## Evidencias

- Código: `src/main/java/com/agrovalle/connect/`.
- Pruebas: `src/test/java/com/agrovalle/connect/`.
- Automatización: `.github/workflows/ci-cd.yml`.
- Calidad: `checkstyle.xml`, JaCoCo y JUnit 5.
- Tablero: configurar en GitHub Projects las columnas operativas y los límites WIP definidos en `docs/github-projects-kanban.md`.

## Pendientes para el siguiente Sprint

- Publicación de cosechas/ofertas.
- Filtro por categoría (HU-04).
- Contacto directo (HU-05).
- Disponibilidad y stock.
