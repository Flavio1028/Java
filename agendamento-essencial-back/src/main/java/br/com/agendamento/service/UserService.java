package br.com.agendamento.service;

import java.time.LocalDate;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agendamento.exception.LoginException;
import br.com.agendamento.model.User;
import br.com.agendamento.repository.UserRepository;
import br.com.agendamento.vo.TransferObject;
import br.com.agendamento.vo.forms.FormLogin;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public TransferObject login(FormLogin login) throws LoginException {

		TransferObject object = new TransferObject();

		// Find user by email from
		User user = this.repository.findByEmail(login.getEmail());
		if (user != null) {
			Boolean status = BCrypt.checkpw(login.getPassword(), user.getPassword());
			if (status) {
				object.getObjects().add(user);
				object.setSuccess(Boolean.TRUE);
				return object;
			}
		}

		throw new LoginException("Usuário/Senha invalido(s).");
	}
	
	public void save() {

		User user = new User();

		user.setName("Teste");
		user.setActive(Boolean.TRUE);
		user.setCreate(LocalDate.now());
		user.setPassword(BCrypt.hashpw("123", BCrypt.gensalt()));
		user.setEmail("teste@teste.com");

		this.repository.save(user);

	}

}