# 1. Diagrama de casos de uso

## Actores

| Actor | Descripción |
|---|---|
| **Agricultor** | Productor del Valle del Cauca que registra finca, publica oferta y alista pedidos. |
| **Comprador** | Cliente urbano (Cali y alrededores) que consulta el catálogo y compra. |
| **Operador logístico** | Programa rutas y despachos. Un agricultor también puede asumir este rol (HU-13). |
| **Sistema de notificaciones** | Actor secundario que entrega los avisos de cambio de estado (HU-15). |

## Diagrama

```mermaid
flowchart LR
    AG([👤 Agricultor])
    CO([👤 Comprador])
    OP([👤 Operador logístico])
    NT([⚙️ Sistema de notificaciones])

    subgraph M1[Módulo 1 · Productores y Ofertas]
        CU01((CU-01 Registrar agricultor))
        CU06((CU-06 Registrar finca))
        CU02((CU-02 Publicar producto))
    end
    subgraph M2[Módulo 2 · Catálogo y Búsqueda]
        CU04((CU-04 Filtrar por categoría))
        CU07((CU-07 Buscar por municipio))
        CU03((CU-03 Ver precios regionales))
        CU05((CU-05 Contactar productor))
        CU08((CU-08 Consultar disponibilidad))
    end
    subgraph M3[Módulo 3 · Pedidos y Stock]
        CU09((CU-09 Gestionar carrito))
        CU10((CU-10 Reservar stock))
        CU11((CU-11 Generar orden de compra))
    end
    subgraph M4[Módulo 4 · Logística y Trazabilidad]
        CU12((CU-12 Confirmar alistamiento))
        CU13((CU-13 Programar ruta))
        CU14((CU-14 Consultar trazabilidad))
        CU15((CU-15 Recibir notificaciones))
    end

    AG --- CU01
    AG --- CU06
    AG --- CU02
    AG --- CU12
    AG --- CU13
    AG --- CU03

    CO --- CU04
    CO --- CU07
    CO --- CU03
    CO --- CU05
    CO --- CU08
    CO --- CU09
    CO --- CU11
    CO --- CU14

    OP --- CU13

    CU11 -. include .-> CU10
    CU09 -. include .-> CU08
    CU06 -. depende de .-> CU01
    CU02 -. depende de .-> CU06
    CU12 -. extend .-> CU15
    CU13 -. extend .-> CU15
    CU11 -. extend .-> CU15
    CU15 --- NT
```

## Especificación resumida de casos de uso

| CU | HU | Actor | Precondición | Flujo principal | Postcondición |
|---|---|---|---|---|---|
| CU-01 | HU-01 | Agricultor | Cédula válida, municipio del Valle | Envía nombre, municipio y cédula → el sistema valida → persiste | Agricultor creado (`201 Created`) |
| CU-02 | HU-02 | Agricultor | Autenticado (JWT), finca registrada | Ingresa tipo, cantidad, precio y fecha de cosecha → valida (fecha ≥ hoy) | Oferta publicada con id único |
| CU-03 | HU-03 | Usuario | — | Solicita producto → el sistema promedia transacciones de 24 h | Precio promedio en COP |
| CU-04 | HU-04 | Comprador | Existen ofertas categorizadas | Selecciona categoría | Solo ofertas de esa categoría y disponibles |
| CU-05 | HU-05 | Comprador | Oferta con productor asociado | Pulsa "Contactar productor" | Canal de contacto habilitado, sin datos internos |
| CU-06 | HU-06 | Agricultor | Autenticado | Ingresa nombre de finca y municipio | Finca asociada al agricultor |
| CU-07 | HU-07 | Comprador | — | Filtra por municipio | Ofertas del municipio (lista vacía si no hay) |
| CU-08 | HU-08 | Comprador | Oferta publicada | Consulta la oferta | Stock actual visible; no se permite reservar más |
| CU-09 | HU-09 | Comprador | Autenticado | Agrega ofertas al carrito | Ítems consolidados con subtotal |
| CU-10 | HU-10 | Comprador | Carrito con stock suficiente | Confirma compra | Stock reservado en transacción |
| CU-11 | HU-11 | Comprador | Reservas válidas | Confirma pedido | Pedido con número único y estado `RESERVADO` |
| CU-12 | HU-12 | Agricultor | Pedido `RESERVADO` | Confirma alistamiento | Pedido `ALISTADO` con fecha y responsable |
| CU-13 | HU-13 | Agricultor/Operador | Pedido `ALISTADO` | Registra fecha, vehículo y ruta | Despacho guardado; pedido `DESPACHADO` al iniciar envío |
| CU-14 | HU-14 | Comprador | Pedido con eventos | Consulta seguimiento | Secuencia de estados con fecha/hora |
| CU-15 | HU-15 | Usuario | Pedido cambia de estado | (automático) Observer notifica | Notificación entregada |


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
