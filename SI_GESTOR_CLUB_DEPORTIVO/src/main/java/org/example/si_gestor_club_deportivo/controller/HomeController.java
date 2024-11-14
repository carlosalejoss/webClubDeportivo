package org.example.si_gestor_club_deportivo.controller;

import jakarta.servlet.http.HttpSession;
import org.example.si_gestor_club_deportivo.model.Pista;
import org.example.si_gestor_club_deportivo.model.Reserva;
import org.example.si_gestor_club_deportivo.model.Usuario;
import org.example.si_gestor_club_deportivo.service.PistaService;
import org.example.si_gestor_club_deportivo.service.UsuarioService;
import org.example.si_gestor_club_deportivo.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/iniciarSesion")
    public String iniciarsesion(HttpSession session) {
        return "iniciarSesion";
    }

    @GetMapping("/registrarse")
    public String registrarse(HttpSession session) {
        return "registrarse";
    }

    @GetMapping("/sobreNosotros")
    public String sobreNosotros(HttpSession session) {
        return "sobreNosotros";
    }

    @GetMapping("/misReservas")
    public String misReservas(HttpSession session) {
        return "misReservas";
    }

    @GetMapping("/eleccionCampoFut11")
    public String eleccionCamopFut11(HttpSession session) {
        return "eleccionCampo";
    }

    @GetMapping("/eleccionCampoFut7")
    public String eleccionCampoFut7(HttpSession session) {
        return "eleccionCampo";
    }

    @GetMapping("/eleccionCampoBasket")
    public String eleccionCampoBasket(HttpSession session) {
        return "eleccionCampo";
    }

    @GetMapping("/eleccionCampoTenis")
    public String eleccionCampoTenis(HttpSession session) {
        return "eleccionCampo";
    }

    @GetMapping("/eleccionCampoPadel")
    public String eleccionCampoPadel(HttpSession session) {
        return "eleccionCampo";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        Usuario usuario = usuarioService.findByEmail(email);

        if (usuario == null) {
            model.addAttribute("error", "El correo ingresado no está registrado.");
            return "iniciarSesion";
        } else {
            if (password.equals(usuario.getPassword())) {
                // Establece los atributos de sesión en función del rol del usuario
                session.setAttribute("usuario", usuario);
                session.setAttribute("loggedUser", true);
                session.setAttribute("isAdmin", usuario.isEsAdmin());

                if (usuario.isEsAdmin()) {
                    session.setAttribute("viewAsAdmin", true);
                }
            } else {
                model.addAttribute("error", "La contraseña ingresada es incorrecta.");
                return "iniciarSesion";
            }
        }

        return "redirect:/";
    }

    @PostMapping("/signUp")
    public String signUp(@RequestParam String email,
                              @RequestParam String password,
                              @RequestParam String nombre,
                              @RequestParam String apellidos,
                              @RequestParam String telefono,
                              @RequestParam String dni,
                              HttpSession session,
                              Model model) {

        if (email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                nombre == null || nombre.trim().isEmpty() ||
                apellidos == null || apellidos.trim().isEmpty() ||
                telefono == null || telefono.trim().isEmpty() ||
                dni == null || dni.trim().isEmpty()) {

            model.addAttribute("error", "Todos los campos son obligatorios.");
            return "registrarse"; // Redirige de nuevo a la página de registro en caso de error
        }

        if (usuarioService.findByEmail(email) != null) {
            model.addAttribute("error", "El email ya está registrado.");
            return "registrarse"; // Redirige de nuevo a la página de registro en caso de error
        }

        if (usuarioService.findByDni(dni) != null) {
            model.addAttribute("error", "El dni ya está registrado.");
            return "registrarse"; // Redirige de nuevo a la página de registro en caso de error
        }

        if (usuarioService.findByTelefono(telefono) != null) {
            model.addAttribute("error", "El telefono ya está registrado.");
            return "registrarse"; // Redirige de nuevo a la página de registro en caso de error
        }

        // Crear el nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(password); // Asegúrate de encriptar la contraseña antes de guardarla
        nuevoUsuario.setNombre(nombre); // Valores opcionales o vacíos
        nuevoUsuario.setApellidos(apellidos);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setDni(dni);
        nuevoUsuario.setEsAdmin(false); // Por defecto, no es administrador

        // Guardar el usuario en la base de datos
        usuarioService.registrarUsuario(nuevoUsuario);

        // Iniciar sesión para el usuario recién registrado
        session.setAttribute("usuario", nuevoUsuario);

        // Establece los atributos de sesión en función del rol del usuario
        session.setAttribute("loggedUser", true);
        session.setAttribute("isAdmin", nuevoUsuario.isEsAdmin());

        return "redirect:/"; // Redirige a la página de inicio o a la página de bienvenida
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

    @GetMapping("/datosCuenta")
    public String datosCuenta(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("nombre", usuario.getNombre());
        model.addAttribute("apellidos", usuario.getApellidos());
        model.addAttribute("dni", usuario.getDni());
        model.addAttribute("email", usuario.getEmail());
        model.addAttribute("telefono", usuario.getTelefono());

        return "datosCuenta";  //Lo mandamos al html
    }

    @PostMapping("/actualizarDatos")
    public String actualizarDatos(
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "apellidos", required = false) String apellidos,
            @RequestParam(value = "dni", required = false) String dni,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "telefono", required = false) String telefono,
            HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (!nombre.isEmpty()) {
            usuario.setNombre(nombre);
        } else {
            usuario.setNombre((String) session.getAttribute("nombre"));
        }
        if (!apellidos.isEmpty()) {
            usuario.setApellidos(apellidos);
        } else {
            usuario.setApellidos((String) session.getAttribute("apellidos"));
        }
        if (!dni.isEmpty()) {
            usuario.setDni(dni);
        } else {
            usuario.setDni((String) session.getAttribute("dni"));
        }
        if (!email.isEmpty()) {
            usuario.setEmail(email);
        } else {
            usuario.setEmail((String) session.getAttribute("email"));
        }
        if (!telefono.isEmpty()) {
            usuario.setTelefono(telefono);
        } else {
            usuario.setTelefono((String) session.getAttribute("telefono"));
        }

        usuarioService.guardarUsuario(usuario);

        return "redirect:/";
    }

    @GetMapping("/eleccion")
    public String mostrarPistasPorTipo(@RequestParam("tipo") String tipo, Model model) {
        List<Pista> pistas = pistaService.obtenerPistasPorTipo(tipo);
        model.addAttribute("pistas", pistas);
        model.addAttribute("tipo", tipo); // Pasamos el tipo para usar en el título dinámico
        return "eleccionCampo";
    }

    @GetMapping("/reservar")
    public String mostrarReservasSemanaActual(Model model, HttpSession session) {
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/iniciarSesion";
        }

        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.with(ChronoField.DAY_OF_WEEK, 1);
        List<LocalDate> diasSemana = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            diasSemana.add(inicioSemana.plusDays(i));
        }

        List<String> horas = List.of(
                "08:00 - 09:30", "09:30 - 11:00", "11:00 - 12:30", "12:30 - 14:00",
                "14:00 - 15:30", "15:30 - 17:00", "17:00 - 18:30", "18:30 - 20:00", "20:00 - 21:30"
        );

        List<Reserva> reservasSemana = reservaService.obtenerReservasEntreFechas(inicioSemana, inicioSemana.plusDays(6));
        Map<LocalDate, List<String>> reservasMap = new HashMap<>();

        for (Reserva reserva : reservasSemana) {
            reservasMap.computeIfAbsent(reserva.getFecha(), k -> new ArrayList<>()).add(reserva.getHora());
        }

        model.addAttribute("diasSemana", diasSemana);
        model.addAttribute("horas", horas);
        model.addAttribute("reservasMap", reservasMap);

        return "reservar";
    }



}