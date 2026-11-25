-- ============================================================================
-- AgroValle Connect - Esquema Relacional PostgreSQL (3FN)
-- Basado en: docs/sprint-diseño/07-modelo-datos.md
-- ============================================================================

CREATE TABLE agricultor (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    municipio VARCHAR(80) NOT NULL,
    cedula VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE finca (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    municipio VARCHAR(80) NOT NULL,
    agricultor_id BIGINT NOT NULL REFERENCES agricultor(id) ON DELETE CASCADE
);

CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE oferta (
    id BIGSERIAL PRIMARY KEY,
    producto VARCHAR(120) NOT NULL,
    municipio VARCHAR(80) NOT NULL,
    cantidad_kg NUMERIC(10, 2) NOT NULL CHECK (cantidad_kg >= 0),
    precio_por_kg NUMERIC(12, 2) NOT NULL CHECK (precio_por_kg > 0),
    fecha_cosecha DATE NOT NULL,
    finca_id BIGINT NOT NULL REFERENCES finca(id) ON DELETE CASCADE,
    categoria_id BIGINT NOT NULL REFERENCES categoria(id),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE comprador (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE carrito (
    id BIGSERIAL PRIMARY KEY,
    comprador_id BIGINT NOT NULL UNIQUE REFERENCES comprador(id) ON DELETE CASCADE
);

CREATE TABLE item_carrito (
    id BIGSERIAL PRIMARY KEY,
    carrito_id BIGINT NOT NULL REFERENCES carrito(id) ON DELETE CASCADE,
    oferta_id BIGINT NOT NULL REFERENCES oferta(id) ON DELETE CASCADE,
    cantidad_kg NUMERIC(10, 2) NOT NULL CHECK (cantidad_kg > 0)
);

CREATE TABLE pedido (
    id BIGSERIAL PRIMARY KEY,
    numero VARCHAR(40) NOT NULL UNIQUE,
    estado VARCHAR(20) NOT NULL CHECK (
        estado IN ('RESERVADO', 'ALISTADO', 'DESPACHADO', 'ENTREGADO', 'CANCELADO')
    ),
    total NUMERIC(14, 2) NOT NULL CHECK (total >= 0),
    comprador_id BIGINT NOT NULL REFERENCES comprador(id),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE item_pedido (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL REFERENCES pedido(id) ON DELETE CASCADE,
    oferta_id BIGINT NOT NULL REFERENCES oferta(id),
    cantidad_kg NUMERIC(10, 2) NOT NULL CHECK (cantidad_kg > 0),
    precio_unitario NUMERIC(12, 2) NOT NULL CHECK (precio_unitario > 0)
);

CREATE TABLE reserva (
    id BIGSERIAL PRIMARY KEY,
    item_pedido_id BIGINT NOT NULL UNIQUE REFERENCES item_pedido(id) ON DELETE CASCADE,
    cantidad_kg NUMERIC(10, 2) NOT NULL CHECK (cantidad_kg > 0),
    creada_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE evento_pedido (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL REFERENCES pedido(id) ON DELETE CASCADE,
    estado VARCHAR(20) NOT NULL,
    fecha_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ubicacion VARCHAR(120) NOT NULL,
    responsable VARCHAR(120) NOT NULL
);

CREATE TABLE despacho (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL UNIQUE REFERENCES pedido(id) ON DELETE CASCADE,
    fecha_salida TIMESTAMP NOT NULL,
    vehiculo VARCHAR(80) NOT NULL,
    ruta VARCHAR(200) NOT NULL
);

CREATE TABLE notificacion (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL REFERENCES pedido(id) ON DELETE CASCADE,
    mensaje VARCHAR(500) NOT NULL,
    canal VARCHAR(40) NOT NULL,
    enviada_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
