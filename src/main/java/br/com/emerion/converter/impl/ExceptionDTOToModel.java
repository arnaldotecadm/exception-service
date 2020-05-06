package br.com.emerion.converter.impl;

import java.io.IOException;

import br.com.emerion.converter.Conversor;
import br.com.emerion.model.ExceptionModel;
import br.com.emerion.model.dto.ExceptionDto;

public class ExceptionDTOToModel implements Conversor<ExceptionModel, ExceptionDto> {

	@Override
	public ExceptionModel getModel(Object object) throws IOException {
		ExceptionDto exDTO = (ExceptionDto) object;

		ExceptionModel ex = new ExceptionModel();
		ex.setStackTrace(exDTO.getStacktrace());

		return ex;
	}

	@Override
	public ExceptionDto getDTO(Object object) throws IOException {
		ExceptionModel exDTO = (ExceptionModel) object;

		ExceptionDto ex = new ExceptionDto();
		ex.setStacktrace(exDTO.getStackTrace());

		return ex;
	}
}
