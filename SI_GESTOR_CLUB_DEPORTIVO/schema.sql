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