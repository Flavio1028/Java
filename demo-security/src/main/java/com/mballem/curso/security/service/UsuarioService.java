package com.mballem.curso.security.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import com.mballem.curso.security.datatables.Datatables;
import com.mballem.curso.security.datatables.DatatablesColunas;
import com.mballem.curso.security.domain.Perfil;
import com.mballem.curso.security.domain.PerfilTipo;
import com.mballem.curso.security.domain.Usuario;
import com.mballem.curso.security.exception.AcessoNegadoException;
import com.mballem.curso.security.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private Datatables datatables; 
	
	@Autowired
	private EmailService emailService;
	
	@Transactional(readOnly = true)
	public Usuario buscarPorEmail(String email) {
		return this.repository.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = this.buscarPorEmailEAtivo(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " não encontrado."));
		return new User(
				usuario.getEmail(),
				usuario.getSenha(),
				AuthorityUtils.createAuthorityList(this.getAuthorities(usuario.getPerfis()))
				);
	}
	
	// Converte o perfil
	private String[] getAuthorities(List<Perfil> perfis) {
		
		String[] authorities = new String[perfis.size()];
		
		for (int index = 0; index < perfis.size(); index++) {
			authorities[index] = perfis.get(index).getDesc();
		}
		
		return authorities;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarTodos(HttpServletRequest request) {
		this.datatables.setRequest(request);
		this.datatables.setColunas(DatatablesColunas.USUARIOS);
		
		Page<Usuario> page = this.datatables.getSearch().isEmpty()
				? this.repository.findAll(this.datatables.getPageable())
				: this.repository.findByEmailOrPerfil(this.datatables.getSearch(), this.datatables.getPageable());
		
		return this.datatables.getResponse(page);
	}

	@Transactional(readOnly = false)
	public void salvarUsuario(Usuario usuario) {

		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);

		this.repository.save(usuario);
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return this.repository.findById(id).get();
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorIdEPerfis(Long usuarioId, Long[] perfisId) {
		return this.repository.findByIdAndPerfis(usuarioId, perfisId)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário inexistente!"));
	}

	public static boolean isSenhaCorreta(String senhaDigitada, String senhaArmazenada) {
		return new BCryptPasswordEncoder().matches(senhaDigitada, senhaArmazenada);
	}

	@Transactional(readOnly = false)
	public void alterarSenha(Usuario usuario, String senha) {
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
		this.repository.save(usuario);
	}
	
	@Transactional(readOnly = false)
	public void salvarCadastroPaciente(Usuario usuario) throws MessagingException {

		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		usuario.addPerfil(PerfilTipo.PACIENTE);

		this.repository.save(usuario);
		
		this.emailDeConfirmacaoDeCadastro(usuario.getEmail());
		
	}
	
	@Transactional(readOnly = true)
	private Optional<Usuario> buscarPorEmailEAtivo(String email) {

		return this.repository.findByEmailAndAtivo(email);
	}
	
	public void emailDeConfirmacaoDeCadastro(String email) throws MessagingException {

		String codigo = Base64Utils.encodeToString(email.getBytes());
		this.emailService.enviarPedidoDeConfirmacaoDeCadastro(email, codigo);

	}
	
	@Transactional(readOnly = false)
	public void ativarCadastroPaciente(String codigo) {

		String email = new String(Base64Utils.decodeFromString(codigo));

		Usuario usuario = this.buscarPorEmail(email);

		if (usuario.hasNotId()) {
			throw new AcessoNegadoException("Não foi possivel ativar o seu cadastro");
		}
		usuario.setAtivo(true);
	}
	
	@Transactional(readOnly = false)
	public void pedidoRedefinicaoSenha(String email) throws MessagingException {

		Usuario usuario = this.buscarPorEmailEAtivo(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario " + email + " não encontrado."));

		String verificador = RandomStringUtils.randomAlphanumeric(6);
		usuario.setCodigoVerificador(verificador);
		
		this.emailService.enviarPedidoRedefinicaoSenha(email, verificador);

	}
	
}