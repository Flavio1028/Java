package br.com.agendamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agendamento.model.UserTokens;

@Repository
public interface UserTokensRepository extends JpaRepository<UserTokens, Long> {

}