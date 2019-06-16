package br.com.emerion.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InstituicaoDTO {
	private Integer id;
	private String entidade;
	private String logradouro;
	private String endereco;
	private String numero;
	private String municipio;
	private Integer codmunicipio;
	private String cep;
	private String uf;
	private Integer coduf;
	private String fundacao;
	private String curador;
	private String responsavel;
	private String ddd;
	private String telefone;
	private String website;
	private String sigla;
	private Integer natureza;
	private String anocriacao;
	private String anoabertura;
	private String diretor;
	private Integer situacao;
	private boolean tip01;
	private boolean tip02;
	private boolean tip03;
	private boolean tip04;
	private boolean tip05;
	private boolean tip06;
	private boolean tip07;
	private boolean tip08;
	private boolean tip09;
	private boolean tip10;
	private boolean tip11;
	private boolean tip12;
	private String cpfcnpj;
	private String cpfdir;
}
