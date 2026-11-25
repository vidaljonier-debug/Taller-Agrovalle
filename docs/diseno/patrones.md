# Patrones de Diseño GoF — AgroValle Connect (Sprint 2)

## 1. Patrón Comportamental Implementado: Strategy (Cálculo Dinámico de Fletes)

### Problema de Negocio Resuelto
En el dominio agrícola del Valle del Cauca, el costo de transporte de las cosechas varía según la topografía, distancia y tipo de vía de cada municipio (por ejemplo, rutas con recargo por trocha como Dagua frente a corredores de autopista como Palmira). Implementar esta lógica mediante bloques condicionales rígidos (`if-else` o `switch`) dentro de la capa de servicio violaría el principio **Open/Closed (OCP)**, incrementaría la complejidad ciclomática y obligaría a modificar y re-probar código existente cada vez que se incorpore un nuevo municipio a la plataforma[cite: 52, 57].

### Solución Arquitectónica en Spring Boot
Se aplicó el patrón **Strategy** aprovechando la inyección automática de dependencias del contenedor IoC de Spring Boot:
- **Interfaz Estrategia (`CalculoFleteStrategy`):** Define el contrato único `calcular(double pesoKg)` y `getMunicipio()`.
- **Estrategias Concretas (`@Component`):**
  - `FleteDaguaStrategy` (`@Component("Dagua")`): Aplica tarifa base por kilogramo más recargo fijo por vía terciaria/trocha.
  - `FletePalmiraStrategy` (`@Component("Palmira")`): Aplica tarifa directa por kilogramo en corredor plano/autopista.
- **Contexto Orquestador (`FleteService`):** Recibe por constructor un `Map<String, CalculoFleteStrategy>` inyectado automáticamente por Spring, resolviendo la tarifa correspondiente en tiempo $O(1)$ sin condicionales[cite: 52].

### Diagrama de Clases UML

```mermaid
classDiagram
    class FleteService {
        -Map~String, CalculoFleteStrategy~ estrategias
        +FleteService(Map~String, CalculoFleteStrategy~ estrategias)
        +calcularFlete(String municipio, double pesoKg) double
    }

    class CalculoFleteStrategy {
        <<interface>>
        +calcular(double pesoKg) double
        +getMunicipio() String
    }

    class FleteDaguaStrategy {
        -double TARIFA_KG
        -double RECARGO_TROCHA
        +calcular(double pesoKg) double
        +getMunicipio() String
    }

    class FletePalmiraStrategy {
        -double TARIFA_KG
        +calcular(double pesoKg) double
        +getMunicipio() String
    }

    FleteService --> CalculoFleteStrategy : delega cálculo
    CalculoFleteStrategy <|.. FleteDaguaStrategy : implementa
    CalculoFleteStrategy <|.. FletePalmiraStrategy : implementa