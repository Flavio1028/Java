package com.mballem.curso.security.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	// abrir pagina home
	@GetMapping({ "/", "/home" })
	public String home() {
		return "home";
	}

	// abrir pagina login
	@GetMapping({ "/login" })
	public String login() {
		return "login";
	}

	// login invalido
	@GetMapping({ "/login-error" })
	public String loginError(ModelMap model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String lastException = String.valueOf(session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION"));

		model.addAttribute("alerta", "erro");

		if (lastException.contains(SessionAuthenticationException.class.getName())) {
			model.addAttribute("titulo", "Acesso recusado!");
			model.addAttribute("texto", "Você já está logado em outro dispositivo.");
			model.addAttribute("subtexto", "Faça o logout ou espere sua sessão expirar.");
		} else {
			model.addAttribute("titulo", "Credenciais inválidas!");
			model.addAttribute("texto", "Login/Senha incorretos, tente novamente");
			model.addAttribute("subtexto", "Acesso permitidos apenas para cadastros já ativados.");
		}

		return "login";
	}

	@GetMapping({ "/acesso-negado" })
	public String acessoNegado(ModelMap model, HttpServletResponse resp) {
		model.addAttribute("status", resp.getStatus());
		model.addAttribute("error", "Acesso Negado");
		model.addAttribute("message", "Você não tem permissão para esta área ou ação.");
		return "error";
	}
	
	@GetMapping({ "/expired" })
	public String sessaoExpirada(ModelMap model, HttpServletRequest request) {
		model.addAttribute("alerta", "erro");
		model.addAttribute("titulo", "Acesso recusado!");
		model.addAttribute("texto", "Sua sessão expirou.");
		model.addAttribute("subtexto", "Um outro login foi detectado em sua conta.");

		return "login";
	}
	
}