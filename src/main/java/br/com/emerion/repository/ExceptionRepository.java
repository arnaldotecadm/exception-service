package br.com.emerion.repository;

import java.util.List;
import java.util.UUID;

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

	@Query(value = "select e.* from exception_management e where e.important_line ilike %?1%", nativeQuery = true)
	List<ExceptionModel> findExceptionByImportantLine(String importantLine);

	@Query(value = "select e.* from exception_management e where e.detail ilike %?1%", nativeQuery = true)
	List<ExceptionModel> findExceptionByDetail(String detail);

	@Query(value = "select e.* from exception_management e where e.version_name ilike %?1%", nativeQuery = true)
	List<ExceptionModel> findExceptionByVersionName(String versionName);

	@Query(value = "select e.* from exception_management e where e.aplicacao = ?1", nativeQuery = true)
	List<ExceptionModel> findExceptionByAplicacao(UUID aplicacao);

	@Query(value = "select e.* from exception_management e where e.tipo_versao ilike %?1%", nativeQuery = true)
	List<ExceptionModel> findExceptionByVersionType(String tipoVersao);
}
