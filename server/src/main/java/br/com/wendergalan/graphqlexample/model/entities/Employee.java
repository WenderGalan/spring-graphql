package br.com.wendergalan.graphqlexample.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * graphql-example
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: Employee.java
 * Criado por: Wender Galan
 * Data da criação: 01/11/2023
 * Observação:
 * ***********************************************
 */
@Entity(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Integer id;
    private String firstName;
    private String lastName;
    private String position;
    private int salary;
    private int age;
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;
    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;
}
