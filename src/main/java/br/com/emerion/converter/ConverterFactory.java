package br.com.emerion.converter;

import br.com.emerion.converter.impl.ExceptionDTOToModel;
import br.com.emerion.exception.MethodNotImplemented;
import br.com.emerion.model.ExceptionModel;

public class ConverterFactory {

	private ConverterFactory() {
		/**
		 * Default constructor just to hide the public one
		 */
	}

	@SuppressWarnings("rawtypes")
	public static Conversor getConverter(Class<?> clazz) {

		if (clazz.isAssignableFrom(ExceptionModel.class)) {
			return new ExceptionDTOToModel();

		} else {
			throw new MethodNotImplemented("Converter not yet defined.");
		}
	}
}
