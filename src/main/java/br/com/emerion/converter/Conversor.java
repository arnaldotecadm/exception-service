package br.com.emerion.converter;

import java.io.IOException;

public interface Conversor<T extends Object, T1> {

	public abstract T getModel(Object object) throws IOException;

	public abstract T1 getDTO(Object object) throws IOException;
}
