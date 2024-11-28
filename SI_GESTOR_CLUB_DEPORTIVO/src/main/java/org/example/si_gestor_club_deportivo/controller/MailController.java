package org.example.si_gestor_club_deportivo.controller;

import org.example.si_gestor_club_deportivo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.List;

/**
 * Controlador para gestionar las operaciones de correo electrónico relacionadas con la recuperación de contraseñas.
 * Proporciona endpoints para enviar correos de recuperación de contraseña.
 */
public class MailController {

    private final MailService mailService;

    /**
     * Constructor para inicializar el servicio de correo electrónico.
     *
     * @param mailService Servicio para el envío de correos electrónicos.
     */
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * Endpoint para enviar un correo de recuperación de contraseña a un usuario.
     *
     * @param email Email del usuario que solicita la recuperación de contraseña.
     * @return Respuesta con el estado y el mensaje de éxito o error.
     */
    @PostMapping("/recuperar")
    public ResponseEntity<String> enviarCorreoRecuperacion(@RequestBody String email) {
        String response = mailService.sendPasswordRecoveryEmail(email);
        if (response.contains("Error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}