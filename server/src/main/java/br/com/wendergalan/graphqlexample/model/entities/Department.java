package br.com.wendergalan.graphqlexample.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * graphql-example
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: Department.java
 * Criado por: Wender Galan
 * Data da criação: 01/11/2023
 * Observação:
 * ***********************************************
 */
@Entity(name = "department")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Department {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "department")
    private Set<Employee> employees;
    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;
}

