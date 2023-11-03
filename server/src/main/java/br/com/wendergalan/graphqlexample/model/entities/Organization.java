package br.com.wendergalan.graphqlexample.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * graphql-example
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: Organization.java
 * Criado por: Wender Galan
 * Data da criação: 01/11/2023
 * Observação:
 * ***********************************************
 */
@Entity(name = "organization")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Organization {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "organization")
    private Set<Department> departments;
    @OneToMany(mappedBy = "organization")
    private Set<Employee> employees;
}

