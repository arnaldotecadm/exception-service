package br.com.emerion.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class ExceptionModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 844985288237450152L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "message")
	private String message;

	@Column(columnDefinition = "TEXT", name = "stack_trace")
	private String stackTrace;

	private UUID aplicacao;

	@Column(name = "tipo_versao")
	private String versionType;

	@Column(name = "user_name")
	private String userName;

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

}