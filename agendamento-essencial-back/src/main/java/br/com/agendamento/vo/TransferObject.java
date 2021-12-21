package br.com.agendamento.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class TransferObject {

	private Integer code;

	private String message;

	private Boolean success = Boolean.FALSE;

	private List<Object> objects = new ArrayList<>();

	public TransferObject() {

	}

	public TransferObject(HttpStatus status, String message, Boolean success, Object objects) {
		this.code = status.value();
		this.message = message;
		this.success = success;
		if (objects != null) {
			this.objects.add(objects);
		}
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public List<Object> getObjects() {
		return objects;
	}

	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}

	@Override
	public String toString() {
		return "TransferObject [code=" + code + ", message=" + message + ", success=" + success + ", objects=" + objects
				+ "]";
	}

}