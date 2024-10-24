// 1. Buscar si existe un usuario con ese DNI.
// 2. Comparar contraseñas
// Devolver error o ok.

import { authSchema } from "$lib/auth/zod";
import { prisma } from "$lib/db.server";
import { fail } from "@sveltejs/kit";
import argon from 'argon2';
import { ZodError } from "zod";

class ErrorString extends Error {
    cause?: string;
}

class InvalidCredentialsError extends ErrorString {
    cause = "Credenciales invalidas."
}

export const actions = {
    default: async ({ request, locals }) => {
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

            // User authenticated.
            locals.user.id = user.userId

            return {
                error: null
            }

        } catch (error) {
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