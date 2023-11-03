package br.com.wendergalan.graphqlexample.resolvers;

import br.com.wendergalan.graphqlexample.model.entities.Department;
import br.com.wendergalan.graphqlexample.model.entities.Employee;
import br.com.wendergalan.graphqlexample.model.entities.Organization;
import br.com.wendergalan.graphqlexample.model.inputs.OrganizationInput;
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
import java.util.NoSuchElementException;

/**
 * graphql-example
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: OrganizationController.java
 * Criado por: Wender Galan
 * Data da criação: 01/11/2023
 * Observação:
 * ***********************************************
 */
@Controller
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationRepository organizationRepository;

    @MutationMapping
    public Organization newOrganization(@Argument OrganizationInput organization) {
        return organizationRepository.save(new Organization(null, organization.getName(), null, null));
    }

    @QueryMapping
    public Iterable<Organization> organizations() {
        return organizationRepository.findAll();
    }

    @QueryMapping
    public Organization organization(@Argument Integer id, DataFetchingEnvironment environment) {
        Specification<Organization> spec = byId(id);
        DataFetchingFieldSelectionSet selectionSet = environment.getSelectionSet();
        if (selectionSet.contains("employees"))
            spec = spec.and(fetchEmployees());
        if (selectionSet.contains("departments"))
            spec = spec.and(fetchDepartments());
        return organizationRepository.findOne(spec).orElseThrow(NoSuchElementException::new);
    }

    private Specification<Organization> fetchDepartments() {
        return (root, query, builder) -> {
            Fetch<Organization, Department> f = root.fetch("departments", JoinType.LEFT);
            Join<Organization, Department> join = (Join<Organization, Department>) f;
            return join.getOn();
        };
    }

    private Specification<Organization> fetchEmployees() {
        return (root, query, builder) -> {
            Fetch<Organization, Employee> f = root.fetch("employees", JoinType.LEFT);
            Join<Organization, Employee> join = (Join<Organization, Employee>) f;
            return join.getOn();
        };
    }

    private Specification<Organization> byId(Integer id) {
        return (root, query, builder) -> builder.equal(root.get("id"), id);
    }
}