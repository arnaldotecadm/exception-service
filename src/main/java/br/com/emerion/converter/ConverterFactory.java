package br.com.emerion.converter;

import br.com.emerion.converter.impl.ExceptionDTOToModel;
import br.com.emerion.converter.impl.InstituicaoDTOToModel;
import br.com.emerion.exception.MethodNotImplemented;
import br.com.emerion.model.ExceptionModel;
import br.com.emerion.model.Instituicao;

public class ConverterFactory {

	private ConverterFactory() {
		/**
		 * Default constructor just to hide the public one
		 */
	}

	@SuppressWarnings("rawtypes")
	public static Conversor getConverter(Class<?> clazz) {

		if (clazz.isAssignableFrom(Instituicao.class)) {
			return new InstituicaoDTOToModel();

		} else if (clazz.isAssignableFrom(ExceptionModel.class)) {
			return new ExceptionDTOToModel();

		} else {
			throw new MethodNotImplemented("Converter not yet defined.");
		}
	}
}
