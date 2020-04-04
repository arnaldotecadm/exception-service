package br.com.emerion.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.emerion.enums.EnumExceptionSeverity;
import br.com.emerion.enums.EnumTipoVersao;
import br.com.emerion.exception.ValidationExceptionWithErrors;
import br.com.emerion.model.ExceptionModel;
import br.com.emerion.service.ExceptionModelService;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping()
public class ExceptionController {

	@Autowired
	private ExceptionModelService service;

	@ApiOperation(value = "Recuperar todas as excecoes cadastradas no sistema.")
	@GetMapping(path = "/all")
	public List<ExceptionModel> getAll() {
		return service.getAll();
	}

	@GetMapping(path = "/filter/byField/id/{id}")
	public ExceptionModel getById(@PathVariable("id") Integer id) {
		return service.getExceptionById(id);
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
	@ApiOperation(value = "Adicionar nova excecao no sistema")
	public ResponseEntity<String> addNew(
			@RequestParam("message") String message,
			@RequestParam("stack_trace") String stackTrace, 
			@RequestParam("application") Optional<UUID> application,
			@RequestParam("user_name") Optional<String> userName,
			@RequestParam("tipo_versao") Optional<String> tipoVersao,
			@RequestParam("campo_controle") Optional<String> campoControle,
			@RequestParam("class_excecao") Optional<String> classExcecao,
			@RequestParam("numerp_versao") Optional<String> numeroVersao,
			@RequestParam("severity") Optional<String> severity, 
			@RequestParam("wiki_how") Optional<String> wikiHow)
			throws IOException {

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

		ExceptionModel exceptionModel = new ExceptionModel();

		exceptionModel.setMessage(message);
		exceptionModel.setStackTrace(stackTrace);

		if (application.isPresent()) {
			exceptionModel.setAplicacao(application.get());
		}

		if (userName.isPresent()) {
			exceptionModel.setUserName(userName.get());
		}

		if (tipoVersao.isPresent()) {

			exceptionModel.setVersionType(tipoVersao.get());
			boolean existe = false;
			for (EnumTipoVersao tv : EnumTipoVersao.values()) {
				if (tv.toString().equals(tipoVersao.get())) {
					existe = true;
					break;
				}
			}
			if (!existe) {
				throw new ValidationExceptionWithErrors(EnumException.VERSION_TYPE_NOT_VALID);
			}
		}
		
		if(campoControle.isPresent()) {
			exceptionModel.setCampoControle(campoControle.get());
		}
		if(classExcecao.isPresent()) {
			exceptionModel.setClassExcecao(classExcecao.get());
		}
		if(numeroVersao.isPresent()) {
			exceptionModel.setNumerVersao(numeroVersao.get());
		}
		if(severity.isPresent()) {
			exceptionModel.setServerity(severity.get());
			boolean existe = false;
			for (EnumExceptionSeverity tv : EnumExceptionSeverity.values()) {
				if (tv.toString().equals(severity.get())) {
					existe = true;
					break;
				}
			}
			if (!existe) {
				throw new ValidationExceptionWithErrors(EnumException.EXCEPTION_SEVERITY);
			}
		}
		if(wikiHow.isPresent()) {
			exceptionModel.setWikiHow(wikiHow.get());
		}

		try {
			service.save(exceptionModel);
		} catch (Exception e) {
			throw new ValidationExceptionWithErrors(EnumException.UNKNOWN_ERROR);
		}

		return new ResponseEntity<String>("{ \"message\" : \"Registro salvo com Sucesso\"}", HttpStatus.OK);
	}

}
