package br.com.wendergalan.graphqlexample.model.inputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * graphql-example
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: DepartmentInput.java
 * Criado por: Wender Galan
 * Data da criação: 01/11/2023
 * Observação:
 * ***********************************************
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentInput {
    private String name;
    private Integer organizationId;
}
