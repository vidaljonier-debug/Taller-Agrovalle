# 7. Modelo de datos (E-R lógico, PostgreSQL)

```mermaid
erDiagram
    AGRICULTOR ||--|{ FINCA : posee
    FINCA ||--o{ OFERTA : origina
    CATEGORIA ||--o{ OFERTA : clasifica
    COMPRADOR ||--o| CARRITO : tiene
    CARRITO ||--|{ ITEM_CARRITO : contiene
    OFERTA ||--o{ ITEM_CARRITO : referencia
    COMPRADOR ||--o{ PEDIDO : realiza
    PEDIDO ||--|{ ITEM_PEDIDO : contiene
    OFERTA ||--o{ ITEM_PEDIDO : referencia
    ITEM_PEDIDO ||--o| RESERVA : respalda
    PEDIDO ||--|{ EVENTO_PEDIDO : historial
    PEDIDO ||--o| DESPACHO : "se entrega por"
    PEDIDO ||--o{ NOTIFICACION : genera

    AGRICULTOR {
        bigint id PK
        varchar nombre
        varchar municipio
        varchar cedula UK
    }
    FINCA {
        bigint id PK
        varchar nombre
        varchar municipio
        bigint agricultor_id FK
    }
    CATEGORIA {
        bigint id PK
        varchar nombre UK
    }
    OFERTA {
        bigint id PK
        varchar producto
        varchar municipio
        numeric cantidad_kg
        numeric precio_por_kg
        date fecha_cosecha
        bigint finca_id FK
        bigint categoria_id FK
        bigint version
    }
    COMPRADOR {
        bigint id PK
        varchar nombre
        varchar email UK
    }
    CARRITO {
        bigint id PK
        bigint comprador_id FK
    }
    ITEM_CARRITO {
        bigint id PK
        bigint carrito_id FK
        bigint oferta_id FK
        numeric cantidad_kg
    }
    PEDIDO {
        bigint id PK
        varchar numero UK
        varchar estado
        numeric total
        bigint comprador_id FK
        timestamp creado_en
    }
    ITEM_PEDIDO {
        bigint id PK
        bigint pedido_id FK
        bigint oferta_id FK
        numeric cantidad_kg
        numeric precio_unitario
    }
    RESERVA {
        bigint id PK
        bigint item_pedido_id FK
        numeric cantidad_kg
        timestamp creada_en
    }
    EVENTO_PEDIDO {
        bigint id PK
        bigint pedido_id FK
        varchar estado
        timestamp fecha_hora
        varchar ubicacion
        varchar responsable
    }
    DESPACHO {
        bigint id PK
        bigint pedido_id FK
        timestamp fecha_salida
        varchar vehiculo
        varchar ruta
    }
    NOTIFICACION {
        bigint id PK
        bigint pedido_id FK
        varchar mensaje
        varchar canal
        timestamp enviada_en
    }
```

## Reglas de integridad

- `cedula` única por agricultor; `email` único por comprador.
- `cantidad_kg >= 0` en `OFERTA` (CHECK) para impedir stock negativo.
- `PEDIDO.estado` ∈ {`RESERVADO`, `ALISTADO`, `DESPACHADO`, `ENTREGADO`, `CANCELADO`}.
- `OFERTA.version` habilita bloqueo optimista (ver [ADR-004](../adr/ADR-004-control-concurrencia-stock.md)).
- Contraseñas (cuando se agregue autenticación) se almacenan solo con hash.


