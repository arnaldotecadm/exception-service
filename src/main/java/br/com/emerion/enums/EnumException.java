package br.com.emerion.enums;

import org.springframework.http.HttpStatus;

public enum EnumException {

	UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error found."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "Opera√ß√£o n√£o permitida."),
	ITEM_CREATED(HttpStatus.CREATED, "Item inserido com sucesso."),
	COMPANY_NOT_FOUND(HttpStatus.FORBIDDEN, "Institui√ß√£o n√£o informada."),
	
	VERSION_FIELD_NOT_INFORMED(HttpStatus.FORBIDDEN, "Vers„o n„o informada"),
	UUID_FIELD_NOT_INFORMED(HttpStatus.FORBIDDEN, "Sistema n„o informado"),
	VERSION_TYPE_FIELD_NOT_INFORMED(HttpStatus.FORBIDDEN, "Tipo de Vers„o n„o informada"),
	VERSION_TYPE_NOT_VALID(HttpStatus.FORBIDDEN, "Tipo de vers„o informada n„o È valido");

	private HttpStatus httpStatus;
	private String description;

	EnumException(HttpStatus httpStatus, String description) {
		this.httpStatus = httpStatus;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
