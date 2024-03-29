package com.mballem.curso.security.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.curso.security.datatables.Datatables;
import com.mballem.curso.security.datatables.DatatablesColunas;
import com.mballem.curso.security.domain.Agendamento;
import com.mballem.curso.security.domain.Horario;
import com.mballem.curso.security.exception.AcessoNegadoException;
import com.mballem.curso.security.repository.AgendamentoRepository;
import com.mballem.curso.security.repository.projection.HistoricoPaciente;

@Service
public class AgendamentoService {

	@Autowired
	private AgendamentoRepository repository;
	
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = true)
	public List<Horario> buscarHorariosNaoAgendadosPorMedicoIdEData(Long id, LocalDate data) {
		return this.repository.findByMedicoIdAndDataNotHorarioAgendado(id, data);
	}

	@Transactional(readOnly = false)
	public void salvar(Agendamento agendamento) {
		this.repository.save(agendamento);
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarHistoricoPorPacienteEmail(String email, HttpServletRequest request) {

		this.datatables.setRequest(request);
		this.datatables.setColunas(DatatablesColunas.AGENDAMENTOS);

		Page<HistoricoPaciente> page = this.repository.findHistoricoByPacienteEmail(email, this.datatables.getPageable());

		return this.datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarHistoricoPorMedicoEmail(String email, HttpServletRequest request) {

		this.datatables.setRequest(request);
		this.datatables.setColunas(DatatablesColunas.AGENDAMENTOS);

		Page<HistoricoPaciente> page = this.repository.findHistoricoByMedicoEmail(email, this.datatables.getPageable());

		return this.datatables.getResponse(page);
	}
	
	@Transactional(readOnly = true)
	public Agendamento buscarPorId(Long id) {
		return this.repository.findById(id).get();
	}
	
	@Transactional(readOnly = false)
	public void editar(Agendamento agendamento, String email) {

		Agendamento ag = this.buscarPorIdEUsuario(agendamento.getId(), email);
		ag.setDataConsulta(agendamento.getDataConsulta());
		ag.setEspecialidade(agendamento.getEspecialidade());
		ag.setHorario(agendamento.getHorario());
		ag.setMedico(agendamento.getMedico());

	}
	
	@Transactional(readOnly = true)
	public Agendamento buscarPorIdEUsuario(Long id, String email) {
		return this.repository.findByIdAndPacienteOrMedicoEmail(id, email)
				.orElseThrow(() -> new AcessoNegadoException("Acesso negado ao usuário: " + email));
	}
	
	@Transactional(readOnly = false)
	public void remover(Long id) {
		this.repository.deleteById(id);
	}

}