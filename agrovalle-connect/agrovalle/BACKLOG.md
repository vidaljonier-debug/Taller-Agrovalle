# Product Backlog — AgroValle Connect

**MoSCoW:** Must = imprescindible · Should = importante · Could = deseable · Won't = fuera de alcance de esta versión.
**Story Points:** estimación propuesta (a validar por el equipo con Planning Poker real), escala Fibonacci 1,2,3,5,8,13.

**Total estimado:** 71 Story Points.

| ID | Nombre | Historia de usuario | MoSCoW | SP |
|---|---|---|---|---|
| HU-01 | Registro de Agricultores | Como Agricultor, quiero registrarme en la plataforma para ofrecer mis productos. | Must | 5 |
| HU-02 | Publicación de Productos | Como Agricultor, quiero publicar mis cosechas para que sean visibles. | Must | 5 |
| HU-03 | Precios Regionales | Como Usuario, quiero ver los precios promedio del Valle para negociar mejor. | Should | 5 |
| HU-04 | Filtro de Categorías | Como Comprador, quiero filtrar productos por categoría para encontrar rápidamente lo que necesito. | Must | 3 |
| HU-05 | Contacto Directo | Como Comprador, quiero contactar directamente al productor para resolver dudas antes de comprar. | Should | 3 |
| HU-06 | Registro de Finca | Como Agricultor, quiero registrar mi finca y municipio para identificar el origen de la oferta. | Must | 3 |
| HU-07 | Búsqueda por Municipio | Como Comprador, quiero buscar oferta por municipio para conocer productos disponibles por zona. | Must | 3 |
| HU-08 | Disponibilidad en Tiempo Real | Como Comprador, quiero consultar cantidades disponibles para evitar comprar productos agotados. | Must | 5 |
| HU-09 | Carrito de Compra | Como Comprador, quiero consolidar productos en un carrito para realizar un pedido. | Must | 5 |
| HU-10 | Reserva de Stock | Como Comprador, quiero reservar el inventario al confirmar mi compra para evitar sobreventa. | Must | 8 |
| HU-11 | Orden de Compra | Como Comprador, quiero generar una orden de compra directa con el productor. | Must | 5 |
| HU-12 | Alistamiento | Como Agricultor, quiero confirmar que un lote está listo para despacho. | Must | 3 |
| HU-13 | Programación de Ruta | Como Agricultor/Operador, quiero programar el despacho para coordinar la entrega. | Must | 8 |
| HU-14 | Trazabilidad | Como Comprador, quiero ver el estado de mi pedido para conocer su avance. | Must | 5 |
| HU-15 | Notificaciones | Como Usuario, quiero recibir notificaciones cuando cambie el estado de mi pedido. | Should | 5 |

## Criterios de aceptación (BDD — Given/When/Then)

### HU-01 — Registro de Agricultores
Given que el usuario ingresa a `/api/v1/auth/register` y proporciona nombre, ubicación (municipio del Valle) y una cédula válida,
When envía la solicitud de registro,
Then el sistema responde `201 Created` y persiste el agricultor en PostgreSQL.

### HU-02 — Publicación de Productos
Given un agricultor autenticado con JWT,
When publica un lote con tipo, cantidad, precio y fecha de cosecha,
Then el sistema valida los datos, rechaza fechas anteriores a hoy y devuelve un identificador único.

### HU-03 — Precios Regionales
Given que existen 50 transacciones de Café en las últimas 24 horas,
When el usuario solicita el precio promedio de Café,
Then el sistema calcula la media aritmética y la muestra en pesos colombianos.

### HU-04 — Filtro de Categorías
Given que existen ofertas de varias categorías,
When el comprador selecciona una categoría,
Then solo se muestran ofertas pertenecientes a esa categoría y disponibles.

### HU-05 — Contacto Directo
Given que una oferta está publicada y tiene productor asociado,
When el comprador selecciona "Contactar productor",
Then el sistema habilita un canal de contacto sin exponer datos internos innecesarios.

### HU-06 — Registro de Finca
Given un agricultor autenticado,
When registra nombre de finca y municipio del Valle,
Then el sistema valida los campos y crea la finca asociada al agricultor.

### HU-07 — Búsqueda por Municipio
Given que existen ofertas en Dagua y Palmira,
When el comprador filtra por Dagua,
Then el catálogo devuelve únicamente ofertas cuyo origen sea Dagua.

### HU-08 — Disponibilidad en Tiempo Real
Given una oferta con stock de 100 kg,
When el comprador consulta disponibilidad,
Then el sistema muestra el stock actual y no permite reservar una cantidad superior.

### HU-09 — Carrito de Compra
Given un comprador autenticado,
When agrega una o más ofertas disponibles al carrito,
Then el sistema consolida los ítems y calcula cantidades y subtotal.

### HU-10 — Reserva de Stock
Given un carrito con stock suficiente,
When el comprador confirma la compra,
Then el sistema reserva el inventario dentro de una transacción y evita que otro pedido reserve la misma cantidad.

### HU-11 — Orden de Compra
Given un carrito con reservas válidas,
When el comprador confirma el pedido,
Then el sistema crea una orden con número único, ítems, cantidades, precios y estado inicial `RESERVADO`.

### HU-12 — Alistamiento
Given una orden en estado `RESERVADO` y un lote disponible,
When el agricultor confirma el alistamiento,
Then el estado del lote/pedido cambia a `ALISTADO` y se registra fecha y responsable.

### HU-13 — Programación de Ruta
Given un pedido `ALISTADO`,
When el operador registra fecha, vehículo y ruta de despacho,
Then el sistema guarda la programación y cambia el pedido a `DESPACHADO` cuando inicia el envío.

### HU-14 — Trazabilidad
Given un pedido con eventos de estado,
When el comprador consulta el seguimiento,
Then el sistema devuelve la secuencia de estados con fecha/hora y ubicación disponible.

### HU-15 — Notificaciones
Given que un pedido cambia de estado,
When se registra el nuevo estado,
Then los observadores suscritos reciben una notificación con el estado actualizado.

## Estados del pedido

```
RESERVADO -> ALISTADO -> DESPACHADO -> ENTREGADO
RESERVADO -> CANCELADO
ALISTADO -> CANCELADO (solo si una regla de negocio autorizada lo permite)
```

Cada transición válida genera un `EventoPedido` y activa el patrón Observer para notificaciones.

> Nota: la estimación de Story Points debe validarse nuevamente con el equipo mediante Planning Poker real antes de la entrega final.
