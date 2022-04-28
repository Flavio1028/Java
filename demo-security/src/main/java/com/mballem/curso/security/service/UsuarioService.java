package com.mballem.curso.security.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.mballem.curso.security.datatables.Datatables;
import com.mballem.curso.security.datatables.DatatablesColunas;
import com.mballem.curso.security.domain.Perfil;
import com.mballem.curso.security.domain.Usuario;
import com.mballem.curso.security.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private Datatables datatables; 

	@Transactional(readOnly = true)
	public Usuario buscarPorEmail(String email) {
		return this.repository.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = this.buscarPorEmail(username);
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

}