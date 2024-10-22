CREATE database clubesDeportivosDB;


CREATE TABLE public."Admin" (
	nombre varchar NULL,
	apellidos varchar NULL,
	contra varchar NOT NULL,
	telefono int4 NULL,
	dni varchar NOT NULL,
	correo varchar NULL,
	iden varchar NOT NULL,
	CONSTRAINT admin_pk PRIMARY KEY (iden),
	CONSTRAINT admin_user_fk FOREIGN KEY (iden) REFERENCES public."user"(iden)
);

CREATE TABLE public.clasepista (
	profesor varchar NULL,
	nombre varchar NULL,
	useriden varchar NOT NULL,
	horafecha timestamp NOT NULL,
	pistaiden varchar NOT NULL,
	CONSTRAINT clasepista_pk PRIMARY KEY (useriden, horafecha, pistaiden, profesor),
	CONSTRAINT clasepista_reserva_fk FOREIGN KEY (useriden,horafecha,pistaiden) REFERENCES public.reserva(useriden,horafecha,pistaiden)
);

CREATE TABLE public.clasesinpista (
	profesor varchar NULL,
	nombre varchar NULL,
	sala varchar NOT NULL,
	fechahora timestamp NOT NULL,
	CONSTRAINT clasesinpista_pk PRIMARY KEY (sala, fechahora, profesor)
);

CREATE TABLE public.pista (
	nombre varchar NOT NULL,
	tipo varchar NULL,
	CONSTRAINT pista_pk PRIMARY KEY (nombre)
);

CREATE TABLE public.reserva (
	useriden varchar NOT NULL,
	horafecha timestamp NOT NULL,
	nombrePista varchar NOT NULL,
	precio float8 NOT NULL,
	CONSTRAINT reserva_pk PRIMARY KEY (useriden, horafecha, nombrePista),
	CONSTRAINT reserva_pista_fk FOREIGN KEY (nombrePista) REFERENCES public.pista(nombre),
	CONSTRAINT reserva_user_fk FOREIGN KEY (useriden) REFERENCES public."user"(iden)
);

CREATE TABLE public.socio (
	nombre varchar NULL,
	apellidos varchar NULL,
	contra varchar NOT NULL,
	telefono numeric NULL,
	dni varchar NOT NULL,
	correo varchar NULL,
	iden varchar NOT NULL,
	cp int NOT NULL,
	iban varchar NOT NULL,
	CONSTRAINT socio_pk PRIMARY KEY (iden),
	CONSTRAINT socio_user_fk FOREIGN KEY (iden) REFERENCES public."user"(iden)
);

CREATE TABLE public."user" (
	iden numeric NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (iden)
);





