package com.mballem.curso.security.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mballem.curso.security.domain.Especialidade;

@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

	@Query("SELECT e FROM Especialidade e WHERE e.titulo LIKE :search%")
	public Page<Especialidade> findAllByTitulo(String search, Pageable pageable);

	@Query("SELECT e.titulo FROM Especialidade e WHERE e.titulo LIKE :termo% ")
	public List<String> findEspecialidadesByTermo(String termo);
	
	@Query("SELECT e FROM Especialidade e WHERE e.titulo IN :titulos")
	public Set<Especialidade> findByTitulos(String[] titulos);
	
	@Query("SELECT e FROM Especialidade e "
			+ "join e.medicos m "
			+ "WHERE m.id = :id ")
	public Page<Especialidade> findByIdMedico(Long id, Pageable pageable);

}