package br.com.emerion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.emerion.model.Instituicao;
import br.com.emerion.repository.InstituicaoRepository;

@Service
public class InstituicaoService {

	@Autowired
	private InstituicaoRepository repository;
	
	public List<Instituicao> getAll() {
		Iterable<Instituicao> findAll = repository.findAll();
		return (List<Instituicao>) findAll;
	}
	
	public Instituicao getBySigla(String sigla) {
		return repository.findBySigla(sigla);
	}
	
	public Instituicao save(Instituicao instituicao) {
		return repository.save(instituicao);
	}
}
