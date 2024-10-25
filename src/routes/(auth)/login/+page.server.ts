// 1. Buscar si existe un usuario con ese DNI.
// 2. Comparar contraseñas
// Devolver error o ok.

import { authSchema } from "$lib/auth/zod";
import { prisma } from "$lib/db.server";
import { fail, redirect } from "@sveltejs/kit";
import argon from 'argon2';
import { ZodError } from "zod";

class ErrorString extends Error {
    cause?: string;
}

class InvalidCredentialsError extends ErrorString {
    cause = "Credenciales invalidas."
}

class InvalidSessionError extends ErrorString {
    cause = "La sesión es erronea. Por favor, actualize la pagina."
}

export const load = async ({ locals }) => {
    return locals
}

export const actions = {
    default: async ({ request, cookies }) => {
        try {
            const formData = await request.formData()

            const credentialsProvided = await authSchema.parseAsync({
                dni: formData.get("dni"),
                password: formData.get("password"),
            })

            const user = await prisma.credencial.findUnique({
                where: {
                    dni: credentialsProvided.dni
                },
                select: {
                    userId: true,
                    dni: true,
                    password: true
                }
            })

            if (!user) {
                throw new InvalidCredentialsError();
            }

            const samePassword = await argon.verify(user.password, credentialsProvided.password);

            if (!samePassword) {
                throw new InvalidCredentialsError();
            }

            const session = cookies.get("SESSION");

            if (!session) {
                throw new InvalidSessionError();
            }

            // User authenticated.
            // TODO: check if this fails. It should not fail under normal circunstances

            const dateToExpire = new Date(Date.now() + /* 1 hour */ 1 * 60 * 60 * 1000)
            await prisma.session.upsert({
                where: {
                    sessionId: session,
                },
                create: {
                    sessionId: session,
                    usuarioId: user.userId,
                    onboard: false, // FIXME (TODO)
                    expiresAt: dateToExpire,
                },
                update: {
                    usuarioId: user.userId,
                    onboard: false // FIXME (TODO)
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