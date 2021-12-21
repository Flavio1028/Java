package br.com.agendamento.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_tokens")
public class UserTokens {

	@Id
	@Column(name = "id_user_tokens")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUserToken;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(columnDefinition = "idUser", insertable = false, updatable = false)
	private User users;

	private String token;

	@Column(name = "st_expires")
	private LocalDate stExpires;

	@Column(name = "create_at")
	private LocalDate create;

	@Column(name = "update_at")
	private LocalDate update;

	@Column(name = "delete_at")
	private LocalDate delete;

	public Long getIdUserToken() {
		return idUserToken;
	}

	public void setIdUserToken(Long idUserToken) {
		this.idUserToken = idUserToken;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDate getStExpires() {
		return stExpires;
	}

	public void setStExpires(LocalDate stExpires) {
		this.stExpires = stExpires;
	}

	public LocalDate getCreate() {
		return create;
	}

	public void setCreate(LocalDate create) {
		this.create = create;
	}

	public LocalDate getUpdate() {
		return update;
	}

	public void setUpdate(LocalDate update) {
		this.update = update;
	}

	public LocalDate getDelete() {
		return delete;
	}

	public void setDelete(LocalDate delete) {
		this.delete = delete;
	}

	@Override
	public String toString() {
		return "UserTokens [idUserToken=" + idUserToken + ", users=" + users + ", token=" + token + ", stExpires="
				+ stExpires + ", create=" + create + ", update=" + update + ", delete=" + delete + "]";
	}

}