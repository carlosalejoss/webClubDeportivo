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
    tipo VARCHAR(50) NOT NULL,
    numero_pista INT NOT NULL,
    descripcion VARCHAR(200) NOT NULL
);

-- Tabla para las reservas
CREATE TABLE IF NOT EXISTS reserva (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    pista_nombre VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    horaInicio TIME NOT NULL,
    horaFin TIME NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES usuario (id) ON DELETE CASCADE,
    FOREIGN KEY (pista_nombre) REFERENCES pista (nombre) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS clase (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    profesor VARCHAR(50) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    max_asistentes INT NOT NULL,
    precio DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS horario_clase (
    id SERIAL PRIMARY KEY, -- Identificador único del horario
    clase_id INT NOT NULL, -- Relación con la tabla de clase
    fecha DATE NOT NULL, -- Fecha del horario
    hora_inicio TIME NOT NULL, -- Hora de inicio de la clase
    hora_fin TIME NOT NULL, -- Hora de fin de la clase
    FOREIGN KEY (clase_id) REFERENCES clase (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reserva_clase (
    id SERIAL PRIMARY KEY, -- Identificador único de la reserva
    usuario_id INT NOT NULL, -- Relación con la tabla de usuario
    horario_id INT NOT NULL, -- Relación con la tabla de horario_clase
    fecha_reserva TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Fecha y hora en que se realizó la reserva
    FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE,
    FOREIGN KEY (horario_id) REFERENCES horario_clase (id) ON DELETE CASCADE
);

-- Insertar administradores iniciales
INSERT INTO usuario (nombre, apellidos, telefono, dni, email, password, esAdmin)
VALUES ('Carlos', 'Alejos Fumanal', '123456789', '11111111A', 'admin1@club.com', 'adminpass1', TRUE);

INSERT INTO usuario (nombre, apellidos, telefono, dni, email, password, esAdmin)
VALUES ('Diego', 'Valenzuela Saz', '987654321', '22222222B', 'admin2@club.com', 'adminpass2', TRUE);

INSERT INTO usuario (nombre, apellidos, telefono, dni, email, password, esAdmin)
VALUES ('Victor', 'Martinez Paramo', '123123123', '33333333C', 'socio1@club.com', 'sociopass1', FALSE);

-- Insertar pistas
INSERT INTO pista (nombre, tipo, numero_pista, descripcion) VALUES ('Pista 1 Baloncesto', 'Baloncesto', 1, 'Pista para la practica de baloncesto, construida en 2009 con motivo del primer aniversario de la creación del club. Dispone de material alquilable para su practica');
INSERT INTO pista (nombre, tipo, numero_pista, descripcion) VALUES ('Pista 2 Baloncesto', 'Baloncesto', 2, 'Pista para la practica de baloncesto, construida en 2009 con motivo del primer aniversario de la creación del club. Dispone de material alquilable para su practica');
INSERT INTO pista (nombre, tipo, numero_pista, descripcion) VALUES ('Pista 1 Tenis', 'Tenis',1, 'Pista para la practica del tenis, construida en 2009 con motivo del primer aniversario de la creación del club. Dispone de material alquilable para su practica');
INSERT INTO pista (nombre, tipo, numero_pista, descripcion) VALUES ('Pista 2 Tenis', 'Tenis', 2, 'Pista para la practica del tenis, construida en 2009 con motivo del primer aniversario de la creación del club. Dispone de material alquilable para su practica');
INSERT INTO pista (nombre, tipo, numero_pista, descripcion) VALUES ('Pista 1 Futbol 11', 'Futbol 11', 1,'Pista para la practica de fútbol base, construida en 2009 con motivo del primer aniversario de la creación del club. Dispone de material alquilable para su practica');
INSERT INTO pista (nombre, tipo, numero_pista, descripcion) VALUES ('Pista 2 Futbol 11', 'Futbol 11',2,'Pista para la practica defútbol base, construida en 2009 con motivo del primer aniversario de la creación del club. Dispone de material alquilable para su practica');
INSERT INTO pista (nombre, tipo, numero_pista, descripcion) VALUES ('Pista 1 Futbol 7', 'Futbol 7',1, 'Pista para la practica de fútbol 11, construida en 2009 con motivo del primer aniversario de la creación del club. Dispone de material alquilable para su practica');
INSERT INTO pista (nombre, tipo, numero_pista, descripcion) VALUES ('Pista 2 Futbol 7', 'Futbol 7',2, 'Pista para la practica de fútbol 11, construida en 2009 con motivo del primer aniversario de la creación del club. Dispone de material alquilable para su practica');

INSERT INTO clase (nombre, profesor, tipo, descripcion, max_asistentes, precio)
VALUES
    ('Yoga Básico', 'María López', 'Yoga', 'Clase de yoga para principiantes', 15, 20.00),
    ('Pilates Avanzado', 'Carlos García', 'Pilates', 'Sesión avanzada de pilates para mejorar la flexibilidad y fuerza', 10, 25.00),
    ('Zumba Fitness', 'Laura Martínez', 'Zumba', 'Clase de zumba para quemar calorías bailando', 20, 15.00),
    ('HIIT Intensivo', 'Javier Pérez', 'HIIT', 'Entrenamiento por intervalos de alta intensidad', 12, 30.00),
    ('HIIT Chill', 'Paqui hiit', 'HIIT', 'Entrenamiento por intervalos de alta intensidad', 12, 30.00),
    ('Meditación Guiada', 'Ana Torres', 'Meditación', 'Sesión guiada para reducir el estrés y mejorar la concentración', 8, 10.00);

INSERT INTO horario_clase (clase_id, fecha, hora_inicio, hora_fin)
VALUES
    (1, '2024-11-20', '08:00:00', '09:00:00'), -- Yoga Básico
    (1, '2024-11-22', '08:00:00', '09:00:00'), -- Yoga Básico
    (2, '2024-11-21', '10:00:00', '11:30:00'), -- Pilates Avanzado
    (2, '2024-11-23', '10:00:00', '11:30:00'), -- Pilates Avanzado
    (3, '2024-11-20', '18:00:00', '19:00:00'), -- Zumba Fitness
    (3, '2024-11-22', '18:00:00', '19:00:00'), -- Zumba Fitness
    (4, '2024-11-21', '07:00:00', '07:45:00'), -- HIIT Intensivo
    (4, '2024-11-23', '07:00:00', '07:45:00'), -- HIIT Intensivo
    (5, '2024-11-20', '19:30:00', '20:30:00'), -- Meditación Guiada
    (5, '2024-11-22', '19:30:00', '20:30:00'); -- Meditación Guiada


