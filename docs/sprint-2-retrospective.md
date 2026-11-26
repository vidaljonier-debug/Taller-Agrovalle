# Sprint 2 Retrospective — AgroValle Connect

## 1. Lo que funcionó bien
- El equipo logró estabilizar la estructura raíz del proyecto Maven y activar el pipeline de Integración Continua en GitHub Actions, detectando errores de formato (Checkstyle) antes de integrar código a `main`.
- La adopción de principios SOLID y del patrón Strategy facilitó la escritura de pruebas unitarias aisladas en JUnit 5 sin depender de una conexión activa a PostgreSQL.

## 2. Aspectos por mejorar
- Se presentaron desincronizaciones menores entre cambios realizados directamente en la interfaz web de GitHub y los commits locales en Visual Studio Code, generando conflictos de historial (`non-fast-forward`).
- Algunas historias completadas a nivel de código tardaron en actualizarse visualmente en las columnas del tablero Kanban de GitHub Projects.

## 3. Plan de Acción para el Sprint 3
1. **Flujo Git estricto:** Ejecutar siempre `git pull --rebase origin main` antes de iniciar trabajo local y canalizar todos los cambios de código mediante ramas `feature/*` y Pull Requests con Peer Review.
2. **Actualización diaria del tablero:** Mover cada tarjeta en GitHub Projects en el mismo momento en que se abre o aprueba su Pull Request, respetando los límites WIP.
3. **Preparación de despliegue:** Incorporar contenerización con Docker y pruebas de integración completas en el siguiente ciclo.