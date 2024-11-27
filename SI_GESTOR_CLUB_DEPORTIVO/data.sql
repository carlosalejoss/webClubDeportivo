-- Insertar administradores iniciales y un usuario basico.
INSERT INTO usuario (nombre, apellidos, telefono, dni, email, password, esAdmin)
VALUES
    ('Carlos', 'Alejos Fumanal', '123456789', '11111111A', 'admin1@club.com', 'adminpass1', TRUE),
    ('Diego', 'Valenzuela Saz', '987654321', '22222222B', 'admin2@club.com', 'adminpass2', TRUE),
    ('Victor', 'Martinez Paramo', '123123123', '33333333C', 'socio1@club.com', 'sociopass1', FALSE);

-- Insertar pistas
INSERT INTO pista (nombre, tipo, numero_pista, descripcion)
VALUES
    ('Pista 1 Baloncesto', 'Baloncesto', 1, 'Pista para la practica de baloncesto, construida en 2009 con motivo del primer aniversario de la creacion del club. Dispone de material alquilable para su practica'),
    ('Pista 2 Baloncesto', 'Baloncesto', 2, 'Pista para la practica de baloncesto, construida en 2009 con motivo del primer aniversario de la creacion del club. Dispone de material alquilable para su practica'),
    ('Pista 1 Tenis', 'Tenis',1, 'Pista para la practica del tenis, construida en 2009 con motivo del primer aniversario de la creacion del club. Dispone de material alquilable para su practica'),
    ('Pista 2 Tenis', 'Tenis', 2, 'Pista para la practica del tenis, construida en 2009 con motivo del primer aniversario de la creacion del club. Dispone de material alquilable para su practica'),
    ('Pista 1 Futbol 11', 'Futbol 11', 1,'Pista para la practica de futbol base, construida en 2009 con motivo del primer aniversario de la creacion del club. Dispone de material alquilable para su practica'),
    ('Pista 2 Futbol 11', 'Futbol 11',2,'Pista para la practica de futbol base, construida en 2009 con motivo del primer aniversario de la creacion del club. Dispone de material alquilable para su practica'),
    ('Pista 1 Futbol 7', 'Futbol 7',1, 'Pista para la practica de futbol 11, construida en 2009 con motivo del primer aniversario de la creacion del club. Dispone de material alquilable para su practica'),
    ('Pista 2 Futbol 7', 'Futbol 7',2, 'Pista para la practica de futbol 11, construida en 2009 con motivo del primer aniversario de la creacion del club. Dispone de material alquilable para su practica');

INSERT INTO clase (nombre, profesor, tipo, descripcion, max_asistentes, precio)
VALUES
    ('Yoga Basico', 'Maria Lopez', 'Yoga', 'Clase de yoga para principiantes', 15, 20.00),
    ('Pilates Avanzado', 'Carlos Garcia', 'Pilates', 'Sesion avanzada de pilates para mejorar la flexibilidad y fuerza', 10, 25.00),
    ('Zumba Fitness', 'Laura Martinez', 'Zumba', 'Clase de zumba para quemar calorias bailando', 20, 15.00),
    ('HIIT Intensivo', 'Javier Perez', 'HIIT', 'Entrenamiento por intervalos de alta intensidad', 12, 30.00),
    ('HIIT Chill', 'Paqui hiit', 'HIIT', 'Entrenamiento por intervalos de alta intensidad', 12, 30.00),
    ('Meditacion Guiada', 'Ana Torres', 'Meditacion', 'Sesion guiada para reducir el estres y mejorar la concentracion', 8, 10.00);

INSERT INTO horario_clase (clase_id, fecha, hora_inicio, hora_fin)
VALUES
    (1, '2024-11-25', '08:00:00', '09:00:00'), -- Yoga Basico
    (1, '2024-11-29', '08:00:00', '09:00:00'), -- Yoga Basico
    (1, '2024-11-30', '08:00:00', '09:00:00'), -- Yoga Basico
    (2, '2024-11-30', '10:00:00', '11:30:00'), -- Pilates Avanzado
    (2, '2024-11-29', '10:00:00', '11:30:00'), -- Pilates Avanzado
    (3, '2024-11-30', '18:00:00', '19:00:00'), -- Zumba Fitness
    (3, '2024-11-29', '18:00:00', '19:00:00'), -- Zumba Fitness
    (4, '2024-11-30', '07:00:00', '07:45:00'), -- HIIT Intensivo
    (4, '2024-11-29', '07:00:00', '07:45:00'), -- HIIT Intensivo
    (5, '2024-11-30', '19:30:00', '20:30:00'), -- Meditacion Guiada
    (5, '2024-11-29', '19:30:00', '20:30:00'); -- Meditacion Guiada


INSERT INTO reserva_clase (usuario_id, horario_id, fecha_reserva)
VALUES (
           (SELECT id FROM usuario WHERE nombre = 'Victor' AND apellidos = 'Martinez Paramo'),
           (SELECT hc.id
            FROM horario_clase hc
                     JOIN clase c ON hc.clase_id = c.id
            WHERE c.nombre = 'Yoga Basico' AND hc.fecha = '2024-11-25'),
           '2024-11-25'
       );