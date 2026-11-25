# ADR-002: PostgreSQL como base de datos

- **Estado:** Aceptada
- **Fecha:** 2026-10-15

## Contexto
El sistema necesita integridad referencial (agricultor → finca → oferta → pedido), transacciones para reservar stock y consultas por municipio y categoría.

## Decisión
PostgreSQL 15+ con Spring Data JPA/Hibernate. Credenciales por variables de entorno (`DB_URL`, `DB_USER`, `DB_PASSWORD`).

## Consecuencias
- (+) ACID, restricciones `CHECK`/`UNIQUE` y bloqueo de filas para el stock.
- (+) Software libre y compatible con el pipeline de CI (contenedor de pruebas).
- (−) Requiere una instancia propia en desarrollo y staging.
- (−) `ddl-auto: update` es aceptable solo en desarrollo; para staging se recomienda migraciones versionadas (Flyway), a decidir en un ADR posterior.

## Alternativas descartadas
- **MySQL:** viable, pero el equipo definió PostgreSQL en el stack del proyecto.
- **MongoDB:** el dominio es fuertemente relacional y transaccional.
