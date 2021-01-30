package br.com.seguradora.cliente;

public class ClienteException extends RuntimeException{

    public ClienteException() {
        super();
    }

    public ClienteException(String message) {
        super(message);
    }
}
