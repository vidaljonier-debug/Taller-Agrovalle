# ADR-003: Autenticación con JWT y roles

- **Estado:** Aceptada
- **Fecha:** 2026-10-15

## Contexto
Las HU-02, HU-06, HU-09 y siguientes requieren usuarios autenticados con permisos distintos (Agricultor, Comprador, Operador). La API es REST consumida por un frontend web.

## Decisión
Spring Security con tokens JWT sin estado (expiración de 60 minutos, ver `application.yml`). Roles: `AGRICULTOR`, `COMPRADOR`, `OPERADOR`. Contraseñas con hash (BCrypt). El secreto JWT se lee de `JWT_SECRET`.

## Consecuencias
- (+) API sin sesión en servidor, fácil de escalar y de probar.
- (+) Autorización por rol a nivel de endpoint.
- (−) Revocar un token antes de su expiración requiere mecanismo adicional (lista de bloqueo o refresh tokens), fuera del alcance inicial.
- (−) El secreto debe rotarse y no versionarse.
