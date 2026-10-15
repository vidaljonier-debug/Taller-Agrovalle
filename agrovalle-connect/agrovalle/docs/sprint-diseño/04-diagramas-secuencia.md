# 4. Diagramas de secuencia

## SEQ-01 — Registro de agricultor (HU-01, implementado)

```mermaid
sequenceDiagram
    actor Ag as Agricultor
    participant C as AgricultorController
    participant S as AgricultorService
    participant R as AgricultorRepository
    participant DB as PostgreSQL

    Ag->>C: POST /api/v1/agricultores {nombre, municipio, cedula}
    C->>S: registrar(agricultor)
    S->>S: validar(nombre, municipio, cedula)
    alt datos inválidos
        S-->>C: IllegalArgumentException
        C-->>Ag: 400 Bad Request
    else datos válidos
        S->>R: save(agricultor)
        R->>DB: INSERT agricultor
        DB-->>R: id generado
        R-->>S: Agricultor
        S-->>C: Agricultor
        C-->>Ag: 201 Created
    end
```

## SEQ-02 — Publicación de oferta (HU-02)

```mermaid
sequenceDiagram
    actor Ag as Agricultor
    participant F as JwtFilter
    participant C as OfertaController
    participant S as OfertaService
    participant FR as FincaRepository
    participant OR as OfertaRepository

    Ag->>F: POST /api/v1/ofertas (Bearer JWT)
    F->>F: validar token y rol AGRICULTOR
    F->>C: solicitud autenticada
    C->>S: publicar(OfertaRequest)
    S->>S: validar datos y fechaCosecha ≥ hoy
    S->>FR: findByAgricultorId(id)
    FR-->>S: Finca
    S->>OR: save(oferta)
    OR-->>S: Oferta con id
    S-->>C: Oferta
    C-->>Ag: 201 Created {id}
```

## SEQ-03 — Confirmación de compra: reserva de stock y orden (HU-10, HU-11)

```mermaid
sequenceDiagram
    actor Co as Comprador
    participant PC as PedidoController
    participant PS as PedidoService
    participant SS as StockService
    participant OR as OfertaRepository
    participant PF as PedidoFactory
    participant PR as PedidoRepository
    participant OB as PedidoObserver(s)

    Co->>PC: POST /api/v1/pedidos (carritoId)
    PC->>PS: crear(carrito)
    activate PS
    Note over PS,PR: @Transactional (una sola transacción)
    PS->>SS: reservar(items)
    SS->>OR: buscar con bloqueo (versión/lock)
    OR-->>SS: ofertas
    alt stock insuficiente
        SS-->>PS: StockInsuficienteException
        PS-->>PC: rollback
        PC-->>Co: 409 Conflict (ítems agotados)
    else stock suficiente
        SS->>OR: descontar cantidad
        SS-->>PS: reservas
        PS->>PF: desde(carrito, reservas)
        PF-->>PS: Pedido(RESERVADO, número único)
        PS->>PR: save(pedido + EventoPedido)
        PS->>OB: notificar(EventoPedido)
        PS-->>PC: Pedido
        PC-->>Co: 201 Created {numero, estado: RESERVADO}
    end
    deactivate PS
```

## SEQ-04 — Cambio de estado del pedido con notificación (HU-12, HU-13, HU-15)

```mermaid
sequenceDiagram
    actor Ag as Agricultor / Operador
    participant PC as PedidoController
    participant PS as PedidoService
    participant PR as PedidoRepository
    participant TO as TrazabilidadObserver
    participant NO as NotificacionObserver
    participant ES as Servicio de correo/SMS

    Ag->>PC: PATCH /api/v1/pedidos/{id}/estado {ALISTADO}
    PC->>PS: cambiarEstado(id, ALISTADO)
    PS->>PR: findById(id)
    PR-->>PS: Pedido
    PS->>PS: validar transición (RESERVADO → ALISTADO)
    alt transición inválida
        PS-->>PC: TransicionInvalidaException
        PC-->>Ag: 422 Unprocessable Entity
    else transición válida
        PS->>PR: save(pedido + EventoPedido)
        PS-)TO: alCambiarEstado(evento)
        TO->>TO: registrar fecha/hora y ubicación
        PS-)NO: alCambiarEstado(evento)
        NO->>ES: enviar notificación al comprador
        PS-->>PC: Pedido actualizado
        PC-->>Ag: 200 OK
    end
```

## SEQ-05 — Consulta de trazabilidad (HU-14)

```mermaid
sequenceDiagram
    actor Co as Comprador
    participant PC as PedidoController
    participant TS as TrazabilidadService
    participant ER as EventoPedidoRepository

    Co->>PC: GET /api/v1/pedidos/{id}/trazabilidad
    PC->>TS: historial(id, compradorId)
    TS->>TS: verificar que el pedido pertenece al comprador
    TS->>ER: findByPedidoIdOrderByFechaHora(id)
    ER-->>TS: eventos
    TS-->>PC: lista de estados con fecha/hora y ubicación
    PC-->>Co: 200 OK
```
