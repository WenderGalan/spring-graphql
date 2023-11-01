package br.com.wendergalan.graphqlexample.filters;

import lombok.Data;

/**
 * graphql-example
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: GenericFilter.java
 * Criado por: Wender Galan
 * Data da criação: 01/11/2023
 * Observação:
 * ***********************************************
 */
@Data
public class GenericFilter {
    private String field;
    private String value;
    // > < >= <= = !=
    private String operator;
}
