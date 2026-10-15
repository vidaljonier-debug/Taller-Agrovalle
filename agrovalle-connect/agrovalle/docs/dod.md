# Contrato de Calidad — Definition of Done (DoD)

Basado en el estándar **ISO/IEC 25010**. Una historia de usuario se considera **terminada**
únicamente cuando cumple todos los criterios siguientes:

| Criterio (ISO/IEC 25010) | Condición para considerar terminada una historia |
|---|---|
| Adecuación funcional | La funcionalidad cumple la historia y sus criterios Given-When-Then. |
| Rendimiento | No se introducen consultas innecesariamente costosas; endpoints críticos son medidos. |
| Compatibilidad | La API y el frontend funcionan con las versiones definidas del proyecto. |
| Usabilidad | Los flujos principales tienen mensajes claros y validaciones. |
| Fiabilidad | Errores controlados; operaciones críticas son transaccionales. |
| Seguridad | JWT, roles, validación de entradas, contraseñas con hash y secretos fuera del código. |
| Mantenibilidad | Clean Code, separación de responsabilidades, nombres claros y ausencia de duplicación relevante. |
| Testabilidad | Pruebas unitarias e integradas asociadas a la funcionalidad. |
| Checkstyle | Cero advertencias estáticas en CI. |
| Cobertura | JaCoCo reporta al menos 60%. |
| Code Review | Pull Request revisado y aprobado por un par. |
| CI/CD | Build, linter y pruebas pasan automáticamente; staging recibe despliegue automático. |

## Checklist final de entrega (Sprint 0)

- [ ] Proyecto Java 17+ / Spring Boot compilando.
- [ ] PostgreSQL configurado.
- [ ] Frontend web conectado al backend.
- [ ] 15 historias de usuario documentadas y priorizadas (`BACKLOG.md`).
- [ ] Planning Poker realizado y Story Points validados por el equipo.
- [ ] Criterios BDD (Given-When-Then) para las 15 HU.
- [x] Diagrama de casos de uso (`docs/sprint-diseno/01-casos-de-uso.md`).
- [x] Diagrama de actividad (`docs/sprint-diseno/02-diagramas-actividad.md`).
- [x] Diagrama de clases (`docs/sprint-diseno/03-diagrama-clases.md`).
- [x] Diagrama de secuencia (`docs/sprint-diseno/04-diagramas-secuencia.md`).
- [x] Diagrama de colaboración (`docs/sprint-diseno/05-diagrama-colaboracion.md`).
- [x] Diagrama de despliegue físico (`docs/sprint-diseno/06-diagrama-despliegue.md`).
- [x] ADRs (Architecture Decision Records) en el repositorio (`docs/adr/`).
- [x] Patrones Repository, Factory, Observer y Singleton justificados/implementados (`docs/sprint-diseno/08-patrones-diseno.md`).
- [ ] TDD aplicado en funcionalidades críticas.
- [ ] JUnit 5.
- [ ] Pruebas unitarias e integradas.
- [ ] JaCoCo ≥ 60%.
- [ ] Checkstyle sin advertencias (`checkstyle.xml`).
- [ ] Code review en cada Pull Request.
- [ ] Estrategia Git (GitFlow) con diagrama Mermaid en `README.md`.
- [ ] Conventional Commits en todo el historial.
- [ ] GitHub Actions para build, linter y pruebas (`.github/workflows/ci-cd.yml`).
- [ ] Despliegue automático a staging.
- [ ] `README.md` con instalación, variables de entorno, ejecución, pruebas y arquitectura.
- [ ] Hooks de pre-commit con Husky (`.husky/pre-commit`).
- [ ] `.gitignore` configurado para Java/Maven/Spring Boot.

## Firma del equipo

Al marcar este documento como completo, cada integrante certifica que revisó el trabajo entregado
y que cumple los criterios anteriores.

| Nombre | Firma / usuario GitHub | Fecha |
|---|---|---|
| Jhonier | | |
| Deibis | | |
| Valentina | | |
| carlos| | |

## Evidencias documentales de Sprint 1

- `docs/sprint-1-planning.md`
- `docs/bitacora-daily-scrum.md`
- `docs/sprint-1-review.md`
- `docs/sprint-1-retrospective.md`
- `docs/github-projects-kanban.md`

## Evidencias documentales del Sprint de Diseño

- `docs/sprint-diseno/00-planning-sprint-diseno.md` (y archivos 01 a 08)
- `docs/adr/ADR-001` a `ADR-005`


