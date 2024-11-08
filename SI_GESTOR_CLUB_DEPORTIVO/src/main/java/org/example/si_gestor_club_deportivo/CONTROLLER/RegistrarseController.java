package org.example.si_gestor_club_deportivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.example.si_gestor_club_deportivo.repository.SocioRepository;
import org.example.si_gestor_club_deportivo.dto.SocioDTO;

@Controller
public class RegistrarseController {

	@Autowired
	private SocioRepository socioDAO;

	/*@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView homeNoLogin(ModelAndView model) {
		model.setViewName("homeNoLogin");
		return model;
	}*/

	@GetMapping("/")
	public String homeNoLogin() {
		// Retornar la vista donde se mostrará la noticia con las imágenes
		return "home_nologin";  // Vista que mostrará la noticia con las imágenes
	}

	@RequestMapping(value = "/newSocio", method = RequestMethod.GET)
	public ModelAndView newSocio(ModelAndView model) {
		SocioDTO newSocio = new SocioDTO();
		model.addObject("socio", newSocio);
		model.setViewName("home_login");
		return model;
	}

	@RequestMapping(value = "/saveSocio", method = RequestMethod.POST)
	public ModelAndView saveSocio(@ModelAttribute("socio") SocioDTO socio) {
		socioDAO.insertarOactualizar(socio);
		return new ModelAndView("redirect:/home_login");
	}

	@RequestMapping(value = "/home_login", method = RequestMethod.GET)
	public ModelAndView homeLogin(){
    	return new ModelAndView("home_login"); // nombre de la vista (home_login.jsp o home_login.html)
	}

	@RequestMapping(value = "/registrarse")
	public ModelAndView registrarse(){
	    return new ModelAndView("registrarse"); // Asegúrate de que este archivo existe en /WEB-INF/views/
	}
}
