# ADR-001: Arquitectura por capas (MVC) en un monolito Spring Boot

- **Estado:** Aceptada
- **Fecha:** 2026-10-15

## Contexto
El equipo es de cuatro personas, con un semestre de plazo y experiencia previa en Java/Spring. Los cuatro módulos (Productores y Ofertas, Catálogo, Pedidos y Stock, Logística) comparten datos y transacciones, sobre todo la reserva de stock.

## Decisión
Un único servicio Spring Boot con capas `Controller → Service → Repository → Model`, y DTOs en la frontera HTTP. Un paquete por capa, como en el Sprint 1.

## Consecuencias
- (+) Despliegue simple y transacciones ACID locales (necesarias para HU-10).
- (+) Cada HU se reparte fácilmente entre integrantes.
- (−) Escala como una sola unidad; si algún módulo lo requiere, podría extraerse más adelante.
- (−) Exige disciplina para que los controladores no accedan directamente a repositorios.

## Alternativas descartadas
- **Microservicios:** complejidad operativa desproporcionada para el alcance.
