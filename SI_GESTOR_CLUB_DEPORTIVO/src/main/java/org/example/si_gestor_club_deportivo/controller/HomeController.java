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

    @GetMapping("/")
    public String home(HttpSession session) {
        return "home";
    }

    @GetMapping("/exitoRsv")
    public String exitoRsv(HttpSession session) {return "exitoReserva";}

    @GetMapping("/falloRsv")
    public String falloRsv(HttpSession session) {return "falloReserva";}

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

    private Double calculatePrice(String pista){
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

        if(yaReservado){
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

    @PostMapping("/eliminarReserva")
    public String eliminarReserva(Model model, HttpSession session, @RequestParam("reservaId") Long pista) {
        reservaService.eliminarReserva(pista);

        return "redirect:/misReservas";
    }

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

    @PostMapping("/gestionarPistas")
    public String eliminarCampo(Model model, HttpSession session, @RequestParam("campoId") String nombrePista) {

        pistaService.eliminarPista(nombrePista);
        return("redirect:/gestionarPistas");
    }

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

    @PostMapping("/newCampo")
    public String guardarCampo(@RequestParam String nombre, @RequestParam String tipo,  @RequestParam String descripcion, Model model, HttpSession session) {

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

    @GetMapping("/clases")
    public String clases(Model model, HttpSession session) {
        List<String> tipos = claseService.obtenerTiposDeClases();

        model.addAttribute("tiposDeClases", tipos);
        return "clases";
    }

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


    @PostMapping("/clasesReserva")
    public String apuntarseClase(Model model, HttpSession session, @RequestParam("horarioId") Long horarioId) {
        Usuario user = (Usuario) session.getAttribute("usuario");
        Horario horario = horarioService.obtenerHorarioPorId(horarioId);

        // Verificar si ya está reservado por el usuario en el mismo horario
        boolean yaReservado = reservaClaseRepository.existsByHorarioIdAndUsuarioId(horarioId, user.getId());
        if (yaReservado) {
            return "redirect:/falloRsv";
        }

        // Verificar si ya hay una clase en el mismo horario
        List<ReservaClase> reservasUsuario = reservaClaseRepository.findByUsuarioId(user.getId());
        for (ReservaClase reserva : reservasUsuario) {
            Horario horarioReserva = reserva.getHorarioClase();
            if (horario.getFecha().equals(horarioReserva.getFecha()) &&
                    ((horario.getHoraInicio().isBefore(horarioReserva.getHoraFin()) &&
                            horario.getHoraFin().isAfter(horarioReserva.getHoraInicio())))) {
                return "redirect:/falloRsv";
            }
        }

        // Verificar si el número máximo de asistentes ya fue alcanzado
        List<ReservaClase> reservasActuales = reservaClaseRepository.findByHorarioId(horarioId);
        if ((reservasActuales.size() + 1) > horario.getClase().getMaxAsistentes()) {
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

    @PostMapping("/eliminarClase")
    public String eliminarClase(Model model, HttpSession session, @RequestParam("claseId") Long pista) {
        reservaClaseService.eliminarClase(pista);

        return "redirect:/misReservas";
    }

    @GetMapping("/gestionarClases")
    public String gestionarClases(Model model, HttpSession session) {
        List<Clase> clases = claseService.obtenerTodasLasClases();
        model.addAttribute("clases", clases);
        return "gestionarClasesAdmin";
    }

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

    @GetMapping("/clases/editar/{id}")
    public String editarClase(@PathVariable Long id, Model model) {
        Clase clase = claseService.obtenerClasePorId(id);
        model.addAttribute("clase", clase);
        return "editarClase"; // Vista para editar una clase
    }

    // Eliminar clase
    @PostMapping("/clases/eliminar/{id}")
    public String eliminarClase(@PathVariable Long id) {
        claseService.eliminarClase(id);
        return "redirect:/gestionarClases";
    }

    // Crear nueva clase
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


    /*@GetMapping("/sendMail")
    public String sendMail(@RequestParam("email") String email, HttpSession session) {

        mailService.sendMail("{mail Address}", "Title", "Massage");
        return "mirarCorreo";

    }*/

    @PostMapping("/sendMail")
    public ResponseEntity<String> sendMail(@RequestParam String email) {

        String resultado = mailService.sendPasswordRecoveryEmail(email);
        if (resultado.startsWith("Correo enviado")) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.badRequest().body(resultado);
        }
    }


    @GetMapping("/restablecer")
    public String mostrarFormularioRestablecer(@RequestParam("token") String token, Model model) {
        // Verificar si el token es válido
        Usuario usuario = usuarioService.obtenerUsuarioPorToken(token);
        if (usuario == null) {
            model.addAttribute("error", "El enlace de restablecimiento no es válido o ha expirado.");
            return "error"; // Página de error
        }

        // Imprimir el email para depuración
        System.out.println("Email para formulario: " + usuario.getEmail());

        model.addAttribute("email", usuario.getEmail());
        return "nuevaContrasegna";
    }

    @PostMapping("/restablecer")
    public String restablecerContrasegna(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            Model model) {

        System.out.println("Email recibido: " + email);
        System.out.println("Contraseñas: " + password + " / " + password2);

        if (!password.equals(password2)) {
            System.out.println("Las contraseñas no coinciden.");
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "nuevaContrasegna";
        }

        boolean actualizado = usuarioService.actualizarContrasegnaSinEncriptar(email, password);
        System.out.println("Contraseña actualizada: " + actualizado);

        if (!actualizado) {
            model.addAttribute("error", "No se pudo actualizar la contraseña.");
            return "nuevaContrasegna";
        }

        System.out.println("Redirigiendo a iniciar sesión...");
        return "redirect:/iniciarSesion";
    }



}