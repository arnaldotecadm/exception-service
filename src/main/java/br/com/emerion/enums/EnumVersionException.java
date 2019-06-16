package br.com.emerion.enums;

import org.springframework.http.HttpStatus;

public enum EnumVersionException {
	
	VERSAO_JA_EXISTENTE(HttpStatus.CONFLICT, "Vers�o informada j� existente. Por favor informar uma vers�o diferente."), 
	VERSAO_INFORMADA_INCORRETAMENTE(HttpStatus.BAD_REQUEST, "N�o foi poss�vel recuperar a vers�o. Por favor informar a vers�o no formato: \"9.9.9.9\""), 
	INFORMAR_ARQUIVO_DOWNLOAD(HttpStatus.BAD_REQUEST, "Se o download n�o for se referir � vers�o completa, por favor especificar o arquivo a ser baixado."),
	ARQUIVO_NAO_ENCONTRADO(HttpStatus.NOT_FOUND, "Erro ao tentar processar requisi��o; Recurso n�o encontrado"),
	
	MANIFESTO_MAL_FORMATADO(HttpStatus.NOT_FOUND, "Erro converter o arquivo de MANIFESTO. Arquivo mal formatado, por favor consutar o manual para maiores detalhes sobre formata��o"),
	MANIFESTO_INFORMADO_INCORRETAMENTE(HttpStatus.NOT_FOUND, "Erro converter o arquivo de MANIFESTO. Arquivo informado n�o condiz com arquivos enviados."),
	
	NUMERO_MAXIMO_ARQUIVOS(HttpStatus.METHOD_NOT_ALLOWED, "O servi�o pode receber no m�ximo dois scripts. Leia o manual para maiores detalhes."),
	
	ERRO_DESCONHECIDO(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao tentar processar a requisi��o. Motivo desconhecido"), 
	
	NENHUM_ARQUIVO_ADICIONADO(HttpStatus.METHOD_NOT_ALLOWED, "Nenhum arquivo foi adicionado para atualiza��o. Por favor verifique"), 
	SCRIPT_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST,"Arquivo de script com o mesmo nome j� enviado anteriormente para o mesmo tipo de vers�o."), 
	
	SCRIPT_TYPE_NOT_ACCEPTABLE(HttpStatus.BAD_REQUEST, "Tipo de arquivo SQL n�o � aceito, favor enviar os scripts de banco no formato SQL"),
	
	APP_TYPE_NOT_ACCEPTABLE(HttpStatus.BAD_REQUEST, "Tipo de Aplica��o n�o � aceito, favor enviar apenas execut�veis");
	
	private final String label;
	private HttpStatus httpStatus;
	
	private EnumVersionException(HttpStatus httpStatus, String label) {
		this.httpStatus = httpStatus;
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}
}
