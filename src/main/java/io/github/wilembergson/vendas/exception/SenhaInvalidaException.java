package io.github.wilembergson.vendas.exception;

public class SenhaInvalidaException extends RuntimeException{

	public SenhaInvalidaException() {
		super("Senha invalida.");
	}
}
