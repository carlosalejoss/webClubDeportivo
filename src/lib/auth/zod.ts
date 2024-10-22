import { object, string } from 'zod';

const TOO_SMALL_PASSWORD = "La contraseña debe contener al menos 8 caracteres."
const TOO_BIG_PASSWORD = "La contraseña no puede exceder los 128 caracteres."
const INVALID_PHONE_NUMBER = "El numero de telefono introducido no es valido."
const INVALID_DNI = "El DNI introducido no es valido."

const DNIRegex = new RegExp(/[0-9]+[A-z]/)

export const authSchema = object({
    dni: string().regex(DNIRegex, INVALID_DNI),
    password: string()
        .min(8, TOO_SMALL_PASSWORD)
        .max(128, TOO_BIG_PASSWORD)
}).required()


export const userSchema = object({
    nombre: string(),
    email: string().email(),
    telefono: string()
        .regex(/\+?[0-9 -()]+/, INVALID_PHONE_NUMBER)
        .max(32, INVALID_PHONE_NUMBER)
})

export const socioSchema = object({
    codigoPostal: string(),
    // TODO: add iban validation
    iban: string()
})