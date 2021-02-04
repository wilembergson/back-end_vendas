package io.github.wilembergson.vendas.rest;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {

	private List<String> errors;
	 
	public ApiErrors(List<String> errors) {
		this.errors = errors;
	}

	public ApiErrors(String mensagemErro) {
		this.errors = Arrays.asList(mensagemErro);
	}

	public List<String> getErrors() {
		return errors;
	}
}
