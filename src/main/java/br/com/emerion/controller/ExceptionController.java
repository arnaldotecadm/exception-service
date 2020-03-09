package br.com.emerion.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.emerion.enums.EnumException;
import br.com.emerion.enums.EnumTipoVersao;
import br.com.emerion.exception.ValidationExceptionWithErrors;
import br.com.emerion.model.ExceptionModel;
import br.com.emerion.service.ExceptionModelService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping()
public class ExceptionController {

	@Autowired
	private ExceptionModelService service;

	@GetMapping(path = "/all")
	public List<ExceptionModel> getAll() {
		return service.getAll();
	}

	@GetMapping(path = "/filter/byField/id/{id}")
	public ExceptionModel getById(@PathVariable("id") Integer id) {
		return service.getExceptionById(id);
	}

	@GetMapping(path = "/filter/byField/unit_name/{unitName}")
	public List<ExceptionModel> getByUnit(@PathVariable("unitName") String unitName) {
		return service.getExceptionByUnitName(unitName);
	}

	@GetMapping(path = "/filter/byField/class_name/{className}")
	public List<ExceptionModel> getByClass(@PathVariable("className") String className) {
		return service.getExceptionByClassName(className);
	}

	@GetMapping(path = "/filter/byField/component_name/{componentName}")
	public List<ExceptionModel> getByComponent(@PathVariable("componentName") String componentName) {
		return service.getExceptionByComponentName(componentName);
	}

	@GetMapping(path = "/filter/byField/important_line/{importantLine}")
	public List<ExceptionModel> getByImportantLine(@PathVariable("importantLine") String importantLine) {
		return service.getExceptionByImportantLine(importantLine);
	}

	@GetMapping(path = "/filter/byField/detail/{detail}")
	public List<ExceptionModel> getByDetail(@PathVariable("detail") String detail) {
		return service.getExceptionByDetail(detail);
	}

	@GetMapping(path = "/filter/byField/version_name/{versionName}")
	public List<ExceptionModel> getByVersionName(@PathVariable("versionName") String versionName) {
		return service.getExceptionByVersionName(versionName);
	}

	@GetMapping(path = "/filter/general", consumes = "application/json")
	public List<ExceptionModel> getByGeneralFields(@RequestParam("object") String json)
			throws JsonParseException, JsonMappingException, IOException {
		ExceptionModel model = new ExceptionModel();
		ObjectMapper mapper = new ObjectMapper();
		model = mapper.readValue(json, ExceptionModel.class);

		return service.getExceptionByGeneralFilter(model);
	}

	@GetMapping(path = "/filter/byField/aplicacao/{aplicacao}")
	public List<ExceptionModel> getByAplicacao(@PathVariable("aplicacao") String aplicacao) {

		try {
			UUID uuid = UUID.fromString(aplicacao);
			return service.getExceptionByAplicacao(uuid);
		} catch (Exception e) {
			return null;
		}

	}

	@GetMapping(path = "/filter/byField/version_type/{versionType}")
	public List<ExceptionModel> getByVersionType(@PathVariable("versionType") String versionType) {
		return service.getExceptionByVersionType(versionType);
	}

	@PostMapping(path = "/add")
	public ResponseEntity<String> addNew(@RequestParam Map<String, Object> allRequestParams) throws IOException {

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

		ExceptionModel exceptionModel = new ExceptionModel();

		if (StringUtils.isEmpty(allRequestParams.get("message"))) {
			throw new ValidationExceptionWithErrors(EnumException.MESSAGE_FIELD_NOT_INFORMED);
		} else {
			exceptionModel.setMessage(allRequestParams.get("message").toString());
		}

		if (StringUtils.isEmpty(allRequestParams.get("stack_trace"))) {
			throw new ValidationExceptionWithErrors(EnumException.STACKTRACE_FIELD_NOT_INFORMED);
		} else {
			exceptionModel.setStackTrace(allRequestParams.get("stack_trace").toString());
		}

		if (StringUtils.isEmpty(allRequestParams.get("application"))) {
			throw new ValidationExceptionWithErrors(EnumException.UUID_FIELD_NOT_INFORMED);
		} else {
			try {
				UUID.fromString(allRequestParams.get("application").toString());
			} catch (Exception ex) {
				throw new ValidationExceptionWithErrors(EnumException.UUID_MALFORMED);
			}
			exceptionModel.setAplicacao(UUID.fromString(allRequestParams.get("application").toString()));
		}

		if (StringUtils.isEmpty(allRequestParams.get("user_name"))) {
			throw new ValidationExceptionWithErrors(EnumException.USER_NAME_FIELD_NOT_INFORMED);
		} else {
			exceptionModel.setUserName(allRequestParams.get("user_name").toString());
		}

		if (StringUtils.isEmpty(allRequestParams.get("tipo_versao"))) {
			throw new ValidationExceptionWithErrors(EnumException.VERSION_TYPE_FIELD_NOT_INFORMED);
		} else {
			exceptionModel.setVersionType(allRequestParams.get("tipo_versao").toString());
			boolean existe = false;
			for (EnumTipoVersao tv : EnumTipoVersao.values()) {
				if (tv.toString().equals(allRequestParams.get("tipo_versao").toString())) {
					existe = true;
					break;
				}
			}
			if (!existe) {
				throw new ValidationExceptionWithErrors(EnumException.VERSION_TYPE_NOT_VALID);
			}
		}

		try {
			service.save(exceptionModel);
		} catch (Exception e) {
			throw new ValidationExceptionWithErrors(EnumException.UNKNOWN_ERROR);
		}

		return new ResponseEntity<String>("{ \"message\" : \"Registro salvo com Sucesso\"}", HttpStatus.OK);
	}

}