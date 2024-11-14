package org.example.si_gestor_club_deportivo.controller;

import jakarta.servlet.http.HttpSession;
import org.example.si_gestor_club_deportivo.model.Pista;
import org.example.si_gestor_club_deportivo.model.Reserva;
import org.example.si_gestor_club_deportivo.model.Usuario;
import org.example.si_gestor_club_deportivo.service.PistaService;
import org.example.si_gestor_club_deportivo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.example.si_gestor_club_deportivo.service.ReservaService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final UsuarioService usuarioService;
    private final PistaService pistaService;
    private final ReservaService reservaService;

    @Autowired
    public HomeController(UsuarioService usuarioService, PistaService pistaService, ReservaService reservaService) {
        this.usuarioService = usuarioService;
        this.pistaService = pistaService;
        this.reservaService = reservaService;
    }

    @GetMapping("/")
    public String home(HttpSession session) {
        return "home";
    }

    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        return "home";
    }

    @GetMapping("/iniciarSesion")
    public String iniciarsesion(HttpSession session) {
        return "iniciarSesion";
    }

    @GetMapping("/registrarse")
    public String registrarse(HttpSession session) {
        return "registrarse";
    }

    @GetMapping("/eleccionCampoFut11")
    public String eleccionCamopFut11(HttpSession session) {
        return "reservar";
    }

    @GetMapping("/eleccionCampoFut7")
    public String eleccionCampoFut7(HttpSession session) {
        return "reservar";
    }

    @GetMapping("/eleccionCampoBasket")
    public String eleccionCampoBasket(HttpSession session) {
        return "reservar";
    }

    @GetMapping("/eleccionCampoTenis")
    public String eleccionCampoTenis(HttpSession session) {
        return "reservar";
    }

    @GetMapping("/eleccionCampoPadel")
    public String eleccionCampoPadel(HttpSession session) {
        return "reservar";
    }

    @GetMapping("/sobreNosotros")
    public String sobreNosotros(HttpSession session) {
        return "sobreNosotros";
    }

    @GetMapping("/eleccion")
    public String mostrarPistasPorTipo(@RequestParam("tipo") String tipo, Model model) {
        List<Pista> pistas = pistaService.obtenerPistasPorTipo(tipo);
        model.addAttribute("pistas", pistas);
        model.addAttribute("tipo", tipo); // Pasamos el tipo para usar en el título dinámico
        return "eleccionCampo";
    }

    @PostMapping("/login")
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
                System.out.println("Usuario no encontrado para el email: " + email);
                return "redirect:/";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/toggleView")
    public String toggleView(HttpSession session) {
        if (Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            Boolean viewAsAdmin = (Boolean) session.getAttribute("viewAsAdmin");
            session.setAttribute("viewAsAdmin", !viewAsAdmin);
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/registrarse")
    public String registrarse(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String nombre,
            @RequestParam String apellidos,
            @RequestParam String telefono,
            @RequestParam String dni,
            HttpSession session,
            Model model) {

        // Verificar si el usuario ya existe
        if (usuarioService.findByEmail(email) != null) {
            model.addAttribute("error", "El email ya está registrado.");
            return "registrarse"; // Redirige de nuevo a la página de registro en caso de error
        }
        if (usuarioService.findByDni(dni) != null) {
            model.addAttribute("error", "El dni ya está registrado.");
            return "registrarse";
        }
        if (usuarioService.findByTelefono(telefono) != null) {
            model.addAttribute("error", "El telefono ya está registrado.");
            return "registrarse";
        }

        // Crear el nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(password); // Asegúrate de encriptar la contraseña antes de guardarla
        nuevoUsuario.setNombre(nombre != null ? nombre : ""); // Valores opcionales o vacíos
        nuevoUsuario.setApellidos(apellidos != null ? apellidos : "");
        nuevoUsuario.setTelefono(telefono != null ? telefono : "");
        nuevoUsuario.setDni(dni != null ? dni : "");
        nuevoUsuario.setEsAdmin(false); // Por defecto, no es administrador

        // Guardar el usuario en la base de datos
        usuarioService.registrarUsuario(nuevoUsuario);

        // Iniciar sesión para el usuario recién registrado
        session.setAttribute("usuario", nuevoUsuario);

        // Establece los atributos de sesión en función del rol del usuario
        session.setAttribute("loggedUser", true);

        return "redirect:/"; // Redirige a la página de inicio o a la página de bienvenida
    }

    @GetMapping("/reservar")
    public String mostrarReservasSemanaActual(Model model, HttpSession session) {
        // Verifica si el usuario está autenticado en la sesión
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/iniciarSesion";
        }

        // Obtener las fechas de inicio y fin de la semana actual
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.minusDays(hoy.getDayOfWeek().getValue() - 1);
        LocalDate finSemana = inicioSemana.plusDays(6);

        // Obtener las reservas de la semana actual
        List<Reserva> reservasSemana = reservaService.obtenerReservasEntreFechas(inicioSemana, finSemana);

        // Agregar atributos al modelo para la vista
        model.addAttribute("reservasSemana", reservasSemana);
        model.addAttribute("inicioSemana", inicioSemana);
        model.addAttribute("finSemana", finSemana);

        return "reservar"; // Renderiza la vista `reservar.html`
    }
}