-- Tabla principal de usuarios, que incluye los identificadores únicos
CREATE TABLE IF NOT EXISTS user (
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
    contraseña VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);

-- Tabla para los administradores
CREATE TABLE IF NOT EXISTS administrador (
    user_id INT PRIMARY KEY,  -- Usamos user_id como clave primaria
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    telefono VARCHAR(15),
    dni VARCHAR(15) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    contraseña VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);

-- Tabla para las pistas
CREATE TABLE IF NOT EXISTS pista (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL
);

-- Tabla para las reservas
CREATE TABLE IF NOT EXISTS reserva (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    pista_id INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (pista_id) REFERENCES pista (id) ON DELETE CASCADE
);

-- Insertar administradores iniciales
INSERT INTO user DEFAULT VALUES;
INSERT INTO administrador (user_id, nombre, apellidos, telefono, dni, email, contraseña)
VALUES ((SELECT MAX(id) FROM user), 'Carlos', 'Alejos Fumanal', '123456789', '11111111A', 'admin1@club.com', 'adminpass1');

INSERT INTO user DEFAULT VALUES;
INSERT INTO administrador (user_id, nombre, apellidos, telefono, dni, email, contraseña)
VALUES ((SELECT MAX(id) FROM user), 'Diego', 'Valenzuela Saz', '987654321', '22222222B', 'admin2@club.com', 'adminpass2');

-- Insertar pistas
INSERT INTO pista (nombre, tipo) VALUES ('Pista 1 Baloncesto', 'Baloncesto');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 2 Baloncesto', 'Baloncesto');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 1 Tenis', 'Tenis');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 2 Tenis', 'Tenis');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 1 Futbol', 'Futbol 11');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 2 Futbol', 'Futbol 11');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 1 Futbol', 'Futbol 11');
INSERT INTO pista (nombre, tipo) VALUES ('Pista 2 Futbol', 'Futbol 11');
