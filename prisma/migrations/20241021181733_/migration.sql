-- CreateEnum
CREATE TYPE "EstadoClase" AS ENUM ('Disponible', 'EnProceso', 'Finalizada', 'Cancelada');

-- CreateEnum
CREATE TYPE "Rol" AS ENUM ('Administrador', 'Socio', 'Usuario');

-- CreateTable
CREATE TABLE "Clase" (
    "id" TEXT NOT NULL,
    "profesor" TEXT NOT NULL,
    "nombre" TEXT NOT NULL,
    "descripcion" TEXT,
    "pistaNombre" TEXT,
    "creadaPorUserId" UUID NOT NULL,
    "fecha" TIMESTAMP(3) NOT NULL,
    "estado" "EstadoClase" NOT NULL DEFAULT 'Disponible',

    CONSTRAINT "Clase_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "Reserva" (
    "id" UUID NOT NULL DEFAULT gen_random_uuid(),
    "precio" DOUBLE PRECISION,
    "fecha" TIMESTAMP(3) NOT NULL,
    "nombrePista" TEXT NOT NULL,

    CONSTRAINT "Reserva_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "Pista" (
    "nombre" TEXT NOT NULL,

    CONSTRAINT "Pista_pkey" PRIMARY KEY ("nombre")
);

-- CreateTable
CREATE TABLE "SocioDatos" (
    "userId" UUID NOT NULL,
    "numeroSocio" INTEGER NOT NULL,
    "codigoPostal" TEXT,
    "iban" TEXT,

    CONSTRAINT "SocioDatos_pkey" PRIMARY KEY ("userId")
);

-- CreateTable
CREATE TABLE "Usuario" (
    "id" UUID NOT NULL DEFAULT gen_random_uuid(),
    "nombre" TEXT NOT NULL,
    "dni" TEXT NOT NULL,
    "email" TEXT,
    "telefono" TEXT,
    "rol" "Rol" NOT NULL DEFAULT 'Usuario',

    CONSTRAINT "Usuario_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "Credencial" (
    "userId" UUID NOT NULL,
    "dni" TEXT NOT NULL,
    "password" VARCHAR(1023) NOT NULL,

    CONSTRAINT "Credencial_pkey" PRIMARY KEY ("userId")
);

-- CreateTable
CREATE TABLE "_alumnos" (
    "A" TEXT NOT NULL,
    "B" UUID NOT NULL
);

-- CreateIndex
CREATE UNIQUE INDEX "SocioDatos_numeroSocio_key" ON "SocioDatos"("numeroSocio");

-- CreateIndex
CREATE UNIQUE INDEX "Usuario_id_dni_key" ON "Usuario"("id", "dni");

-- CreateIndex
CREATE UNIQUE INDEX "_alumnos_AB_unique" ON "_alumnos"("A", "B");

-- CreateIndex
CREATE INDEX "_alumnos_B_index" ON "_alumnos"("B");

-- AddForeignKey
ALTER TABLE "Clase" ADD CONSTRAINT "Clase_creadaPorUserId_fkey" FOREIGN KEY ("creadaPorUserId") REFERENCES "Usuario"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Clase" ADD CONSTRAINT "Clase_pistaNombre_fkey" FOREIGN KEY ("pistaNombre") REFERENCES "Pista"("nombre") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Reserva" ADD CONSTRAINT "Reserva_nombrePista_fkey" FOREIGN KEY ("nombrePista") REFERENCES "Pista"("nombre") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "SocioDatos" ADD CONSTRAINT "SocioDatos_userId_fkey" FOREIGN KEY ("userId") REFERENCES "Usuario"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Credencial" ADD CONSTRAINT "Credencial_userId_fkey" FOREIGN KEY ("userId") REFERENCES "Usuario"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "_alumnos" ADD CONSTRAINT "_alumnos_A_fkey" FOREIGN KEY ("A") REFERENCES "Clase"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "_alumnos" ADD CONSTRAINT "_alumnos_B_fkey" FOREIGN KEY ("B") REFERENCES "Usuario"("id") ON DELETE CASCADE ON UPDATE CASCADE;
