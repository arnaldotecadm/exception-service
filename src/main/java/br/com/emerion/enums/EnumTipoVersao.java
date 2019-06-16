package br.com.emerion.enums;

public enum EnumTipoVersao {
	DESENVOLVIMENTO("Ambiente de desenvolvimento da aplica��o, sujeito a todos os tipos de bugs."),
	SNAPSHOT("Ambiente um pouco mais est�vel, geralmente pela equipe de testes."),
	HOMOLOGACAO("Ambiente est�vel, j� tendo passado por testes. Serve como uma vers�o de valida��o e para a realiza��o de testes mais rigorosos(Estresse, Carga, etc)."),
	PRODUCAO("Ambiente seguro e livre de erros. Pelo menos te�ricamente.");

	private final String label;

	private EnumTipoVersao(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
}
