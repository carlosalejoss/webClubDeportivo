-- Tabla principal de usuarios, que incluye los identificadores únicos
CREATE TABLE IF NOT EXISTS usuario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    dni VARCHAR(15) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    esAdmin BOOLEAN NOT NULL
);

-- Tabla para las pistas
CREATE TABLE IF NOT EXISTS pista (
    nombre VARCHAR(100) PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL
);

-- Tabla para las reservas
CREATE TABLE IF NOT EXISTS reserva (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    pista_nombre VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES usuario (id) ON DELETE CASCADE,
    FOREIGN KEY (pista_nombre) REFERENCES pista (nombre) ON DELETE CASCADE
);

-- Insertar administradores iniciales
INSERT INTO usuario (nombre, apellidos, telefono, dni, email, password, esAdmin)
VALUES ('Carlos', 'Alejos Fumanal', '123456789', '11111111A', 'admin1@club.com', 'adminpass1', TRUE);

INSERT INTO usuario (nombre, apellidos, telefono, dni, email, password, esAdmin)
VALUES ('Diego', 'Valenzuela Saz', '987654321', '22222222B', 'admin2@club.com', 'adminpass2', TRUE);

INSERT INTO usuario (nombre, apellidos, telefono, dni, email, password, esAdmin)
VALUES ('Victor', 'Martinez Paramo', '123123123', '33333333C', 'socio1@club.com', 'sociopass1', FALSE);

-- Insertar pistas
INSERT INTO pista (nombre, tipo) VALUES ('Pista 1 Baloncesto', 'Baloncesto');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 2 Baloncesto', 'Baloncesto');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 1 Tenis', 'Tenis');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 2 Tenis', 'Tenis');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 1 Futbol 11', 'Futbol 11');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 2 Futbol 11', 'Futbol 11');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 1 Futbol 7', 'Futbol 11');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 2 Futbol 7', 'Futbol 11');
