package br.com.emerion.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import br.com.emerion.model.ExceptionSummary;
import br.com.emerion.model.GraphByMonth;
import br.com.emerion.model.GraphGRouppedByType;
import br.com.emerion.model.GraphModel;
import br.com.emerion.service.ExceptionModelService;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping()
public class ExceptionController {

	@Autowired
	private ExceptionModelService service;

	@ApiOperation(value = "Recuperar todas as excecoes cadastradas no sistema.")
	@GetMapping(path = "/all")
	public Page<ExceptionModel> getAll(
			@PageableDefault(page = 0, size = 30, sort = "dataExcecao", direction = Direction.DESC) Pageable paginacao) {
		return service.getAll(paginacao);
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
	public ResponseEntity<String> addNew(@RequestParam("message") String message,
			@RequestParam("stack_trace") String stackTrace, @RequestParam("application") Optional<UUID> application,
			@RequestParam("user_name") Optional<String> userName,
			@RequestParam("tipo_versao") Optional<String> tipoVersao,
			@RequestParam("campo_controle") Optional<String> campoControle,
			@RequestParam("class_excecao") Optional<String> classExcecao,
			@RequestParam("numerp_versao") Optional<String> numeroVersao,
			@RequestParam("severity") Optional<String> severity, @RequestParam("wiki_how") Optional<String> wikiHow)
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

		if (campoControle.isPresent()) {
			exceptionModel.setCampoControle(campoControle.get());
		}
		if (classExcecao.isPresent()) {
			exceptionModel.setClassExcecao(classExcecao.get());
		}
		if (numeroVersao.isPresent()) {
			exceptionModel.setNumerVersao(numeroVersao.get());
		}
		if (severity.isPresent()) {
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
		if (wikiHow.isPresent()) {
			exceptionModel.setWikiHow(wikiHow.get());
		}

		try {
			service.save(exceptionModel);
		} catch (Exception e) {
			throw new ValidationExceptionWithErrors(EnumException.UNKNOWN_ERROR);
		}

		return new ResponseEntity<String>("{ \"message\" : \"Registro salvo com Sucesso\"}", HttpStatus.OK);
	}

	@GetMapping(value = "getGrouppedByMonth/{application}/{exceptionType}")
	public List<GraphModel> getGrouppedByMonth(@PathVariable("application") String application, @PathVariable("exceptionType") String exceptionType) {
		try {
			UUID.fromString(application);
		} catch (Exception ex) {
			return new ArrayList<>();
		}
		List<GraphModel> grouppedByWeek = this.service.getGrouppedByWeek(application, exceptionType);
		List<GraphModel> grouppedByWeekOrganized = new ArrayList<>();

		int mesAnt = -1;
		for (GraphModel g : grouppedByWeek) {
			if (mesAnt == -1) {
				mesAnt = g.getMonth();
			}
			if ((g.getMonth() - mesAnt) > 1) {
				grouppedByWeekOrganized.add(new GraphModel(g.getYear(), g.getWeek(), g.getMonth() - 1, 0, 0));
			}
			grouppedByWeekOrganized.add(g);
			mesAnt = g.getMonth();
		}

		if(grouppedByWeekOrganized.size() == 1) {
			GraphModel model = grouppedByWeekOrganized.get(0);
			int ano = model.getMonth() > 1 ? model.getYear() : model.getYear() - 1;
			int mes = model.getMonth() > 1 ? model.getMonth() - 1 : 12;
			
			grouppedByWeekOrganized.add(0, new GraphModel(ano, mes, 0, 0, 0));
		}
		return grouppedByWeekOrganized;
	}

	@GetMapping(value = "getAllPercentages/{application}")
	public List<GraphGRouppedByType> getAllPercentages(@PathVariable("application") String application) {
		try {
			UUID.fromString(application);
		} catch (Exception ex) {
			return new ArrayList<>();
		}
		return this.service.getPercentageException(application);
	}

	@GetMapping(value = "totalException")
	public List<ExceptionSummary> getTotalException() {
		return this.service.getTotalException();
	}

	@GetMapping(value = "getGrouppedByMonthV2/{application}/{typeLimit}")
	public List<GraphByMonth> getGrouppedByMonthV2(@PathVariable("application") String application,
			@PathVariable("typeLimit") int typeLimit) {

		try {
			UUID.fromString(application);
		} catch (Exception ex) {
			return new ArrayList<>();
		}
		List<String> typesByMonth = this.service.getAllExceptionTypesByMonth(application, typeLimit);
		List<GraphByMonth> graphByMonthList = new ArrayList<GraphByMonth>();
		for (String s : typesByMonth) {
			graphByMonthList.add(new GraphByMonth(s, this.getGrouppedByMonth(application, s)));
		}

		int maior = 0;
		List<GraphModel> graphModelListMaior = null;
		for (GraphByMonth g : graphByMonthList) {
			if (g.getGraphModelList().size() > maior) {
				maior = g.getGraphModelList().size();
				graphModelListMaior = g.getGraphModelList();
			}
		}

		for (GraphByMonth g : graphByMonthList) {
			if (g.getGraphModelList().size() == 1) {
				GraphModel gm = g.getGraphModelList().get(0);
				g.getGraphModelList().add(0, new GraphModel(gm.getYear(), gm.getMonth(), gm.getWeek(), 1, 0));
			}  {
				if (g.getGraphModelList().size() < maior) {
					for (GraphModel mMaior : graphModelListMaior) {
						boolean possui = false;
						for(GraphModel gm2 : g.getGraphModelList()) {
							if(gm2.getMonth() == mMaior.getMonth()) {
								possui = true;
							}
						}
						if(!possui) {							
							g.getGraphModelList().add(new GraphModel(mMaior.getYear(), mMaior.getMonth(), mMaior.getWeek(), mMaior.getDay(), 0));
						}
					}
				}
			}
		}

		return graphByMonthList;
	}

	@GetMapping(value = "top-trend-exception-name/{application}/{timePast}")
	public List<String> getTopTrendExceptionName(@PathVariable("application") String application,
			@PathVariable("timePast") int timePast) {
		try {
			UUID.fromString(application);
		} catch (Exception ex) {
			return new ArrayList<>();
		}
		return this.service.getTopTrendExceptionName(application, timePast);
	}

	@GetMapping(value = "top-trend-exception-detail/{application}/{timePast}")
	public List<GraphByMonth> getTopTrendExceptionDetail(@PathVariable("application") String application,
			@PathVariable("timePast") int timePast) {

		try {
			UUID.fromString(application);
		} catch (Exception ex) {
			return new ArrayList<>();
		}

		List<String> topTrend = this.service.getTopTrendExceptionName(application, timePast);

		List<GraphByMonth> graphByMonthList = new ArrayList<GraphByMonth>();
		for (String s : topTrend) {
			graphByMonthList.add(new GraphByMonth(s, this.service.getTopTrendExceptionDetail(application, timePast, s)));
		}

		return graphByMonthList;
	}

	List<String> listaExcecoesCompleta = new ArrayList<>(Arrays.asList(
			"ArrayIndexOutOfBoundsException", 
			"ArithmeticException",
			"ArrayStoreException", 
			"ClassCastException", 
			"IllegalArgumentException", 
			"IllegalMonitorStateException",
			"ClassNotFoundException", 
			"CloneNotSupportedException", 
			"IllegalAccessException", 
			"InstantiationException",
			"AbstractMethodError", 
			"NoSuchFieldError", 
			"StackOverflowError", 
			"ThreadDeath", 
			"UnknownError",
			"UnsatisfiedLinkError", 
			"VerifyError", 
			"VirtualMachineError"));
	
	List<String> listaExcecoesReduzida = new ArrayList<>(Arrays.asList( 
			"ArithmeticException",
			"ArrayStoreException", 
			"ClassCastException"));

	@PostMapping(value = "/generateData")
	ResponseEntity<String> gerarMassaDados(
			@RequestParam("usa_web_service") Boolean usaWebService,
			@RequestParam("data_inicial") Date dataInicialLong,
			@RequestParam("data_final") Date dataFinalLong,
			@RequestParam("qtd_registros") int qtdRegistros,
			@RequestParam("application") String application,
			@RequestParam("lista_exceptions_simples") Boolean tipoListaSimples
			) throws IOException {

		List<String> listagem = (tipoListaSimples) ? listaExcecoesReduzida : listaExcecoesCompleta;
		
		for (int i = 0; i < qtdRegistros; i++) {
			
			int randomNum = ThreadLocalRandom.current().nextInt(0, listagem.size());

			Calendar calendar = Calendar.getInstance();

			calendar.setTime(new Date(ThreadLocalRandom.current().nextLong(dataInicialLong.getTime(), dataFinalLong.getTime())));

			ExceptionModel exceptionModel = 
					new ExceptionModel(
							null, 
							(usaWebService) ? post(5, 45) : "",
							(usaWebService) ? post(50, 125) : "", 
							UUID.fromString(application),
							"DESENVOLVIMENTO", 
							"1.1.0", 
							"", 
							listagem.get(randomNum), 
							"Não Possui", 
							"ERROR", 
							calendar, 
							"");

			this.service.save(exceptionModel);

			System.out.println(String.format("Exceção de Nº%d salva com sucesso", i + 1));
		}

		return new ResponseEntity<String>("Massa de Dados Gerada com Sucesso!", HttpStatus.OK);

	}

	String post(int minPalavras, int maxPalavras) throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url("http://www.randomtext.me/api/lorem/p-1/" + minPalavras + "-" + maxPalavras).get().build();
		try (Response response = client.newCall(request).execute()) {
			return new JSONObject(response.body().string()).getString("text_out").replace("<p>", "").replace("</p>",
					"");
		}
	}

}
