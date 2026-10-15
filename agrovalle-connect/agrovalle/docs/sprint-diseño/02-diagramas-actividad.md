# 2. Diagramas de actividad

## ACT-01 — Publicación de un producto (HU-02)

```mermaid
flowchart TD
    A([Inicio]) --> B[Agricultor inicia sesión]
    B --> C{¿JWT válido?}
    C -- No --> C1[Responder 401] --> Z([Fin])
    C -- Sí --> D[Ingresar tipo, cantidad, precio y fecha de cosecha]
    D --> E{¿Datos completos<br/>y cantidad/precio > 0?}
    E -- No --> E1[Responder 400 con detalle del error] --> Z
    E -- Sí --> F{¿Fecha de cosecha ≥ hoy?}
    F -- No --> F1[Rechazar: fecha anterior a hoy] --> Z
    F -- Sí --> G{¿Agricultor tiene finca registrada?}
    G -- No --> G1[Solicitar registro de finca - HU-06] --> Z
    G -- Sí --> H[Persistir Oferta con stock inicial]
    H --> I[Devolver identificador único 201]
    I --> Z
```

## ACT-02 — Compra: carrito, reserva de stock y orden (HU-09, HU-10, HU-11)

```mermaid
flowchart TD
    A([Inicio]) --> B[Comprador agrega ofertas al carrito]
    B --> C{¿Cantidad ≤ stock disponible?}
    C -- No --> C1[Informar disponibilidad máxima] --> B
    C -- Sí --> D[Consolidar ítems y calcular subtotal]
    D --> E[Comprador confirma la compra]
    E --> F[[Iniciar transacción]]
    F --> G[Bloquear filas de oferta - lock optimista/pesimista]
    G --> H{¿Stock suficiente<br/>para todos los ítems?}
    H -- No --> H1[Rollback] --> H2[Informar ítems agotados] --> B
    H -- Sí --> I[Descontar stock y crear Reservas]
    I --> J[Crear Pedido con número único - PedidoFactory]
    J --> K[Estado inicial RESERVADO + EventoPedido]
    K --> L[[Commit]]
    L --> M[Notificar creación - Observer]
    M --> Z([Fin])
```

## ACT-03 — Ciclo de vida del pedido: alistamiento, despacho y trazabilidad (HU-12 a HU-15)

```mermaid
flowchart TD
    A([Pedido RESERVADO]) --> B{¿Agricultor confirma alistamiento?}
    B -- Cancelar --> X1[Liberar stock reservado] --> X([CANCELADO])
    B -- Sí --> C[Registrar fecha y responsable]
    C --> D[Estado ALISTADO + EventoPedido]
    D --> N1[Notificar a observadores]
    D --> E{¿Se programa ruta?}
    E -- Cancelación autorizada --> X1
    E -- Sí --> F[Registrar fecha, vehículo y ruta]
    F --> G[Inicia el envío]
    G --> H[Estado DESPACHADO + EventoPedido]
    H --> N2[Notificar a observadores]
    H --> I[Comprador confirma o se registra la entrega]
    I --> J[Estado ENTREGADO + EventoPedido]
    J --> N3[Notificar a observadores]
    J --> Z([Fin])
    D -. consulta .-> T[Comprador consulta trazabilidad - HU-14]
    H -. consulta .-> T
```
