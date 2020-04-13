package br.com.emerion.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.emerion.model.ExceptionModel;
import br.com.emerion.model.ExceptionSummary;
import br.com.emerion.model.GraphGRouppedByType;
import br.com.emerion.model.GraphModel;
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

	public Page<ExceptionModel> getAll(Pageable paginacao) {
		return repository.findAll(paginacao);
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

		if (model.getId() != null) {
			sql.append(" and id = " + model.getId());
		}

		if (model.getAplicacao() != null) {
			sql.append(" and aplicacao = '" + model.getAplicacao().toString() + "'");
		}

		if (model.getVersionType() != null) {
			sql.append(" and tipo_versao like '%" + model.getVersionType() + "%'");
		}

		Query query = manager.createNativeQuery(sql.toString(), ExceptionModel.class);

		return query.getResultList();
	}

	public List<GraphModel> getGrouppedByWeek(String application, String exceptionType) {
		String sql = "select cast(date_part('year', cast(data_excecao as date)) as int) as year,"
				+ "      cast(date_part('month', cast(data_excecao as date)) as int) as month, cast(count(1) as int) from asoft.exception_management em where em.classe_excecao = '"
				+ exceptionType + "'"
				+ " and aplicacao = '" + application + "' "
				+ " GROUP BY year, month " + " order BY year, month ";

		Query grouppedByMonth = this.manager.createNativeQuery(sql).unwrap(NativeQuery.class)
				.setResultTransformer(Transformers.aliasToBean(GraphModel.class));

		return (List<GraphModel>) grouppedByMonth.getResultList();
	}

	public List<String> getTopTrendExceptionName(String application, int timePast) {
		String sql = " select classe_excecao,  count(1)  from asoft.exception_management em " + " where 1 = 1 "
				+ " and aplicacao = '" + application + "' "
				+ "	 and date_part('year', cast(data_excecao as date)) = date_part('year', cast(now() as date)) "
				+ "	 and date_part('week', cast(data_excecao as date)) >= date_part('week', cast(now() as date)) - "
				+ ((timePast > 0) ? timePast : "0") + " group by classe_excecao";

		Query grouppedByMonth = this.manager.createNativeQuery(sql);

		List<String> exceptionTypes = new ArrayList<String>();
		List<?> resultList = grouppedByMonth.getResultList();

		if (resultList != null) {
			for (Object obj : resultList) {
				exceptionTypes.add(((Object[]) obj)[0].toString());
			}
		}

		return exceptionTypes;
	}

	public List<GraphModel> getTopTrendExceptionDetail(String application, int timePast, String exceptionType) {
		String sql = "select " + "	   cast(date_part('year', cast(data_excecao as date)) as int) as year, "
				+ "	   cast(date_part('month', cast(data_excecao as date)) as int) as month, "
				+ "	   cast(date_part('day', cast(data_excecao as date)) as int) as day, "
				+ "	   cast(count(1) as int) " + " from asoft.exception_management em " + " where 1 = 1 "
				+ " and aplicacao = '" + application + "' "
				+ "	and date_part('year', cast(data_excecao as date)) = date_part('year', cast(now() as date))"
				+ "	and date_part('week', cast(data_excecao as date)) >= date_part('week', cast(now() as date))-"
				+ ((timePast > 0) ? timePast : "0")
				+ ((exceptionType != null) ? " and em.classe_excecao = '" + exceptionType + "' " : "")
				+ " and cast(data_excecao as date) <= cast(now() as date) "
				+ " group by classe_excecao, year, month, day";
		Query grouppedByMonth = this.manager.createNativeQuery(sql).unwrap(NativeQuery.class)
				.setResultTransformer(Transformers.aliasToBean(GraphModel.class));

		return (List<GraphModel>) grouppedByMonth.getResultList();
	}

	public List<GraphGRouppedByType> getPercentageException(String application) {
		List<GraphGRouppedByType> lista = new ArrayList<>();

		String sql = " select classe_excecao, count(1) from asoft.exception_management em "
				+ " where aplicacao = '" + application + "' "
				+ " group by classe_excecao " + " order by 2 desc ";
		Query query = this.manager.createNativeQuery(sql);
		List<Object[]> resultList = query.getResultList();
		if (resultList != null) {
			for (Object[] obj : resultList) {
				lista.add(new GraphGRouppedByType(obj[0].toString(), Integer.parseInt(obj[1].toString())));
			}
		}

		return lista;
	}

	public List<ExceptionSummary> getTotalException() {
		List<ExceptionSummary> summarylist = new ArrayList<>();
		String sql = " select cast(em.aplicacao as varchar),sum(1) from asoft.exception_management em group by em.aplicacao ";
		Query query = this.manager.createNativeQuery(sql);
		List<Object[]> objList = (List<Object[]>) query.getResultList();
		if (objList != null) {
			for (Object[] obj : objList) {
				summarylist.add(
						new ExceptionSummary(Integer.parseInt(obj[1].toString()), UUID.fromString(obj[0].toString())));
			}
		}

		return summarylist;
	}

	public List<String> getAllExceptionTypesByMonth(String application, int typeLimit) {
		String sql = "select classe_excecao, count(1) from asoft.exception_management em " + " where aplicacao = '"
				+ application + "' " + " group by classe_excecao " + " order by 2 desc ";
		sql += typeLimit > 0 ? (" limit " + typeLimit) : "";

		Query grouppedByMonth = this.manager.createNativeQuery(sql);

		List<String> exceptionTypes = new ArrayList<String>();
		List<?> resultList = grouppedByMonth.getResultList();

		if (resultList != null) {
			for (Object obj : resultList) {
				exceptionTypes.add(((Object[]) obj)[0].toString());
			}
		}

		return exceptionTypes;
	}
}
