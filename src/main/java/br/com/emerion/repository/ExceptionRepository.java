package br.com.emerion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.emerion.model.ExceptionModel;

public interface ExceptionRepository extends CrudRepository<ExceptionModel, Integer> {

	@Query(value = "select e.* from exception_management e where e.unit_name ilike %?1%", nativeQuery = true)
	List<ExceptionModel> findExceptionByUnitName(String unitName);

	@Query(value = "select e.* from exception_management e where e.class_name ilike %?1%", nativeQuery = true)
	List<ExceptionModel> findExceptionByClassName(String className);

	@Query(value = "select e.* from exception_management e where e.component_name ilike %?1%", nativeQuery = true)
	List<ExceptionModel> findExceptionByComponentName(String componentName);
}
