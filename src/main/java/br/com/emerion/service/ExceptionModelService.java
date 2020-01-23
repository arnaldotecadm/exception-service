package br.com.emerion.service;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.emerion.model.ExceptionModel;
import br.com.emerion.repository.ExceptionRepository;

@Service
public class ExceptionModelService {

	@Autowired
	private ExceptionRepository repository;
	
	@Autowired
	EntityManager manager;

	public ExceptionModel getExceptionById(Integer id) {
		return repository.findById(id).orElse(null);
	}

	public List<ExceptionModel> getAll() {
		return (List<ExceptionModel>) repository.findAll();
	}

	public ExceptionModel save(ExceptionModel exceptionModel) {
		return repository.save(exceptionModel);
	}

	public List<ExceptionModel> getExceptionByUnitName(String unitName) {
		return repository.findExceptionByUnitName(unitName);
	}

	public List<ExceptionModel> getExceptionByClassName(String className) {
		return repository.findExceptionByClassName(className);
	}

	public List<ExceptionModel> getExceptionByComponentName(String componentName) {
		return repository.findExceptionByComponentName(componentName);
	}

	public List<ExceptionModel> getExceptionByImportantLine(String importantLine) {
		return repository.findExceptionByImportantLine(importantLine);
	}

	public List<ExceptionModel> getExceptionByDetail(String detail) {
		return repository.findExceptionByDetail(detail);
	}

	public List<ExceptionModel> getExceptionByVersionName(String versionName) {
		return repository.findExceptionByVersionName(versionName);
	}

	public List<ExceptionModel> getExceptionByAplicacao(UUID aplicacao) {
		return repository.findExceptionByAplicacao(aplicacao);
	}

	public List<ExceptionModel> getExceptionByVersionType(String versionType) {
		return repository.findExceptionByVersionType(versionType);
	}

	public List<ExceptionModel> getExceptionByGeneralFilter(ExceptionModel model) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from exception_management");
		sql.append(" where 1 = 1 ");
		
		if(model.getId() != null) {
			sql.append(" and id = " + model.getId());
		}
		
		if(model.getAplicacao() != null) {
			sql.append(" and aplicacao = '" + model.getAplicacao().toString() + "'");
		}
		
		if(model.getClassName() != null) {
			sql.append(" and class_name like '%" + model.getClassName() + "%'");
		}
		
		if(model.getDetail() != null) {
			sql.append(" and detail like '%" + model.getDetail() + "%'");
		}
		
		if(model.getComponentName() != null) {
			sql.append(" and component_name like '%" + model.getComponentName() + "%'");
		}
		
		if(model.getUnitName() != null) {
			sql.append(" and unit_name like '%" + model.getUnitName()  + "%'");
		}
		
		if(model.getVersionName() != null) {
			sql.append(" and version_name like '%" + model.getVersionName()  + "%'");
		}
		
		if(model.getVersionType() != null) {
			sql.append(" and tipo_versao like '%" + model.getVersionType()  + "%'");
		}
		
		Query query = manager.createNativeQuery(sql.toString(), ExceptionModel.class);
		
		return query.getResultList();
	}
}
