# 6. Diagrama de despliegue físico

```mermaid
flowchart TB
    subgraph CLI[Dispositivos cliente]
        WEB["💻 Navegador web<br/>Frontend Web (HTML/JS)"]
        MOV["📱 Navegador móvil"]
    end

    subgraph GH[GitHub]
        REPO["Repositorio Taller-Agrovalle"]
        ACT["GitHub Actions<br/>build · Checkstyle · JUnit · JaCoCo"]
    end

    subgraph STG[Entorno staging · se despliega al fusionar en main]
        direction TB
        subgraph APPN[Nodo de aplicación · Linux]
            JAR["«artifact» agrovalle-connect.jar<br/>Spring Boot 3.3 · Java 17<br/>puerto 8080"]
        end
        subgraph DBN[Nodo de datos · Linux]
            PG[("PostgreSQL 15+<br/>base: agrovalle<br/>puerto 5432")]
        end
        subgraph EXT[Servicios externos]
            MAIL["Servicio de correo / SMS<br/>notificaciones HU-15"]
        end
    end

    WEB -- "HTTPS · REST/JSON + JWT" --> JAR
    MOV -- "HTTPS · REST/JSON + JWT" --> JAR
    JAR -- "JDBC (TCP 5432)" --> PG
    JAR -- "SMTP / API HTTPS" --> MAIL
    REPO -- "push / PR" --> ACT
    ACT -- "despliegue automático" --> JAR
```

## Nodos y configuración

| Nodo | Tecnología | Configuración (variables de entorno) |
|---|---|---|
| Cliente | Navegador moderno | — |
| Aplicación | JVM 17 + Spring Boot | `DB_URL`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET` |
| Base de datos | PostgreSQL 15+ | Usuario `agrovalle`; credenciales fuera del código |
| CI/CD | GitHub Actions (`.github/workflows/ci-cd.yml`) | Build + Checkstyle + pruebas en push/PR a `main` y `develop` |

> Los secretos nunca se versionan: se inyectan por variables de entorno (ver `application.yml`).
