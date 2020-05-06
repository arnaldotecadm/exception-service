package br.com.emerion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ExceptionDto {
	private Integer id;
	private String unitname;
	private String classname;
	private String componentname;
	private String linharelevante;
	private String stacktrace;
	private String detail;
}
