package br.com.agendamento.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_details")
public class UserDetails {

	@Id
	@Column(name = "id_user_details")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUserDetails;

	private Long idUser;

	private String cpf;

	private LocalDate birth;

	private String phone;

	private String celphone;

	private String gender;

	@Column(name = "create_at")
	private LocalDate create;

	@Column(name = "update_at")
	private LocalDate update;

	@Column(name = "delete_at")
	private LocalDate delete;

	public Long getIdUserDetails() {
		return idUserDetails;
	}

	public void setIdUserDetails(Long idUserDetails) {
		this.idUserDetails = idUserDetails;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCelphone() {
		return celphone;
	}

	public void setCelphone(String celphone) {
		this.celphone = celphone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
		return "UserDetails [idUserDetails=" + idUserDetails + ", cpf=" + cpf + ", birth=" + birth + ", phone=" + phone
				+ ", celphone=" + celphone + ", gender=" + gender + ", create=" + create + ", update=" + update
				+ ", delete=" + delete + "]";
	}

}