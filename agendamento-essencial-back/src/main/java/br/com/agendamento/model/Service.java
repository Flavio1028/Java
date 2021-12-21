package br.com.agendamento.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "services")
public class Service {

	@Id
	@Column(name = "id_services")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idService;

	private String name;

	private String description;

	private BigDecimal price;

	private LocalTime duration;

	@Column(name = "create_at")
	private LocalDate create;

	@Column(name = "update_at")
	private LocalDate update;

	@Column(name = "delete_at")
	private LocalDate delete;

	public Long getIdService() {
		return idService;
	}

	public void setIdService(Long idService) {
		this.idService = idService;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public LocalTime getDuration() {
		return duration;
	}

	public void setDuration(LocalTime duration) {
		this.duration = duration;
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
		return "Service [idService=" + idService + ", name=" + name + ", description=" + description + ", price="
				+ price + ", duration=" + duration + ", create=" + create + ", update=" + update + ", delete=" + delete
				+ "]";
	}

}