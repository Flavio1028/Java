package com.mballem.curso.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mballem.curso.security.domain.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

	@Query("select m from Medico m where m.usuario.id = :id")
	public Optional<Medico> findByUsuarioId(Long id);

	@Query("SELECT m FROM Medico m WHERE m.usuario.email LIKE :email")
	public Optional<Medico> findByUsuarioEmail(String email);
	
	@Query("SELECT DISTINCT m FROM Medico m "
			+ "join m.especialidades e "
			+ "WHERE e.titulo LIKE :titulo% "
			+ "AND m.usuario.ativo = true")
	public List<Medico> findByMedicosPorEspecialidade(String titulo);
	
	@Query("SELECT m.id FROM Medico m "
			+ "JOIN m.especialidades e "
			+ "JOIN m.agendamentos a "
			+ "WHERE a.especialidade.id = :idEsp AND a.medico.id = :idMed ")
	public Optional<Long> hasEspecialidadeAgendada(Long idMed, Long idEsp);
}
