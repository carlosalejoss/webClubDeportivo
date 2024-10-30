package net.codejava.spring.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.codejava.spring.dao.Socio_DAO;
import net.codejava.spring.model.Socio_DTO;

@Controller
public class RegistrarseController {

	@Autowired
	private Socio_DAO socioDAO;

	@RequestMapping(value="/")
	public ModelAndView test(ModelAndView model) throws IOException{
		model.setViewName("home_nologin");
		return new ModelAndView("home_nologin");
		//return model;
	}

	@RequestMapping(value = "/newSocio", method = RequestMethod.GET)
	public ModelAndView newSocio(ModelAndView model) {
		Socio_DTO newSocio = new Socio_DTO();
		model.addObject("socio", newSocio);
		model.setViewName("home_login");
		return model;
	}

	@RequestMapping(value = "/saveSocio", method = RequestMethod.POST)
	public ModelAndView saveSocio(@ModelAttribute("socio") Socio_DTO socio) {
		socioDAO.insertarOactualizar(socio);
		return new ModelAndView("redirect:/home_login");
	}

	@RequestMapping(value = "/home_login", method = RequestMethod.GET)
	public ModelAndView homeLogin() throws IOException{
    	return new ModelAndView("home_login"); // nombre de la vista (home_login.jsp o home_login.html)
	}

	@RequestMapping(value = "/registrarse")
	public ModelAndView registrarse() throws IOException{
	    return new ModelAndView("registrarse"); // Asegúrate de que este archivo existe en /WEB-INF/views/
	}
}
