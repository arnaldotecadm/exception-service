package br.com.emerion.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GraphByMonth {

	private String exceptionType;
	private List<GraphModel> graphModelList;
}
