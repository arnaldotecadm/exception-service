package br.com.emerion.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.emerion.model.Instituicao;

public interface InstituicaoRepository extends CrudRepository<Instituicao, Integer>{

	Instituicao findByNome(String nome);
	Instituicao findBySigla(String sigla);
}
