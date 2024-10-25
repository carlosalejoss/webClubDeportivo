// 1. Buscar si existe un usuario con ese DNI. => Error
// 2. Añadir usuario
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

class CrearUsuarioError extends ErrorString {
    cause = "Se ha producido un error en la base de datos al crear el usuario. Contacte con soporte."
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

            let user = await prisma.credencial.findUnique({
                where: {
                    dni: registerProvided.credentials.dni
                },
                select: {
                    userId: false,
                    dni: true,
                    password: false
                }
            })

            if (user) {
                throw new DNIExistenteError();
            }

            // Creamos usuario
            const passwordHash = await argon.hash(registerProvided.credentials.password)

            let newUser = await prisma.usuario.create({
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

            if (!newUser) {
                throw new CrearUsuarioError()
            }

            const session = cookies.get("SESSION");

            if (!session) {
                return redirect(302, '/login');
            }

            // User created.
            await prisma.session.upsert({
                where: {
                    sessionId: session,
                },
                create: {
                    sessionId: session,
                    usuarioId: newUser.id,
                    onboard: false
                },
                update: {
                    usuarioId: newUser.id,
                    onboard: false
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