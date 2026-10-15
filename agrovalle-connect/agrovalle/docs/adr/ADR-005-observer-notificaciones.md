# ADR-005: Patrón Observer para trazabilidad y notificaciones

- **Estado:** Aceptada
- **Fecha:** 2026-10-15

## Contexto
Cada cambio de estado del pedido debe registrar un `EventoPedido` (HU-14) y notificar al usuario (HU-15). Se espera sumar canales (correo, SMS) sin tocar la lógica del pedido.

## Decisión
`PedidoService` publica el evento a una lista de `PedidoObserver` (inyectados por Spring). Implementaciones iniciales: `TrazabilidadObserver` y `NotificacionObserver`. El envío externo se hace de forma asíncrona para que una falla del proveedor de correo no revierta el cambio de estado.

## Consecuencias
- (+) Bajo acoplamiento; agregar un canal es agregar una clase.
- (+) Cada observador es testeable por separado.
- (−) La notificación asíncrona puede fallar silenciosamente; se registra el error y se reintenta.

## Alternativas
- **Llamadas directas desde `PedidoService`:** acopla el servicio a todos los canales.
- **Broker de mensajes (Kafka/RabbitMQ):** excesivo para el alcance actual.
