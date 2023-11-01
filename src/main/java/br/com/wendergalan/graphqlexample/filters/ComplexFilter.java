package br.com.wendergalan.graphqlexample.filters;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * graphql-example
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: ComplexFilter.java
 * Criado por: Wender Galan
 * Data da criação: 01/11/2023
 * Observação:
 * ***********************************************
 */
@Data
public class ComplexFilter {
    private Set<GenericFilter> conditions;
    // AND OR EQUALS
    private String operator;
}
