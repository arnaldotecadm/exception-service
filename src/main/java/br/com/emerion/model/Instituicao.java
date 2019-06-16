package br.com.emerion.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Instituicao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303991188778805022L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "cpf_cnpj")
	private String cpfCnpj;

	private String nome;

	private String sigla;

	private String telefone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_fundacao")
	private Calendar dataFundacao;

	@Column(name = "home_page")
	private String homePage;

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
		Instituicao other = (Instituicao) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Instituicao [id=" + getId() + ", cpfCnpj=" + cpfCnpj + ", nome=" + nome + ", sigla=" + sigla
				+ ", telefone=" + telefone + ", dataFundacao=" + dataFundacao + ", homePage=" + homePage + "]";
	}

}
