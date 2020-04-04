package br.com.emerion.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.emerion.enums.EnumExceptionSeverity;
import br.com.emerion.enums.EnumTipoVersao;
import io.swagger.annotations.ApiModelProperty;
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
@Entity(name = "exception_management")
public class ExceptionModel implements Serializable, Comparable<ExceptionModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 844985288237450152L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Integer id;

	@Column(columnDefinition = "TEXT", name = "message")
	private String message;

	@Column(columnDefinition = "TEXT", name = "stack_trace")
	@ApiModelProperty(name = "stack_trace")
	private String stackTrace;

	private UUID aplicacao;

	@Column(name = "tipo_versao")
	@ApiModelProperty(allowableValues = EnumTipoVersao.ENUMERADO_SWAGGER, name = "tipo_versao")
	private String versionType;

	@Column(name = "numero_versao")
	private String numerVersao;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "classe_excecao")
	private String classExcecao;

	@Column(columnDefinition = "TEXT", name = "wiki_how")
	@ApiModelProperty(value = "Se disponível, informar neste campo uma possível maneira de solucionar o problema")
	private String wikiHow;

	@ApiModelProperty(allowableValues = EnumExceptionSeverity.ENUMERADO_SWAGGER)
	private String serverity;

	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(hidden = true)
	private Calendar dataExcecao = Calendar.getInstance();

	@Column(name = "campo_controle")
	@ApiModelProperty(value = "Campo de controle por parte do usuario, campo de digitação livre apenas para controles internos.")
	private String campoControle;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExceptionModel other = (ExceptionModel) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(ExceptionModel exceptionModel) {
		return this.getId().compareTo(exceptionModel.getId());
	}

}
