package br.com.agendamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agendamento.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

}