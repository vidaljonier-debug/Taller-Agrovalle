# 5. Diagrama de colaboración (comunicación)

Muestra los mismos objetos que SEQ-03 (confirmación de compra, HU-10/HU-11) pero organizados por sus vínculos; el orden de los mensajes se indica con números.

```mermaid
flowchart LR
    Co([":Comprador"])
    PC[":PedidoController"]
    PS[":PedidoService"]
    SS[":StockService"]
    OR[(":OfertaRepository")]
    PF[":PedidoFactory"]
    PR[(":PedidoRepository")]
    OB[":PedidoObserver"]

    Co -- "1: POST /pedidos(carritoId)" --> PC
    PC -- "2: crear(carrito)" --> PS
    PS -- "3: reservar(items)" --> SS
    SS -- "4: buscarConBloqueo(ids)" --> OR
    SS -- "5: descontar(cantidades)" --> OR
    PS -- "6: desde(carrito, reservas)" --> PF
    PS -- "7: save(pedido, evento)" --> PR
    PS -- "8: notificar(evento)" --> OB
    PC -- "9: 201 Created" --> Co
```

### Mensajes

| # | Emisor → Receptor | Mensaje | Respuesta |
|---|---|---|---|
| 1 | Comprador → PedidoController | `POST /api/v1/pedidos` | — |
| 2 | PedidoController → PedidoService | `crear(carrito)` | `Pedido` |
| 3 | PedidoService → StockService | `reservar(items)` | `List<Reserva>` |
| 4 | StockService → OfertaRepository | consulta con bloqueo | ofertas |
| 5 | StockService → OfertaRepository | actualización de stock | — |
| 6 | PedidoService → PedidoFactory | `desde(carrito, reservas)` | `Pedido(RESERVADO)` |
| 7 | PedidoService → PedidoRepository | `save` | pedido persistido |
| 8 | PedidoService → PedidoObserver | `notificar(evento)` | — |
| 9 | PedidoController → Comprador | `201 Created` | — |
