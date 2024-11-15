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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
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

    @GetMapping("/datosCuenta")
    public String datosCuenta(HttpSession session) {
        return "datosCuenta";
    }

    @GetMapping("/sobreNosotros")
    public String sobreNosotros(HttpSession session) {
        return "sobreNosotros";
    }

    @GetMapping("/misReservas")
    public String misReservas(HttpSession session) {
        return "misReservas";
    }

    @GetMapping("/eleccionCampo")
    public String eleccionCampo(@RequestParam("tipo") String tipo, Model model) {
        // Mapear tipo a su imagen correspondiente
        String imagenCampo = switch (tipo) {
            case "Futbol 11" -> "/campof11.png";
            case "Futbol 7" -> "/campoFut7.png";
            case "Baloncesto" -> "/pistBaloncesto.png";
            case "Tenis" -> "/tenis.png";
            case "Padel" -> "/padel.png";
            default -> "/campo_default.png"; // Imagen por defecto
        };

        // Obtener pistas según el tipo
        List<Pista> pistas = pistaService.obtenerPistasPorTipo(tipo);

        // Pasar datos al modelo
        model.addAttribute("pistas", pistas);
        model.addAttribute("tipo", tipo);
        model.addAttribute("imagenCampo", imagenCampo);

        return "eleccionCampo";
    }


    @GetMapping("/eleccionCampoFut11")
    public String eleccionCampoFut11(Model model) {
        return "redirect:/eleccionCampo?tipo=Futbol 11";
    }


    @GetMapping("/eleccionCampoFut7")
    public String eleccionCampoFut7(HttpSession session) {
        return "redirect:/eleccionCampo?tipo=Futbol 7";
    }

    @GetMapping("/eleccionCampoBasket")
    public String eleccionCampoBasket(HttpSession session) {
        return "redirect:/eleccionCampo?tipo=Baloncesto";
    }

    @GetMapping("/eleccionCampoTenis")
    public String eleccionCampoTenis(HttpSession session) {
        return "redirect:/eleccionCampo?tipo=Tenis";
    }

    @GetMapping("/eleccionCampoPadel")
    public String eleccionCampoPadel(HttpSession session) {
        return "redirect:/eleccionCampo?tipo=Padel";
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

    @GetMapping("/reservar")
    public String mostrarReservasSemanaActual(@RequestParam String campo, Model model, HttpSession session) {
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/iniciarSesion";
        }

        // Obtener la semana actual
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.with(ChronoField.DAY_OF_WEEK, 1);
        List<Map<String, Object>> diasConHoras = new ArrayList<>();

        // Lista de horas
        List<String> horas = List.of(
                "08:00 - 09:30", "09:30 - 11:00", "11:00 - 12:30", "12:30 - 14:00",
                "14:00 - 15:30", "15:30 - 17:00", "17:00 - 18:30", "18:30 - 20:00", "20:00 - 21:30"
        );

        List<String> Dias = List.of(
                "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"
        );

        // Obtener las reservas de la semana actual
        List<Reserva> reservasSemana = reservaService.obtenerReservasEntreFechas(inicioSemana, inicioSemana.plusDays(6), campo);

        for (int i = 0; i < 7; i++) {
            LocalDate dia = inicioSemana.plusDays(i);
            Map<String, Object> diaConHoras = new HashMap<>();
            diaConHoras.put("nombreDia", dia.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
            diaConHoras.put("fecha", dia);

            List<Map<String, Object>> horasDelDia = new ArrayList<>();
            for (String hora : horas) {
                boolean reservada = reservasSemana.stream()
                        .anyMatch(reserva -> reserva.getFecha().equals(dia) && reserva.getHora().equals(hora));
                Map<String, Object> horaEstado = new HashMap<>();
                horaEstado.put("hora", hora);
                horaEstado.put("reservada", reservada);
                horasDelDia.add(horaEstado);
            }

            diaConHoras.put("horas", horasDelDia);
            diasConHoras.add(diaConHoras);
        }

        // Pasar los datos al modelo
        model.addAttribute("dias", Dias);
        model.addAttribute("horas", horas);
        model.addAttribute("diasConHoras", diasConHoras);

        return "reservar";
    }

}