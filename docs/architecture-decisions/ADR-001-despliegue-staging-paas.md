# ADR-001: Adopción de PaaS y GitHub Actions para Despliegue Continuo en Staging

## Estado
Aceptado

## Contexto
El proyecto AgroValle Connect requiere un entorno accesible remotamente (Staging) para realizar pruebas End-to-End y la sustentación en vivo sin depender de ejecuciones locales, manteniendo costo operativo cero y alta automatización tras cada integración en la rama `main`.

## Decisión
Se adopta una arquitectura de despliegue continuo (CD) basada en **GitHub Actions** como orquestador de CI/CD, **Render / Railway** (PaaS con soporte nativo para contenedores Docker y Java 17) para el alojamiento del backend Spring Boot, y **Neon.tech** como proveedor de base de datos PostgreSQL en la nube. Las credenciales sensibles se gestionan mediante **GitHub Secrets** (`STAGING_DB_URL`, `STAGING_DB_USER`, `STAGING_DB_PASSWORD`, `JWT_SECRET_KEY`, `DEPLOY_HOOK_URL`) e inyección de variables de entorno en `src/main/resources/application-staging.yml`.

## Consecuencias
* **Positivas:** Despliegue automático sujeto a la aprobación previa de pruebas unitarias, cobertura JaCoCo >= 60% y Checkstyle; aislamiento estricto de credenciales; disponibilidad remota con certificado SSL y verificación de salud en `/api/v1/estado`.
* **Negativas:** Posible latencia en el primer arranque (*cold start*) tras periodos de inactividad debido a las cuotas de los planes gratuitos de PaaS.
