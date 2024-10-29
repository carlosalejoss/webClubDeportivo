import { prisma } from "$lib/db.server";
import { redirect, type Handle } from "@sveltejs/kit";
import { v4 as generateUUID } from "uuid";

export const handle: Handle = async ({ event, resolve }) => {
    const session = event.cookies.get("SESSION")

    await async function () {
        if (session === undefined) {
            // Crear una nueva sesión.
            event.cookies.set("SESSION", generateUUID(), {
                httpOnly: true,
                path: '/'
            })
            return
        }

        // Comprobar si la sesión esta autenticada.
        const sessionDB = await prisma.session.findUnique({
            where: {
                sessionId: session
            }
        })

        if (!sessionDB) {
            return
        }

        // Check if the session is expired
        const isSessionExpired = sessionDB.expiresAt.getTime() <= Date.now()
        if (isSessionExpired) {
            event.cookies.set("SESSION", generateUUID(), {
                httpOnly: true,
                path: '/'
            })
            // TODO: mensaje con tu sesión ha caducado
            return redirect(302, '/login')
        }

        event.locals.user = {
            id: sessionDB.socioId,
        }
    }();

    return resolve(event)
}