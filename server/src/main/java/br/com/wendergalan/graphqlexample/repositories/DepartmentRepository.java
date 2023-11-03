package br.com.wendergalan.graphqlexample.repositories;

import br.com.wendergalan.graphqlexample.model.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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
public interface DepartmentRepository extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {
}
