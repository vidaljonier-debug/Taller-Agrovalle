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
- [ ] Diagrama de casos de uso.
- [ ] Diagrama de actividad.
- [ ] Diagrama de clases.
- [ ] Diagrama de secuencia.
- [ ] Diagrama de colaboración.
- [ ] Diagrama de despliegue físico.
- [ ] ADRs (Architecture Decision Records) en el repositorio.
- [ ] Patrones Repository, Factory, Observer y Singleton justificados/implementados.
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
| Valentina | | |
| Jhon  | | |
| Jean carlos| | |
