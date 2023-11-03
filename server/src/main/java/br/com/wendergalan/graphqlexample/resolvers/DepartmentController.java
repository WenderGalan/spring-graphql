package br.com.wendergalan.graphqlexample.resolvers;

import br.com.wendergalan.graphqlexample.model.entities.Department;
import br.com.wendergalan.graphqlexample.model.entities.Employee;
import br.com.wendergalan.graphqlexample.model.entities.Organization;
import br.com.wendergalan.graphqlexample.model.inputs.DepartmentInput;
import br.com.wendergalan.graphqlexample.repositories.DepartmentRepository;
import br.com.wendergalan.graphqlexample.repositories.OrganizationRepository;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * graphql-example
 * ***********************************************
 * Nome do arquivo: DepartmentQueryResolver.java
 * Criado por: Wender Galan
 * Data da criação: 01/11/2023
 * Observação:
 * ***********************************************
 */
@Controller
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentRepository departmentRepository;
    private final OrganizationRepository organizationRepository;

    @MutationMapping
    public Department newDepartment(@Argument DepartmentInput department) {
        Organization organization = organizationRepository.findById(department.getOrganizationId()).get();
        return departmentRepository.save(new Department(null, department.getName(), null, organization));
    }

    @QueryMapping
    public Iterable<Department> departments(DataFetchingEnvironment environment) {
        DataFetchingFieldSelectionSet s = environment.getSelectionSet();
        if (s.contains("employees") && !s.contains("organization"))
            return departmentRepository.findAll(fetchEmployees());
        else if (!s.contains("employees") && s.contains("organization"))
            return departmentRepository.findAll(fetchOrganization());
        else if (s.contains("employees") && s.contains("organization"))
            return departmentRepository.findAll(fetchEmployees().and(fetchOrganization()));
        else
            return departmentRepository.findAll();
    }

    @QueryMapping
    public Department department(@Argument Integer id, DataFetchingEnvironment environment) {
        Specification<Department> spec = byId(id);
        DataFetchingFieldSelectionSet selectionSet = environment.getSelectionSet();
        if (selectionSet.contains("employees"))
            spec = spec.and(fetchEmployees());
        if (selectionSet.contains("organization"))
            spec = spec.and(fetchOrganization());
        return departmentRepository.findOne(spec).orElseThrow(NoSuchElementException::new);
    }

    private Specification<Department> fetchOrganization() {
        return (root, query, builder) -> {
            Fetch<Department, Organization> f = root.fetch("organization", JoinType.LEFT);
            Join<Department, Organization> join = (Join<Department, Organization>) f;
            return join.getOn();
        };
    }

    private Specification<Department> fetchEmployees() {
        return (root, query, builder) -> {
            Fetch<Department, Employee> f = root.fetch("employees", JoinType.LEFT);
            Join<Department, Employee> join = (Join<Department, Employee>) f;
            return join.getOn();
        };
    }

    private Specification<Department> byId(Integer id) {
        return (root, query, builder) -> builder.equal(root.get("id"), id);
    }
}