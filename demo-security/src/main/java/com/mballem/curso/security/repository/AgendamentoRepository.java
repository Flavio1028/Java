package com.mballem.curso.security.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mballem.curso.security.domain.Agendamento;
import com.mballem.curso.security.domain.Horario;
import com.mballem.curso.security.repository.projection.HistoricoPaciente;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

	@Query("SELECT h "
			+ "FROM Horario h "
			+ "WHERE NOT EXISTS( "
				+ "SELECT a.horario.id "
				+ "FROM Agendamento a "
				+ "WHERE "
				+ "a.medico.id = :id AND "
				+ "a.dataConsulta = :data AND "
				+ "a.horario.id = h.id"
			+ ") "
			+ " ORDER BY h.horaMinuto ASC")
	public List<Horario> findByMedicoIdAndDataNotHorarioAgendado(Long id, LocalDate data);

	@Query("SELECT a.id as id, "
			+ "a.paciente as paciente, "
			+ "CONCAT(a.dataConsulta, ' ', a.horario.horaMinuto) as dataConsulta, "
			+ "a.medico as medico, "
			+ "a.especialidade as especialidade "
			+ "FROM Agendamento a "
			+ "WHERE a.paciente.usuario.email LIKE :email")
	public Page<HistoricoPaciente> findHistoricoByPacienteEmail(String email, Pageable pageable);
	
	@Query("SELECT a.id as id, "
			+ "a.paciente as paciente, "
			+ "CONCAT(a.dataConsulta, ' ', a.horario.horaMinuto) as dataConsulta, "
			+ "a.medico as medico, "
			+ "a.especialidade as especialidade "
			+ "FROM Agendamento a "
			+ "WHERE a.medico.usuario.email LIKE :email")
	public Page<HistoricoPaciente> findHistoricoByMedicoEmail(String email, Pageable pageable);
	
	@Query("SELECT a FROM Agendamento a WHERE  " 
			+ "( a.id = :id AND a.paciente.usuario.email LIKE :email) "
			+ " OR ( a.id = :id AND a.medico.usuario.email LIKE :email )")
	public Optional<Agendamento> findByIdAndPacienteOrMedicoEmail(Long id, String email);

}