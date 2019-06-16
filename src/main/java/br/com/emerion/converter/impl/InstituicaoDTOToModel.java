package br.com.emerion.converter.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.emerion.converter.Conversor;
import br.com.emerion.model.Instituicao;
import br.com.emerion.model.DTO.InstituicaoDTO;

public class InstituicaoDTOToModel implements Conversor<Instituicao, InstituicaoDTO> {

	@Override
	public Instituicao getModel(Object object) throws IOException {
		InstituicaoDTO inst = (InstituicaoDTO) object;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();

		if (inst == null) {
			return null;
		}

		Instituicao instituicao = new Instituicao();
		instituicao.setId(null);
		instituicao.setCpfCnpj(inst.getCpfcnpj());
		instituicao.setNome(inst.getEntidade());
		instituicao.setSigla(inst.getSigla());

		instituicao.setTelefone(inst.getTelefone());
		
		if (inst.getFundacao() != null) {
			try {
				c.setTime(sdf.parse(inst.getFundacao()));
				instituicao.setDataFundacao(c);
			} catch (ParseException e) {
				throw new br.com.emerion.exception.ParseException(e);

			}
		}
		instituicao.setHomePage(inst.getWebsite());

		return instituicao;
	}

	@Override
	public InstituicaoDTO getDTO(Object object) throws IOException {
		Instituicao inst = (Instituicao) object;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (inst == null) {
			return null;
		}

		InstituicaoDTO instituicao = new InstituicaoDTO();
		instituicao.setCpfcnpj(inst.getCpfCnpj());
		instituicao.setEntidade(inst.getNome());
		instituicao.setSigla(inst.getSigla());

		instituicao.setTelefone(inst.getTelefone());
		instituicao.setFundacao(sdf.format(inst.getDataFundacao().getTime()));
		instituicao.setWebsite(inst.getHomePage());

		return instituicao;
	}
}
