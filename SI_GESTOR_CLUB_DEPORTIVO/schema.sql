-- Tabla principal de usuarios, que incluye los identificadores únicos
CREATE TABLE IF NOT EXISTS usuario (
    id SERIAL PRIMARY KEY
);

-- Tabla para los socios
CREATE TABLE IF NOT EXISTS socio (
    user_id INT PRIMARY KEY,  -- Usamos user_id como clave primaria
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    telefono VARCHAR(15),
    dni VARCHAR(15) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES usuario (id) ON DELETE CASCADE
);

-- Tabla para los administradores
CREATE TABLE IF NOT EXISTS administrador (
    user_id INT PRIMARY KEY,  -- Usamos user_id como clave primaria
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    telefono VARCHAR(15),
    dni VARCHAR(15) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES usuario (id) ON DELETE CASCADE
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
    pista_nombre INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES usuario (id) ON DELETE CASCADE,
    FOREIGN KEY (pista_nombre) REFERENCES pista (nombre) ON DELETE CASCADE
);

-- Insertar administradores iniciales
INSERT INTO usuario DEFAULT VALUES;
INSERT INTO administrador (user_id, nombre, apellidos, telefono, dni, email, password)
VALUES ((SELECT MAX(id) FROM usuario), 'Carlos', 'Alejos Fumanal', '123456789', '11111111A', 'admin1@club.com', 'adminpass1');

INSERT INTO usuario DEFAULT VALUES;
INSERT INTO administrador (user_id, nombre, apellidos, telefono, dni, email, password)
VALUES ((SELECT MAX(id) FROM usuario), 'Diego', 'Valenzuela Saz', '987654321', '22222222B', 'admin2@club.com', 'adminpass2');

-- Insertar pistas
INSERT INTO pista (nombre, tipo) VALUES ('Pista 1 Baloncesto', 'Baloncesto');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 2 Baloncesto', 'Baloncesto');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 1 Tenis', 'Tenis');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 2 Tenis', 'Tenis');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 1 Futbol', 'Futbol 11');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 2 Futbol', 'Futbol 11');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 1 Futbol', 'Futbol 11');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 2 Futbol', 'Futbol 11');
