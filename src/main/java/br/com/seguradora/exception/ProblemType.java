package br.com.seguradora.exception;

import lombok.Getter;

@Getter
public enum ProblemType {
    DADOS_INVALIDOS("Dados inválidos"),
    RECURSO_NAO_ENCONTRADO("Recurso não encontrado");
    private String title;

    ProblemType(String title) {
        this.title = title;
    }
}