package br.com.emerion.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.emerion.model.ExceptionModel;

public interface ExceptionRepository extends CrudRepository<ExceptionModel, Integer>{
}
