package br.com.seguradora.apolice;

public class ApoliceException extends RuntimeException{
    public ApoliceException(String message) {
        super(message);
    }

    public ApoliceException(String message, Throwable cause) {
        super(message, cause);
    }
}
