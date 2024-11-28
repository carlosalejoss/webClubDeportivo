package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.example.si_gestor_club_deportivo.repository.MailRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

/**
 * Servicio para el envio de correos electronicos relacionados con la gestion de usuarios.
 * En particular, se encarga de la recuperacion de contraseñas mediante el envio de un correo con un enlace de restablecimiento.
 */
@Service("MailService")
public class MailService {

    @Autowired
    private MailRepository mailRepository;

    private final JavaMailSender mailSender;

    /**
     * Constructor para inicializar el servicio de correo.
     *
     * @param mailSender El servicio de envio de correos.
     */
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Envia un correo electronico para la recuperacion de contraseña a un usuario.
     *
     * Este metodo genera un token unico, lo guarda en la base de datos asociado al usuario, y luego envia un
     * correo con un enlace para restablecer la contraseña.
     *
     * @param email El correo electronico del usuario al que se enviara el enlace de recuperacion.
     * @return Un mensaje que indica si el correo se ha enviado correctamente o si ha habido algun error.
     */
    public String sendPasswordRecoveryEmail(String email) {

        // Verificar si el usuario existe en la base de datos
        Optional<Usuario> usuarioOptional = mailRepository.findByEmail(email);
        if (usuarioOptional.isEmpty()) {
            return "No se encontro un usuario con el correo proporcionado.";
        }
        Usuario usuario = usuarioOptional.get();
        String token = UUID.randomUUID().toString(); // Generar token unico
        usuario.setPasswordResetToken(token);
        mailRepository.save(usuario); // Guardar el token en la base de datos

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Recuperacion de contraseña");
            message.setText("Hola, este es tu enlace para restablecer la contraseña: " +
                    "http://http://localhost:8080/restablecer?token=" + token);
            mailSender.send(message);
            return "Correo enviado correctamente a: " + email;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}