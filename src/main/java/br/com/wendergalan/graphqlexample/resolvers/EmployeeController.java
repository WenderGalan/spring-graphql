package br.com.wendergalan.graphqlexample.resolvers;

import br.com.wendergalan.graphqlexample.model.entities.Department;
import br.com.wendergalan.graphqlexample.model.entities.Employee;
import br.com.wendergalan.graphqlexample.model.entities.Organization;
import br.com.wendergalan.graphqlexample.model.inputs.EmployeeInput;
import br.com.wendergalan.graphqlexample.repositories.DepartmentRepository;
import br.com.wendergalan.graphqlexample.repositories.EmployeeRepository;
import br.com.wendergalan.graphqlexample.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.NoSuchElementException;

/**
 * graphql-example
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: EmployeeController.java
 * Criado por: Wender Galan
 * Data da criação: 01/11/2023
 * Observação:
 * ***********************************************
 */
@Controller
@RequiredArgsConstructor
public class EmployeeController {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;

    @QueryMapping
    public Iterable<Employee> employees() {
        return employeeRepository.findAll();
    }

    @QueryMapping
    public Employee employee(@Argument Integer id) {
        return employeeRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @MutationMapping
    public Employee newEmployee(@Argument EmployeeInput employee) {
        Department department = departmentRepository.findById(employee.getDepartmentId()).get();
        Organization organization = organizationRepository.findById(employee.getOrganizationId()).get();
        return employeeRepository.save(new Employee(null, employee.getFirstName(), employee.getLastName(),
                employee.getPosition(), employee.getAge(), employee.getSalary(),
                department, organization));
    }

//    @QueryMapping
//    public Iterable<Employee> employeesWithFilter(@Argument EmployeeFilter filter) {
//        Specification<Employee> spec = null;
//        if (filter.getSalary() != null)
//            spec = bySalary(filter.getSalary());
//        if (filter.getAge() != null)
//            spec = (spec == null ? byAge(filter.getAge()) : spec.and(byAge(filter.getAge())));
//        if (filter.getPosition() != null)
//            spec = (spec == null ? byPosition(filter.getPosition()) :
//                    spec.and(byPosition(filter.getPosition())));
//        if (spec != null)
//            return employeeRepository.findAll(spec);
//        else
//            return employeeRepository.findAll();
//    }
//
//    private Specification<Employee> bySalary(FilterField filterField) {
//        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("salary"));
//    }
//
//    private Specification<Employee> byAge(FilterField filterField) {
//        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("age"));
//    }
//
//    private Specification<Employee> byPosition(FilterField filterField) {
//        return (root, query, builder) -> filterField.generateCriteria(builder, root.get("position"));
//    }
}