package org.example.si_gestor_club_deportivo.controller;

import jakarta.servlet.http.HttpSession;
import org.example.si_gestor_club_deportivo.model.Usuario;
import org.example.si_gestor_club_deportivo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class MainController {

    private final UsuarioService usuarioService;

    public MainController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String home() {
        return "home"; // Redirige a la plantilla de inicio
    }

    @GetMapping("/iniciarSesion")
    public String login(@RequestParam String email, HttpSession session) {
        try {
            Usuario usuario = usuarioService.findByEmail(email);

            if (usuario != null) {
                // Establece los atributos de sesión en función del rol del usuario
                session.setAttribute("loggedUser", true);
                session.setAttribute("isAdmin", usuario.isEsAdmin());

                if (usuario.isEsAdmin()) {
                    session.setAttribute("viewAsAdmin", true);
                }
            } else {
                // Usuario no encontrado, puedes redirigir a una página de error o mostrar un mensaje
                return "redirect:/";
            }
        } catch (Exception e) {
            // Loguea la excepción para diagnóstico
            e.printStackTrace();
            // Redirige a una página de error general
            return "redirect:/";
        }
        return "redirect:/"; // Redirige a la página de inicio después de iniciar sesión
    }


    // Método para alternar entre la vista de usuario y administrador
    @GetMapping("/toggleView")
    public String toggleView(HttpSession session) {
        // Solo permitir el cambio de vista si el usuario es administrador
        if (Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            Boolean viewAsAdmin = (Boolean) session.getAttribute("viewAsAdmin");
            session.setAttribute("viewAsAdmin", !viewAsAdmin); // Alterna el valor de `viewAsAdmin`
        }
        return "redirect:/"; // Redirige a la página principal para reflejar el cambio
    }

    // Método para cerrar sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalida la sesión para eliminar todos los atributos de sesión
        session.invalidate();
        return "redirect:/"; // Redirige a la página de inicio después de cerrar sesión
    }
}

