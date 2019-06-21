package br.com.emerion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.emerion.model.ExceptionModel;
import br.com.emerion.repository.ExceptionRepository;

@Service
public class ExceptionModelService {

	@Autowired
	private ExceptionRepository repository;
	
	public List<ExceptionModel> getAll() {
		return (List<ExceptionModel>) repository.findAll();
	}
	
	public ExceptionModel save(ExceptionModel exceptionModel) {
		return repository.save(exceptionModel);
	}
	
	public List<ExceptionModel> getExceptionByUnitName(String unitName){
		return repository.findExceptionByUnitName(unitName);
	}
	
	public List<ExceptionModel> getExceptionByClassName(String className){
		return repository.findExceptionByClassName(className);
	}
	
	public List<ExceptionModel> getExceptionByComponentName(String componentName){
		return repository.findExceptionByComponentName(componentName);
	}
}
