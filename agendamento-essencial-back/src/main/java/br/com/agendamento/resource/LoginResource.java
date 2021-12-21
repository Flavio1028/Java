package br.com.agendamento.resource;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agendamento.exception.LoginException;
import br.com.agendamento.service.UserService;
import br.com.agendamento.vo.TransferObject;
import br.com.agendamento.vo.forms.FormLogin;

@RestController
@RequestMapping(value = "/login")
public class LoginResource {

	@Autowired
	private UserService service;

	@CrossOrigin
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(HttpServletResponse response, @RequestBody FormLogin login) {
		try {
			return ResponseEntity.ok(this.service.login(login));
		} catch (LoginException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new TransferObject(HttpStatus.UNAUTHORIZED, e.getMessage(), Boolean.FALSE, null));
		} catch (Exception e) {
			return ResponseEntity.internalServerError()
					.body(new TransferObject(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), Boolean.FALSE, null));
		}
	}

}