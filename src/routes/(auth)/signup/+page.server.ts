// 1. Buscar si existe un socio con ese DNI. => Error
// 2. Añadir socio
// 3. onboarding?
// Devolver error o ok.

import { authSchema, registerSchema } from "$lib/auth/zod";
import { prisma } from "$lib/db.server";
import { fail, redirect } from "@sveltejs/kit";
import argon from 'argon2';
import { ZodError } from "zod";

class ErrorString extends Error {
    cause?: string;
}

class DNIExistenteError extends ErrorString {
    cause = "El DNI ya esta registrado."
}

class CrearSocioError extends ErrorString {
    cause = "Se ha producido un error en la base de datos al crear el socio. Contacte con soporte."
}

export const load = async ({ locals }) => {
    return locals
}

export const actions = {
    default: async ({ request, cookies }) => {
        try {
            const formData = await request.formData()

            const registerProvided = await registerSchema.parseAsync({
                nombre: formData.get("nombre"),
                credentials: await authSchema.parseAsync({
                    dni: formData.get("dni"),
                    password: formData.get("password"),
                })
            })

            let socio = await prisma.credencial.findUnique({
                where: {
                    dni: registerProvided.credentials.dni
                },
                select: {
                    socioId: false,
                    dni: true,
                    password: false
                }
            })

            if (socio) {
                throw new DNIExistenteError();
            }

            // Creamos socio
            const passwordHash = await argon.hash(registerProvided.credentials.password)

            let newsocio = await prisma.socio.create({
                data: {
                    nombre: registerProvided.nombre,
                    dni: registerProvided.credentials.dni,
                    credencial: {
                        create: {
                            dni: registerProvided.credentials.dni,
                            password: passwordHash,
                        }
                    }
                }
            })

            if (!newsocio) {
                throw new CrearSocioError()
            }

            const session = cookies.get("SESSION");

            if (!session) {
                return redirect(302, '/login');
            }

            // socio created.
            await prisma.session.upsert({
                where: {
                    sessionId: session,
                },
                create: {
                    sessionId: session,
                    socioId: newsocio.id,
                },
                update: {
                    socioId: newsocio.id,
                }
            })

            return {
                error: null
            }

        } catch (error) {
            console.error(error)
            if (error instanceof ZodError) {
                return {
                    error: error.issues[0].message
                }
            }
            else if (error instanceof ErrorString) {
                return {
                    error: error.cause
                }
            }
            else {
                return fail(400, {
                    error: "undefined error, please contact support."
                })
            }
        }
    }
}