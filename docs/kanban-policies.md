# Políticas Explícitas del Flujo Kanban (Scrumban) — AgroValle Connect

## 1. Límites de Trabajo en Progreso (WIP Limits — Ley de Little)
Bajo la relación *Cycle Time = WIP / Throughput* y el principio *"Stop Starting, Start Finishing"*, el tablero en GitHub Projects aplica los siguientes límites estrictos:
- **Product Backlog:** Sin límite (15 HUs priorizadas con MoSCoW y estimadas en Fibonacci).
- **Sprint Backlog (To Do):** Tareas técnicas comprometidas para el Sprint activo.
- **In Progress:** **WIP Limit = 3** tareas simultáneas máximo en todo el equipo.
- **Testing / Code Review:** **WIP Limit = 2** Pull Requests abiertos pendientes de aprobación.
- **Done:** Sin límite (incrementos verificados y listos para despliegue).

## 2. Políticas Explícitas de Salida (Pull Criteria)
Ninguna tarjeta puede moverse a la siguiente columna sin cumplir estas reglas:
1. **De `To Do` a `In Progress`:**
   - La Historia de Usuario cuenta con criterios de aceptación BDD (*Given-When-Then*).
   - Existe cupo disponible en la columna (`WIP <= 3`) y se crea rama dedicada (`feature/huXX-...`).
2. **De `In Progress` a `Testing / Code Review`:**
   - Código refactorizado aplicando principios **SOLID** (SRP y DIP).
   - Validación local exitosa con `mvn verify` (compilación Java 17, 0 errores Checkstyle, 100% tests JUnit 5 aprobados y cobertura > 60%).
   - Pull Request abierto hacia `develop` o `main` con el check verde de GitHub Actions.
3. **De `Testing / Code Review` a `Done`:**
   - Revisión cruzada (*Peer Review*) aprobada por al menos un compañero.
   - Fusión (*Merge*) sin conflictos y cumplimiento del 100% del **Definition of Done** (`docs/dod.md`).

## 3. Métricas de Flujo Monitoreadas
- **Lead Time:** Tiempo desde el registro de la necesidad en el Backlog hasta su entrega en `Done`.
- **Cycle Time:** Tiempo activo de desarrollo desde `In Progress` hasta `Done`.
- **Throughput:** Número de Historias de Usuario completadas por Sprint.
- **Diagrama de Flujo Acumulado (CFD):** Revisado en GitHub Projects (*Insights*) para identificar ensanchamientos o cuellos de botella en *Code Review*.
