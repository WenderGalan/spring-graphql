package br.com.wendergalan.graphqlexample.model.inputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * graphql-example
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: OrganizationInput.java
 * Criado por: Wender Galan
 * Data da criação: 01/11/2023
 * Observação:
 * ***********************************************
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationInput {
    private String name;
}
