package br.com.agendamento.vo.forms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FormLogin {

	@JsonProperty("email")
	private String email;

	@JsonProperty("password")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "FormLogin [email=" + email + ", password=" + password + "]";
	}

}