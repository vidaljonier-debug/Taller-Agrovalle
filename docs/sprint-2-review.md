# Sprint 2 Review & Demo Day — AgroValle Connect

## 1. Objetivo del Sprint 2 (Cierre Segundo Corte)
Consolidar la arquitectura en capas, aplicar refactorización SOLID, implementar patrones de diseño GoF (Strategy/Observer), garantizar cobertura de pruebas unitarias con JUnit 5 + JaCoCo ($\ge 60\%$) y automatizar los Quality Gates mediante GitHub Actions.

## 2. Incremento Funcional y Técnico Verificado
- **HU-01, HU-03, HU-04 (Gestión de Productores, Fincas y Búsqueda):** Endpoints REST validados, persistencia relacional en 3FN (`docs/sprint-diseño/schema.sql`) y principios SRP/DIP aplicados en Controllers y Services.
- **HU-12 (Cálculo Dinámico de Fletes — Patrón Strategy):** Desacoplamiento de tarifas rurales por municipio (`CalculoFleteStrategy`, `FleteDaguaStrategy`, `FletePalmiraStrategy`) sin condicionales rígidos.
- **Integración Continua (`.github/workflows/ci.yml`):** Pipeline activo en GitHub Actions verificando compilación Java 17, 0 errores en Checkstyle, suite JUnit 5 aprobada y cobertura JaCoCo $\ge 60\%$.

## 3. Evidencias de Auditoría
- **Tablero Kanban:** Políticas explícitas documentadas en `docs/kanban-policies.md` con límites WIP activos.
- **Especificación Formal:** Comparativa BDD vs. IEEE 29148 documentada en `docs/ieee-29148-hu01.md`.
- **Estado del Backlog:** Historias del Sprint 1 y Sprint 2 verificadas en columna `Hecho`; historias restantes (`HU-05` a `HU-15`) refinadas para el Sprint 3.