# Arquitectura y Diagramación UML - AgroValle Connect

> **Decisión Arquitectónica Relacionada:** [ADR-001: Adopción de PaaS y GitHub Actions para Despliegue Continuo en Staging](../architecture-decisions/ADR-001-despliegue-staging-paas.md)

Documento consolidado de vistas estructurales y de comportamiento del sistema AgroValle Connect, alineado con los estándares **ISO/IEC/IEEE 29148:2018** e **ISO/IEC 25010**.

---

## 1. Diagramas de Secuencia

### 1.1 Autenticación e Inicio de Sesión JWT (HU-07)
```mermaid
sequenceDiagram
    autonumber
    actor Cliente as "Comprador / Agricultor"
    participant Controller as AuthController
    participant Service as AuthService
    participant Repo as UsuarioRepository
    participant DB as "PostgreSQL (BD)"

    Cliente->>Controller: POST /api/v1/auth/login (LoginRequestDTO)
    activate Controller
    Controller->>Service: autenticarUsuario(dto)
    activate Service
    Service->>Repo: findByCorreo(correo)
    activate Repo
    Repo->>DB: SELECT * FROM usuarios_productores WHERE correo = ?
    activate DB
    DB-->>Repo: Registro usuario / Hash BCrypt
    deactivate DB
    Repo-->>Service: Optional<Usuario>
    deactivate Repo

    alt Credenciales inválidas
        Service-->>Controller: throw CredencialesInvalidasException
        Controller-->>Cliente: HTTP 401 Unauthorized
    else Credenciales válidas
        Service->>Service: generarTokenJWT(usuario)
        Service-->>Controller: JwtResponseDTO(tokenJWT)
        deactivate Service
        Controller-->>Cliente: HTTP 200 OK (tokenJWT)
    end
    deactivate Controller
```

### 1.2 Publicación de Oferta de Cosecha (HU-02)
```mermaid
sequenceDiagram
    autonumber
    actor Cliente as Agricultor
    participant Controller as ProductoController
    participant Service as ProductoService
    participant FincaRepo as FincaRepository
    participant ProdRepo as ProductoRepository
    participant DB as "PostgreSQL (BD)"

    Cliente->>Controller: POST /api/v1/productos (PublicarProductoDTO)
    activate Controller
    Controller->>Service: publicarCosecha(dto, usuarioId)
    activate Service
    Service->>FincaRepo: findByIdAndPropietarioId(fincaId, agricultorId)
    activate FincaRepo
    FincaRepo->>DB: SELECT * FROM fincas WHERE id = ?
    activate DB
    DB-->>FincaRepo: Datos Finca
    deactivate DB
    FincaRepo-->>Service: Optional<Finca>
    deactivate FincaRepo

    alt Finca no pertenece al agricultor
        Service-->>Controller: throw FincaNoAutorizadaException
        Controller-->>Cliente: HTTP 403 Forbidden
    else Datos válidos y propiedad verificada
        Service->>ProdRepo: save(ofertaCosecha)
        activate ProdRepo
        ProdRepo->>DB: INSERT INTO ofertas_cosecha (...)
        activate DB
        DB-->>ProdRepo: OfertaCosecha (ID)
        deactivate DB
        ProdRepo-->>Service: OfertaCosecha
        deactivate ProdRepo
        Service-->>Controller: ProductoResponseDTO
        deactivate Service
        Controller-->>Cliente: HTTP 201 Created (ProductoResponseDTO)
    end
    deactivate Controller
```

### 1.3 Consulta de Precios Promedio Regionales (HU-03)
```mermaid
sequenceDiagram
    autonumber
    actor Cliente as "Usuario / Comprador"
    participant Controller as PrecioController
    participant Service as PrecioService
    participant Repo as TransaccionRepository
    participant DB as "PostgreSQL (BD)"

    Cliente->>Controller: GET /api/v1/precios/promedio?producto=Cafe
    activate Controller
    Controller->>Service: calcularPrecioPromedio("Cafe")
    activate Service
    Service->>Repo: findTransaccionesEnVentana("Cafe", 24h)
    activate Repo
    Repo->>DB: SELECT precio_unitario FROM transacciones
    activate DB
    DB-->>Repo: List<BigDecimal> precios
    deactivate DB
    Repo-->>Service: List<BigDecimal>
    deactivate Repo
    Service->>Service: calcularPromedioBigDecimal(precios)
    Service-->>Controller: PrecioPromedioDTO("Cafe", 2500.00)
    deactivate Service
    Controller-->>Cliente: HTTP 200 OK (2500.00 COP/kg)
    deactivate Controller
```

---

## 2. Diagramas de Colaboración / Comunicación

### 2.1 Confirmación de Orden y Reserva de Inventario (HU-12)
```mermaid
flowchart TD
    C["1: Cliente (Comprador)"]
    CTRL[": OrdenController"]
    SERV[": OrdenService"]
    INV[": InventarioService"]
    OREPO[": OrdenRepository"]
    CREPO[": CarritoRepository"]
    DB[("PostgreSQL")]

    C -- "1: POST /api/v1/ordenes" --> CTRL
    CTRL -- "2: confirmarOrden(carritoId)" --> SERV
    SERV -- "3: verificarIdempotencia()" --> SERV
    SERV -- "4: obtenerCarrito(carritoId)" --> CREPO
    CREPO -- "5: SELECT * FROM carrito" --> DB
    DB -. "6: datosCarrito" .-> CREPO
    CREPO -. "7: CarritoEntidad" .-> SERV
    SERV -- "8: descontarStock(items)" --> INV
    INV -- "9: UPDATE ofertas_cosecha" --> DB
    SERV -- "10: save(nuevaOrden)" --> OREPO
    OREPO -- "11: INSERT INTO ordenes" --> DB
    DB -. "12: OrdenPersistida" .-> OREPO
    SERV -- "13: vaciarCarrito(carritoId)" --> CREPO
    CREPO -- "14: DELETE FROM carrito_items" --> DB
    SERV -. "15: OrdenResponseDTO" .-> CTRL
    CTRL -. "16: HTTP 201 Created" .-> C
```

### 2.2 Cambio de Estado de Despacho y Notificación - Patrón Observer (HU-13)
```mermaid
flowchart TD
    A["1: Agricultor"]
    CTRL[": OrdenController"]
    SERV[": OrdenService"]
    OREPO[": OrdenRepository"]
    PUB[": ApplicationEventPublisher"]
    OBS[": NotificacionCompradorListener (Observer)"]
    DB[("PostgreSQL")]

    A -- "1: PATCH /api/v1/ordenes/10/estado" --> CTRL
    CTRL -- "2: actualizarEstado(10, LISTO_PARA_DESPACHO)" --> SERV
    SERV -- "3: findById(10)" --> OREPO
    OREPO -- "4: SELECT * FROM ordenes" --> DB
    DB -. "5: OrdenEntidad" .-> OREPO
    OREPO -. "6: OrdenEntidad" .-> SERV
    SERV -- "7: validarTransicionEstado()" --> SERV
    SERV -- "8: save(ordenActualizada)" --> OREPO
    OREPO -- "9: UPDATE ordenes SET estado = 'LISTO'" --> DB
    SERV -- "10: publishEvent(EstadoOrdenCambiadoEvent)" --> PUB
    PUB -- "11: @EventListener onEstadoOrdenCambiado()" --> OBS
    OBS -- "12: INSERT INTO notificaciones" --> DB
    SERV -. "13: OrdenEstadoDTO" .-> CTRL
    CTRL -. "14: HTTP 200 OK" .-> A
```

---

## 3. Diagrama de Componentes (Arquitectura MVC en 4 Capas)
```mermaid
flowchart TB
    subgraph Presentacion["Capa de Presentación (REST API)"]
        AuthCtrl[AuthController]
        ProdCtrl[ProductoController]
        OrdCtrl[OrdenController]
        EstadoCtrl[EstadoController]
    end

    subgraph Seguridad["Capa de Seguridad & Middleware"]
        SecFilter[Spring Security Filter]
        JwtFilter[JwtAuthenticationFilter]
    end

    subgraph Negocio["Capa de Lógica de Negocio (Domain Services)"]
        AuthSrv[AuthService]
        ProdSrv[ProductoService]
        OrdSrv[OrdenService]
        FleteCtx[FleteStrategyContext]
    end

    subgraph Datos["Capa de Acceso a Datos (Spring Data JPA)"]
        UserRepo[UsuarioRepository]
        ProdRepo[ProductoRepository]
        OrdRepo[OrdenRepository]
    end

    subgraph Infra["PostgreSQL Cloud"]
        DB[(Base de Datos Staging)]
    end

    SecFilter --> JwtFilter
    JwtFilter ..> AuthCtrl
    AuthCtrl ..> AuthSrv
    ProdCtrl ..> ProdSrv
    OrdCtrl ..> OrdSrv
    OrdSrv ..> FleteCtx

    AuthSrv ..> UserRepo
    ProdSrv ..> ProdRepo
    OrdSrv ..> OrdRepo

    UserRepo --> DB
    ProdRepo --> DB
    OrdRepo --> DB
```

---

## 4. Diagrama de Despliegue Físico de Infraestructura
```mermaid
flowchart LR
    subgraph Client["Cliente Web / Mobile"]
        Browser[Navegador Web / Postman]
    end

    subgraph GitHub["GitHub Platform"]
        Repo[GitHub Repository - main branch]
        Runner[GitHub Actions Runner - CD Pipeline]
        Repo --> Runner
    end

    subgraph PaaS["PaaS Cloud Infrastructure (Render / Railway)"]
        subgraph Docker["Docker Container (Spring Boot App)"]
            JVM[Java 17 Runtime Environment]
            Jar[AgroValle Backend .jar]
        end
    end

    subgraph DBNode["PostgreSQL Cloud DB (Neon.tech)"]
        DB[(agrovalle_staging)]
    end

    Browser -- "HTTP REST (Port 8080 / SSL)" --> PaaS
    Runner -- "Webhook Event (DEPLOY_HOOK_URL)" --> PaaS
    PaaS -- "JDBC Connection (Port 5432)" --> DBNode
```
