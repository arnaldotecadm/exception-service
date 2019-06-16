package br.com.emerion.converter.impl;

import java.io.IOException;

import br.com.emerion.converter.Conversor;
import br.com.emerion.model.ExceptionModel;
import br.com.emerion.model.DTO.ExceptionDTO;

public class ExceptionDTOToModel implements Conversor<ExceptionModel, ExceptionDTO> {

	@Override
	public ExceptionModel getModel(Object object) throws IOException {
		ExceptionDTO exDTO = (ExceptionDTO) object;

		ExceptionModel ex = new ExceptionModel();
		ex.setUnitName(exDTO.getUnitname());
		ex.setClassName(exDTO.getClassname());
		ex.setComponentName(exDTO.getComponentname());
		ex.setImportantLine(exDTO.getLinharelevante());
		ex.setStackTrace(exDTO.getStacktrace());
		ex.setDetail(exDTO.getDetail());

		return ex;
	}

	@Override
	public ExceptionDTO getDTO(Object object) throws IOException {
		ExceptionModel exDTO = (ExceptionModel) object;

		ExceptionDTO ex = new ExceptionDTO();
		ex.setUnitname(exDTO.getUnitName());
		ex.setClassname(exDTO.getClassName());
		ex.setComponentname(exDTO.getComponentName());
		ex.setLinharelevante(exDTO.getImportantLine());
		ex.setStacktrace(exDTO.getStackTrace());
		ex.setDetail(exDTO.getDetail());

		return ex;
	}
}
