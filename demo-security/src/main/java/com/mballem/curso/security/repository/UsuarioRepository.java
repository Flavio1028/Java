package com.mballem.curso.security.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mballem.curso.security.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query("SELECT u FROM Usuario u WHERE u.email LIKE :email")
	public Usuario findByEmail(@Param("email") String email);
	
	@Query("SELECT DISTINCT u FROM Usuario u "
			+ "join u.perfis p "
			+ "WHERE u.email LIKE :search% OR p.desc LIKE :search% ")
	public Page<Usuario> findByEmailOrPerfil(String search, Pageable pageable);

	@Query("SELECT DISTINCT u FROM Usuario u "
			+ "join u.perfis p "
			+ "WHERE u.id = :usuarioId AND p.id IN :perfisId ")
	public Optional<Usuario> findByIdAndPerfis(Long usuarioId, Long[] perfisId);

	@Query("SELECT u FROM Usuario u WHERE u.email LIKE :email AND u.ativo = true")
	public Optional<Usuario> findByEmailAndAtivo(String email);
	
}