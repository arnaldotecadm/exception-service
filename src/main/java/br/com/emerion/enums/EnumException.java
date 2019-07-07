package br.com.emerion.enums;

import org.springframework.http.HttpStatus;

public enum EnumException {

	UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error found."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "Operação não permitida."),
	ITEM_CREATED(HttpStatus.CREATED, "Item inserido com sucesso."),
	COMPANY_NOT_FOUND(HttpStatus.FORBIDDEN, "Instituição não informada."),
	
	VERSION_FIELD_NOT_INFORMED(HttpStatus.FORBIDDEN, "Versão não informada"),
	UUID_FIELD_NOT_INFORMED(HttpStatus.FORBIDDEN, "Sistema não informado"),
	VERSION_TYPE_FIELD_NOT_INFORMED(HttpStatus.FORBIDDEN, "Tipo de Versão não informada"),
	VERSION_TYPE_NOT_VALID(HttpStatus.FORBIDDEN, "Tipo de versão informada não é valido");

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
