# Sprint de Diseño — Planning

## Sprint Goal
Diseñar la arquitectura y el comportamiento completo de AgroValle Connect (15 HU del `BACKLOG.md`) antes de implementar los Sprints de construcción, dejando documentados los diagramas UML, el modelo de datos, los patrones de diseño y las decisiones arquitectónicas (ADR). El diseño parte del código ya existente del Sprint 1 (`Agricultor`, `Finca`, `Oferta`) y lo extiende hacia los módulos de Pedidos, Stock, Logística y Trazabilidad.

## Entregables

| # | Entregable | Archivo |
|---|---|---|
| 1 | Diagrama de casos de uso | [`01-casos-de-uso.md`](./01-casos-de-uso.md) |
| 2 | Diagramas de actividad | [`02-diagramas-actividad.md`](./02-diagramas-actividad.md) |
| 3 | Diagrama de clases | [`03-diagrama-clases.md`](./03-diagrama-clases.md) |
| 4 | Diagramas de secuencia | [`04-diagramas-secuencia.md`](./04-diagramas-secuencia.md) |
| 5 | Diagrama de colaboración | [`05-diagrama-colaboracion.md`](./05-diagrama-colaboracion.md) |
| 6 | Diagrama de despliegue físico | [`06-diagrama-despliegue.md`](./06-diagrama-despliegue.md) |
| 7 | Modelo de datos (E-R) | [`07-modelo-datos.md`](./07-modelo-datos.md) |
| 8 | Patrones de diseño (Repository, Factory, Observer, Singleton) | [`08-patrones-diseno.md`](./08-patrones-diseno.md) |
| 9 | ADRs | [`../adr/`](../adr/) |

## Trazabilidad HU → artefactos de diseño

| HU | Casos de uso | Actividad | Secuencia | Clases principales |
|---|---|---|---|---|
| HU-01 Registro de Agricultores | CU-01 | — | SEQ-01 | `Agricultor`, `AgricultorService` |
| HU-02 Publicación de Productos | CU-02 | ACT-01 | SEQ-02 | `Oferta`, `Categoria`, `OfertaService` |
| HU-03 Precios Regionales | CU-03 | — | — | `PrecioRegionalService` (Singleton/caché) |
| HU-04 Filtro de Categorías | CU-04 | — | — | `CatalogoService`, `Categoria` |
| HU-05 Contacto Directo | CU-05 | — | — | `ContactoService` |
| HU-06 Registro de Finca | CU-06 | — | — | `Finca`, `FincaService` |
| HU-07 Búsqueda por Municipio | CU-07 | — | — | `CatalogoService`, `OfertaRepository` |
| HU-08 Disponibilidad en Tiempo Real | CU-08 | — | — | `Oferta.cantidadDisponibleKg` |
| HU-09 Carrito de Compra | CU-09 | ACT-02 | — | `Carrito`, `ItemCarrito` |
| HU-10 Reserva de Stock | CU-10 | ACT-02 | SEQ-03 | `StockService`, `Reserva` |
| HU-11 Orden de Compra | CU-11 | ACT-02 | SEQ-03 | `Pedido`, `PedidoFactory` |
| HU-12 Alistamiento | CU-12 | ACT-03 | SEQ-04 | `PedidoService`, `EstadoPedido` |
| HU-13 Programación de Ruta | CU-13 | ACT-03 | SEQ-04 | `Despacho`, `LogisticaService` |
| HU-14 Trazabilidad | CU-14 | ACT-03 | SEQ-05 | `EventoPedido`, `TrazabilidadService` |
| HU-15 Notificaciones | CU-15 | ACT-03 | SEQ-04 | `PedidoObserver`, `NotificacionService` |

## Descomposición de tareas

| Tarea | Responsable sugerido | Estado |
|---|---|---|
| Casos de uso + actores | _por asignar_ | Hecho (borrador) |
| Diagramas de actividad | _por asignar_ | Hecho (borrador) |
| Diagrama de clases + modelo E-R | _por asignar_ | Hecho (borrador) |
| Diagramas de secuencia y colaboración | _por asignar_ | Hecho (borrador) |
| Diagrama de despliegue | _por asignar_ | Hecho (borrador) |
| Patrones de diseño | _por asignar_ | Hecho (borrador) |
| ADRs | _por asignar_ | Hecho (borrador) |
| Revisión por pares (Pull Request) | Todo el equipo | Pendiente |

> ✏️ Asignar responsables reales a cada tarea y registrar el avance en la bitácora de Daily Scrum antes de la entrega.

## Definition of Done del Sprint de Diseño

- [ ] Cada diagrama renderiza en GitHub (sintaxis Mermaid válida).
- [ ] Cada HU del Backlog aparece en al menos un artefacto de diseño (ver tabla de trazabilidad).
- [ ] Las clases del diagrama son consistentes con las entidades del modelo E-R.
- [ ] Cada patrón tiene justificación y ubicación definida en el código.
- [ ] Cada decisión relevante tiene un ADR con estado y consecuencias.
- [ ] Pull Request revisado y aprobado por un par.

## Riesgos

- El diseño del stock concurrente (HU-10) es el punto más delicado: ver ADR-004.
- La estimación de SP debe validarse con Planning Poker real antes de planificar los Sprints de construcción.
