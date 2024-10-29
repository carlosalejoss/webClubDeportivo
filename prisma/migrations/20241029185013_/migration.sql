-- CreateEnum
CREATE TYPE "Rol" AS ENUM ('Administrador', 'Socio');

-- CreateTable
CREATE TABLE "Reserva" (
    "id" UUID NOT NULL DEFAULT gen_random_uuid(),
    "socioId" UUID NOT NULL,
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
CREATE TABLE "Session" (
    "sessionId" UUID NOT NULL,
    "socioId" UUID NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "expiresAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "Session_pkey" PRIMARY KEY ("sessionId")
);

-- CreateTable
CREATE TABLE "Socio" (
    "id" UUID NOT NULL DEFAULT gen_random_uuid(),
    "nombre" TEXT NOT NULL,
    "dni" TEXT NOT NULL,
    "email" TEXT,
    "telefono" TEXT,
    "rol" "Rol" NOT NULL DEFAULT 'Socio',

    CONSTRAINT "Socio_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "Credencial" (
    "socioId" UUID NOT NULL,
    "dni" TEXT NOT NULL,
    "password" VARCHAR(1023) NOT NULL,

    CONSTRAINT "Credencial_pkey" PRIMARY KEY ("socioId")
);

-- CreateIndex
CREATE UNIQUE INDEX "Socio_id_dni_key" ON "Socio"("id", "dni");

-- CreateIndex
CREATE UNIQUE INDEX "Credencial_dni_key" ON "Credencial"("dni");

-- AddForeignKey
ALTER TABLE "Reserva" ADD CONSTRAINT "Reserva_socioId_fkey" FOREIGN KEY ("socioId") REFERENCES "Socio"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Reserva" ADD CONSTRAINT "Reserva_nombrePista_fkey" FOREIGN KEY ("nombrePista") REFERENCES "Pista"("nombre") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Session" ADD CONSTRAINT "Session_socioId_fkey" FOREIGN KEY ("socioId") REFERENCES "Socio"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Credencial" ADD CONSTRAINT "Credencial_socioId_fkey" FOREIGN KEY ("socioId") REFERENCES "Socio"("id") ON DELETE RESTRICT ON UPDATE CASCADE;
