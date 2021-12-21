package br.com.agendamento.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_address")
public class UserAddress {

	@Id
	@Column(name = "id_user_address")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUserAddress;

	private Long idUser;

	private String street;

	private Integer number;

	private String complement;

	private String district;

	private String city;

	@Column(name = "zip_code")
	private Integer zipCode;

	@Column(name = "create_at")
	private LocalDate create;

	@Column(name = "update_at")
	private LocalDate update;

	@Column(name = "delete_at")
	private LocalDate delete;

	public Long getIdUserAddress() {
		return idUserAddress;
	}

	public void setIdUserAddress(Long idUserAddress) {
		this.idUserAddress = idUserAddress;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
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
		return "UserAddress [idUserAddress=" + idUserAddress + ", street=" + street + ", number=" + number
				+ ", complement=" + complement + ", district=" + district + ", city=" + city + ", zipCode=" + zipCode
				+ ", create=" + create + ", update=" + update + ", delete=" + delete + "]";
	}

}