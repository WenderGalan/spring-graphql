package br.com.wendergalan.graphqlexample.configs;

import br.com.wendergalan.graphqlexample.filters.ComplexFilter;
import br.com.wendergalan.graphqlexample.filters.GenericFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

/**
 * graphql-example
 * Todos os direitos reservados ©
 * ***********************************************
 * Nome do arquivo: ComplexFilterSpecification.java
 * Criado por: Wender Galan
 * Data da criação: 01/11/2023
 * Observação:
 * ***********************************************
 */
public class ComplexFilterSpecification<T> {
    public Specification<T> buildSpecification(ComplexFilter filter) {
        return (root, query, builder) -> {
            Predicate predicate = null;

            for (GenericFilter condition : filter.getConditions()) {
                Specification<T> conditionSpec = (entityRoot, query2, criteriaBuilder) -> {
                    if ("EQUALS".equals(condition.getOperator())) {
                        return criteriaBuilder.equal(entityRoot.get(condition.getField()), condition.getValue());
                    } else if ("CONTAINS".equals(condition.getOperator())) {
                        return criteriaBuilder.like(entityRoot.get(condition.getField()), "%" + condition.getValue() + "%");
                    }
                    return null; // Adicione mais condições para outros operadores
                };

                if (predicate == null) {
                    predicate = Specification.where(conditionSpec).toPredicate(root, query, builder);
                } else {
                    if ("AND".equalsIgnoreCase(filter.getOperator())) {
                        predicate = builder.and(predicate, conditionSpec.toPredicate(root, query, builder));
                    } else if ("OR".equalsIgnoreCase(filter.getOperator())) {
                        predicate = builder.or(predicate, conditionSpec.toPredicate(root, query, builder));
                    }
                }
            }

            return predicate;
        };
    }
}