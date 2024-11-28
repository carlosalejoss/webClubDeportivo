package org.example.si_gestor_club_deportivo.controller;

import jakarta.servlet.http.HttpSession;
import org.example.si_gestor_club_deportivo.model.Pista;
import org.example.si_gestor_club_deportivo.model.Reserva;
import org.example.si_gestor_club_deportivo.model.Usuario;
import org.example.si_gestor_club_deportivo.model.Horario;
import org.example.si_gestor_club_deportivo.model.ReservaClase;
import org.example.si_gestor_club_deportivo.service.ReservaService;
import org.example.si_gestor_club_deportivo.service.UsuarioService;
import org.example.si_gestor_club_deportivo.model.Clase;
import org.example.si_gestor_club_deportivo.repository.ClaseRepository;
import org.example.si_gestor_club_deportivo.repository.ReservaClaseRepository;
import org.example.si_gestor_club_deportivo.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar toda la web, las conexiones de los html, etc.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private final UsuarioService usuarioService;
    private final PistaService pistaService;
    private final ReservaService reservaService;
    private final ClaseService claseService;
    private final HorarioService horarioService;
    private final ReservaClaseRepository reservaClaseRepository;
    private final ClaseRepository claseRepository;
    private final ReservaClaseService reservaClaseService;
    private final MailService mailService;

    /**
     * Constructor con inyección de dependencias para inicializar todos los servicios y repositorios necesarios en el controlador.
     *
     * @param usuarioService Servicio para gestionar las operaciones relacionadas con los usuarios.
     * @param pistaService Servicio para gestionar las operaciones relacionadas con las pistas.
     * @param reservaService Servicio para gestionar las operaciones relacionadas con las reservas.
     * @param claseService Servicio para gestionar las operaciones relacionadas con las clases.
     * @param horarioService Servicio para gestionar los horarios de las clases.
     * @param reservaClaseRepository Repositorio para las operaciones CRUD de reservas de clases.
     * @param claseRepository Repositorio para las operaciones CRUD de clases.
     * @param reservaClaseService Servicio para la gestión de reservas de clases.
     * @param mailService Servicio para el envío de correos electrónicos.
     */
    @Autowired
    public HomeController(UsuarioService usuarioService, PistaService pistaService, ReservaService reservaService, ClaseService claseService, HorarioService horarioService, ReservaClaseRepository reservaClaseRepository, ClaseRepository claseRepository, ReservaClaseService reservaClaseService, MailService mailService) {
        this.usuarioService = usuarioService;
        this.pistaService = pistaService;
        this.reservaService = reservaService;
        this.claseService = claseService;
        this.horarioService = horarioService;
        this.reservaClaseRepository = reservaClaseRepository;
        this.claseRepository = claseRepository;
        this.reservaClaseService = reservaClaseService;
        this.mailService = mailService;
    }

    /**
     * Método para manejar la solicitud GET a la página principal.
     *
     * @param session La sesión del usuario.
     * @return La vista principal "home".
     */
    @GetMapping("/")
    public String home(HttpSession session) {
        return "home";
    }

    /**
     * Método para manejar la solicitud GET a la página de éxito en la reserva.
     *
     * @param session La sesión del usuario.
     * @return La vista "exitoReserva" que muestra un mensaje de éxito en la reserva.
     */
    @GetMapping("/exitoRsv")
    public String exitoRsv(HttpSession session) {
        return "exitoReserva";
    }

    /**
     * Método para manejar la solicitud GET a la página de fallo en la reserva.
     *
     * @param session La sesión del usuario.
     * @return La vista "falloReserva" que muestra un mensaje de error en la reserva.
     */
    @GetMapping("/falloRsv")
    public String falloRsv(HttpSession session) {
        session.getAttribute("tipoFallo");
        return "falloReserva";
    }

    /**
     * Método para manejar la solicitud GET a la página de inicio de sesión.
     *
     * @param session La sesión del usuario.
     * @return La vista "iniciarSesion" que permite al usuario ingresar sus credenciales.
     */
    @GetMapping("/iniciarSesion")
    public String iniciarsesion(HttpSession session) {
        return "iniciarSesion";
    }

    /**
     * Método para manejar la solicitud GET a la página de registro de un nuevo usuario.
     *
     * @param session La sesión del usuario.
     * @return La vista "registrarse" que permite al usuario crear una cuenta.
     */
    @GetMapping("/registrarse")
    public String registrarse(HttpSession session) {
        return "registrarse";
    }

    /**
     * Método para manejar la solicitud GET a la página de datos de cuenta del usuario.
     *
     * @param session La sesión del usuario.
     * @return La vista "datosCuenta" donde el usuario puede ver y editar sus datos.
     */
    @GetMapping("/datosCuenta")
    public String datosCuenta(HttpSession session) {
        return "datosCuenta";
    }

    /**
     * Método para manejar la solicitud GET a la página "Sobre Nosotros".
     *
     * @param session La sesión del usuario.
     * @return La vista "sobreNosotros", que describe la información sobre el club deportivo.
     */
    @GetMapping("/sobreNosotros")
    public String sobreNosotros(HttpSession session) {
        return "sobreNosotros";
    }

    /**
     * Método para manejar la selección de tipo de campo por parte del usuario y mostrar las pistas disponibles.
     *
     * @param tipo El tipo de deporte para el cual se selecciona el campo (ej. Futbol, Baloncesto, etc.).
     * @param model El modelo que contiene los atributos para pasar a la vista.
     * @return La vista "eleccionCampo" con las pistas disponibles para el tipo de campo seleccionado.
     */
    @GetMapping("/eleccionCampo")
    public String eleccionCampo(@RequestParam("tipo") String tipo, Model model) {
        // Mapear tipo a su imagen correspondiente
        String imagenCampo = switch (tipo) {
            case "Futbol 11" -> "/campof11.png";
            case "Futbol 7" -> "/campoFut7.png";
            case "Baloncesto" -> "/pistBaloncesto.png";
            case "Tenis" -> "/pistaTenis.png";
            case "Padel" -> "/pistaPadel.png";
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

    /**
     * Método para redirigir a la página de selección de campo para Futbol 11.
     *
     * @param model El modelo de la vista.
     * @return La vista redirigida para la elección de campo de Futbol 11.
     */
    @GetMapping("/eleccionCampoFut11")
    public String eleccionCampoFut11(Model model) {
        return "redirect:/eleccionCampo?tipo=Futbol 11";
    }

    /**
     * Método para redirigir a la página de selección de campo para Futbol 7.
     *
     * @param session La sesión del usuario.
     * @return La vista redirigida para la elección de campo de Futbol 7.
     */
    @GetMapping("/eleccionCampoFut7")
    public String eleccionCampoFut7(HttpSession session) {
        return "redirect:/eleccionCampo?tipo=Futbol 7";
    }

    /**
     * Método para redirigir a la página de selección de campo para Baloncesto.
     *
     * @param session La sesión del usuario.
     * @return La vista redirigida para la elección de campo de Baloncesto.
     */
    @GetMapping("/eleccionCampoBasket")
    public String eleccionCampoBasket(HttpSession session) {
        return "redirect:/eleccionCampo?tipo=Baloncesto";
    }

    /**
     * Método para redirigir a la página de selección de campo para Tenis.
     *
     * @param session La sesión del usuario.
     * @return La vista redirigida para la elección de campo de Tenis.
     */
    @GetMapping("/eleccionCampoTenis")
    public String eleccionCampoTenis(HttpSession session) {
        return "redirect:/eleccionCampo?tipo=Tenis";
    }

    /**
     * Método para redirigir a la página de selección de campo para Padel.
     *
     * @param session La sesión del usuario.
     * @return La vista redirigida para la elección de campo de Padel.
     */
    @GetMapping("/eleccionCampoPadel")
    public String eleccionCampoPadel(HttpSession session) {
        return "redirect:/eleccionCampo?tipo=Padel";
    }

    /**
     * Método para manejar el inicio de sesión del usuario.
     *
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @param session La sesión del usuario.
     * @param model El modelo para agregar atributos a la vista.
     * @return La vista redirigida según el éxito o error del inicio de sesión.
     */
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

    /**
     * Método para manejar el registro de un nuevo usuario.
     *
     * @param email El correo electrónico del nuevo usuario.
     * @param password La contraseña del nuevo usuario.
     * @param nombre El nombre del nuevo usuario.
     * @param apellidos Los apellidos del nuevo usuario.
     * @param telefono El número de teléfono del nuevo usuario.
     * @param dni El DNI del nuevo usuario.
     * @param session La sesión del usuario.
     * @param model El modelo para agregar atributos a la vista.
     * @return La vista redirigida según el éxito o error del registro.
     */
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

    /**
     * Método para cambiar la vista del usuario entre vista de administrador y vista normal.
     * Si el usuario es administrador, cambia el estado de "viewAsAdmin" en la sesión.
     *
     * @param session La sesión del usuario.
     * @return Redirige a la página de inicio.
     */
    @GetMapping("/toggleView")
    public String toggleView(HttpSession session) {
        if (Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            Boolean viewAsAdmin = (Boolean) session.getAttribute("viewAsAdmin");
            session.setAttribute("viewAsAdmin", !viewAsAdmin);
        }
        return "redirect:/";
    }

    /**
     * Método para cerrar la sesión del usuario.
     * Invalida la sesión actual y redirige a la página de inicio.
     *
     * @param session La sesión del usuario.
     * @return Redirige a la página de inicio después de cerrar sesión.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    /**
     * Método para mostrar las reservas de la semana actual.
     * Verifica que el usuario esté logueado y muestra las reservas disponibles
     * en función de la pista seleccionada y la semana actual.
     *
     * @param campo El nombre de la pista seleccionada.
     * @param model El modelo para pasar datos a la vista.
     * @param session La sesión del usuario.
     * @return La vista "reservar" con los horarios disponibles para la semana actual.
     */
    @GetMapping("/reservar")
    public String mostrarReservasSemanaActual(@RequestParam("campo") String campo, Model model, HttpSession session) {
        // Verifica que el usuario esté logueado
        Object loggedUser = session.getAttribute("loggedUser");
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (loggedUser == null) {
            return "redirect:/iniciarSesion";
        }

        Optional<Pista> pista = pistaService.obtenerPistaPorNombre(campo);

        // Obtener la semana actual
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.with(ChronoField.DAY_OF_WEEK, 1);

        // Lista de horas
        List<String> horas = List.of(
                "08:00 - 09:30", "09:30 - 11:00", "11:00 - 12:30", "12:30 - 14:00",
                "14:00 - 15:30", "15:30 - 17:00", "17:00 - 18:30", "18:30 - 20:00", "20:00 - 21:30"
        );

        List<Map<String, Object>> diasSemanaConFechas = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate dia = inicioSemana.plusDays(i);
            Map<String, Object> diasSemFecha = new HashMap<>();
            diasSemFecha.put("diaSem", dia.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
            diasSemFecha.put("fecha", dia);
            diasSemanaConFechas.add(diasSemFecha);
        }

        // Obtener las reservas de la semana actual
        List<Reserva> reservasSemana = reservaService.obtenerReservasEntreFechas(inicioSemana, inicioSemana.plusDays(6), campo);

        List<Map<String, Object>> horariosConReservas = new ArrayList<>();

        for (Map<String, Object> diaFecha : diasSemanaConFechas) {
            for (String hora : horas) {
                Map<String, Object> horario = new HashMap<>();
                horario.put("fecha", diaFecha.get("fecha"));
                horario.put("hora", hora);
                boolean reservado = reservasSemana.stream().anyMatch(reserva ->
                        reserva.getFecha().equals(diaFecha.get("fecha")) && reserva.getHora().equals(hora)
                );

                horario.put("reservado", reservado);
                horariosConReservas.add(horario);
            }
        }

        model.addAttribute("pistaActual", pista);
        model.addAttribute("horariosConReservas", horariosConReservas);
        model.addAttribute("userId", usuario.getId());
        model.addAttribute("pistaNombre", campo);
        model.addAttribute("diasSemanaConFechas", diasSemanaConFechas);

        return "reservar";
    }

    /**
     * Método para calcular el precio de la reserva dependiendo del tipo de pista seleccionada.
     *
     * @param pista El tipo de pista seleccionada.
     * @return El precio de la reserva según el tipo de pista.
     */
    private Double calculatePrice(String pista) {
        Double price = 20.0;

        switch (pista) {
            case "Futbol 11":
                price = 50.0;
                break;
            case "Futbol 7":
                price = 30.0;
                break;
            case "Baloncesto":
                price = 35.0;
                break;
            case "Tenis":
                price = 29.0;
                break;
            case "Padel":
                price = 25.0;
                break;
        }

        return price;
    }

    /**
     * Método para crear una nueva reserva.
     * Verifica que el usuario esté logueado y que la fecha y hora seleccionadas sean válidas.
     * Si no hay conflicto, guarda la nueva reserva.
     *
     * @param pistaNombre El nombre de la pista seleccionada.
     * @param fecha La fecha de la reserva.
     * @param horaInicio La hora de inicio de la reserva.
     * @param horaFin La hora de fin de la reserva.
     * @param session La sesión del usuario.
     * @param model El modelo para pasar datos a la vista.
     * @return La vista redirigida según el éxito o error al crear la reserva.
     */
    @PostMapping("/nueva")
    public String crearReserva(
            @RequestParam String pistaNombre,
            @RequestParam String fecha,
            @RequestParam String horaInicio,
            @RequestParam String horaFin,
            HttpSession session,
            Model model) {

        // Validar que el usuario esté logueado
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            model.addAttribute("error", "Usuario no autenticado.");
            return "redirect:/iniciarSesion";
        }

        LocalDate fechaParsed = LocalDate.parse(fecha);
        boolean yaReservado = reservaService.obtenerReservaUsuarioFecha(usuario.getId(), fechaParsed);

        if (yaReservado) {
            return "redirect:/falloRsv";
        }

        // Obtener la fecha y hora actuales
        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();
        LocalTime horaInicioParsed = LocalTime.parse(horaInicio);

        // Verificar si la fecha del horario es la fecha actual
        if (fechaParsed.equals(fechaActual)) {
            // Si es el mismo día, verificar si la hora ya ha pasado
            boolean reservaEnCurso = horaActual.equals(horaInicioParsed) || horaActual.isAfter(horaInicioParsed);

            if (reservaEnCurso) {
                return "redirect:/falloRsv";
            }
        } else if (fechaParsed.isBefore(fechaActual)) {
            // Si la fecha es anterior al día actual, no se puede reservar
            return "redirect:/falloRsv";
        }

        // Crear nueva reserva
        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setUsuario(usuario);

        // Validar pista
        Optional<Pista> pista = pistaService.obtenerPistaPorNombre(pistaNombre);

        if (pista.isEmpty()) {
            model.addAttribute("error", "La pista no existe.");
            return "redirect:/reservar";
        }
        nuevaReserva.setPista(pista.get());

        // Validar y asignar fecha y horas
        LocalDate parsedFecha = LocalDate.parse(fecha);
        if (parsedFecha.isBefore(LocalDate.now())) {
            model.addAttribute("error", "La fecha no puede ser anterior a la actual.");
            return "redirect:/";
        }
        nuevaReserva.setFecha(parsedFecha);
        nuevaReserva.setHoraInicio(LocalTime.parse(horaInicio));
        nuevaReserva.setHoraFin(LocalTime.parse(horaFin));
        nuevaReserva.setPrecio(calculatePrice(pistaNombre));

        // Guardar reserva
        reservaService.crearReserva(nuevaReserva);

        return "redirect:/exitoRsv";
    }

    /**
     * Método para mostrar las reservas del usuario para la semana actual.
     * Separa las reservas activas y no activas, y también muestra las reservas fuera de la semana actual.
     *
     * @param model El modelo para pasar los datos de reservas a la vista.
     * @param session La sesión del usuario.
     * @return La vista "misReservas" con las reservas activas y no activas.
     */
    @GetMapping("/misReservas")
    public String misReservas(Model model, HttpSession session) {
        // Obtener la semana actual
        LocalDate hoy = LocalDate.now();
        LocalTime horaActual = LocalTime.now();
        LocalDate inicioSemana = hoy.with(ChronoField.DAY_OF_WEEK, 1);

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Obtener todas las reservas del usuario para la semana actual
        List<Reserva> reservasSemana = reservaService.obtenerReservasUsuarioFechas(inicioSemana, inicioSemana.plusDays(6), usuario.getId());

        // Separar reservas activas y no activas
        List<Reserva> reservasActivas = new ArrayList<>();
        List<Reserva> reservasNoActivas = new ArrayList<>();

        for (Reserva reserva : reservasSemana) {
            if (reserva.getFecha().isAfter(hoy) ||
                    (reserva.getFecha().isEqual(hoy) && reserva.getHoraInicio().isAfter(horaActual))) {
                reservasActivas.add(reserva);
            } else {
                reservasNoActivas.add(reserva);
            }
        }

        // Obtener reservas fuera del rango de la semana actual
        List<Reserva> reservasFueraDeLaSemana = reservaService.obtenerReservasUsuarioExcluyendoRangoFechas(inicioSemana, inicioSemana.plusDays(6), usuario.getId());

        // Combinar reservas no activas de la semana actual con las reservas fuera de la semana
        reservasNoActivas.addAll(reservasFueraDeLaSemana);

        model.addAttribute("reservasActivas", reservasActivas);
        model.addAttribute("reservasNoActivas", reservasNoActivas);

        return "misReservas";
    }

    /**
     * Método para eliminar una reserva.
     * Elimina la reserva seleccionada y redirige al usuario a la página de sus reservas.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @param session La sesión del usuario.
     * @param pista El ID de la reserva a eliminar.
     * @return La vista redirigida a la página de reservas del usuario.
     */
    @PostMapping("/eliminarReserva")
    public String eliminarReserva(Model model, HttpSession session, @RequestParam("reservaId") Long pista) {
        reservaService.eliminarReserva(pista);

        return "redirect:/misReservas";
    }

    /**
     * Método para gestionar las pistas. Obtiene todas las pistas y las agrupa por tipo,
     * pasando los datos al modelo para mostrarlas en la vista "gestionarPistasAdmin".
     *
     * @param model El modelo para pasar los datos a la vista.
     * @param session La sesión del usuario.
     * @return La vista "gestionarPistasAdmin" que muestra las pistas agrupadas por tipo.
     */
    @GetMapping("/gestionarPistas")
    public String mostrarGestionPistas(Model model, HttpSession session) {
        // Obtener todas las pistas
        List<Pista> pistas = pistaService.obtenerTodasLasPistas();

        // Agrupar las pistas por tipo
        Map<String, List<Pista>> pistasPorTipo = pistas.stream()
                .collect(Collectors.groupingBy(Pista::getTipo));

        // Obtener todos los tipos de pista (sin duplicados)
        List<String> tiposDePistas = pistasPorTipo.keySet().stream().sorted().collect(Collectors.toList());

        // Pasar los datos a la vista
        model.addAttribute("tiposDePistas", tiposDePistas);
        model.addAttribute("pistasPorTipo", pistasPorTipo);

        return "gestionarPistasAdmin"; // Nombre de la plantilla Thymeleaf
    }

    /**
     * Método para eliminar una pista seleccionada.
     * Elimina la pista correspondiente y redirige a la página de gestión de pistas.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @param session La sesión del usuario.
     * @param nombrePista El nombre de la pista a eliminar.
     * @return Redirige a la página de gestión de pistas después de eliminar la pista.
     */
    @PostMapping("/gestionarPistas")
    public String eliminarCampo(Model model, HttpSession session, @RequestParam("campoId") String nombrePista) {
        pistaService.eliminarPista(nombrePista);
        return("redirect:/gestionarPistas");
    }

    /**
     * Método para mostrar la vista de creación de una nueva pista.
     * Obtiene todas las pistas y las agrupa por tipo, pasando los datos al modelo
     * para mostrar los tipos de pistas disponibles en la vista "newCampo".
     *
     * @param model El modelo para pasar los datos a la vista.
     * @param session La sesión del usuario.
     * @return La vista "newCampo" que muestra los tipos de pistas disponibles para crear una nueva pista.
     */
    @GetMapping("/newCampo")
    public String newCampo(Model model, HttpSession session) {
        // Obtener todas las pistas
        List<Pista> pistas = pistaService.obtenerTodasLasPistas();

        // Agrupar las pistas por tipo
        Map<String, List<Pista>> pistasPorTipo = pistas.stream()
                .collect(Collectors.groupingBy(Pista::getTipo));

        // Obtener todos los tipos de pista (sin duplicados)
        List<String> tiposDePistas = pistasPorTipo.keySet().stream().sorted().collect(Collectors.toList());

        // Pasar los datos a la vista
        model.addAttribute("tiposDePistas", tiposDePistas);
        return "newCampo";
    }

    /**
     * Método para guardar una nueva pista en la base de datos.
     * Crea una nueva pista con los datos proporcionados y asigna un número de pista basado
     * en el tipo de pista. Luego, guarda la pista en la base de datos y redirige a la página
     * de gestión de pistas.
     *
     * @param nombre El nombre de la nueva pista.
     * @param tipo El tipo de la nueva pista.
     * @param descripcion La descripción de la nueva pista.
     * @param model El modelo para pasar los datos a la vista.
     * @param session La sesión del usuario.
     * @return Redirige a la página de gestión de pistas después de crear la nueva pista.
     */
    @PostMapping("/newCampo")
    public String guardarCampo(@RequestParam String nombre, @RequestParam String tipo, @RequestParam String descripcion, Model model, HttpSession session) {
        Pista newPista = new Pista();
        newPista.setNombre(nombre);
        newPista.setTipo(tipo);
        newPista.setDescripcion(descripcion);
        Pista auxPista = pistaService.obtenerUltimaPistaPorTipo(tipo);

        if(auxPista == null){
            newPista.setNumero_pista(1);
        }
        else {
            newPista.setNumero_pista(auxPista.getNumero_pista() + 1);
        }

        pistaService.crearPista(newPista);
        return "redirect:/gestionarPistas";
    }

    /**
     * Método para actualizar los datos del usuario.
     * Permite al usuario modificar su nombre, apellidos, correo electrónico, teléfono y contraseña.
     * Valida que al menos un campo sea cambiado y que los datos no estén duplicados en la base de datos.
     *
     * @param newNombre El nuevo nombre del usuario.
     * @param newApellidos Los nuevos apellidos del usuario.
     * @param newDNI El nuevo DNI del usuario.
     * @param newEmail El nuevo email del usuario.
     * @param newTelefono El nuevo número de teléfono del usuario.
     * @param newPassword La nueva contraseña del usuario.
     * @param session La sesión del usuario.
     * @param model El modelo para pasar los datos a la vista.
     * @return Redirige a la página de datos de cuenta después de actualizar los datos.
     */
    @PostMapping("/NewDatos")
    public String nuevosDatos(
            @RequestParam String newNombre,
            @RequestParam String newApellidos,
            @RequestParam String newDNI,
            @RequestParam String newEmail,
            @RequestParam String newTelefono,
            @RequestParam String newPassword,
            HttpSession session,
            Model model) {

        // Obtener el usuario actual desde la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if ((newEmail == null || newEmail.trim().isEmpty()) &&
                (newPassword == null || newPassword.trim().isEmpty()) &&
                (newNombre == null || newNombre.trim().isEmpty()) &&
                (newApellidos == null || newApellidos.trim().isEmpty()) &&
                (newTelefono == null || newTelefono.trim().isEmpty()) &&
                (newDNI == null || newDNI.trim().isEmpty())) {

            model.addAttribute("error", "Hay que rellenar mínimo un campo para cambiar los datos.");
            return "datosCuenta"; // Redirige de nuevo a la página de registro en caso de error
        }

        if (usuarioService.findByEmail(newEmail) != null) {
            model.addAttribute("error", "El email ya está registrado.");
            return "datosCuenta"; // Redirige de nuevo a la página de registro en caso de error
        }

        if (usuarioService.findByDni(newDNI) != null) {
            model.addAttribute("error", "El dni ya está registrado.");
            return "datosCuenta"; // Redirige de nuevo a la página de registro en caso de error
        }

        if (usuarioService.findByTelefono(newTelefono) != null) {
            model.addAttribute("error", "El telefono ya está registrado.");
            return "datosCuenta"; // Redirige de nuevo a la página de registro en caso de error
        }

        // Cambiar los datos del usuario
        if (newNombre != null && !newNombre.trim().isEmpty()) {
            usuario.setNombre(newNombre);
        }
        if (newApellidos != null && !newApellidos.trim().isEmpty()) {
            usuario.setApellidos(newApellidos);
        }
        if (newDNI != null && !newDNI.trim().isEmpty()) {
            usuario.setDni(newDNI);
        }
        if (newEmail != null && !newEmail.trim().isEmpty()) {
            usuario.setEmail(newEmail);
        }
        if (newTelefono != null && !newTelefono.trim().isEmpty()) {
            usuario.setTelefono(newTelefono);
        }
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            usuario.setPassword(newPassword);
        }

        // Guardar los cambios
        usuarioService.guardarUsuario(usuario);

        // Actualizar la sesión con los datos modificados si es necesario
        session.setAttribute("usuario", usuario);

        return "redirect:/datosCuenta";
    }

    /**
     * Método para mostrar todas las clases disponibles.
     * Obtiene los tipos de clases y las pasa al modelo para mostrarlas en la vista "clases".
     *
     * @param model El modelo para pasar los datos a la vista.
     * @param session La sesión del usuario.
     * @return La vista "clases" con los tipos de clases disponibles.
     */
    @GetMapping("/clases")
    public String clases(Model model, HttpSession session) {
        List<String> tipos = claseService.obtenerTiposDeClases();

        model.addAttribute("tiposDeClases", tipos);
        return "clases";
    }

    /**
     * Método para mostrar las clases disponibles según el tipo seleccionado.
     * Verifica que el usuario esté logueado y filtra las clases por tipo, mostrando las disponibles
     * en la vista "reservarClases". Además, filtra los horarios según la fecha y hora actuales.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @param session La sesión del usuario.
     * @param tipoSeleccionado El tipo de clase seleccionada.
     * @return La vista "reservarClases" que muestra las clases disponibles para el tipo seleccionado.
     */
    @GetMapping("/clasesReserva")
    public String clasesReserva(Model model, HttpSession session, @RequestParam("tipo") String tipoSeleccionado) {
        // Verificar que el usuario esté logueado
        Object loggedUser = session.getAttribute("loggedUser");
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (loggedUser == null) {
            return "redirect:/iniciarSesion";
        }

        // Obtener las clases por tipo
        List<Clase> clases = claseService.obtenerClasesPorTipo(tipoSeleccionado);

        // Fecha y hora actuales
        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        // Filtrar horarios por fecha y hora actuales o posteriores
        Map<Long, List<Horario>> horariosPorClase = clases.stream()
                .collect(Collectors.toMap(
                        Clase::getId,
                        clase -> horarioService.obtenerHorariosPorClase(clase.getId())
                                .stream()
                                .filter(horario ->
                                        horario.getFecha().isAfter(fechaActual) ||
                                                (horario.getFecha().isEqual(fechaActual) && horario.getHoraInicio().isAfter(horaActual))
                                )
                                .collect(Collectors.toList())
                ));

        // Filtrar las clases que no tienen horarios válidos
        List<Clase> clasesConHorarios = clases.stream()
                .filter(clase -> horariosPorClase.get(clase.getId()) != null && !horariosPorClase.get(clase.getId()).isEmpty())
                .collect(Collectors.toList());

        // Pasar datos al modelo
        model.addAttribute("tipoSeleccionado", tipoSeleccionado);
        model.addAttribute("clases", clasesConHorarios);
        model.addAttribute("horariosPorClase", horariosPorClase);
        return "reservarClases";
    }

    /**
     * Método para reservar una clase. Verifica si el usuario ya tiene una reserva en el mismo horario o si
     * el número máximo de asistentes ya ha sido alcanzado. Si todo está en orden, realiza la reserva.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @param session La sesión del usuario.
     * @param horarioId El ID del horario de la clase que el usuario desea reservar.
     * @return Redirige a la página de éxito o de fallo según si la reserva fue exitosa o no.
     */
    @PostMapping("/clasesReserva")
    public String apuntarseClase(Model model, HttpSession session, @RequestParam("horarioId") Long horarioId) {
        Usuario user = (Usuario) session.getAttribute("usuario");
        Horario horario = horarioService.obtenerHorarioPorId(horarioId);

        // Verificar si ya está reservado por el usuario en el mismo horario
        boolean yaReservado = reservaClaseRepository.existsByHorarioIdAndUsuarioId(horarioId, user.getId());
        if (yaReservado) {
            session.setAttribute("tipoFallo", "No se ha podido completar la reserva porque ya has reservado esta clase en concreto.");
            return "redirect:/falloRsv";
        }

        // Verificar si ya hay una clase en el mismo horario
        List<ReservaClase> reservasUsuario = reservaClaseRepository.findByUsuarioId(user.getId());
        for (ReservaClase reserva : reservasUsuario) {
            Horario horarioReserva = reserva.getHorarioClase();
            if (horario.getFecha().equals(horarioReserva.getFecha()) &&
                    ((horario.getHoraInicio().isBefore(horarioReserva.getHoraFin()) &&
                            horario.getHoraFin().isAfter(horarioReserva.getHoraInicio())))) {
                session.setAttribute("tipoFallo", "No se ha podido completar la reserva ya que ya tienes una clase reservada en esa hora.");
                return "redirect:/falloRsv";
            }
        }

        // Verificar si el número máximo de asistentes ya fue alcanzado
        List<ReservaClase> reservasActuales = reservaClaseRepository.findByHorarioId(horarioId);
        if ((reservasActuales.size() + 1) > horario.getClase().getMaxAsistentes()) {
            session.setAttribute("tipoFallo", "No se ha podido completar la reserva ya que se ha alcanzado el número máximo de asistentes a la clase.");
            return "redirect:/falloRsv";
        }

        // Crear y guardar la nueva reserva
        ReservaClase newReserva = new ReservaClase();
        newReserva.setUsuario(user);
        newReserva.setHorarioClase(horario);
        newReserva.setFechaReserva(LocalDateTime.now());

        reservaClaseRepository.save(newReserva);

        return "redirect:/exitoRsv";
    }

    /**
     * Método para mostrar las clases reservadas por el usuario. Filtra las clases activas y no activas de la semana
     * actual y las clases fuera del rango de fechas actual.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @param session La sesión del usuario.
     * @return La vista "misClases" que muestra las clases activas y no activas.
     */
    @GetMapping("/verClases")
    public String misClases(Model model, HttpSession session) {
        // Obtener la semana actual
        LocalDate hoy = LocalDate.now();
        LocalTime horaActual = LocalTime.now();
        LocalDate inicioSemana = hoy.with(ChronoField.DAY_OF_WEEK, 1);

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Obtener todas las reservas de clases del usuario para la semana actual
        List<ReservaClase> clasesSemana = reservaClaseService.obtenerReservasClasesUsuarioFechas(inicioSemana, inicioSemana.plusDays(6), usuario.getId());

        // Separar clases activas y no activas
        List<ReservaClase> clasesActivas = new ArrayList<>();
        List<ReservaClase> clasesNoActivas = new ArrayList<>();

        for (ReservaClase reservaClase : clasesSemana) {
            if (reservaClase.getHorarioClase().getFecha().isAfter(hoy) ||
                    (reservaClase.getHorarioClase().getFecha().isEqual(hoy) && reservaClase.getHorarioClase().getHoraInicio().isAfter(horaActual))) {
                clasesActivas.add(reservaClase);
            } else {
                clasesNoActivas.add(reservaClase);
            }
        }

        // Obtener reservas de clases fuera del rango de la semana actual
        List<ReservaClase> clasesFueraDeLaSemana = reservaClaseService.obtenerReservasUsuarioExcluyendoRangoFechas(inicioSemana, inicioSemana.plusDays(6), usuario.getId());

        // Combinar clases no activas de la semana actual con las clases fuera de la semana
        clasesNoActivas.addAll(clasesFueraDeLaSemana);

        // Pasar las clases activas y no activas al modelo
        model.addAttribute("clasesActivas", clasesActivas);
        model.addAttribute("clasesNoActivas", clasesNoActivas);

        return "misClases";
    }

    /**
     * Método para eliminar una clase reservada por el usuario.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @param session La sesión del usuario.
     * @param pista El ID de la clase a eliminar.
     * @return Redirige a la página de mis reservas después de eliminar la clase.
     */
    @PostMapping("/eliminarClase")
    public String eliminarClase(Model model, HttpSession session, @RequestParam("claseId") Long pista) {
        reservaClaseService.eliminarClase(pista);

        return "redirect:/misReservas";
    }

    /**
     * Método para gestionar todas las clases disponibles en el sistema.
     * Obtiene todas las clases y las pasa al modelo para ser mostradas en la vista "gestionarClasesAdmin".
     *
     * @param model El modelo para pasar los datos a la vista.
     * @param session La sesión del usuario.
     * @return La vista "gestionarClasesAdmin" que muestra todas las clases disponibles.
     */
    @GetMapping("/gestionarClases")
    public String gestionarClases(Model model, HttpSession session) {
        List<Clase> clases = claseService.obtenerTodasLasClases();
        model.addAttribute("clases", clases);
        return "gestionarClasesAdmin";
    }

    /**
     * Método para buscar clases por un rango de fechas. Filtra las clases según las fechas de inicio y fin
     * y pasa los resultados al modelo para ser mostrados en la vista de resultados.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @param fechaInicio La fecha de inicio del rango de búsqueda.
     * @param fechaFin La fecha de fin del rango de búsqueda.
     * @return La vista "resultadoClases" que muestra las clases filtradas por el rango de fechas.
     */
    @GetMapping("/clases/buscar")
    public String buscarClasesPorFechas(
            Model model,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        // Filtrar horarios dentro del rango de fechas
        List<Horario> horariosEnRango = horarioService.obtenerTodosLosHorarios()
                .stream()
                .filter(horario -> !horario.getFecha().isBefore(fechaInicio) && !horario.getFecha().isAfter(fechaFin))
                .collect(Collectors.toList());

        // Obtener IDs de las clases relacionadas con esos horarios
        List<Long> claseIds = horariosEnRango.stream()
                .map(horario -> horario.getClase().getId())
                .distinct()
                .collect(Collectors.toList());

        // Obtener las clases relacionadas
        List<Clase> clases = claseService.obtenerClasesPorIds(claseIds);

        // Pasar las clases y horarios al modelo
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        model.addAttribute("clases", clases);
        model.addAttribute("horarios", horariosEnRango);

        return "resultadoClases"; // Página de resultados
    }

    /**
     * Método para guardar los cambios en los datos de una clase.
     * Actualiza los detalles de una clase en la base de datos con la nueva información proporcionada por el usuario.
     *
     * @param id El ID de la clase que se va a editar.
     * @param nombre El nuevo nombre de la clase.
     * @param profesor El nuevo profesor de la clase.
     * @param tipo El nuevo tipo de la clase.
     * @param descripcion La nueva descripción de la clase.
     * @param maxAsistentes El nuevo número máximo de asistentes.
     * @param precio El nuevo precio de la clase.
     * @return Redirige a la página de gestión de clases después de guardar los cambios.
     */
    @PostMapping("/clases/editar/{id}")
    public String guardarEdicionClase(
            @PathVariable Long id,
            @RequestParam String nombre,
            @RequestParam String profesor,
            @RequestParam String tipo,
            @RequestParam String descripcion,
            @RequestParam int maxAsistentes,
            @RequestParam double precio) {

        Clase clase = claseService.obtenerClasePorId(id);
        clase.setNombre(nombre);
        clase.setProfesor(profesor);
        clase.setTipo(tipo);
        clase.setDescripcion(descripcion);
        clase.setMaxAsistentes(maxAsistentes);
        clase.setPrecio(BigDecimal.valueOf(precio));
        claseService.guardarClase(clase);
        return "redirect:/gestionarClases";
    }

    /**
     * Método para mostrar el formulario de edición de una clase.
     * Obtiene los detalles de una clase específica para mostrar en el formulario de edición.
     *
     * @param id El ID de la clase que se va a editar.
     * @param model El modelo para pasar los datos a la vista.
     * @return La vista "editarClase" con los datos de la clase a editar.
     */
    @GetMapping("/clases/editar/{id}")
    public String editarClase(@PathVariable Long id, Model model) {
        Clase clase = claseService.obtenerClasePorId(id);
        model.addAttribute("clase", clase);
        return "editarClase"; // Vista para editar una clase
    }

    /**
     * Método para eliminar una clase.
     * Elimina una clase de la base de datos.
     *
     * @param id El ID de la clase que se va a eliminar.
     * @return Redirige a la página de gestión de clases después de eliminar la clase.
     */
    @PostMapping("/clases/eliminar/{id}")
    public String eliminarClase(@PathVariable Long id) {
        claseService.eliminarClase(id);
        return "redirect:/gestionarClases";
    }

    /**
     * Método para crear una nueva clase.
     * Crea una clase con los datos proporcionados por el usuario y la guarda en la base de datos.
     *
     * @param nombre El nombre de la nueva clase.
     * @param profesor El profesor de la nueva clase.
     * @param tipo El tipo de la nueva clase.
     * @param descripcion La descripción de la nueva clase.
     * @param maxAsistentes El número máximo de asistentes para la clase.
     * @param precio El precio de la nueva clase.
     * @return Redirige a la página de gestión de clases después de crear la clase.
     */
    @PostMapping("/clases/crear")
    public String crearClase(
            @RequestParam String nombre,
            @RequestParam String profesor,
            @RequestParam String tipo,
            @RequestParam String descripcion,
            @RequestParam int maxAsistentes,
            @RequestParam double precio) {

        Clase nuevaClase = new Clase();
        nuevaClase.setNombre(nombre);
        nuevaClase.setProfesor(profesor);
        nuevaClase.setTipo(tipo);
        nuevaClase.setDescripcion(descripcion);
        nuevaClase.setMaxAsistentes(maxAsistentes);
        nuevaClase.setPrecio(BigDecimal.valueOf(precio));

        claseService.guardarClase(nuevaClase);
        return "redirect:/gestionarClases";
    }

    /**
     * Método para crear un nuevo horario para una clase.
     * Verifica que la clase exista y crea un nuevo horario asociado a esa clase.
     *
     * @param claseId El ID de la clase a la que se le asignará el nuevo horario.
     * @param fecha La fecha del nuevo horario.
     * @param horaInicio La hora de inicio del nuevo horario.
     * @param horaFin La hora de fin del nuevo horario.
     * @param model El modelo para pasar los datos a la vista.
     * @return Redirige a la página de gestión de clases después de crear el horario.
     */
    @PostMapping("/clases/horarios/crear")
    public String crearHorario(
            @RequestParam Long claseId,
            @RequestParam String fecha,
            @RequestParam String horaInicio,
            @RequestParam String horaFin,
            Model model) {

        // Validar que la clase existe
        Clase clase = claseService.obtenerClasePorId(claseId);
        if (clase == null) {
            model.addAttribute("error", "La clase seleccionada no existe.");
            return "redirect:/gestionarClases"; // Ajustar según la vista de error
        }

        // Crear el nuevo horario
        Horario nuevoHorario = new Horario();
        nuevoHorario.setClase(clase);
        nuevoHorario.setFecha(LocalDate.parse(fecha));
        nuevoHorario.setHoraInicio(LocalTime.parse(horaInicio));
        nuevoHorario.setHoraFin(LocalTime.parse(horaFin));

        // Guardar el horario
        horarioService.guardarHorario(nuevoHorario);

        return "redirect:/gestionarClases";
    }

    /**
     * Método para enviar un correo de recuperación de contraseña.
     * Llama al servicio para enviar un correo de recuperación de contraseña al usuario.
     *
     * @param email El correo electrónico del usuario al que se le enviará el correo de recuperación.
     * @return Devuelve una respuesta HTTP con el resultado del envío del correo.
     */
    @PostMapping("/sendMail")
    public ResponseEntity<String> sendMail(@RequestParam String email) {
        String resultado = mailService.sendPasswordRecoveryEmail(email);
        if (resultado.startsWith("Correo enviado")) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.badRequest().body(resultado);
        }
    }

    /**
     * Método para mostrar el formulario de restablecimiento de contraseña.
     * Verifica si el token proporcionado es válido y muestra el formulario para restablecer la contraseña.
     *
     * @param token El token de restablecimiento de contraseña.
     * @param model El modelo para pasar los datos a la vista.
     * @return La vista para ingresar una nueva contraseña si el token es válido.
     */
    @GetMapping("/restablecer")
    public String mostrarFormularioRestablecer(@RequestParam("token") String token, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorToken(token);
        if (usuario == null) {
            model.addAttribute("error", "El enlace de restablecimiento no es válido o ha expirado.");
            return "falloNuevaContrasegna"; // Página de error
        }

        model.addAttribute("email", usuario.getEmail());
        return "nuevaContrasegna";
    }

    /**
     * Método para restablecer la contraseña de un usuario.
     * Verifica que las contraseñas ingresadas coincidan y actualiza la contraseña del usuario.
     *
     * @param email El correo electrónico del usuario.
     * @param password La nueva contraseña.
     * @param password2 La confirmación de la nueva contraseña.
     * @param model El modelo para pasar los datos a la vista.
     * @return Redirige al inicio de sesión si la contraseña se actualiza correctamente.
     */
    @PostMapping("/restablecer")
    public String restablecerContrasegna(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            Model model) {

        if (!password.equals(password2)) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "nuevaContrasegna";
        }

        boolean actualizado = usuarioService.actualizarContrasegnaSinEncriptar(email, password);
        if (!actualizado) {
            model.addAttribute("error", "No se pudo actualizar la contraseña.");
            return "nuevaContrasegna";
        }

        return "redirect:/iniciarSesion";
    }

}