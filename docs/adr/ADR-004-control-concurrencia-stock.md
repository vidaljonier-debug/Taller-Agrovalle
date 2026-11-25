# ADR-004: Control de concurrencia para la reserva de stock

- **Estado:** Propuesta (validar con el equipo)
- **Fecha:** 2026-10-15

## Contexto
HU-10 exige que dos pedidos simultáneos no reserven la misma cantidad (evitar sobreventa). Es el requisito con más riesgo técnico del backlog (8 SP).

## Decisión
Reservar el stock dentro de una única transacción (`@Transactional`) usando **bloqueo optimista** con un campo `@Version` en `Oferta`. Si hay conflicto de versión, se reintenta una vez y, si persiste, se responde `409 Conflict`. Restricción `CHECK (cantidad_kg >= 0)` como última defensa en la base de datos.

## Consecuencias
- (+) Sin bloqueos largos; buen rendimiento con baja contención (caso esperado: pocos compradores por lote).
- (+) La base de datos garantiza que el stock no sea negativo aunque falle la lógica.
- (−) Bajo alta contención sobre un mismo lote habrá reintentos/409; en ese caso se puede pasar a bloqueo pesimista (`SELECT ... FOR UPDATE`).

## Alternativas
- **Bloqueo pesimista:** más simple de razonar, pero reduce concurrencia.
- **Cola de reservas:** innecesaria para el volumen esperado.

## Verificación
Prueba de integración con dos hilos reservando la misma oferta: solo uno debe tener éxito cuando el stock no alcanza para ambos.
