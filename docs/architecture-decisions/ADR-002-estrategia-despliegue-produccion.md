# ADR-002: Estrategia de Despliegue a Producción y Gestión de Versiones

## Estado
Aceptado

## Contexto
Para garantizar estabilidad en las entregas de AgroValle Connect y evitar regresiones en entornos productivos, es necesario definir una política formal de promoción de artefactos, etiquetado semántico y congelamiento de código (*Code Freeze*).

## Decisión
Se establece el uso de **Semantic Versioning (SemVer)** mediante etiquetas Git anotadas (ej. `v3.0.0-final`) sobre commits verificados en `main` tras superar los gates de calidad de CI (`mvn -B verify`) y validación funcional en Staging. La configuración de base de datos en producción desactiva la mutación automática de esquemas (`spring.jpa.hibernate.ddl-auto=validate`) delegando cambios estructurales a scripts DDL versionados.

## Consecuencias
* **Positivas:** Trazabilidad exacta entre cada versión desplegada y su código fuente; capacidad de rollback inmediato a etiquetas estables anteriores.
* **Negativas:** Requiere disciplina del equipo para registrar etiquetas y actualizar el historial de cambios antes de cada liberación.
