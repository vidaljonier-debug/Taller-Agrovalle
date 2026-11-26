# Reporte de Métricas Ágiles de Flujo - Tercer Corte

## 1. Velocidad del Equipo (Velocity)

Medición de Story Points (SP) comprometidos frente a los realmente entregados que cumplen al 100% el Definition of Done (DoD).

| Sprint | Story Points Comprometidos | Story Points Completados (DoD 100%) | Factor de Cumplimiento (%) | Observaciones Técnicas |
| :--- | :---: | :---: | :---: | :--- |
| **Sprint 1** | 10 SP | 8 SP | 80.0% | Ajuste inicial en configuración de Spring Boot y PostgreSQL. |
| **Sprint 2** | 14 SP | 12 SP | 85.7% | Integración de TDD con JUnit 5 y pipelines de CI en GitHub Actions. |
| **Sprint 3** | 16 SP | 16 SP | 100.0% | Estabilización del flujo con CD automatizado y despliegue a Staging. |
| **Promedio** | **13.3 SP** | **12.0 SP** | **90.2%** | Velocidad de crucero establecida para planificación futura. |

---

## 2. Gráfico de Trabajo Pendiente (Burndown Chart - Sprint 3)

Registro del avance diario del Sprint 3 de 14 días (16 Story Points totales).

| Día del Sprint | Trabajo Ideal Pendiente (SP) | Trabajo Real Pendiente (SP) | Estado y Bloqueos Identificados |
| :---: | :---: | :---: | :--- |
| **Día 1** | 16.0 | 16.0 | Sprint Planning y asignación de tareas en GitHub Projects. |
| **Día 3** | 13.7 | 14.0 | Ajuste de variables de entorno en `application-staging.yml`. |
| **Día 6** | 10.3 | 11.0 | Registro de secretos en GitHub Secrets (`STAGING_DB_URL`). |
| **Día 9** | 6.9 | 6.0 | Implementación de pipeline de CD (`cd.yml`) y Webhook de Render. |
| **Día 12** | 3.4 | 2.0 | Diagramación UML en Mermaid y pruebas de integración. |
| **Día 14** | 0.0 | 0.0 | Sprint Goal alcanzado. Incremento listo para sustentación. |

---

## 3. Diagrama de Flujo Acumulado (Cumulative Flow Diagram - CFD)

Seguimiento del estado de las tareas para verificar el cumplimiento de los límites de trabajo en progreso (WIP Limits).

| Día del Sprint | Backlog (Por Hacer) | In Progress (WIP Limit: 3) | Testing/Review (WIP Limit: 2) | Done (Desplegado en Staging) |
| :---: | :---: | :---: | :---: | :---: |
| **Día 1** | 10 tareas | 2 tareas | 0 tareas | 0 tareas |
| **Día 5** | 3 tareas | 2 tareas | 5 tareas | 2 tareas |
| **Día 10** | 2 tareas | 1 tarea | 1 tarea | 8 tareas |
| **Día 14** | 0 tareas | 0 tareas | 0 tareas | 12 tareas |

**Diagnóstico del CFD:** El gráfico demuestra estabilidad operacional. En ningún momento la columna *In Progress* superó el límite de 3 tareas simultáneas por pareja de desarrolladores, previniendo cuellos de botella y garantizando un flujo continuo.

---

## 4. Tiempos de Entrega (Lead Time y Cycle Time)

* **Lead Time Promedio** *(Desde creación de HU en Backlog hasta Despliegue en Staging)*: **4.2 días**.
* **Cycle Time Promedio** *(Desde que el desarrollador mueve la tarea a In Progress hasta que pasa el DoD)*: **1.8 días**.
