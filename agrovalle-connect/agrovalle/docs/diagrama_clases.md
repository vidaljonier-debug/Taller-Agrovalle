# 3. Diagrama de clases

Las clases marcadas con `«existe»` ya están implementadas en el Sprint 1 (`model/`, `service/`, `repository/`). El resto es diseño objetivo para los siguientes Sprints.

## 3.1 Modelo de dominio

```mermaid
classDiagram
    direction LR

    class Agricultor {
        «existe · HU-01»
        -Long id
        -String nombre
        -String municipio
        -String cedula
    }
    class Finca {
        «existe · HU-06»
        -Long id
        -String nombre
        -String municipio
        -Long agricultorId
    }
    class Categoria {
        -Long id
        -String nombre
    }
    class Oferta {
        «existe · HU-02, HU-07, HU-08»
        -Long id
        -String producto
        -String municipio
        -double cantidadKg
        -BigDecimal precioPorKg
        -LocalDate fechaCosecha
        -Long version
    }
    class Comprador {
        -Long id
        -String nombre
        -String email
    }
    class Carrito {
        -Long id
        +agregar(oferta, kg)
        +subtotal() BigDecimal
    }
    class ItemCarrito {
        -double cantidadKg
        -BigDecimal precioUnitario
    }
    class Reserva {
        -Long id
        -double cantidadKg
        -Instant creadaEn
    }
    class Pedido {
        -Long id
        -String numero
        -EstadoPedido estado
        -BigDecimal total
        +avanzarA(EstadoPedido)
    }
    class ItemPedido {
        -double cantidadKg
        -BigDecimal precioUnitario
    }
    class EventoPedido {
        -Long id
        -EstadoPedido estado
        -Instant fechaHora
        -String ubicacion
        -String responsable
    }
    class Despacho {
        -Long id
        -LocalDateTime fechaSalida
        -String vehiculo
        -String ruta
    }
    class Notificacion {
        -Long id
        -String mensaje
        -Canal canal
        -Instant enviadaEn
    }
    class EstadoPedido {
        <<enumeration>>
        RESERVADO
        ALISTADO
        DESPACHADO
        ENTREGADO
        CANCELADO
    }

    Agricultor "1" --> "1..*" Finca : posee
    Finca "1" --> "0..*" Oferta : origina
    Categoria "1" --> "0..*" Oferta : clasifica
    Comprador "1" --> "0..1" Carrito : tiene
    Carrito "1" *-- "1..*" ItemCarrito
    ItemCarrito "*" --> "1" Oferta
    Comprador "1" --> "0..*" Pedido : realiza
    Pedido "1" *-- "1..*" ItemPedido
    ItemPedido "*" --> "1" Oferta
    ItemPedido "1" --> "0..1" Reserva
    Pedido "1" *-- "1..*" EventoPedido : historial
    Pedido "1" --> "0..1" Despacho
    Pedido --> EstadoPedido
    Pedido "1" --> "0..*" Notificacion
```

> **Nota de evolución:** hoy `Finca.agricultorId` y `Oferta.municipio` son campos simples. En los Sprints siguientes se migran a relaciones JPA (`@ManyToOne Agricultor`, `@ManyToOne Finca`), y el `municipio` de la oferta se deriva de la finca. `Oferta.cantidadKg` se interpreta como stock disponible.

## 3.2 Capas, servicios y patrones

```mermaid
classDiagram
    direction TB

    class AgricultorController
    class FincaController
    class CatalogoController
    class PedidoController
    class AgricultorService
    class FincaService
    class CatalogoService
    class StockService {
        +reservar(items) List~Reserva~
        +liberar(pedido)
    }
    class PedidoService {
        +crear(carrito) Pedido
        +cambiarEstado(id, nuevo)
    }
    class LogisticaService {
        +programarRuta(pedidoId, despacho)
    }
    class TrazabilidadService {
        +historial(pedidoId) List~EventoPedido~
    }
    class PrecioRegionalService {
        +promedio24h(producto) BigDecimal
    }

    class AgricultorRepository {
        <<interface>>
    }
    class FincaRepository {
        <<interface>>
    }
    class OfertaRepository {
        <<interface>>
        +findByMunicipioIgnoreCase(m)
    }
    class PedidoRepository {
        <<interface>>
    }

    class PedidoFactory {
        +desde(carrito, reservas) Pedido
    }
    class PedidoSubject {
        <<interface>>
        +suscribir(PedidoObserver)
        +notificar(EventoPedido)
    }
    class PedidoObserver {
        <<interface>>
        +alCambiarEstado(EventoPedido)
    }
    class NotificacionEmailObserver
    class TrazabilidadObserver
    class JwtProvider {
        <<singleton>>
    }

    AgricultorController --> AgricultorService
    FincaController --> FincaService
    CatalogoController --> CatalogoService
    PedidoController --> PedidoService
    PedidoController --> TrazabilidadService
    AgricultorService --> AgricultorRepository
    FincaService --> FincaRepository
    CatalogoService --> OfertaRepository
    StockService --> OfertaRepository
    PedidoService --> StockService
    PedidoService --> PedidoFactory
    PedidoService --> PedidoRepository
    PedidoService ..|> PedidoSubject
    PedidoSubject o--> PedidoObserver
    NotificacionEmailObserver ..|> PedidoObserver
    TrazabilidadObserver ..|> PedidoObserver
    LogisticaService --> PedidoService
    CatalogoService --> PrecioRegionalService
```
